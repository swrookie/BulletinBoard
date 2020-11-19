package com.swrookie.bulletinboard.security;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Generator 
{
	private String result;
	
	public MD5Generator(String input) throws UnsupportedEncodingException, NoSuchAlgorithmException
	{
		MessageDigest mdM5 = MessageDigest.getInstance("MD5");
		mdM5.update(input.getBytes("UTF-8"));
		byte[] md5Hash = mdM5.digest();
		StringBuilder hexMD5hash = new StringBuilder();
		
		for (byte b : md5Hash)
		{
			String hexString = String.format("%02x", b);
			hexMD5hash.append(hexString);
		}
		result = hexMD5hash.toString();
	}
	
	public String toString()
	{
		return result;
	}
}
