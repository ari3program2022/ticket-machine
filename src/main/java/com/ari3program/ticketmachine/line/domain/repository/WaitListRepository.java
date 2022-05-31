package com.ari3program.ticketmachine.line.domain.repository;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ari3program.ticketmachine.line.domain.model.WaitList;

@Repository
public interface WaitListRepository extends JpaRepository<WaitList, Long> {
	
	@Query("SELECT a FROM WaitList a WHERE a.storeId = :storeId and a.reserveDate = :reserveDate and a.customerId = :customerId")
    List<WaitList> findMyWaitList(
            @Param("storeId") int storeId, @Param("reserveDate") Date reserveDate, @Param("customerId") String customerId);
	
	@Query("SELECT a FROM WaitList a WHERE a.storeId = :storeId and a.reserveDate = :reserveDate and a.status = 'WAIT' ORDER BY a.reserveNo ASC")
    List<WaitList> findTodayWaitList(
            @Param("storeId") int storeId, @Param("reserveDate") Date reserveDate);
	
	@Query("SELECT MAX(a.reserveNo) as sum FROM WaitList a WHERE a.storeId = :storeId and a.reserveDate = :reserveDate")
	Integer getMaxReserveNoObjects(
			@Param("storeId") int storeId, @Param("reserveDate") Date reserveDate);
	
	default int getWaitAmount(WaitList waitList) {
		List<WaitList> findTodayWaitList = findTodayWaitList(waitList.getStoreId(), waitList.getReserveDate());
		return findTodayWaitList.indexOf(waitList) + 1;
	}
	
	default int getMaxReserveNo(int storeId, Date reserveDate) {
		Integer maxReserveNo = getMaxReserveNoObjects(storeId, reserveDate);
		
		if(Objects.isNull(maxReserveNo)) {
			return 0;
		}else {
			return maxReserveNo;			
		}
	}

}
