package org.ourgrid.node.util;

import java.io.UnsupportedEncodingException;
import java.security.SignatureException;

import org.apache.commons.codec.binary.Base64;

public class CodecUtils {

	public static String encodeBase64(byte[] content)
			throws UnsupportedEncodingException, SignatureException {
		return new String(Base64.encodeBase64(content), "UTF-8");
	}

}
