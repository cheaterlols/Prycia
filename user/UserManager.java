package me.vrekt.prycia.user;

import java.util.ArrayList;
import java.util.UUID;

public class UserManager {

	private ArrayList<User> allUsers = new ArrayList<User>();

	// Get a user from UUID.
	
	public User getUser(UUID uuid) {
		for (User user : allUsers) {
			if (user.getUUID() == uuid) {
				return user;
			}
		}
		return null;
	}

	// Add a user to the users.
	
	public void add(User user){
		allUsers.add(user);
	}
	
	// Remove a user from users.
	
	public void remove(User user){
		allUsers.remove(user);
	}
	
}
