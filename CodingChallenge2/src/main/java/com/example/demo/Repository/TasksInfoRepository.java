package com.example.demo.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Entity.TasksInfo;

public interface TasksInfoRepository extends JpaRepository<TasksInfo, String> {
	Optional<TasksInfo> findByTaskId(String taskId);
}