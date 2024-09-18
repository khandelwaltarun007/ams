package com.javalabs.service;

import java.util.List;

import com.javalabs.dto.CreateOptionalWfoRequest;
import com.javalabs.dto.CreateOptionalWfoResponse;
import com.javalabs.model.OptionalWfo;

public interface IOptionalWfoService {

	CreateOptionalWfoResponse createOptionalWfo(CreateOptionalWfoRequest createOptionalWfoRequest);

	List<OptionalWfo> getOptionalWfoByYear(int year);

	List<OptionalWfo> getOptionalWfoFromDate(int day, int month, int year);

}
