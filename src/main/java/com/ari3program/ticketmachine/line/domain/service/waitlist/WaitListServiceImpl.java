package com.ari3program.ticketmachine.line.domain.service.waitlist;

import java.sql.Time;
import java.util.Date;
import java.util.HashMap;
import java.util.Objects;

import org.springframework.stereotype.Service;

import com.ari3program.ticketmachine.line.app.resource.CancelResultResponse;
import com.ari3program.ticketmachine.line.app.resource.ClosedStoreResponse;
import com.ari3program.ticketmachine.line.app.resource.ErrorMessageResponse;
import com.ari3program.ticketmachine.line.app.resource.IssueTicketResponse;
import com.ari3program.ticketmachine.line.app.resource.WaitAmountResponse;
import com.ari3program.ticketmachine.line.constant.WaitListStatus;
import com.ari3program.ticketmachine.line.domain.model.StoreMst;
import com.ari3program.ticketmachine.line.domain.model.WaitList;
import com.ari3program.ticketmachine.line.domain.repository.WaitListRepository;
import com.ari3program.ticketmachine.line.domain.service.storemst.StoreMstService;
import com.linecorp.bot.model.message.Message;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class WaitListServiceImpl implements WaitListService {

	private WaitListRepository waitListRepository;
	
	private StoreMstService storeMstService;
	
	public WaitListServiceImpl(WaitListRepository waitListRepository) {
		this.waitListRepository = waitListRepository;
	}
	
	@Override
	public Message issueTicket(StoreMst storeMst, int store_id, Date today, Time currentTime, String userId,
			HashMap<String, String> messageMap) {
		
		//営業時間外の場合は、その旨メッセージを返す
		if(!storeMstService.isStoreOpen(storeMst, currentTime)) {
			log.info("営業時間外です。 現在時刻:{} 営業開始:{} 営業終了:{}",currentTime, storeMst.getOpenTime(), storeMst.getCloseTime());
			return new ClosedStoreResponse(storeMst).get();
		}
		
		//発券済みの場合は、その旨メッセージを返す
		WaitList myWaitList = waitListRepository.existsMyWaitList(store_id, today, userId);
		if(Objects.nonNull(myWaitList)) { 
			log.info("既に発券済みです。store_id:{} reserve_date:{} userId:{}", store_id, today, userId);
			int waitAmount = waitListRepository.getMyWaitAmount(myWaitList);
			return new IssueTicketResponse(myWaitList, waitAmount, false).get();
		}
		log.info("未発券です。store_id:{} reserve_date:{} userId:{}", store_id, today, userId);
		//整理券を発券する
		WaitList insertResult = register(store_id, today, userId, messageMap);
		if(Objects.isNull(insertResult.getId())) {
			log.warn("整理券の発券に失敗しました。store_id:{} reserve_date:{} userId:{} insertResult:{}", store_id, today, userId, insertResult);
			return new ErrorMessageResponse("申し訳ございません。\n整理券の発行に失敗しました。\nもう一度発券ボタンを押してください。").get();
		}else {
			log.info("整理券を発行しました。 waitList:{}", insertResult);
			int waitAmount = waitListRepository.getMyWaitAmount(insertResult);
			return new IssueTicketResponse(insertResult, waitAmount, true).get();
		}
	}

	@Override
	public Message checkWaitAmount(int store_id, Date today, String userId) {
		
		WaitList waitResult = waitListRepository.existsMyWaitList(store_id, today, userId);
		if(Objects.isNull(waitResult)) {
			//TOTALの待ち人数を取得する
			int amount = waitListRepository.getWaitAmount(store_id, today);
			return new WaitAmountResponse(amount).get();
		}else {
			//自分が呼ばれるまでの待ち人数を取得する
			int waitAmount = waitListRepository.getMyWaitAmount(waitResult);
			return new IssueTicketResponse(waitResult, waitAmount, true).get();
		}
	}


	@Override
	public Message cancelTicket(int store_id, Date today, String userId) {		
		WaitList cancelMyWaitList = waitListRepository.existsMyWaitList(store_id, today, userId);
		if(Objects.isNull(cancelMyWaitList)) {
			return new ErrorMessageResponse("キャンセル済み、\nもしくは未発券のため\nキャンセルできませんでした。").get();
		}else {
			log.info("チケットをキャンセルします。 waitList:{}", cancelMyWaitList);
			waitListRepository.cancelMyWaitList(cancelMyWaitList.getId());
			return new CancelResultResponse().get();
		}
	}
	
	private WaitList register(int store_id, Date today, String userId, HashMap<String, String> messageMap) {
		
		WaitList waitList = new WaitList();
		waitList.setStoreId(store_id);
		waitList.setReserveDate(today);
		waitList.setCustomerId(userId);
		waitList.setAmount(Integer.parseInt(messageMap.get("人数")));
		waitList.setStatus(WaitListStatus.WAIT);
		waitList.setReserveNo(waitListRepository.getMaxReserveNo(store_id, today)+1);
		
		return waitListRepository.save(waitList);
	}
	
	
	
}
