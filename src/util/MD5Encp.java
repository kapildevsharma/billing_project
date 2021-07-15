package util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Encp {
	public static void main (String args[]){
	 	MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    byte[] dataBytes = new byte[1024];
	    int nread = 0;
	    md.update(dataBytes, 0, nread);
	    byte[] mdbytes = md.digest();
	
	    //convert the byte to hex format method 1
	    StringBuffer sb = new StringBuffer();
	    for (int i = 0; i < mdbytes.length; i++) {
	      sb.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16).substring(1));
	    }
	
	    System.out.println("Digest(in hex format):: " + sb.toString());
	
	    //convert the byte to hex format method 2
	    StringBuffer hexString = new StringBuffer();
		for (int i=0;i<mdbytes.length;i++) {
			String hex=Integer.toHexString(0xff & mdbytes[i]);
		     	if(hex.length()==1) hexString.append('0');
		     	hexString.append(hex);
		}
		System.out.println("Digest(in hex format):: " + hexString.toString());
	
	}
}
