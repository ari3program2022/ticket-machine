package com.ari3program.ticketmachine.line.service.impl;

import org.springframework.stereotype.Service;

import com.ari3program.ticketmachine.line.entity.WaitList;
import com.ari3program.ticketmachine.line.repository.WaitListRepository;
import com.ari3program.ticketmachine.line.service.WaitListService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class WaitListServiceImpl implements WaitListService {

	private WaitListRepository waitListRepository;

	public WaitListServiceImpl(WaitListRepository waitListRepository) {
		this.waitListRepository = waitListRepository;
	}

	@Override
	public void register(String userId) {

		WaitList waitList = new WaitList();
		waitList.setCustomer_id(userId);
		waitListRepository.save(waitList);
	}

}
