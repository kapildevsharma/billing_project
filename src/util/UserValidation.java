package util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.apache.tomcat.util.codec.binary.Base64;

public class UserValidation {

	public static boolean validUserFlag = false;

	public static boolean isValidUserFlag() {
		return validUserFlag;
	}

	public static void setValidUserFlag(boolean validUserFlag) {
		UserValidation.validUserFlag = validUserFlag;
	}

	public String getProjectExpireDate() {
		InputStream inputStream = null;
		String expireDate = null;
		try {
			Properties prop = new Properties();
			String propFileName = "projectLicense.properties";
 
			inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
 
			if (inputStream != null) {
				prop.load(inputStream);
			} else {
				throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
			}
			expireDate = prop.getProperty("date");
		//	System.out.println("Encode Expire Date " + expireDate);
			
			// Decode data on other side, by processing encoded data
			byte[] valueDecoded= Base64.decodeBase64(expireDate .getBytes() );
			expireDate = new String(valueDecoded);
		//	System.out.println("Expire Date " + expireDate);
			
		} catch (Exception e) {
			System.err.println("Exception: " + e);
			expireDate = null;
		} finally {
			try {
				inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return expireDate;
	}

}
