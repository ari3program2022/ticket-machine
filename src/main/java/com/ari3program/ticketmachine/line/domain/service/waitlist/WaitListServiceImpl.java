package com.ari3program.ticketmachine.line.domain.service.waitlist;

import java.util.Date;
import java.util.HashMap;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ari3program.ticketmachine.line.constant.WaitListStatus;
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
	public void register(String userId, HashMap<String, String> messageMap) {
		
		
		WaitList waitList = new WaitList();
		waitList.setCustomer_id(userId);
		waitList.setAmount(Integer.parseInt(messageMap.get("人数")));
		waitList.setStore_id(Integer.parseInt(messageMap.get("店舗ID")));
		Date today = new Date();
		waitList.setReserve_date(today);
		waitList.setStatus(WaitListStatus.WAIT);
		
		Optional<WaitList> waitListOpt = waitListRepository.findByStoreCustomerDate(1, userId, today);
		if(waitListOpt.isPresent()) {
			log.info("既に発券済みです。store_id:{} userId:{} reserve_date:{}",1,userId,today);
		}else {
			waitListRepository.save(waitList);
		}
	}
}
