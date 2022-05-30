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
import com.linecorp.bot.model.message.flex.unit.FlexLayout;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorMessageResponse implements Supplier<FlexMessage> {
	
	private String message;
	
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
		
		final Box info = Box
                .builder()
                .layout(FlexLayout.BASELINE)
                .contents(asList(
                        Text.builder()
                            .text(message)
                            .wrap(true)
                            .build()
                ))
                .build();
        
        return Box.builder()
                  .layout(FlexLayout.VERTICAL)
                  .contents(asList(info))
                  .build();
	}

}
