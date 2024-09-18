package com.javalabs.service.impl;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javalabs.dto.CreateOptionalWfoRequest;
import com.javalabs.dto.CreateOptionalWfoResponse;
import com.javalabs.model.OptionalWfo;
import com.javalabs.repository.IOptionalWfoRepository;
import com.javalabs.service.IOptionalWfoService;

@Service
public class OptionalWfoServiceImpl implements IOptionalWfoService {

	@Autowired
	private IOptionalWfoRepository optionalWfoRepository;
	
	@Override
	public CreateOptionalWfoResponse createOptionalWfo(CreateOptionalWfoRequest createOptionalWfoRequest) {
		OptionalWfo optionalWfo = new OptionalWfo();
		BeanUtils.copyProperties(createOptionalWfoRequest, optionalWfo);
		optionalWfo = optionalWfoRepository.save(optionalWfo);
		CreateOptionalWfoResponse optionalWfoResponse = new CreateOptionalWfoResponse();
		BeanUtils.copyProperties(optionalWfo, optionalWfoResponse);
		return optionalWfoResponse;
	}

	@Override
	public List<OptionalWfo> getOptionalWfoByYear(int year) {
		return null;
	}

	@Override
	public List<OptionalWfo> getOptionalWfoFromDate(int day, int month, int year) {
		YearMonth yearMonth = YearMonth.of(year, month);
		LocalDate startDate = yearMonth.atDay(day);
		LocalDate endDate = LocalDate.now();
		System.out.println("startDate : "+startDate+"\t endDate: "+endDate);
		System.out.println("optionalWfoRepository.findByDateBetween(startDate, endDate) : "+optionalWfoRepository.findByDateBetween(startDate, endDate));
		return optionalWfoRepository.findByDateBetween(startDate, endDate);
	}

}
