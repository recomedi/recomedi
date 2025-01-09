package com.recomedi.myapp.api;

import java.util.HashMap;

/**
 * <pre>
 * io.codef.easycodef
 *   |_ EasyCodefTokenMap.java
 * </pre>
 * 
 * Desc : �돩�슫 肄붾뱶�뿉�봽 �씠�슜�쓣 �쐞�븳 �넗�겙 愿�由� �겢�옒�뒪
 * @Company : 짤CODEF corp.
 * @Author  : notfound404@codef.io
 * @Date    : Jun 26, 2020 3:41:13 PM
 */
public class EasyCodefTokenMap {
	
	/**	�돩�슫 肄붾뱶�뿉�봽 �씠�슜�쓣 �쐞�븳 �넗�겙 ���옣 留�	*/
	private static HashMap<String, String> ACCESS_TOKEN_MAP = new HashMap<String, String>();
	
	/**
	 * Desc : �넗�겙 ���옣 
	 * @Company : 짤CODEF corp.
	 * @Author  : notfound404@codef.io
	 * @Date    : Jun 26, 2020 3:41:21 PM
	 * @param clientId
	 * @param accessToken
	 */
	public static void setToken(String clientId, String accessToken) {
		ACCESS_TOKEN_MAP.put(clientId, accessToken);
	}
	
	/**
	 * Desc : �넗�겙 諛섑솚
	 * @Company : 짤CODEF corp.
	 * @Author  : notfound404@codef.io
	 * @Date    : Jun 26, 2020 3:41:28 PM
	 * @param clientId
	 * @return
	 */
	public static String getToken(String clientId) {
		return ACCESS_TOKEN_MAP.get(clientId);
	}
}
