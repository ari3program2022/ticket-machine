package com.ari3program.ticketmachine.line.domain.service.storemst;

import java.sql.Time;

import com.ari3program.ticketmachine.line.domain.model.StoreMst;

public interface StoreMstService {
	
	StoreMst findMyStoreMst(String bot_id);
	
	boolean isStoreOpen(StoreMst storeMst, Time currentTime);

}
