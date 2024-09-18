package com.javalabs.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.javalabs.dto.CreateOptionalWfoRequest;
import com.javalabs.dto.CreateOptionalWfoResponse;
import com.javalabs.model.OptionalWfo;
import com.javalabs.service.IOptionalWfoService;

@RestController
public class OptionalWfoResource {

	@Autowired
	private IOptionalWfoService optionalWfoService;
	
	@PostMapping("/optionalWfo")
	public ResponseEntity<CreateOptionalWfoResponse> createOptionalWfo(@RequestBody CreateOptionalWfoRequest createOptionalWfoRequest){
		return new ResponseEntity<>(optionalWfoService.createOptionalWfo(createOptionalWfoRequest),HttpStatus.CREATED);
	}
	
	@GetMapping("/optionalWfo/{day}/{month}/{year}")
	public ResponseEntity<List<OptionalWfo>> getOptionalWfos(@PathVariable("day") int day, @PathVariable("month") int month, @PathVariable("year") int year){
		return ResponseEntity.ok(optionalWfoService.getOptionalWfoFromDate(day, month, year));
	}
}
