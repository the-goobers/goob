package net.goobers.goob;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;

import java.util.List;

public class Auth {
	/* krisvers ID */
	public static final long superuserID = 790355926565519380L;

	private static String[] superusers = null;
	private static String[] superroleIDs = null;
	private static String[] superroleNames = null;

	public static void init() {
		if (Config.properties != null) {
			String prop = Config.properties.getProperty("superusers");
			if (prop != null) {
				superusers = prop.split(",");
			}


			prop = Config.properties.getProperty("superroleIDs");
			if (prop != null) {
				superroleIDs = prop.split(",");
			}

			prop = Config.properties.getProperty("superroleNames");
			if (prop != null) {
				superroleNames = prop.split(",");
			}
		}
	}

	public static boolean isSuperUser(Member member) {
		if (member.getIdLong() == superuserID) {
			return true;
		}

		if (superusers != null) {
			for (String id : superusers) {
				if (member.getId().equals(id)) {
					return true;
				}
			}
		}

		if (superroleNames != null) {
			for (String role : superroleNames) {
				List<Role> roles = member.getRoles();
				for (Role r : roles) {
					if (r.getName().equals(role)) {
						return true;
					}
				}
			}
		}

		if (superroleIDs == null) {
			return false;
		}

		for (String role : superroleIDs) {
			List<Role> roles = member.getRoles();
			for (Role r : roles) {
				if (r.getId().equals(role)) {
					return true;
				}
			}
		}

		return false;
	}
}
