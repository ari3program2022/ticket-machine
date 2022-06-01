package com.ari3program.ticketmachine.line.app.resource;

import static java.util.Arrays.asList;

import java.net.URI;
import java.util.function.Supplier;

import com.linecorp.bot.model.message.FlexMessage;
import com.linecorp.bot.model.message.flex.component.Box;
import com.linecorp.bot.model.message.flex.component.Image;
import com.linecorp.bot.model.message.flex.component.Text;
import com.linecorp.bot.model.message.flex.component.Image.ImageSize;
import com.linecorp.bot.model.message.flex.container.Bubble;
import com.linecorp.bot.model.message.flex.unit.FlexFontSize;
import com.linecorp.bot.model.message.flex.unit.FlexLayout;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WaitAmountResponse implements Supplier<FlexMessage> {
	
	private int amount;
	
	@Override
	public FlexMessage get() {
		
		final Image heroBlock =
                Image.builder()
                     .url(URI.create("https://thumb.ac-illust.com/0e/0ec6f43f41bc75c3eddbfb8c7bb15ecd_t.jpeg"))
                     .size(ImageSize.FULL_WIDTH)
                     .build();
		
		final Box bodyBlock = createBodyBlock();
		
		final Bubble bubble =
                Bubble.builder()
                      .hero(heroBlock)
                      .body(bodyBlock)
                      .build();

        return new FlexMessage("ALT", bubble);
	}

	private Box createBodyBlock() {
		
		final Box amountBox = Box
                .builder()
                .layout(FlexLayout.BASELINE)
                .contents(asList(
                        Text.builder()
                            .text("待ち状況")
                            .color("#aaaaaa")
                            .flex(1)
                            .build(),
                        Text.builder()
                            .text(String.valueOf(amount) + "組待ち")
                            .color("#666666")
                            .size(FlexFontSize.XXL)
                            .flex(2)
                            .build()
                ))
                .build();
        
        return Box.builder()
                  .layout(FlexLayout.VERTICAL)
                  .contents(asList(amountBox))
                  .build();
		
		
	}

}
