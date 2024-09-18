package com.javalabs.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import com.javalabs.model.DayOfWeek;
import com.javalabs.model.WfoDay;
import com.javalabs.repository.IWfoDayRepository;
import com.javalabs.service.IWfoDayService;

@Service
public class WfoDayServiceImpl implements IWfoDayService {

	@EventListener(ApplicationReadyEvent.class)
	public void postContructMethod() {
		WfoDay monday = WfoDay.builder().days(DayOfWeek.MONDAY).wfo(false).build();
		WfoDay tuesday = WfoDay.builder().days(DayOfWeek.TUESDAY).wfo(false).build();
		WfoDay wednesday = WfoDay.builder().days(DayOfWeek.WEDNESDAY).wfo(false).build();
		WfoDay thursday = WfoDay.builder().days(DayOfWeek.THURSDAY).wfo(false).build();
		WfoDay friday = WfoDay.builder().days(DayOfWeek.FRIDAY).wfo(false).build();
		WfoDay saturday = WfoDay.builder().days(DayOfWeek.SATURDAY).wfo(false).build();
		WfoDay sunday = WfoDay.builder().days(DayOfWeek.SUNDAY).wfo(false).build();
		wfoDayRepository.save(sunday);
		wfoDayRepository.save(monday);
		wfoDayRepository.save(tuesday);
		wfoDayRepository.save(wednesday);
		wfoDayRepository.save(thursday);
		wfoDayRepository.save(friday);
		wfoDayRepository.save(saturday);
	}
	
	@Autowired
	private IWfoDayRepository wfoDayRepository;
	
	@Override
	public WfoDay createWfo(WfoDay day) {
		WfoDay wfoDay = wfoDayRepository.findByDays(day.getDays()).get(0);
		if(null!=wfoDay) {
			return wfoDay;
		}else {
			return wfoDayRepository.save(day);
		}
	}

	@Override
	public List<WfoDay> getAllWfoDays() {
		return wfoDayRepository.findAll();
	}

	@Override
	public WfoDay getWfoDayByName(DayOfWeek dayOfWeek) {
		return wfoDayRepository.findByDays(dayOfWeek).get(0);
	}

	@Override
	public WfoDay updateWfoDay(WfoDay day) {
		WfoDay wfoDay = wfoDayRepository.findByDays(day.getDays()).get(0);
		wfoDay.setWfo(day.isWfo());
		return wfoDayRepository.save(wfoDay);
	}

}
