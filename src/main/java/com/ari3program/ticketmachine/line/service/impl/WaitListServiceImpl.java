package com.ari3program.ticketmachine.line.service.impl;

import com.ari3program.ticketmachine.line.entity.WaitList;
import com.ari3program.ticketmachine.line.repository.WaitListRepository;
import com.ari3program.ticketmachine.line.service.WaitListService;

public class WaitListServiceImpl implements WaitListService {

	private WaitListRepository waitListRepository;

	@Override
	public void register(String userId) {

		WaitList waitList = new WaitList();
		waitList.setCustomer_id(userId);
		waitListRepository.save(waitList);
	}

}
