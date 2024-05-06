package net.goobers.goob;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class Config {
	public static String fileName = "goob.properties";
	public static Properties properties;

	public static void init() {
		try (FileInputStream fis = new FileInputStream(fileName)) {
			properties = new Properties();
			properties.load(fis);
		} catch (FileNotFoundException ex) {
			System.out.println("File not found.");
		} catch (IOException ex) {
			System.out.println("IOException.");
		}
	}
}
