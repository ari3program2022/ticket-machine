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
	
	@Query("SELECT a FROM WaitList a WHERE a.store_id = :store_id and a.reserve_date = :reserve_date and a.customer_id = :customer_id")
    List<WaitList> myFindWaitList(
            @Param("store_id") int store_id, @Param("reserve_date") Date reserve_date, @Param("customer_id") String customer_id);

}
