package com.mahesh.messenger.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import com.mahesh.messenger.database.DatabaseClass;
import com.mahesh.messenger.model.Message;

public class MessageService {

	Map<Long, Message> messages = DatabaseClass.getMessages();

	public MessageService() {
		Message m1 = new Message(1, "Hello world!", "Mahesh");
		Message m2 = new Message(2, "Hello Jorsey!", "Jorsey");
		messages.put(1L, m1);
		messages.put(2L, m2);
	}

	public List<Message> getAllMessages() {
		return new ArrayList<>(messages.values());
	}

	public Message getMessage(long id) {
		return messages.get(id);
	}

	public Message addMessage(Message message) {
		message.setId(messages.size() + 1);
		messages.put(message.getId(), message);
		return message;
	}

	public Message updateMessage(Message message) {
		if (message.getId() <= 0) {
			return null;
		}
		messages.put(message.getId(), message);
		return message;
	}

	public Message removeMessage(long messageId) {
		return messages.remove(messageId);
	}

	public List<Message> getMessagesForYear(int year) {
		List<Message> msgForYrs = new ArrayList<>();
		for (Message msg : messages.values()) {
			Calendar calander = Calendar.getInstance();
			calander.setTime(msg.getCreated());
			if (calander.get(Calendar.YEAR) == year) {
				msgForYrs.add(msg);
			}
		}
		return msgForYrs;
	}

	public List<Message> getMessagesPaginated(int start, int size) {
		List<Message> msgs = new ArrayList<>(messages.values());
		if (start > msgs.size())
			return new ArrayList<Message>();
		return msgs.subList(start, start + size);
	}
}
