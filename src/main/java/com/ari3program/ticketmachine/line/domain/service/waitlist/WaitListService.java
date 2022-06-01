package com.ari3program.ticketmachine.line.domain.service.waitlist;

import java.sql.Time;
import java.util.Date;
import java.util.HashMap;

import com.ari3program.ticketmachine.line.domain.model.StoreMst;
import com.linecorp.bot.model.message.Message;

public interface WaitListService {
	
	Message issueTicket(StoreMst storeMst, int store_id, Date today, Time currentTime, String userId, HashMap<String, String> messageMap);
	
	Message checkWaitAmount(StoreMst storeMst,int store_id, Date today, Time currentTime, String userId);

	Message cancelTicket(int store_id, Date today, String userId);
}
