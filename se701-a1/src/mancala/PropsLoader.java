package mancala;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class PropsLoader {
	
	public static int getInt(Properties props, String key){
		String strValue = props.getProperty(key).trim();
		if (strValue != null) {
			try {
				return Integer.valueOf(strValue);
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

	public static Properties loadProps(String defaultFileLoc, String customFileLoc) {
		Properties props = new Properties();
		loadDefaultProps(props, defaultFileLoc);
		insertCustomProps(props, customFileLoc, defaultFileLoc);
		return props;
	}
	
	public static Properties loadPropsFile(String propsFileLoc) {
		Properties props = new Properties();
		FileInputStream in;
		try {
			in = new FileInputStream(propsFileLoc);
			loadPropsFromFile(props, in);
		} catch (FileNotFoundException e) {
			System.err.println(e.getLocalizedMessage());
			System.err.println("Using default values");
		}
		return props;
	}
	
//	public static Properties loadProps(String defaultFile) {
//		Properties props = new Properties();
//		loadDefaultProps(props, defaultFile);
//		return props;
//	}

	private static void loadDefaultProps(Properties props, String filename) {
		FileInputStream in;
		try {
			in = new FileInputStream(filename);
			loadPropsFromFile(props, in);
		} catch (FileNotFoundException e) {
			System.err.println(e.getLocalizedMessage());
			System.exit(0);
		}
	}

	private static void insertCustomProps(Properties props, String customFile,
			String defaultPropertiesName) {
		FileInputStream in;
		try {
			in = new FileInputStream(customFile);
			loadPropsFromFile(props, in);
		} catch (FileNotFoundException e) {
			System.err.println(e.getLocalizedMessage());
			System.err.printf("Using %s\n", defaultPropertiesName);
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
