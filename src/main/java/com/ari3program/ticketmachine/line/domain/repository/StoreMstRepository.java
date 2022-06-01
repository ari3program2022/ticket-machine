package com.ari3program.ticketmachine.line.domain.repository;

import java.sql.Time;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ari3program.ticketmachine.line.domain.model.StoreMst;

@Repository
public interface StoreMstRepository  extends JpaRepository<StoreMst, Integer>{
	
	List<StoreMst> findByChannelId(String channelId);
	

	@Query("SELECT a FROM StoreMst a WHERE a.id = :id and :currentTime between a.openTime and a.closeTime")
    List<StoreMst> isOpenStoreList(
            @Param("id") int id, @Param("currentTime") Time currentTime);
	
	default boolean isOpenStore(int storeId, Time currentTime) {
		List<StoreMst> openStoreList = isOpenStoreList(storeId, currentTime);
		
		if(openStoreList.size() == 0) {
			return false;
		}else {
			return true;			
		}
	}

}
