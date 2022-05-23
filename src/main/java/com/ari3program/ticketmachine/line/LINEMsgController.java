package com.ari3program.ticketmachine.line;


import org.springframework.beans.factory.annotation.Autowired;

import com.ari3program.ticketmachine.line.service.WaitListService;
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

        handleTextContent(event.getReplyToken(), event, message, userId);

    }

    private void handleTextContent(String replyToken, Event event, TextMessageContent content, String userId)
            throws Exception {
        final String text = content.getText();

        log.info("Got text message from replyToken:{}: text:{} emojis:{}", replyToken, text,
                 content.getEmojis());
        switch (text) {
            case "$発券処理$": {

            	log.info("Got userId from userId:{}: event:{}", userId, event);
            	waitListService.register(userId);
            }
        }
    }

}
