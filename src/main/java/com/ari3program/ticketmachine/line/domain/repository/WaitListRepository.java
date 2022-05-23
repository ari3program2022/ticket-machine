package com.ari3program.ticketmachine.line.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ari3program.ticketmachine.line.domain.model.WaitList;

@Repository
public interface WaitListRepository extends JpaRepository<WaitList, Long> {

}
