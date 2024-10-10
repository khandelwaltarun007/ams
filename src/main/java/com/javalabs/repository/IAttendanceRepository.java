package com.javalabs.repository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.javalabs.model.ApprovalStatus;
import com.javalabs.model.Attendance;
import com.javalabs.model.User;

@Repository
public interface IAttendanceRepository extends JpaRepository<Attendance, Long> {
	Page<Attendance> findByUser(User user, Pageable pageable);
	List<Attendance> findByUser(User user);
    Page<Attendance> findByDate(LocalDate date, Pageable pageable);
    Page<Attendance> findByUserAndStatus(User user, ApprovalStatus status, Pageable pageable);
	Page<Attendance> findByManagerUsernameAndStatus(String username, ApprovalStatus status, Pageable pageable);
	Optional<Attendance> findByUserAndDate(User user, LocalDate date);
	Optional<Attendance> findByUserAndDateAndStatus(User user, LocalDate date, ApprovalStatus status);
	
	@Query("SELECT a FROM Attendance a JOIN FETCH a.user")
    List<Attendance> findAllWithUserEagerly();
}