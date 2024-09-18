package com.javalabs.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.javalabs.model.PasswordHistory;

public interface IPasswordHistoryRepository extends JpaRepository<PasswordHistory, Long> {

	List<PasswordHistory> findByUsername(String username);

}
