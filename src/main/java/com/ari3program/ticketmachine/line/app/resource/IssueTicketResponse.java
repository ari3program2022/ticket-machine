package com.ari3program.ticketmachine.line.app.resource;

import static java.util.Arrays.asList;

import java.net.URI;
import java.util.function.Supplier;
import com.ari3program.ticketmachine.line.domain.model.WaitList;
import com.linecorp.bot.model.action.MessageAction;
import com.linecorp.bot.model.message.FlexMessage;
import com.linecorp.bot.model.message.flex.component.Box;
import com.linecorp.bot.model.message.flex.component.Button;
import com.linecorp.bot.model.message.flex.component.Button.ButtonStyle;
import com.linecorp.bot.model.message.flex.component.Image;
import com.linecorp.bot.model.message.flex.component.Image.ImageSize;
import com.linecorp.bot.model.message.flex.component.Spacer;
import com.linecorp.bot.model.message.flex.component.Text;
import com.linecorp.bot.model.message.flex.component.Text.TextWeight;
import com.linecorp.bot.model.message.flex.container.Bubble;
import com.linecorp.bot.model.message.flex.unit.FlexAlign;
import com.linecorp.bot.model.message.flex.unit.FlexFontSize;
import com.linecorp.bot.model.message.flex.unit.FlexLayout;
import com.linecorp.bot.model.message.flex.unit.FlexMarginSize;

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
        final Box footerBlock = createFooterBlock();
        
        final Bubble bubble =
                Bubble.builder()
                      .header(headerBlock)
                      .hero(heroBlock)
                      .body(bodyBlock)
                      .footer(footerBlock)
                      .build();

        return new FlexMessage("ALT", bubble);
	}
	

	private Box createHeaderBlock() {
		if(newFlg == false) {
			final Text title =
	                Text.builder()
	                    .text("???????????????????????????")
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
                    .text("?????????")
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
                            .flex(2)
                            .build()
                ))
                .build();
        
        final Box amount = Box
                .builder()
                .layout(FlexLayout.BASELINE)
                .contents(asList(
                        Text.builder()
                            .text("??????")
                            .color("#aaaaaa")
                            .flex(1)
                            .build(),
                        Text.builder()
                            .text(String.valueOf(waitList.getAmount() + "??????"))
                            .color("#666666")
                            .size(FlexFontSize.XXL)
                            .flex(2)
                            .build()
                ))
                .build();
        
        final Box order = Box
                .builder()
                .layout(FlexLayout.BASELINE)
                .contents(asList(
                        Text.builder()
                            .text("?????????")
                            .color("#aaaaaa")
                            .flex(1)
                            .build(),
                        Text.builder()
                            .text(String.valueOf(waitAmount) + "??????")
                            .color("#666666")
                            .size(FlexFontSize.XXL)
                            .flex(2)
                            .build()
                ))
                .build();
        
        return Box.builder()
                  .layout(FlexLayout.VERTICAL)
                  .contents(asList(number,amount, order))
                  .build();
    }

	private Box createFooterBlock() {
		
		final Spacer spacer = Spacer.builder().size(FlexMarginSize.SM).build();
        final Button callAction = Button
                .builder()
                .style(ButtonStyle.SECONDARY)
                .action(new MessageAction( "???????????????", "????????????:???????????????"))
                .build();
		
        return Box.builder()
                .layout(FlexLayout.VERTICAL)
                .spacing(FlexMarginSize.SM)
                .contents(asList(spacer, callAction))
                .build();
	}


}
