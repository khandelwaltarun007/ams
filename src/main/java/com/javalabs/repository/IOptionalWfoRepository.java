package com.javalabs.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.javalabs.model.OptionalWfo;

public interface IOptionalWfoRepository extends JpaRepository<OptionalWfo, Long> {
	
	List<OptionalWfo> findByDateBetween(LocalDate startDate, LocalDate endDate);

}
