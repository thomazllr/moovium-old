package com.thomazllr.moovium.repository;

import com.thomazllr.moovium.model.Session;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SessionRepository extends JpaRepository<Session, UUID> {
}
