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

import lombok.Data;

@Data
public class CancelResultResponse implements Supplier<FlexMessage> {
	
	@Override
	public FlexMessage get() {
		
		final Image heroBlock =
                Image.builder()
                     .url(URI.create("https://4.bp.blogspot.com/-_FZcmANBzeE/UOFJ5p4US_I/AAAAAAAAKCs/_Ts6tvheN3E/s400/bunbougu_gomibako.png"))
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
                            .text("キャンセルできました。")
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
