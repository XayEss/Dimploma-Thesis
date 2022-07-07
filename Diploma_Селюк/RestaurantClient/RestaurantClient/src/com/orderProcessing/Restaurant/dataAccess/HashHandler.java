package com.orderProcessing.Restaurant.dataAccess;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import org.springframework.stereotype.Component;

@Component
public interface HashHandler {
	public String encrypt(String text, byte[] salt);
	public byte[] generateSalt();
}
