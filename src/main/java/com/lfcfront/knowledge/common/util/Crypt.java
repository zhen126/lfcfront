package com.lfcfront.knowledge.common.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/*
 * 常用的MD5和SHA1对字符串进行加密
 * 
 * */
public class Crypt {
	/**
	 * MD5加密方式
	 * 
	 * @param data
	 * @return
	 */
	public static String md5(String data) {
		return crypt("MD5", data);
	}

	/**
	 * SHA1加密方式
	 * 
	 * @param data
	 * @return
	 */
	public static String sha1(String data) {
		return crypt("SHA-1", data);
	}

	private static String crypt(String alg, String data) {
		StringBuffer strHash = new StringBuffer();
		try {
			MessageDigest md = MessageDigest.getInstance(alg);
			md.update(data.getBytes());
			byte[] hash = md.digest();
			for (int i = 0; i < hash.length; i++) {
				strHash.append(String.format("%02x", hash[i]));
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return strHash.toString();
	}

}
