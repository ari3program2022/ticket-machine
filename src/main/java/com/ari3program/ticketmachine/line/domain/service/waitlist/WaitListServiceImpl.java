package com.ari3program.ticketmachine.line.domain.service.waitlist;

import org.springframework.stereotype.Service;

import com.ari3program.ticketmachine.line.domain.model.WaitList;
import com.ari3program.ticketmachine.line.domain.repository.WaitListRepository;

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
		waitList.setStore_id(1);//ストアマスタ実装までの暫定対応
		waitList.setCustomer_id(userId);
		waitList.setAmount(2);//人数
		waitListRepository.save(waitList);
	}

}
