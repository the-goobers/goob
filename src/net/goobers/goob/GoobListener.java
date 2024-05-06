package net.goobers.goob;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.channel.ChannelDeleteEvent;
import net.dv8tion.jda.api.hooks.*;
import net.dv8tion.jda.api.events.message.*;

import java.util.ArrayList;

public class GoobListener extends ListenerAdapter {
	@Override
	public void onChannelDelete(ChannelDeleteEvent event) {
		Snipe.deleteChannel(event.getChannel());
	}

	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
		if (event.getAuthor().isBot()) return;

		Message message = event.getMessage();
		if (message.getContentRaw().startsWith(CommandHandler.prefix)) {
			CommandHandler.handleCommand(event);
		}
		Snipe.appendMessage(message);
	}



	@Override
	public void onMessageDelete(MessageDeleteEvent event) {
		Snipe.appendDeletedMessage(event.getChannel(), event.getMessageIdLong());
	}
}
