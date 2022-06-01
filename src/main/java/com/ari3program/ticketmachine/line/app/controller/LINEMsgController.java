package com.ari3program.ticketmachine.line.app.controller;

import static java.util.Collections.singletonList;

import java.sql.Time;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.ari3program.ticketmachine.line.app.resource.ErrorMessageResponse;
import com.ari3program.ticketmachine.line.app.resource.SelectAmountResponse;
import com.ari3program.ticketmachine.line.domain.model.StoreMst;
import com.ari3program.ticketmachine.line.domain.service.storemst.StoreMstService;
import com.ari3program.ticketmachine.line.domain.service.waitlist.WaitListService;
import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.ReplyMessage;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.response.BotApiResponse;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@LineMessageHandler
public class LINEMsgController {

	@Autowired
	private LineMessagingClient lineMessagingClient;

	@Autowired
	private WaitListService waitListService;

	@Autowired
	private StoreMstService storeMstService;

	@Value("${line.bot.channelId}")
	String channelId;

	@EventMapping
	public void handleTextMessageEvent(MessageEvent<TextMessageContent> event) throws Exception {

		String userId = event.getSource().getUserId();
		String replyToken = event.getReplyToken();
		log.info("Got data from MessageEvent ->userId:{} replyToken:{} event:{}", userId, replyToken, event);
		StoreMst storeMst = storeMstService.findMyStoreMst(channelId);
		int store_id = storeMst.getId();
		log.info("Got MY StoreMst from application.properties ->channelId:{} storeId:{}", channelId, store_id);
		Date today = new Date();
		@SuppressWarnings("deprecation") Time currentTime = new Time(today.getHours(), today.getMinutes(), today.getSeconds());
		Message response = null;
		
		//メッセージの各行をkeyとvalueに分解
		TextMessageContent message = event.getMessage();
		String text = message.getText();
		HashMap<String, String> messageMap = new HashMap<String, String>();
		Arrays.asList(text.split("\n")).forEach(s -> {
			String[] key_value = s.split(":");
			if(key_value.length==2) {
				String key = key_value[0];
				String value = key_value[1];				
				messageMap.put(key, value);
				log.info("split text message into key:{} value:{} ", key, value);
			}
		});
		
		
		if(Objects.nonNull(messageMap.get("処理内容"))) {
			switch (messageMap.get("処理内容")) {
			case "人数選択":
				response = new SelectAmountResponse().get();
				break;
			
			case "発券処理": 
				response = waitListService.issueTicket(storeMst, store_id, today, currentTime, userId, messageMap);
				break;
				
			case "キャンセル":
				response = waitListService.cancelTicket(store_id, today, userId);
				break;
				
			case "待ち時間確認":
				response = waitListService.checkWaitAmount(storeMst, store_id, today, currentTime, userId);
				break;
			default:
				response = new ErrorMessageResponse("こちらの処理は、\nサポートされておりません。\n処理内容：" + messageMap.get("処理内容")).get();
				break;	
			}
		}else {
			response = new ErrorMessageResponse("サポートされていない\nキーワードを受信しました。\n画面下部のリッチメニューから\nボタンを押してみてください。").get();
		}
		
		this.reply(replyToken, response);

	}

	private void reply(@NonNull String replyToken, @NonNull Message message) {
		reply(replyToken, singletonList(message));
	}

	private void reply(@NonNull String replyToken, @NonNull List<Message> messages) {
		reply(replyToken, messages, false);
	}

	private void reply(@NonNull String replyToken, @NonNull List<Message> messages, boolean notificationDisabled) {
		try {
			BotApiResponse apiResponse = lineMessagingClient
					.replyMessage(new ReplyMessage(replyToken, messages, notificationDisabled)).get();
			log.info("Sent messages: {}", apiResponse);
		} catch (InterruptedException | ExecutionException e) {
			throw new RuntimeException(e);
		}
	}

}
