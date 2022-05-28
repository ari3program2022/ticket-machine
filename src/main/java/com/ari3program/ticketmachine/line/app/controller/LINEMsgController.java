package com.ari3program.ticketmachine.line.app.controller;

import static java.util.Collections.singletonList;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.ari3program.ticketmachine.line.domain.model.StoreMst;
import com.ari3program.ticketmachine.line.domain.model.WaitList;
import com.ari3program.ticketmachine.line.domain.service.storemst.StoreMstService;
import com.ari3program.ticketmachine.line.domain.service.waitlist.WaitListService;
import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.ReplyMessage;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;
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
			case "発券処理": 
				//発券済みかをチェック
				WaitList myWaitList = waitListService.existsMyWaitList(store_id, today, userId);
				if(Objects.nonNull(myWaitList)) { 
					this.replyText(
							replyToken,
							"既に発券済みです。整理券番号:" + myWaitList.getReserveNo()
							);
					break;
				}
				//整理券を発券
				WaitList insertResult = waitListService.register(store_id, today, userId, messageMap);
				if(Objects.isNull(insertResult.getId())) {
					this.replyText(
							replyToken,
							"申し訳ございません。整理券の発行に失敗しました。もう一度発券ボタンを押してください。"
							);
				}else {
					this.replyText(
							replyToken,
							"整理券を発券しました。整理券番号:" + insertResult.getReserveNo()
							);
				}
				break;
				
			default:
				log.info("non support process-> 処理内容:{}", messageMap.get("処理内容"));
				this.replyText(
						replyToken,
						"処理内容：" + messageMap.get("処理内容")
						);
				break;	
			}
		}else {
			log.info("non support keywords-> replyToken:{} text:{}", replyToken, text);
			this.replyText(
					replyToken,
					"サポートされていないキーワードを受信しました。画面下部のリッチメニューからボタンを押してみてください。"
					);
		}

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

	private void replyText(@NonNull String replyToken, @NonNull String message) {
		if (replyToken.isEmpty()) {
			throw new IllegalArgumentException("replyToken must not be empty");
		}
		if (message.length() > 1000) {
			message = message.substring(0, 1000 - 2) + "……";
		}
		this.reply(replyToken, new TextMessage(message));
	}

}
