package com.mahesh.messenger.database;

import java.util.HashMap;
import java.util.Map;

import com.mahesh.messenger.model.Message;
import com.mahesh.messenger.model.Profile;

/*This class is like database . We can create/update/delete using this class considering as DB
*/
public class DatabaseClass {
	private static Map<Long, Message> messages = new HashMap<>();
	private static Map<String, Profile> profiles = new HashMap<>();

	public DatabaseClass() {
	}

	public static Map<Long, Message> getMessages() {
		return messages;
	}

	public static Map<String, Profile> getProfiles() {
		return profiles;
	}

}
