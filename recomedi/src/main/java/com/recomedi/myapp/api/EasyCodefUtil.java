package com.recomedi.myapp.api;

import java.io.File;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.apache.commons.io.FileUtils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * <pre>
 * io.codef.easycodef
 *   |_ EasyCodefUtil.java
 * </pre>
 * 
 * Desc : �돩�슫 肄붾뱶�뿉�봽 �쑀�떥 �겢�옒�뒪
 * @Company : 짤CODEF corp.
 * @Author  : notfound404@codef.io
 * @Date    : Jun 26, 2020 3:41:39 PM
 */
public class EasyCodefUtil {

	/**
	 * Desc : RSA�븫�샇�솕
	 * @Company : 짤CODEF corp.
	 * @Author  : notfound404@codef.io
	 * @Date    : Jun 26, 2020 3:41:50 PM
	 * @param plainText
	 * @param publicKey
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeyException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 */
	public static String encryptRSA(String plainText, String publicKey) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		byte[] bytePublicKey = Base64.getDecoder().decode(publicKey);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PublicKey key = keyFactory.generatePublic(new X509EncodedKeySpec(bytePublicKey));
		
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.ENCRYPT_MODE, key);
		byte[] bytePlain = cipher.doFinal(plainText.getBytes());
		String encrypted = Base64.getEncoder().encodeToString(bytePlain);
	
		return encrypted;
	}
	
	/**
	 * Desc : byte諛곗뿴濡� 異붿텧�븳 �뙆�씪 �젙蹂대�� BASE64 臾몄옄�뿴濡� �씤肄붾뵫
	 * @Company : 짤CODEF corp.
	 * @Author  : notfound404@codef.io
	 * @Date    : Jun 26, 2020 3:41:58 PM
	 * @param filePath
	 * @return
	 * @throws IOException
	 */
	public static String encodeToFileString(String filePath) throws IOException {
		File file = new File(filePath);
		
		byte[] fileContent = FileUtils.readFileToByteArray(file);
		String fileString = Base64.getEncoder().encodeToString(fileContent);
		
		return fileString;
	}
	
	/**
	 * �넗�겙 留� 蹂��솚
	 * 
	 * @param request
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public static HashMap<String, Object> getTokenMap(String token) throws JsonParseException, JsonMappingException, IOException {

		/** �겢�씪�씠�뼵�듃 �떇蹂� 媛�, �슂泥� �떇蹂� 媛� 異붿텧�쓣 �쐞�븳 �뵒肄붾뱶 */
		String[] split_string = token.split("\\.");
		String base64EncodedBody = split_string[1];
		String tokenBody = new String(Base64.getDecoder().decode(base64EncodedBody));

		/** 留� 蹂��솚 */
		return new ObjectMapper().readValue(tokenBody, HashMap.class);
	}
	
	/**
	 * Comment  : �슂泥� �넗�겙 �젙�빀�꽦 泥댄겕
	 * @version : 1.0.1
	 * @tags    : @param headerMap
	 * @tags    : @return
	 * @date    : Jun 24, 2020
	 */
	public static boolean checkValidity(int expInt) {
		long now = new Date().getTime();
		String expStr = expInt + "000";	// �쁽�옱 �떆媛� ���엫�뒪�꺃�봽�� �옄由ъ닔 留욎텛湲�(13�옄由�)
		long exp  = Long.parseLong(expStr);
		if(now > exp || (exp - now < 3600000)) { // �쑀�슚湲곌컙 �솗�씤::�쑀�슚湲곌컙�씠 吏��궗嫄곕굹 �븳�떆媛� �씠�궡濡� 留뚮즺�릺�뒗 寃쎌슦
			return false;
		}
		
		return true;
	}
}
