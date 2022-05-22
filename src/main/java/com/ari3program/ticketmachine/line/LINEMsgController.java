package com.ari3program.ticketmachine.line;


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

    @EventMapping
    public void handleTextMessageEvent(MessageEvent<TextMessageContent> event) throws Exception {
        TextMessageContent message = event.getMessage();
        final String userId = event.getSource().getUserId();

        log.info("Got text message from userId:{}: event:{}", userId, event);

        handleTextContent(event.getReplyToken(), event, message, userId);

    }

    private void handleTextContent(String replyToken, Event event, TextMessageContent content, String userId)
            throws Exception {
        final String text = content.getText();

        log.info("Got text message from replyToken:{}: text:{} emojis:{}", replyToken, text,
                 content.getEmojis());
        switch (text) {
            case "$発券処理$": {
            	waitListService.register(userId);
            }
        }
    }

}
