package mancala;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class PropsLoader {
	
	public static int getInt(Properties props, String key){
		String strValue = props.getProperty(key);
		if (strValue != null) {
			try {
				return Integer.valueOf(strValue.trim());
			} catch(NumberFormatException e) {
				System.err.println(key + " is not an integer in properties file.");
				System.exit(0);
			}
		} else {
			System.err.println(key + " does not exist in properties file.");
			System.exit(0);
		}
		return 0; //never gets called
	}
	
	public static boolean getBool(Properties props, String key){
		String strValue = props.getProperty(key);
		if (strValue != null) {
			try {
				return Boolean.valueOf(strValue.trim());
			} catch(NumberFormatException e) {
				System.err.println(key + " is not a boolean in properties file.");
				System.exit(0);
			}
		} else {
			System.err.println(key + " does not exist in properties file.");
			System.exit(0);
		}
		return false; //never gets called
	}

	
	public static void insertCustomProps(Properties props, String customFile) {
		FileInputStream in;
		try {
			in = new FileInputStream(customFile);
			loadPropsFromFile(props, in);
			System.err.println("Loaded " + customFile + " successfully.");
		} catch (FileNotFoundException e) {
			System.err.println(e.getLocalizedMessage());
			System.err.printf("Using default properties.");
		}
	}


	private static void loadPropsFromFile(Properties props, FileInputStream in) {
		try {
			props.load(in);
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
