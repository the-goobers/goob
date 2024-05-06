package net.goobers.goob;

import net.dv8tion.jda.api.*;
import net.dv8tion.jda.api.requests.*;

public class Main {
	public static void main(String[] args) throws Exception {
		JDA jda = JDABuilder.createLight(args[0], GatewayIntent.GUILD_MESSAGES, GatewayIntent.MESSAGE_CONTENT, GatewayIntent.GUILD_MEMBERS).build();
		jda.addEventListener(new GoobListener());
		jda.awaitReady();

		System.out.println("Goob is ready!");
	}
}