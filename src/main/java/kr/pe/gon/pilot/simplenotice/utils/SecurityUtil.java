package kr.pe.gon.pilot.simplenotice.utils;

import java.security.MessageDigest;

public class SecurityUtil {

	public static String encryptSHA256(String str) {
		String encryptStr = "";
		
		try {
			MessageDigest sha = MessageDigest.getInstance("SHA-256");
			sha.update(str.getBytes());
			byte byteData[] = sha.digest();
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < byteData.length; i++) {
	              sb.append(Integer.toString((byteData[i]&0xff) + 0x100, 16).substring(1));
			}
			encryptStr = sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
			encryptStr = null;
		}
		return encryptStr;
	}
}
