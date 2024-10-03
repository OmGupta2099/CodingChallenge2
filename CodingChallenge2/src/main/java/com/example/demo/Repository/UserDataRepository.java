package com.example.demo.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Entity.UserData;

public interface UserDataRepository extends JpaRepository<UserData, String> {
	Optional<UserData> findByUserId(String userId);
}
