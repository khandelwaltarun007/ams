package com.javalabs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.javalabs.model.AttendanceCalculation;
import com.javalabs.model.User;

@Repository
public interface IAttendanceCalculationRepository extends JpaRepository<AttendanceCalculation, Long> {
    AttendanceCalculation findByUser(User user);
}