package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.models.TicketStatus;

@Repository
public interface TicketStatusRepository extends JpaRepository<TicketStatus, Integer>{

}
