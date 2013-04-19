package utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.digest.DigestUtils;





public class PasswordEncoder {
	
	private static PasswordEncoder instance;
	
	private final static int ITERATION_COUNT = 552;

	private PasswordEncoder() {}
	
	 public static synchronized PasswordEncoder getInstance() {
			

			if (instance == null) {
			    PasswordEncoder returnPasswordEncoder = new PasswordEncoder();		    
			    return returnPasswordEncoder;
			} else {
			    
			    return instance;
				}
		    }
	 
	 public static synchronized String encode(String password, String saltKey)
			    throws  DecoderException, NoSuchAlgorithmException, UnsupportedEncodingException {
		 	String encodedPassword = null;
		 	
		 	byte[] salt = DigestUtils.sha(saltKey);
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			digest.reset();
			digest.update(salt);
			byte[] btPass = digest.digest(password.getBytes("UTF-8"));
			for (int i = 0; i < ITERATION_COUNT; i++) {
			    digest.reset();
			    btPass = digest.digest(btPass);
			}
			encodedPassword = DigestUtils.shaHex(btPass);
			return encodedPassword;
		 
	 
	 }
	 

}
