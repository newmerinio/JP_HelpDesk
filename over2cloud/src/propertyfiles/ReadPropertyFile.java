package propertyfiles;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
 
public class ReadPropertyFile {
	
	public String getIpPort()
	{
		String ip_port="";
		Properties prop =new Properties();
		InputStream input = null;
		try {
			String filename = "ipAddress.properties";
			input = ClassLoader.getSystemResourceAsStream(filename);
			prop.load(input);
			ip_port = prop.getProperty("serverIp");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return ip_port;
	}
	
	
	/*
	
	public static void main(String[] args) {
		 
			Properties prop = new Properties();
			InputStream input = null;
		 
			try {
		 
				//input = new FileInputStream("/resource/ipAddress.properties");
				String filename="ipAddress.properties";
				input = ClassLoader.getSystemResourceAsStream(filename);
				// load a properties file
				prop.load(input);
		 
				// get the property value and print it out
				System.out.println(prop.getProperty("serverIp"));
				//System.out.println(prop.getProperty("dbuser"));
				//System.out.println(prop.getProperty("dbpassword"));
		 
			} catch (IOException ex) {
				ex.printStackTrace();
			} finally {
				if (input != null) {
					try {
						input.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		 
		  }*/
}
