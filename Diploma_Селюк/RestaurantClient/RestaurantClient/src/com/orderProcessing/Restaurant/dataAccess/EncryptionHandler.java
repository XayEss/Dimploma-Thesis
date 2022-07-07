package com.orderProcessing.Restaurant.dataAccess;

import java.security.NoSuchAlgorithmException;

import javax.crypto.SecretKey;

public interface EncryptionHandler {

	String encrypt(String data, byte[] iv);

	String decrypt(String data, byte[] iv);

	SecretKey getKey(byte[] keyBase);

	SecretKey generateKey(int length) throws NoSuchAlgorithmException;

	byte[] generateVector();

	byte[] generateBytes(int n);

}