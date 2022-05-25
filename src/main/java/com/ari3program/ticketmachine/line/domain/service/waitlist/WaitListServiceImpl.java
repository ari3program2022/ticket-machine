package com.ari3program.ticketmachine.line.domain.service.waitlist;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import org.springframework.stereotype.Service;

import com.ari3program.ticketmachine.line.constant.WaitListStatus;
import com.ari3program.ticketmachine.line.domain.model.StoreMst;
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
	public void register(String userId, HashMap<String, String> messageMap, StoreMst storeMst) {
		
		Date today = new Date();
		int store_id = storeMst.getId();
		
		WaitList waitList = new WaitList();
		waitList.setCustomerId(userId);
		waitList.setAmount(Integer.parseInt(messageMap.get("人数")));
		waitList.setStoreId(store_id);
		waitList.setReserveDate(today);
		waitList.setStatus(WaitListStatus.WAIT);
		
		//同一店×来店日×ユーザーで発券済みかを確認。
		List<WaitList> myWaitList = waitListRepository.myFindWaitList(store_id, today, userId);
		if(myWaitList.size() == 0) {
			waitListRepository.save(waitList);
		}else {
			log.info("既に発券済みです。store_id:{} reserve_date:{} userId:{}",store_id,today,today);
			throw new WaitListFoundException("既に発券済みです。store_id:"+ store_id +" reserve_date:"+today+" userId:"+ userId); 
		}
	}
}
