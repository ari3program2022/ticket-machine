package com.ari3program.ticketmachine.line.app.resource;

import static java.util.Arrays.asList;

import java.net.URI;
import java.util.function.Supplier;
import com.ari3program.ticketmachine.line.domain.model.WaitList;
import com.linecorp.bot.model.message.FlexMessage;
import com.linecorp.bot.model.message.flex.component.Box;
import com.linecorp.bot.model.message.flex.component.Image;
import com.linecorp.bot.model.message.flex.component.Image.ImageSize;
import com.linecorp.bot.model.message.flex.component.Text;
import com.linecorp.bot.model.message.flex.component.Text.TextWeight;
import com.linecorp.bot.model.message.flex.container.Bubble;
import com.linecorp.bot.model.message.flex.unit.FlexAlign;
import com.linecorp.bot.model.message.flex.unit.FlexFontSize;
import com.linecorp.bot.model.message.flex.unit.FlexLayout;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IssueTicketResponse implements Supplier<FlexMessage> {
	
	private WaitList waitList;
	
	private int waitAmount;
	
	private boolean newFlg;
	

	@Override
	public FlexMessage get() {
		
		final Box headerBlock = createHeaderBlock();
		
        final Image heroBlock =
                Image.builder()
                     .url(URI.create("https://thumb.ac-illust.com/e7/e7f7fddc608d85e79a0f0e965ef7507c_t.jpeg"))
                     .size(ImageSize.FULL_WIDTH)
                     .build();

        final Box bodyBlock = createBodyBlock();
        final Bubble bubble =
                Bubble.builder()
                      .header(headerBlock)
                      .hero(heroBlock)
                      .body(bodyBlock)
                      .build();

        return new FlexMessage("ALT", bubble);
	}
	
	private Box createHeaderBlock() {
		if(newFlg == false) {
			final Text title =
	                Text.builder()
	                    .text("既に発券済みです。")
	                    .build();
			
			return Box.builder()
	                  .layout(FlexLayout.VERTICAL)
	                  .backgroundColor("#fff3cd")
	                  .contents(asList(title))
	                  .build();
		}else {
			return null;
		}
		
	}
	
	private Box createBodyBlock() {
        final Text title =
                Text.builder()
                    .text("整理券")
                    .size(FlexFontSize.XXL)
                    .color("#666666")
                    .weight(TextWeight.BOLD)
                    .align(FlexAlign.CENTER)
                    .build();

        final Box number = createNumberBox();

        
        return Box.builder()
                  .layout(FlexLayout.VERTICAL)
                  .contents(asList(title, number))
                  .build();
    }
	
	private Box createNumberBox() {
        final Box number = Box
                .builder()
                .layout(FlexLayout.BASELINE)
                .contents(asList(
                        Text.builder()
                            .text("No.")
                            .color("#aaaaaa")
                            .flex(1)
                            .build(),
                        Text.builder()
                            .text(String.valueOf(waitList.getReserveNo()))
                            .color("#666666")
                            .size(FlexFontSize.XXXXXL)
                            .flex(5)
                            .build()
                ))
                .build();
        
        final Box amount = Box
                .builder()
                .layout(FlexLayout.BASELINE)
                .contents(asList(
                        Text.builder()
                            .text("待ち状況")
                            .color("#aaaaaa")
                            .flex(1)
                            .build(),
                        Text.builder()
                            .text(String.valueOf(waitAmount) + "組待ち")
                            .color("#666666")
                            .size(FlexFontSize.XXL)
                            .flex(2)
                            .build()
                ))
                .build();
        
        return Box.builder()
                  .layout(FlexLayout.VERTICAL)
                  .contents(asList(number,amount))
                  .build();
    }



}
