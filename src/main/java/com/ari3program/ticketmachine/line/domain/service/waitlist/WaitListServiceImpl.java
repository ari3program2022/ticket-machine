package com.ari3program.ticketmachine.line.domain.service.waitlist;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
	public void register(String userId, HashMap<String, String> messageMap, int store_id) {
		
		Date today = new Date();
		
		WaitList waitList = new WaitList();
		waitList.setCustomer_id(userId);
		waitList.setAmount(Integer.parseInt(messageMap.get("人数")));
		waitList.setStore_id(store_id);
		waitList.setReserve_date(today);
		waitList.setStatus(WaitListStatus.WAIT);
		
		List<WaitList> myWaitList = waitListRepository.myFindWaitList(store_id, today, userId);
		if(myWaitList.size() == 0) {
			waitListRepository.save(waitList);
		}else {
			log.info("既に発券済みです。store_id:{} reserve_date:{} userId:{}",store_id,today,today);
		}
	}
}
