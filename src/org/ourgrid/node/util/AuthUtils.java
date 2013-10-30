package org.ourgrid.node.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.util.Properties;

import javax.crypto.Cipher;

public class AuthUtils {

	public static KeyStore getKeyStore(Properties properties) 
			throws KeyStoreException, NoSuchAlgorithmException, CertificateException, FileNotFoundException, IOException {
		
		String keyStoreLocation = properties.getProperty(NodeProperties.KEYSTORE_LOCATION);
		String keyStorePassword = properties.getProperty(NodeProperties.KEYSTORE_PASS);
		KeyStore  keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
		keyStore.load(new FileInputStream(keyStoreLocation), keyStorePassword.toCharArray());
		return keyStore;
	}
	
	public static String decrypt(String token, String certAlias, 
			String certPassword, Properties properties) throws Exception {
		KeyStore keyStore = getKeyStore(properties);
		Key key = keyStore.getKey(certAlias, certPassword.toCharArray());
		byte[] decodedToken = CodecUtils.decodeBase64(token);
		
		Cipher rsa = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        rsa.init(Cipher.DECRYPT_MODE, key);
        byte[] utf8 = rsa.doFinal(decodedToken);
        
        return new String(utf8, "UTF8");
	}
	
	public static String decryptWithNode(String token, Properties properties) throws Exception {
		String privateKeyAlias = properties.getProperty(NodeProperties.PRIVATEKEY_ALIAS);
		String privateKeyPass = properties.getProperty(NodeProperties.PRIVATEKEY_PASS);
		return decrypt(token, privateKeyAlias, privateKeyPass, properties);
	}
	
	public static String encryptWithCloud(String token, Properties properties) throws Exception {
		String privateKeyAlias = properties.getProperty(NodeProperties.CLOUDKEY_ALIAS);
		String privateKeyPass = properties.getProperty(NodeProperties.KEYSTORE_PASS);
		return encrypt(token, privateKeyAlias, privateKeyPass, properties);
	}
	
	public static String encrypt(String token, String certAlias, 
			String certPassword, Properties properties) throws Exception {
		KeyStore keyStore = getKeyStore(properties);
		Certificate cert = keyStore.getCertificate(certAlias);
		
		Cipher rsa = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        rsa.init(Cipher.ENCRYPT_MODE, cert);
        byte[] utf8 = rsa.doFinal(token.getBytes("UTF-8"));

		return CodecUtils.encodeBase64(utf8);
	}
}
