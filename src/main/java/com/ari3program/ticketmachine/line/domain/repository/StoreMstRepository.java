package com.ari3program.ticketmachine.line.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ari3program.ticketmachine.line.domain.model.StoreMst;

@Repository
public interface StoreMstRepository  extends JpaRepository<StoreMst, Long>{
	
	List<StoreMst> findByChannelId(String channelId);

}
