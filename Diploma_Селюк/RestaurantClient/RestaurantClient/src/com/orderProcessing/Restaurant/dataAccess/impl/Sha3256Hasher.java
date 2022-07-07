package com.orderProcessing.Restaurant.dataAccess.impl;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import org.springframework.stereotype.Component;

import com.orderProcessing.Restaurant.dataAccess.HashHandler;

@Component
public class Sha3256Hasher implements HashHandler{

	@Override
	public String encrypt(String text, byte[] salt) {
		MessageDigest md = null;
		String result = null;
		if (text != null) {
			try {
				md = MessageDigest.getInstance("SHA3-256");
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
			if (md != null) {
				md.update(salt);
				md.update(text.getBytes(StandardCharsets.UTF_8));
				result = String.format("%064x", new BigInteger(1, md.digest()));
			}
		}
		return result;
	}

	public byte[] generateSalt() {
		SecureRandom random = new SecureRandom();
		byte[] salt = new byte[32];
		random.nextBytes(salt);
		return salt;
	}
}
