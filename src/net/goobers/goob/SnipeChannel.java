package net.goobers.goob;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.Channel;

import java.util.ArrayList;

public class SnipeChannel {
	private final ArrayList<Message> recordedMessages = new ArrayList<>();
	private final ArrayList<Message> deletedMessages = new ArrayList<>();
	private final Channel channel;
	private final int maxDeleted;
	private final int maxHistory;

	public SnipeChannel(Channel channel, int maxDeleted, int maxHistory) {
		this.channel = channel;
		this.maxDeleted = maxDeleted;
		this.maxHistory = maxHistory;
	}

	public boolean clearMessages() {
		if (deletedMessages.isEmpty()) return false;

		deletedMessages.clear();
		return true;
	}

	public boolean removeMessage(int index) {
		if (index < 0 || index >= deletedMessages.size()) return false;

		deletedMessages.remove(index);
		return true;
	}

	public void appendMessage(Message msg) {
		if (recordedMessages.size() >= maxHistory) {
			recordedMessages.remove(recordedMessages.size() - 1);
		}

		recordedMessages.add(0, msg);
	}

	public void appendDeletedMessage(long messageID) {
		for (Message msg : recordedMessages) {
			if (msg.getIdLong() == messageID) {
				if (deletedMessages.size() >= maxDeleted) {
					deletedMessages.remove(deletedMessages.size() - 1);
				}

				deletedMessages.add(0, msg);
				return;
			}
		}

		System.out.println("Message not found in history.");
	}

	public Message snipeMessage(int index) {
		if (index < 0 || index >= deletedMessages.size()) return null;

		return deletedMessages.get(index);
	}

	public ArrayList<Message> getMessages() {
		return deletedMessages;
	}

	public Channel getChannel() {
		return channel;
	}
}
