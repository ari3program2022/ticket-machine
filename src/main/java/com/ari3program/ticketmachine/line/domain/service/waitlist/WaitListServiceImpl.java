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
	public WaitList register(int store_id, Date today, String userId, HashMap<String, String> messageMap) {
		
		WaitList waitList = new WaitList();
		waitList.setStoreId(store_id);
		waitList.setReserveDate(today);
		waitList.setCustomerId(userId);
		waitList.setAmount(Integer.parseInt(messageMap.get("人数")));
		waitList.setStatus(WaitListStatus.WAIT);
		waitList.setReserveNo(waitListRepository.getMaxReserveNo(store_id, today)+1);
		
		return waitListRepository.save(waitList);
	}

	@Override
	public WaitList existsMyWaitList(int store_id, Date today, String userId) {
		List<WaitList> myWaitList = waitListRepository.findMyWaitList(store_id, today, userId);

		//同一店×来店日×ユーザーで発券済みかを確認。
		if(myWaitList.size() == 0) {
			log.info("未発券です。store_id:{} reserve_date:{} userId:{}", store_id, today, userId);
			return null;
		}else {
			log.info("既に発券済みです。store_id:{} reserve_date:{} userId:{}", store_id, today, userId);
			return myWaitList.get(0);
		}
	}

	@Override
	public int getWaitAmount(WaitList waitList) {
		return waitListRepository.getWaitAmount(waitList);
	}

	@Override
	public void cancelMyWaitList(WaitList waitList) {
		waitListRepository.cancelMyWaitList(waitList.getId());
	}
	
	
	
	
}
