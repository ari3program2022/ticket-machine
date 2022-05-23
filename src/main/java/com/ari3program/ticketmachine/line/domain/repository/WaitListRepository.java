package com.ari3program.ticketmachine.line.domain.repository;

import java.util.Date;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ari3program.ticketmachine.line.domain.model.WaitList;

@Repository
public interface WaitListRepository extends JpaRepository<WaitList, Long> {
	
	@Query("SELECT a FROM WAIT_LIST a WHERE :store_id = a.store_id AND :customer_id = a.customer_id AND :reserve_date = a.reserve_date")
    Optional<WaitList> findByStoreCustomerDate(
            @Param("store_id") int store_id,@Param("customer_id") String customer_id,@Param("reserve_date") Date reserve_date);

}
