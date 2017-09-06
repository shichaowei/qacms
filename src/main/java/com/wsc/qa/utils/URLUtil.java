package com.wsc.qa.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * @author hushengen @date 2016年5月25日
 *
 */
public class URLUtil {

	private static final String UTF_8 = "UTF-8";

	public static String encode(String url, String... encs) {
		try {
			String enc = encs.length > 0 ? encs[0] : UTF_8;
			return URLEncoder.encode(url, enc);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("encode error",e);
		}
	}

	public static String decode(String url, String... encs) {
		try {
			String enc = encs.length > 0 ? encs[0] : UTF_8;
			return URLDecoder.decode(url, enc);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("decode error",e);
		}
	}
}
