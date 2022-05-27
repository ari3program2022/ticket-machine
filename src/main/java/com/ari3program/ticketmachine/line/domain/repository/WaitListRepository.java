package com.ari3program.ticketmachine.line.domain.repository;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ari3program.ticketmachine.line.domain.model.MaxReserveNo;
import com.ari3program.ticketmachine.line.domain.model.WaitList;

@Repository
public interface WaitListRepository extends JpaRepository<WaitList, Long> {
	
	@Query("SELECT a FROM WaitList a WHERE a.storeId = :storeId and a.reserveDate = :reserveDate and a.customerId = :customerId")
    List<WaitList> findMyWaitList(
            @Param("storeId") int storeId, @Param("reserveDate") Date reserveDate, @Param("customerId") String customerId);
	
	@Query("SELECT a.storeId, a.reserveDate, a.customerId, MAX(a.reserveNo) as sum FROM WaitList a WHERE a.storeId = :storeId and a.reserveDate = :reserveDate and a.customerId = :customerId")
	List<MaxReserveNo> getMaxReserveNoObjects(
            @Param("storeId") int storeId, @Param("reserveDate") Date reserveDate, @Param("customerId") String customerId);
	
	default int getMaxReserveNo(int storeId, Date reserveDate, String customerId) {
		List<MaxReserveNo> maxReserveNoList = getMaxReserveNoObjects(storeId, reserveDate, customerId);
		
		if(Objects.isNull(maxReserveNoList.get(0).getSum())) {
			return 1;
		}else {
			return maxReserveNoList.get(0).getSum();			
		}
	}

}
