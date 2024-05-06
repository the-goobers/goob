package net.goobers.goob;

public class Auth {
	/* krisvers ID */
	public static final String superuserID = "790355926565519380";

	public static boolean isSuperUser(String id) {
		return id.equals(superuserID);
	}
}
