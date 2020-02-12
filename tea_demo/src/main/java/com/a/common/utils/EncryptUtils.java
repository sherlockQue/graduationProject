

package com.a.common.utils;

import org.apache.shiro.crypto.hash.SimpleHash;

/**
 * Shiro加密工具类，login登陆 和  添加管理员用户时 使用，同个加密工具
 *
 */
public class EncryptUtils {
	
	/**  加密算法 */
	public final static String hashAlgorithmName = "SHA-256";
	/**  循环次数 */
	public final static int hashIterations = 16;

	/** 加密 方法  **/
	public static String sha256(String password, String salt) {
		return new SimpleHash(hashAlgorithmName, password, salt, hashIterations).toString();
	}

	 

}
