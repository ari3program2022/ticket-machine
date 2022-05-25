package com.ari3program.ticketmachine.line.domain.service.waitlist;

import java.util.HashMap;

import com.ari3program.ticketmachine.line.domain.model.StoreMst;

public interface WaitListService {

	void register(String userId, HashMap<String, String> messageMap, StoreMst storeMst);

}
