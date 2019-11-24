package com.mahesh.messenger.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.mahesh.messenger.database.DatabaseClass;
import com.mahesh.messenger.model.ErrorMessage;
import com.mahesh.messenger.model.Profile;

public class ProfileService {

	private Map<String, Profile> profiles = DatabaseClass.getProfiles();

	public ProfileService() {
		profiles.put("Mahi", new Profile(1, "Mahesh", "Ganiga", "Mahi"));
		profiles.put("Gani", new Profile(2, "Ganesh", "ABC", "Gani"));

	}

	public List<Profile> getAllProfiles() {
		return new ArrayList<>(profiles.values());
	}

	public Profile addProfile(Profile profile) {
		profile.setId(profiles.size() + 1);
		profiles.put(profile.getProfileName(), profile);
		return profile;
	}

	public Profile getProfile(String profileName) {
		ErrorMessage errMsg = new ErrorMessage("This profile not found", 404, "http://error.com");
		Response errorResponse = Response.status(Status.NOT_FOUND).entity(errMsg).build();

		Profile profile = profiles.get(profileName);
		if (profile == null) {
			// throw new NotFoundException(errorResponse); This already have not found as
			// status
			throw new WebApplicationException(errorResponse);
		}
		return profile;
	}

	public Profile updateProfile(Profile profile) {
		if (!profiles.containsKey(profile.getProfileName())) {
			return null;
		}
		profiles.put(profile.getProfileName(), profile);
		return profile;
	}

	public void removeProfile(String profileName) {
		profiles.remove(profileName);
	}
}
