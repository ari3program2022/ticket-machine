package com.ari3program.ticketmachine.line.app.controller;


import java.util.Arrays;
import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.ari3program.ticketmachine.line.domain.model.StoreMst;
import com.ari3program.ticketmachine.line.domain.service.storemst.StoreMstService;
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

	@Autowired
	private WaitListService waitListService;
	
	@Autowired
	private StoreMstService storeMstService;

	@Value("${line.bot.chenelid}")
	String bot_id;

	@EventMapping
    public void handleTextMessageEvent(MessageEvent<TextMessageContent> event) throws Exception {
		
		StoreMst storeMst = storeMstService.findMyStoreMst(bot_id);
        
		TextMessageContent message = event.getMessage();
        String userId = event.getSource().getUserId();
        log.info("Got userId from userId:{}: event:{}", userId, event);

        handleTextContent(event.getReplyToken(), event, message, userId, storeMst);

    }

    private void handleTextContent(String replyToken, Event event, TextMessageContent message, String userId, StoreMst storeMst)
            throws Exception {
    	
    	String text = message.getText();
    	log.info("Got text message from replyToken:{} text:{} emojis:{}", replyToken, text,
    			message.getEmojis());
    	
    	//各行をkeyとvalueに分解
    	HashMap<String, String> messageMap = new HashMap<String, String>();
    	Arrays.asList(text.split("\n"))
    	.forEach(s ->{
        	String[] key_value = s.split(":");
        	String key = key_value[0];
        	String value = key_value[1];
        	messageMap.put(key, value);
        	log.info("split text message into key:{} value:{} ", key , value);
        } );
    	
        switch (messageMap.get("処理内容")) {
            case "発券処理": {
            	waitListService.register(userId,messageMap,storeMst);
            }
        }
    }

}
