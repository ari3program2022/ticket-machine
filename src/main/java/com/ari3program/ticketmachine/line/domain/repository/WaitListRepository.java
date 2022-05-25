package com.ari3program.ticketmachine.line.domain.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ari3program.ticketmachine.line.domain.model.WaitList;

@Repository
public interface WaitListRepository extends JpaRepository<WaitList, Long> {
	
	@Query("SELECT a FROM WaitList a WHERE a.storeId = :storeId and a.reserveDate = :reserveDate and a.customerId = :customerId")
    List<WaitList> myFindWaitList(
            @Param("storeId") int storeId, @Param("reserveDate") Date reserveDate, @Param("customerId") String customerId);

}
