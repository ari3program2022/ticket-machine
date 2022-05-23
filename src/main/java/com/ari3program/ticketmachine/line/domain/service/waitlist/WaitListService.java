package com.ari3program.ticketmachine.line.domain.service.waitlist;

import java.util.HashMap;

public interface WaitListService {

	void register(String userId, HashMap<String, String> messageMap);

}
