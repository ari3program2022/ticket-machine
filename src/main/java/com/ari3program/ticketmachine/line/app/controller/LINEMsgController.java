package com.ari3program.ticketmachine.line.app.controller;


import java.util.Arrays;
import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;

import com.ari3program.ticketmachine.line.domain.service.waitlist.WaitListService;
import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@LineMessageHandler
public class LINEMsgController {

	private WaitListService waitListService;

	@Autowired
	public LINEMsgController(WaitListService waitListService) {
		this.waitListService = waitListService;
	}

    @EventMapping
    public void handleTextMessageEvent(MessageEvent<TextMessageContent> event) throws Exception {
        TextMessageContent message = event.getMessage();
        String userId = event.getSource().getUserId();
        log.info("Got userId from userId:{}: event:{}", userId, event);

        handleTextContent(event.getReplyToken(), event, message, userId);

    }

    private void handleTextContent(String replyToken, Event event, TextMessageContent content, String userId)
            throws Exception {
    	int store_id = 1;//店舗マスタ実装までの暫定対応
    	
    	//lineから送られたメッセージを取得
    	String message = content.getText();

    	log.info("Got text message from replyToken:{} text:{} emojis:{}", replyToken, message,
    			content.getEmojis());
    	
    	//各行をkeyとvalueに分解
    	HashMap<String, String> messageMap = new HashMap<String, String>();
    	Arrays.asList(message.split("\n"))
    	.forEach(s ->{
        	String[] key_value = s.split(":");
        	String key = key_value[0];
        	String value = key_value[1];
        	messageMap.put(key, value);
        	log.info("split text message into key:{} value:{} ", key , value);
        } );
    	
        switch (messageMap.get("処理内容")) {
            case "発券処理": {
            	waitListService.register(userId,messageMap,store_id);
            }
        }
    }

}
