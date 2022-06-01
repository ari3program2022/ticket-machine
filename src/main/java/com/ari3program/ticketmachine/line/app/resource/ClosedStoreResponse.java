package com.ari3program.ticketmachine.line.app.resource;

import static java.util.Arrays.asList;

import java.net.URI;
import java.util.function.Supplier;

import com.ari3program.ticketmachine.line.domain.model.StoreMst;
import com.linecorp.bot.model.message.FlexMessage;
import com.linecorp.bot.model.message.flex.component.Box;
import com.linecorp.bot.model.message.flex.component.Image;
import com.linecorp.bot.model.message.flex.component.Text;
import com.linecorp.bot.model.message.flex.component.Image.ImageSize;
import com.linecorp.bot.model.message.flex.component.Text.TextWeight;
import com.linecorp.bot.model.message.flex.container.Bubble;
import com.linecorp.bot.model.message.flex.unit.FlexFontSize;
import com.linecorp.bot.model.message.flex.unit.FlexLayout;
import com.linecorp.bot.model.message.flex.unit.FlexOffsetSize;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClosedStoreResponse implements Supplier<FlexMessage> {
	
	private StoreMst storeMst;
	
	@Override
	public FlexMessage get() {
		
        final Image heroBlock =
                Image.builder()
                     .url(URI.create("https://thumb.ac-illust.com/a1/a13e1838ef0ff66740dfadbcc7c295d4_t.jpeg"))
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

        final Text title =
                Text.builder()
                    .text("現在は、受付できません。")
                    .size(FlexFontSize.LG)
                    .weight(TextWeight.BOLD)
                    .offsetBottom(FlexOffsetSize.XL)
                    .build();

        final Box openClose = createOpenCloseBox();

        
        return Box.builder()
                  .layout(FlexLayout.VERTICAL)
                  .contents(asList(title, openClose))
                  .build();
	}

	private Box createOpenCloseBox() {
		final Box open = Box
                .builder()
                .layout(FlexLayout.BASELINE)
                .contents(asList(
                        Text.builder()
                            .text("受付開始")
                            .color("#aaaaaa")
                            .flex(1)
                            .build(),
                        Text.builder()
                            .text(String.valueOf(storeMst.getOpenTime()))
                            .color("#666666")
                            .size(FlexFontSize.XXL)
                            .flex(2)
                            .build()
                ))
                .build();
		
		final Box close = Box
                .builder()
                .layout(FlexLayout.BASELINE)
                .contents(asList(
                        Text.builder()
                            .text("受付終了")
                            .color("#aaaaaa")
                            .flex(1)
                            .build(),
                        Text.builder()
                            .text(String.valueOf(storeMst.getCloseTime()))
                            .color("#666666")
                            .size(FlexFontSize.XXL)
                            .flex(2)
                            .build()
                ))
                .build();
		
		return Box.builder()
                .layout(FlexLayout.VERTICAL)
                .contents(asList(open,close))
                .build();
	}

}
