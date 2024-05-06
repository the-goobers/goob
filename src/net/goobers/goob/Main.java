package net.goobers.goob;

import net.dv8tion.jda.api.*;
import net.dv8tion.jda.api.requests.*;

public class Main {
	public static void main(String[] args) throws Exception {
		if (args == null) {
			System.out.println("Please provide a token as argument.");
			return;
		}

		if (args.length == 0) {
			System.out.println("Please provide a token as argument.");
			return;
		}

		if (args.length >= 2) {
			Config.fileName = args[1];
		}

		Config.init();
		Auth.init();

		JDA jda = JDABuilder.createLight(args[0], GatewayIntent.GUILD_MESSAGES, GatewayIntent.MESSAGE_CONTENT, GatewayIntent.GUILD_MEMBERS).build();
		jda.addEventListener(new GoobListener());
		jda.awaitReady();

		System.out.println("Goob is ready!");
	}
}