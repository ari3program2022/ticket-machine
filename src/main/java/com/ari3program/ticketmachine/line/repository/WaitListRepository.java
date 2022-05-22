package com.ari3program.ticketmachine.line.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ari3program.ticketmachine.line.entity.WaitList;

@Repository
public interface WaitListRepository extends JpaRepository<WaitList, Long> {

}
