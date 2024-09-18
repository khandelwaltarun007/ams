package com.javalabs.service;

import java.util.List;

import com.javalabs.model.DayOfWeek;
import com.javalabs.model.WfoDay;

public interface IWfoDayService {
	
	WfoDay createWfo(WfoDay day);
	List<WfoDay> getAllWfoDays();
	WfoDay getWfoDayByName(DayOfWeek dayOfWeek);
	WfoDay updateWfoDay(WfoDay day);

}
