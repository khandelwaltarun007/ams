package com.javalabs.repository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.javalabs.model.ApprovalStatus;
import com.javalabs.model.Attendance;
import com.javalabs.model.User;

@Repository
public interface IAttendanceRepository extends JpaRepository<Attendance, Long> {
    List<Attendance> findByUser(User user);
    List<Attendance> findByDate(LocalDate date);
    List<Attendance> findByUserAndStatus(User user, ApprovalStatus status);
	List<Attendance> findByManagerUsernameAndStatus(String username, ApprovalStatus status);
	Optional<Attendance> findByUserAndDate(User user, LocalDate date);
	
	@Query("SELECT a FROM Attendance a JOIN FETCH a.user")
    List<Attendance> findAllWithUserEagerly();
}