package com.jiran;

import java.security.KeyFactory;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

import org.apache.commons.codec.binary.Base64;


public class ResPKCS8Test {

	public static void main(String[] args) {

		String publicKeyPEM = "-----BEGIN PUBLIC KEY-----\n"
				+ "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAw0z8kmi3HpInWSJECz0B\n"
				+ "B418a95jgY/vT1slXFlHKccQoeIAaFNHJ1SDw3l340toDonYMkC6C1Ax30hjb+1L\n"
				+ "/jwclnejev6RwtgdRvfm78sswNkdIKVkpSeqpxtL12+FUjfgcODAMDjtBhSJ8nHD\n"
				+ "uyZxdIKdFwhHtRKr7tZ3BLLJTTm9yuq/WfGrccvjAQsd3SeFJEz5iJjJgi+9Ibsc\n"
				+ "wUlQg+QfFxVytIJerfJt+Y03Fb1kSU4p9axYNbRnvUl1w6RMc2iEBejK7SJ2FGym\n"
				+ "FZbBnZReZiywEZZHvk06BEC3vAbpA7dfurZq67Pj4k0RU76G7qN930ZDWdkhw+Wn\n"
				+ "fQIDAQAB\n"
				+ "-----END PUBLIC KEY-----";

		String encryptedStr = "me3iQp4UKPT6iGvdXp6TlZVr3byquvqF+zpithNmqNoTa54IJnF1DgYgZe4xHendx27z1sjrCDfm9QpINQmblJpmZxebP1McI33paHwVe7+NTskYfb/T96n7SmmYumSt4HlTnF5r58L2V7boxZZ/rpq+g2WkOki+lWzvP+ZhLNi9a9xRhNpJ16/hTGB2b9yYySSV5DM10HrsiQHVIRx4R1Vzw3JbddYhWnIPFf8KhsDwUiloGja7SohD3oyRK5cl6JNdsgcswHdLQ41XRVbTrSOy1BYr2fsFBxAkzOqMervhN9wekldmcQNHx3XL1q/vT4nPe/XH9WY7oUTh3uldOw==";
		
		String decryptText = decrypt(publicKeyPEM, encryptedStr);
		
		System.out.println("---- Decrypt Text ----\n" + decryptText);

	}

	public static String decrypt(String publicKeyPEM, String encryptedStr) {

		String decryptStr = null;

		publicKeyPEM = publicKeyPEM.replace("-----BEGIN PUBLIC KEY-----\n", "");
		publicKeyPEM = publicKeyPEM.replace("-----END PUBLIC KEY-----", "");

		byte[] encoded = Base64.decodeBase64(publicKeyPEM);

		try {
			KeyFactory kf = KeyFactory.getInstance("RSA");
			RSAPublicKey pubKey = (RSAPublicKey) kf.generatePublic(new X509EncodedKeySpec(encoded));

			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.DECRYPT_MODE, pubKey);

			decryptStr = new String(cipher.doFinal(Base64.decodeBase64(encryptedStr)), "UTF-8");

		} catch(Exception e) {
			System.out.println(e);
		}
		
		return decryptStr;
	}

}
