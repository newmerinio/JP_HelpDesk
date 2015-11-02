package com.Over2Cloud.CommonClasses;

/**
 * 
 */

/**
 * @author dharmvir.singh
 *
 */
public class TestCrypto {
	public static final String DES_ENCRYPTION_KEY = "ankitsin";
	/**
	 * @param args
	 */
	
	public static void main(String[] args) {
		try{
		String input  = "ankits1234";
		String encrypted = Cryptography.encrypt(input, DES_ENCRYPTION_KEY);
		String decrypted = Cryptography.decrypt(encrypted, DES_ENCRYPTION_KEY);
		}catch(Exception e){
			
		}
	}

}
