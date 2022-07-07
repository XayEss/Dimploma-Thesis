package com.orderProcessing.Restaurant.dataAccess.impl;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Component;

import com.orderProcessing.Restaurant.dataAccess.EncryptionHandler;

@Component
public class AESHandler implements EncryptionHandler{
	
	public AESHandler() {

	}

	private static final String KEY_LOCATION = "C:/Users/alexs/eclipse-workspace/RestaurantClient/resources/notakey.conf";
	private static final int BLOCK_SIZE = 128;
	
	@Override
	public String encrypt(String data, byte[] iv) {
		String encrypted = null;
		byte[] keyBytes = decryptKeyKMS(takeKeyOut());
		encrypted = encryptAES(data, getKey(keyBytes), iv);
		return encrypted;
	}

	@Override
	public String decrypt(String data, byte[] iv) {
		String decrypted = null;
		byte[] keyBytes = decryptKeyKMS(takeKeyOut());
		decrypted = decryptAES(data, getKey(keyBytes), iv);
		return decrypted;
	}
	
	public byte[] decryptKeyKMS(byte[] baseKey) {
		byte[] encKey = Base64.getDecoder().decode(baseKey);
		return encKey;
	}

	public byte[] takeKeyOut() {
		byte[] key = null;
		try {
			key = Files.readAllBytes(Path.of(KEY_LOCATION));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return key;
	}

	@Override
	public SecretKey getKey(byte[] key) {
		return new SecretKeySpec(key, 0, key.length, "AES");

	}

	@Override
	public SecretKey generateKey(int length) throws NoSuchAlgorithmException {
		KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
		keyGenerator.init(length);
		SecretKey key = keyGenerator.generateKey();
		return key;
	}
	
	@Override
	public byte[] generateVector() {
		return generateBytes(BLOCK_SIZE);
	}

	@Override
	public byte[] generateBytes(int n) {
		SecureRandom random = new SecureRandom();
		byte[] bytes = new byte[n];
		random.nextBytes(bytes);
		return bytes;
	}

	private String encryptAES(String data, SecretKey key, byte[] iv) {
		try {
			Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
			GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(16 * 8, iv);
			cipher.init(Cipher.ENCRYPT_MODE, key, gcmParameterSpec);
			byte[] ciphered = cipher.doFinal(data.getBytes());
			return Base64.getEncoder().encodeToString(ciphered);
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException
				| IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {
			e.printStackTrace();
		}
		return null;
	}

	private String decryptAES(String data, SecretKey key, byte[] iv) {
		try {
			Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
			GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(16 * 8, iv);
			cipher.init(Cipher.DECRYPT_MODE, key, gcmParameterSpec);
			byte[] deciphered = cipher.doFinal(Base64.getDecoder().decode(data));
			return new String(deciphered);
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException
				| IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {
			e.printStackTrace();
		}
		return null;
	}
}
