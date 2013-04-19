package utils;

import org.apache.commons.lang.WordUtils;

import models.User;

public class UserUtils {
	
	public static void prepareUser(User user){
		user.name=WordUtils.capitalize(user.name).trim();
		user.lastName= WordUtils.capitalize(user.lastName).trim();
		user.email=user.email.trim();
	}

}
