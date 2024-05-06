package net.goobers.goob;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.ArrayList;

public class Super {
	public static void snipeAll(MessageReceivedEvent event) {
		if (!Auth.isSuperUser(event.getAuthor().getId())) {
			event.getChannel().sendMessage("You are not authorized to use this command.").queue();
			return;
		}

		ArrayList<Message> messages = Snipe.getMessages(event.getChannel());
		if (messages != null) {
			if (!messages.isEmpty()) {
				StringBuilder builder = new StringBuilder();
				for (int i = messages.size() - 1; i >= 0; --i) {
					Message msg = messages.get(i);
					event.getChannel().sendMessage("Deleted message from " + msg.getAuthor().getName() + " (id: " + msg.getAuthor().getId() + ")").addEmbeds(new EmbedBuilder().setDescription(msg.getContentRaw()).setAuthor(msg.getAuthor().getName(), msg.getAuthor().getAvatarUrl(), msg.getAuthor().getAvatarUrl()).setTimestamp(msg.getTimeCreated()).build()).queue();
				}
				return;
			}
		}

		event.getChannel().sendMessage("No messages to snipe.").queue();
	}

	public static void clearSnipes(MessageReceivedEvent event) {
		if (!Auth.isSuperUser(event.getAuthor().getId())) {
			event.getChannel().sendMessage("You are not authorized to use this command.").queue();
			return;
		}

		String message = "No messages to clear.";
		if (Snipe.clearMessages(event.getChannel())) {
			message = "Cleared all sniped messages.";
		}

		event.getChannel().sendMessage(message).queue();
	}

	public static void removeSnipe(MessageReceivedEvent event) {
		if (!Auth.isSuperUser(event.getAuthor().getId())) {
			event.getChannel().sendMessage("You are not authorized to use this command.").queue();
			return;
		}

		String[] parts = event.getMessage().getContentRaw().split(" ");
		if (parts.length < 2) {
			event.getChannel().sendMessage("Usage: !removesnipe <index (most recent to least recent)>").queue();
			return;
		}

		try {
			int index = Integer.parseInt(parts[1]);
			String message = "No message to remove at index " + index;
			if (Snipe.removeMessage(event.getChannel(), index)) {
				message = "Removed sniped message at index " + index;
			}

			event.getChannel().sendMessage(message).queue();
		} catch (NumberFormatException e) {
			event.getChannel().sendMessage("Invalid index.").queue();
		}
	}
}
