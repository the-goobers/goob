package net.goobers.goob;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.Channel;
import net.dv8tion.jda.api.entities.channel.middleman.GuildChannel;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.hooks.*;
import net.dv8tion.jda.api.events.message.*;

public class CommandHandler {
	public static final String prefix = "./";

	private static boolean isCommand(String content, String expect) {
		return content.startsWith(prefix + expect);
	}

	public static void handleCommand(MessageReceivedEvent event) {
		String content = event.getMessage().getContentRaw();
		if (isCommand(content, "help")) {
			event.getChannel().sendMessage("Commands: [* = superuser only]\n```" + prefix + "snipeall*\n" + prefix + "snipe <optional channel> [index]\n" + prefix + "clearsnipe*\n" + prefix + "removesnipe* [index]```").queue();
		} else if (isCommand(content, "snipeall")) {
			Super.snipeAll(event);
		} else if (isCommand(content, "snipe")) {
			String[] parts = content.split(" ");

			try {
				int index = 0;
				GuildChannel channel = event.getGuildChannel();
				if (parts.length > 1) {
					if (parts[1].contains("<#")) {
						if (!Auth.isSuperUser(event.getGuild().retrieveMemberById(event.getAuthor().getId()).complete())) {
							event.getChannel().sendMessage("You are not authorized to use this snipe with channel argument.").queue();
							return;
						}

						channel = event.getJDA().getGuildChannelById(parts[1].substring(2, parts[1].length() - 1));
						if (channel == null) {
							event.getChannel().sendMessage("Invalid channel.").queue();
							return;
						}

						if (parts.length > 2) {
							index = Integer.parseInt(parts[2]);
						}

						try {
							if (!event.getGuild().getMember(event.getAuthor()).hasPermission((GuildChannel) channel, Permission.MESSAGE_HISTORY, Permission.MESSAGE_SEND)) {
								event.getChannel().sendMessage("You do not have access to that channel.").queue();
								return;
							}
						} catch (Exception e) {
							event.getChannel().sendMessage("You do not have access to that channel.").queue();
							return;
						}
					} else {
						index = Integer.parseInt(parts[1]);
					}
				}

				Message msg = Snipe.snipeMessage(channel, index);
				if (msg == null) {
					event.getChannel().sendMessage("No message recorded at index " + index).queue();
				} else {
					String description = msg.getContentRaw();
					event.getChannel().sendMessage("Deleted message from " + msg.getAuthor().getName() + " (id: " + msg.getAuthor().getId() + ")").addEmbeds(new EmbedBuilder().setDescription(description).setAuthor(msg.getAuthor().getName(), msg.getAuthor().getAvatarUrl(), msg.getAuthor().getAvatarUrl()).setTimestamp(msg.getTimeCreated()).build()).queue();
				}
			} catch (NumberFormatException e) {
				e.printStackTrace();
				event.getChannel().sendMessage("Invalid index.").queue();
			}
		} else if (isCommand(content, "clearsnipe")) {
			Super.clearSnipes(event);
		} else if (isCommand(content, "removesnipe")) {
			Super.removeSnipe(event);
		}
	}
}
