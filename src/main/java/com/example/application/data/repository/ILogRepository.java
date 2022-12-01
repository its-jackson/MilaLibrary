package com.example.application.data.repository;

import com.example.application.data.entity.Log;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ILogRepository extends JpaRepository<Log, UUID> {
}
