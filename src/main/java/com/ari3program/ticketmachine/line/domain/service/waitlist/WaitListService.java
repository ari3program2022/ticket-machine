package com.ari3program.ticketmachine.line.domain.service.waitlist;

import java.util.Date;
import java.util.HashMap;

import com.ari3program.ticketmachine.line.domain.model.StoreMst;
import com.ari3program.ticketmachine.line.domain.model.WaitList;

public interface WaitListService {

	WaitList register(int store_id,Date today, String userId, HashMap<String, String> messageMap);
	
	WaitList existsMyWaitList(int store_id,Date today, String userId);

}
