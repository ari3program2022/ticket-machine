package com.ari3program.ticketmachine.line.app.resource;

import static java.util.Arrays.asList;

import java.util.function.Supplier;

import com.linecorp.bot.model.action.MessageAction;
import com.linecorp.bot.model.message.FlexMessage;
import com.linecorp.bot.model.message.flex.component.Box;
import com.linecorp.bot.model.message.flex.component.Button;
import com.linecorp.bot.model.message.flex.component.Separator;
import com.linecorp.bot.model.message.flex.component.Spacer;
import com.linecorp.bot.model.message.flex.component.Text;
import com.linecorp.bot.model.message.flex.component.Text.TextWeight;
import com.linecorp.bot.model.message.flex.container.Bubble;
import com.linecorp.bot.model.message.flex.unit.FlexFontSize;
import com.linecorp.bot.model.message.flex.unit.FlexLayout;
import com.linecorp.bot.model.message.flex.unit.FlexMarginSize;

import lombok.Data;

@Data
public class SelectAmountResponse implements Supplier<FlexMessage> {
	
	@Override
	public FlexMessage get() {
		
		final Box bodyBlock = createBodyBlock();
		final Box footerBlock = createFooterBlock();
		
		final Bubble bubble =
                Bubble.builder()
                      .body(bodyBlock)
                      .footer(footerBlock)
                      .build();

        return new FlexMessage("ALT", bubble);
	}


	private Box createBodyBlock() {
		
		final Text title =
                Text.builder()
                    .text("人数を選択してください")
                    .size(FlexFontSize.LG)
                    .weight(TextWeight.BOLD)
                    .build();
		
		return Box.builder()
                .layout(FlexLayout.VERTICAL)
                .contents(asList(title))
                .build();
	}	
	
	private Box createFooterBlock() {
		final Spacer spacer = Spacer.builder().size(FlexMarginSize.SM).build();
		
		Button[] buttons = new Button[11];
		for(int i = 1; i < 11; i++){			
			Button callAction = Button
					.builder()
					.action(new MessageAction( i+"名様", "処理内容:発券処理\n人数:"+i))
					.build();
			
			buttons[i]=callAction;
		}
		final Separator separator = Separator.builder().build();
		
		return Box.builder()
                .layout(FlexLayout.VERTICAL)
                .spacing(FlexMarginSize.SM)
                .contents(asList(spacer, buttons[1], separator, buttons[2], separator, buttons[3], separator, buttons[4], separator, buttons[5], separator, buttons[6], separator, buttons[7], separator, buttons[8], separator, buttons[9], separator, buttons[10]))
                .build();
	}

}
