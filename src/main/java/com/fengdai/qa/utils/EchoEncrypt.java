package com.fengdai.qa.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 *  @author 作者 E-mail:wangbb
 *  @date 创建时间：2017年7月14日 下午3:50:52
 *  @description 描述：宜信致诚加密类
 */
public class EchoEncrypt {
	private static final Logger logger = LoggerFactory.getLogger(EchoEncrypt.class);
	private static final String RC4 = "RC4";
	private static final String UTF8 = "UTF-8";

	/**
	 * RC4加密明文（可能包含汉字），输出是经过Base64的；如果加密失败，返回值是null
	 *
	 * @param plainText
	 * @param rc4Key
	 * @return
	 */
	public static final String encode(final String plainText, final String rc4Key) {
		try {
			final Cipher c1 = Cipher.getInstance(RC4);
			c1.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(rc4Key.getBytes(), RC4));
			return new String(Base64.encodeBase64(c1.doFinal(plainText.getBytes(UTF8))));
		} catch (final Throwable t) {
			logger.error("", t);
			return null;
		}
	}

	/**
	 * RC4从密文解密为明文，输入是经过Base64的；如果解密失败，返回值是null
	 *
	 * @param encodedText
	 * @param rc4Key
	 * @return
	 */
	public static final String decode(final String encodedText, final String rc4Key) {
		try {
			final Cipher c1 = Cipher.getInstance(RC4);
			c1.init(Cipher.DECRYPT_MODE, new SecretKeySpec(rc4Key.getBytes(), RC4));
			return new String(c1.doFinal(Base64.decodeBase64(encodedText.getBytes())), UTF8);
		} catch (final Throwable t) {
			logger.error("", t);
			return null;
		}
	}


	public static void main(String[] args) throws UnsupportedEncodingException {
		String params="{\"tx\":\"201\",\"data\":{\"idNo\":\"410581199007129054\",\"name\":\"魏士超\"}}";
		String jsonStr="{\"message\":\"queryFromEcho\",\"errorCode\":\"0000\",\"params\":\"%s\"}";
		String encodejsonStr =String.format(jsonStr,encode(params, "87f1193ee429aa16"));
		System.out.println(URLEncoder.encode(encodejsonStr,"utf-8"));
//		System.out.println(String.format(jsonStr, encode(params, "87f1193ee429aa16")));
//		System.out.println(decode("hhTO1XghOzGZJbvCr0BHq6z49Dc3%2FibRxYwm3QQwYe2JD5uiXS2VCHFsn9I2OmjWDoj4B%2FLIUgDN7omHo3ayg2R4o4I%3D", "87f1193ee429aa16"));



	}
}
