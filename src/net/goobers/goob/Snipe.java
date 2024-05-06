package net.goobers.goob;
import java.lang.reflect.Array;
import java.util.*;
import net.dv8tion.jda.api.*;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.Channel;

public class Snipe {
	public static final int MAX_HISTORY_PER_CHANNEL = 100;
	public static final int MAX_DELETED_PER_CHANNEL = 10;

	private static ArrayList<SnipeChannel> snipeChannels = new ArrayList<>();

	public static boolean clearMessages(Channel channel) {
		for (SnipeChannel sc : snipeChannels) {
			if (sc.getChannel().equals(channel)) {
				return sc.clearMessages();
			}
		}

		return false;
	}

	public static boolean deleteChannel(Channel channel) {
		for (SnipeChannel sc : snipeChannels) {
			if (sc.getChannel().equals(channel)) {
				return snipeChannels.remove(sc);
			}
		}

		return false;
	}

	public static boolean removeMessage(Channel channel, int index) {
		for (SnipeChannel sc : snipeChannels) {
			return sc.removeMessage(index);
		}

		return false;
	}

	public static void appendMessage(Message msg) {
		Channel channel = msg.getChannel();
		for (SnipeChannel s : snipeChannels) {
			if (s.getChannel().equals(channel)) {
				s.appendMessage(msg);
				return;
			}
		}

		SnipeChannel sc = new SnipeChannel(channel, MAX_DELETED_PER_CHANNEL, MAX_HISTORY_PER_CHANNEL);
		sc.appendMessage(msg);
		snipeChannels.add(sc);
	}

	public static void appendDeletedMessage(Channel channel, long messageId) {
		for (SnipeChannel sc : snipeChannels) {
			if (sc.getChannel().equals(channel)) {
				sc.appendDeletedMessage(messageId);
				return;
			}
		}

		System.out.println("No snipe channel found for channel " + channel.getId());
	}

	public static Message snipeMessage(Channel channel, int index) {
		for (SnipeChannel sc : snipeChannels) {
			if (sc.getChannel().equals(channel)) {
				return sc.snipeMessage(index);
			}
		}

		return null;
	}

	public static ArrayList<Message> getMessages(Channel channel) {
		for (SnipeChannel sc : snipeChannels) {
			if (sc.getChannel().equals(channel)) {
				return sc.getMessages();
			}
		}

		return null;
	}

	public static void deleteEmptySnipeChannels() {
		for (int i = 0; i < snipeChannels.size(); ++i) {
			SnipeChannel sc = snipeChannels.get(i);
			if (sc.getMessages().isEmpty()) {
				snipeChannels.remove(i);
				--i;
			}
		}
	}
}