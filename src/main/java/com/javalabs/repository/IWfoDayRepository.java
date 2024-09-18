package com.javalabs.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.javalabs.model.DayOfWeek;
import com.javalabs.model.WfoDay;

@Repository
public interface IWfoDayRepository extends JpaRepository<WfoDay, Long> {

	List<WfoDay> findByDays(DayOfWeek day);

}
