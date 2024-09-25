package com.javalabs.service.impl;

import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.javalabs.dto.AttendanceCalculationResponse;
import com.javalabs.mapper.AttendanceMapper;
import com.javalabs.model.Attendance;
import com.javalabs.model.AttendanceCalculation;
import com.javalabs.model.OptionalWfo;
import com.javalabs.model.User;
import com.javalabs.repository.IAttendanceCalculationRepository;
import com.javalabs.repository.IAttendanceRepository;
import com.javalabs.repository.IUserRepository;
import com.javalabs.service.IAttendanceCalculationService;
import com.javalabs.service.IAttendanceService;
import com.javalabs.service.IOptionalWfoService;
import com.javalabs.service.IUserService;

@Service
public class AttendanceCalculationServiceImpl implements IAttendanceCalculationService {
	
	private static final int NUMBER_OF_WORKING_DAYS = 2;

	@Autowired
    private IAttendanceCalculationRepository attendanceCalculationRepository;
	
	@Autowired
	private IAttendanceService attendanceService;
	
	@Autowired
	private IOptionalWfoService optionalWfoService;
	
	@Autowired
	private IUserService userService;
	private List<String> absentDayCodes = List.of("SL","PL","CL");
	private List<String> presentDayCodes = List.of("WFO");

	@Autowired
	private IAttendanceRepository attendanceRepository;

	@Autowired
	private IUserRepository userRepository;
	
	@Override
    public AttendanceCalculationResponse calculateAttendance(Long userId) {
		User user = userService.findUserByUserId(userId);
		List<Attendance> attendances = getListOfAttendancesByUser(user);
		AttendanceCalculation attendanceCalculation = calcAttendance(user, attendances);
		AttendanceCalculationResponse attendanceCalculationResponse = new AttendanceCalculationResponse();
		BeanUtils.copyProperties(attendanceCalculation, attendanceCalculationResponse);
		attendanceCalculationResponse = AttendanceMapper.INSTANCE.mapToResponse(attendanceCalculation);
        return attendanceCalculationResponse;
    }
	
	private List<Attendance> getListOfAttendancesByUser(User user){
		return attendanceService.getAttendanceByUser(user);
	}

	@Override
    public List<AttendanceCalculationResponse> getAllAttendanceCalculationsResponse() {
		List<AttendanceCalculationResponse> attendanceCalculationResponse = new ArrayList<>();
		List<AttendanceCalculation> attendanceCalculation = attendanceCalculationRepository.findAll();
		BeanUtils.copyProperties(attendanceCalculation, attendanceCalculationResponse);
		attendanceCalculationResponse = AttendanceMapper.INSTANCE.mapToResponse(attendanceCalculation);
		return attendanceCalculationResponse;
    }
	
	/**
	 * This method is for scheduling the job for calculating the attendance for all users
	 * at specified CRON expression
	 */
	@Transactional(readOnly = true)
	//@Scheduled(cron = "*/5 * * * * *")
	public void refreshAttendanceCalculationForAllUsers() {
		List<Attendance> allAttendance = attendanceRepository.findAllWithUserEagerly();
		Map<User, List<Attendance>> attendanceByUser = allAttendance.stream()
				.collect(Collectors.groupingBy(attendance -> attendance.getUser(), Collectors.toList()));
		attendanceByUser.entrySet().stream().collect(
				Collectors.toMap(Map.Entry::getKey, entry -> calcAttendance(entry.getKey(), entry.getValue())));
	}
	
	/**
	 * This method is to calculate the attendance for user
	 * 
	 * @param user
	 * @param attendances
	 * @return AttendanceCalculation
	 */
	private AttendanceCalculation calcAttendance(User user, List<Attendance> attendances) {
		Double presentDays = getDaysUsingCodes(attendances, presentDayCodes);
		Double absentDays = getDaysUsingCodes(attendances, absentDayCodes);
		double yearPercentage = getYearPercentage(user, attendances, presentDays);
		AttendanceCalculation attendanceCalculation = attendanceCalculationRepository.findByUser(user);
		if(null == attendanceCalculation) {
			attendanceCalculation = new AttendanceCalculation();
			attendanceCalculation.setUser(user);
		}
		attendanceCalculation.setAbsenseDays(absentDays);
		attendanceCalculation.setPercentage(yearPercentage);
		attendanceCalculation.setWfoDays(presentDays);
		attendanceCalculation.setUpdateDate(LocalDate.now());
		attendanceCalculation = attendanceCalculationRepository.save(attendanceCalculation);
		return attendanceCalculation;
    }

	/**
	 * This method is to calculate the percentage of present in the year or after joining date
	 * 
	 * @param user
	 * @param attendances 
	 * @param presentDays
	 * @return Double
	 */
	private Double getYearPercentage(User user, List<Attendance> attendances, Double presentDays) {
		LocalDate userDateOfJoining = user.getDateOfJoining();
		LocalDate date = LocalDate.now();
		WeekFields weekFields = WeekFields.of(Locale.getDefault());
        LocalDate firstDateOfYear = LocalDate.of(date.getYear(), 1, 1);
        float weekNumber = 0;
        if(firstDateOfYear.isBefore(userDateOfJoining)) {
        	weekNumber = date.get(weekFields.weekOfWeekBasedYear()) - userDateOfJoining.get(weekFields.weekOfWeekBasedYear());
        	weekNumber = calculateAttendanceBasedOnOptionalWfo(userDateOfJoining, user, attendances, weekNumber);
		}else {
			weekNumber = date.get(weekFields.weekOfWeekBasedYear());
			weekNumber = calculateAttendanceBasedOnOptionalWfo(firstDateOfYear, user, attendances, weekNumber);
		}
		System.out.println("presentDays"+presentDays+"\t weekNumber"+weekNumber);
		double yearPercentage = (presentDays / (NUMBER_OF_WORKING_DAYS * (weekNumber - 1))) * 100;
		return yearPercentage;
	}
	
	private float calculateAttendanceBasedOnOptionalWfo(LocalDate date, User user, List<Attendance> attendances, float weekNumber) {
		AtomicReference<Float> numberOfWeeks = new AtomicReference<>(weekNumber);
		List<OptionalWfo> optionalWfoList = optionalWfoService.getOptionalWfoFromDate(date.getDayOfMonth(), date.getMonthValue(), date.getYear());
		List<LocalDate> attendanceDates = attendances.stream()
                .map(Attendance::getDate)
                .collect(Collectors.toList());
		
		for(OptionalWfo optionalWfo : optionalWfoList) {
			if(attendanceDates.contains(optionalWfo.getDate())) {
				numberOfWeeks.set(numberOfWeeks.get()-0.5f);
			}
		}
		return numberOfWeeks.get();
	}

	/**
	 * This method is to calculate total number of attendance on the basis of attendance type
	 * @param attendances
	 * @param codes
	 * @return Double
	 */
	private Double getDaysUsingCodes(List<Attendance> attendances, List<String> codes) {
		return (double)attendances.stream().filter(attendance -> codes.contains(attendance.getType())).toList().size();
	}
}