package com.recomedi.myapp.api;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * <pre>
 * io.codef.easycodef
 *   |_ EasyCodef.java
 * </pre>
 * 
 * Desc : 肄붾뱶�뿉�봽�쓽 �돩�슫 �궗�슜�쓣 �쐞�븳 �쑀�떥 �씪�씠釉뚮윭由� �겢�옒�뒪 
 * @Company : 짤CODEF corp.
 * @Author  : notfound404@codef.io
 * @Date    : Jun 26, 2020 3:28:31 PM
 */
public class EasyCodef {
	
	private ObjectMapper mapper = new ObjectMapper();
	
	/**
	 * EasyCodef �궗�슜�쓣 �쐞�븳 蹂��닔 �꽕�젙 �삤釉뚯젥�듃
	 */
	private EasyCodefProperties properties = new EasyCodefProperties();

	/**
	 * Desc : �젙�떇�꽌踰� �궗�슜�쓣 �쐞�븳 �겢�씪�씠�뼵�듃 �젙蹂� �꽕�젙
	 * @Company : 짤CODEF corp.
	 * @Author  : notfound404@codef.io
	 * @Date    : Jun 26, 2020 3:30:59 PM
	 * @param clientId
	 * @param clientSecret
	 */
	public void setClientInfo(String clientId, String clientSecret) {
		properties.setClientInfo(clientId, clientSecret);
	}
	
	/**
	 * Desc : �뜲紐⑥꽌踰� �궗�슜�쓣 �쐞�븳 �겢�씪�씠�뼵�듃 �젙蹂� �꽕�젙
	 * @Company : 짤CODEF corp.
	 * @Author  : notfound404@codef.io
	 * @Date    : Jun 26, 2020 3:31:12 PM
	 * @param demoClientId
	 * @param demoClientSecret
	 */
	public void setClientInfoForDemo(String demoClientId, String demoClientSecret) {
		properties.setClientInfoForDemo(demoClientId, demoClientSecret);
	}
	
	/**
	 * Desc : RSA�븫�샇�솕瑜� �쐞�븳 �띁釉붾┃�궎 �꽕�젙
	 * @Company : 짤CODEF corp.
	 * @Author  : notfound404@codef.io
	 * @Date    : Jun 26, 2020 3:31:24 PM
	 * @param publicKey
	 */
	public void setPublicKey(String publicKey) {
		properties.setPublicKey(publicKey);
	}
	
	/**
	 * Desc : RSA�븫�샇�솕瑜� �쐞�븳 �띁釉붾┃�궎 諛섑솚
	 * @Company : 짤CODEF corp.
	 * @Author  : notfound404@codef.io
	 * @Date    : Jun 26, 2020 3:32:25 PM
	 * @return
	 */
	public String getPublicKey() {
		return properties.getPublicKey();
	}
	
	/**
	 * Desc : �긽�뭹 �슂泥� 
	 * @Company : 짤CODEF corp.
	 * @Author  : notfound404@codef.io
	 * @Date    : Jun 26, 2020 3:32:31 PM
	 * @param productUrl
	 * @param serviceType
	 * @param parameterMap
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws JsonProcessingException
	 * @throws InterruptedException
	 */
	public String requestProduct(String productUrl, EasyCodefServiceType serviceType, HashMap<String, Object> parameterMap) throws UnsupportedEncodingException, JsonProcessingException, InterruptedException {
		boolean validationFlag = true;
		
		/**	#1.�븘�닔 �빆紐� 泥댄겕 - �겢�씪�씠�뼵�듃 �젙蹂�	*/
		validationFlag = checkClientInfo(serviceType.getServiceType());
		if(!validationFlag) {
			EasyCodefResponse response = new EasyCodefResponse(EasyCodefMessageConstant.EMPTY_CLIENT_INFO);
			return mapper.writeValueAsString(response);
		}
		
		/**	#2.�븘�닔 �빆紐� 泥댄겕 - �띁釉붾┃ �궎	*/
		validationFlag = checkPublicKey();
		if(!validationFlag) {
			EasyCodefResponse response = new EasyCodefResponse(EasyCodefMessageConstant.EMPTY_PUBLIC_KEY);
			return mapper.writeValueAsString(response);
		}
		
		/**	#3.異붽��씤利� �궎�썙�뱶 泥댄겕	*/
		validationFlag = checkTwoWayKeyword(parameterMap);
		if(!validationFlag) {
			EasyCodefResponse response = new EasyCodefResponse(EasyCodefMessageConstant.INVALID_2WAY_KEYWORD);
			return mapper.writeValueAsString(response);
		}
		
		/**	#4.�긽�뭹 議고쉶 �슂泥�	*/
		EasyCodefResponse response = EasyCodefConnector.execute(productUrl, serviceType.getServiceType(), parameterMap, properties);
		
		/**	#5.寃곌낵 諛섑솚	*/
		return mapper.writeValueAsString(response);
	}
	
	/**
	 * Desc : �긽�뭹 異붽��씤利� �슂泥�
	 * @Company : 짤CODEF corp.
	 * @Author  : notfound404@codef.io
	 * @Date    : Jun 26, 2020 3:32:41 PM
	 * @param productUrl
	 * @param serviceType
	 * @param parameterMap
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws JsonProcessingException
	 * @throws InterruptedException
	 */
	public String requestCertification(String productUrl, EasyCodefServiceType serviceType, HashMap<String, Object> parameterMap) throws UnsupportedEncodingException, JsonProcessingException, InterruptedException {
		boolean validationFlag = true;
		
		/**	#1.�븘�닔 �빆紐� 泥댄겕 - �겢�씪�씠�뼵�듃 �젙蹂�	*/
		validationFlag = checkClientInfo(serviceType.getServiceType());
		if(!validationFlag) {
			EasyCodefResponse response = new EasyCodefResponse(EasyCodefMessageConstant.EMPTY_CLIENT_INFO);
			return mapper.writeValueAsString(response);
		}
		
		/**	#2.�븘�닔 �빆紐� 泥댄겕 - �띁釉붾┃ �궎	*/
		validationFlag = checkPublicKey();
		if(!validationFlag) {
			EasyCodefResponse response = new EasyCodefResponse(EasyCodefMessageConstant.EMPTY_PUBLIC_KEY);
			return mapper.writeValueAsString(response);
		}
		
		/**	#3.異붽��씤利� �뙆�씪誘명꽣 �븘�닔 �엯�젰 泥댄겕	*/
		validationFlag = checkTwoWayInfo(parameterMap);
		if(!validationFlag) {
			EasyCodefResponse response = new EasyCodefResponse(EasyCodefMessageConstant.INVALID_2WAY_INFO);
			return mapper.writeValueAsString(response);
		}
		
		/**	#4.�긽�뭹 議고쉶 �슂泥�	*/
		EasyCodefResponse response = EasyCodefConnector.execute(productUrl, serviceType.getServiceType(), parameterMap, properties);
		
		/**	#5.寃곌낵 諛섑솚	*/
		return mapper.writeValueAsString(response);
	}
	
	
	/**
	 * Desc : �꽌鍮꾩뒪 ���엯�뿉 �뵲瑜� �겢�씪�씠�뼵�듃 �젙蹂� �꽕�젙 �솗�씤
	 * @Company : 짤CODEF corp.
	 * @Author  : notfound404@codef.io
	 * @Date    : Jun 26, 2020 3:33:23 PM
	 * @param serviceType
	 * @return
	 */
	private boolean checkClientInfo(int serviceType) {
		if(serviceType == 0) {
			if(properties.getClientId() == null || "".equals(properties.getClientId().trim())) {
				return false;
			}
			if(properties.getClientSecret() == null || "".equals(properties.getClientSecret().trim())) {
				return false;
			}
		} else if(serviceType == 1) {
			if(properties.getDemoClientId() == null || "".equals(properties.getDemoClientId().trim())) {
				return false;
			}
			if(properties.getDemoClientSecret() == null || "".equals(properties.getDemoClientSecret().trim())) {
				return false;
			}
		} else {
			if(EasyCodefConstant.SANDBOX_CLIENT_ID == null || "".equals(EasyCodefConstant.SANDBOX_CLIENT_ID.trim())) {
				return false;
			}
			if(EasyCodefConstant.SANDBOX_CLIENT_SECRET == null || "".equals(EasyCodefConstant.SANDBOX_CLIENT_SECRET.trim())) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Desc : �띁釉붾┃�궎 �젙蹂� �꽕�젙 �솗�씤
	 * @Company : 짤CODEF corp.
	 * @Author  : notfound404@codef.io
	 * @Date    : Jun 26, 2020 3:33:31 PM
	 * @return
	 */
	private boolean checkPublicKey() {
		if(properties.getPublicKey() == null || "".equals(properties.getPublicKey().trim())) {
			return false;
		}
		return true;
	}
	
	/**
	 * Desc : 異붽��씤利� �뙆�씪誘명꽣 �꽕�젙 �솗�씤
	 * @Company : 짤CODEF corp.
	 * @Author  : notfound404@codef.io
	 * @Date    : Jun 26, 2020 3:33:39 PM
	 * @param parameterMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private boolean checkTwoWayInfo(HashMap<String, Object> parameterMap) {
		if(!parameterMap.containsKey("is2Way") || !(parameterMap.get("is2Way") instanceof Boolean) || !(boolean)parameterMap.get("is2Way")){
			return false;
		}
		
		if(!parameterMap.containsKey("twoWayInfo")) {
			return false;
		}
		
		HashMap<String, Object> twoWayInfoMap = (HashMap<String, Object>)parameterMap.get("twoWayInfo");
		if(!twoWayInfoMap.containsKey("jobIndex") || !twoWayInfoMap.containsKey("threadIndex") || !twoWayInfoMap.containsKey("jti") || !twoWayInfoMap.containsKey("twoWayTimestamp")){
			return false;
		}
		
		return true;
	}
	
	/**
	 * Desc : 異붽��씤利� �궎�썙�뱶 �솗�씤
	 * @Company : 짤CODEF corp.
	 * @Author  : notfound404@codef.io
	 * @Date    : Jun 26, 2020 3:33:45 PM
	 * @param parameterMap
	 * @return
	 */
	private boolean checkTwoWayKeyword(HashMap<String, Object> parameterMap) {
		if(parameterMap != null && (parameterMap.containsKey("is2Way") || parameterMap.containsKey("twoWayInfo"))) {
			return false;
		}
		
		return true;
	}
	
	
	/**
	 * Desc : connectedId 諛쒓툒�쓣 �쐞�븳 怨꾩젙 �벑濡�
	 * @Company : 짤CODEF corp.
	 * @Author  : notfound404@codef.io
	 * @Date    : Jun 26, 2020 3:34:02 PM
	 * @param serviceType
	 * @param parameterMap
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws JsonProcessingException
	 * @throws InterruptedException
	 */
	public String createAccount(EasyCodefServiceType serviceType, HashMap<String, Object> parameterMap) throws UnsupportedEncodingException, JsonProcessingException, InterruptedException {
		return requestProduct(EasyCodefConstant.CREATE_ACCOUNT, serviceType, parameterMap);
	}
	
	/**
	 * Desc : 怨꾩젙 �젙蹂� 異붽�
	 * @Company : 짤CODEF corp.
	 * @Author  : notfound404@codef.io
	 * @Date    : Jun 26, 2020 3:34:11 PM
	 * @param serviceType
	 * @param parameterMap
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws JsonProcessingException
	 * @throws InterruptedException
	 */
	public String addAccount(EasyCodefServiceType serviceType, HashMap<String, Object> parameterMap) throws UnsupportedEncodingException, JsonProcessingException, InterruptedException {
		return requestProduct(EasyCodefConstant.ADD_ACCOUNT, serviceType, parameterMap);
	}
	
	/**
	 * Desc : 怨꾩젙 �젙蹂� �닔�젙 
	 * @Company : 짤CODEF corp.
	 * @Author  : notfound404@codef.io
	 * @Date    : Jun 26, 2020 3:34:21 PM
	 * @param serviceType
	 * @param parameterMap
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws JsonProcessingException
	 * @throws InterruptedException
	 */
	public String updateAccount(EasyCodefServiceType serviceType, HashMap<String, Object> parameterMap) throws UnsupportedEncodingException, JsonProcessingException, InterruptedException {
		return requestProduct(EasyCodefConstant.UPDATE_ACCOUNT, serviceType, parameterMap);
	}
	
	/**
	 * Desc : 怨꾩젙 �젙蹂� �궘�젣
	 * @Company : 짤CODEF corp.
	 * @Author  : notfound404@codef.io
	 * @Date    : Jun 26, 2020 3:34:30 PM
	 * @param serviceType
	 * @param parameterMap
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws JsonProcessingException
	 * @throws InterruptedException
	 */
	public String deleteAccount(EasyCodefServiceType serviceType, HashMap<String, Object> parameterMap) throws UnsupportedEncodingException, JsonProcessingException, InterruptedException {
		return requestProduct(EasyCodefConstant.DELETE_ACCOUNT, serviceType, parameterMap);
	}
	
	/**
	 * Desc : connectedId濡� �벑濡앸맂 怨꾩젙 紐⑸줉 議고쉶
	 * @Company : 짤CODEF corp.
	 * @Author  : notfound404@codef.io
	 * @Date    : Jun 26, 2020 3:34:37 PM
	 * @param serviceType
	 * @param parameterMap
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws JsonProcessingException
	 * @throws InterruptedException
	 */
	public String getAccountList(EasyCodefServiceType serviceType, HashMap<String, Object> parameterMap) throws UnsupportedEncodingException, JsonProcessingException, InterruptedException {
		return requestProduct(EasyCodefConstant.GET_ACCOUNT_LIST, serviceType, parameterMap);
	}
	
	/**
	 * Desc : �겢�씪�씠�뼵�듃 �젙蹂대줈 �벑濡앸맂 紐⑤뱺 connectedId 紐⑸줉 議고쉶
	 * @Company : 짤CODEF corp.
	 * @Author  : notfound404@codef.io
	 * @Date    : Jun 26, 2020 3:34:44 PM
	 * @param serviceType
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws JsonProcessingException
	 * @throws InterruptedException
	 */
	public String getConnectedIdList(EasyCodefServiceType serviceType) throws UnsupportedEncodingException, JsonProcessingException, InterruptedException {
		return requestProduct(EasyCodefConstant.GET_CID_LIST, serviceType, null);
	}
	
	/**
	 * Desc : �넗�겙 諛섑솚 �슂泥� - 蹂댁쑀 以묒씤 �쑀�슚�븳 �넗�겙�씠 �엳�뒗 寃쎌슦 諛섑솚, �뾾�뒗 寃쎌슦 �떊洹� 諛쒓툒 �썑 諛섑솚
	 * @Company : 짤CODEF corp.
	 * @Author  : notfound404@codef.io
	 * @Date    : Jun 26, 2020 3:35:03 PM
	 * @param serviceType
	 * @return
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 */
	public String requestToken(EasyCodefServiceType serviceType) throws JsonParseException, JsonMappingException, IOException {
		String clientId = null;
		String clientSecret = null;
		
		if(serviceType.getServiceType() == 0) {
			clientId = properties.getClientId();
			clientSecret = properties.getClientSecret();
		} else if(serviceType.getServiceType() == 1) {
			clientId = properties.getDemoClientId();
			clientSecret = properties.getDemoClientSecret();
		} else {
			clientId = EasyCodefConstant.SANDBOX_CLIENT_ID;
			clientSecret = EasyCodefConstant.SANDBOX_CLIENT_SECRET;
		}
		
		String accessToken = EasyCodefTokenMap.getToken(clientId); // 蹂댁쑀 以묒씤 �넗�겙�씠 �엳�뒗 寃쎌슦 諛섑솚
		if(accessToken != null) {
			HashMap<String, Object> tokenMap = EasyCodefUtil.getTokenMap(accessToken);
			if(EasyCodefUtil.checkValidity((int)(tokenMap.get("exp")))) {	// �넗�겙�쓽 �쑀�슚 湲곌컙 �솗�씤
				return accessToken;	// �젙�긽 �넗�겙�씤 寃쎌슦 諛섑솚
			}
		}
		
		HashMap<String, Object> tokenMap = EasyCodefConnector.publishToken(clientId, clientSecret);	// 蹂댁쑀 以묒씤 �넗�겙�씠 �뾾嫄곕굹 �떊洹� 諛쒓툒 議곌굔�뿉 �빐�떦�븯�뒗 寃쎌슦 諛쒓툒 �썑 諛섑솚(留뚮즺�씪�떆瑜� 吏��궗嫄곕굹 �븳�떆媛� �씠�궡濡� �룄�옒�븳 寃쎌슦 �떊洹� 諛쒓툒)
		if(tokenMap != null) {
			accessToken = (String)tokenMap.get("access_token");
			EasyCodefTokenMap.setToken(clientId, accessToken);	// 諛쒓툒 �넗�겙 ���옣
			return accessToken;
		} else {
			return null;
		}
	}
	
	/**
	 * Desc : �넗�겙 �떊洹� 諛쒓툒 �썑 諛섑솚(肄붾뱶�뿉�봽 �씠�슜 以� 異붽� �뾽臾� �궗�슜�쓣 �븯�뒗 �벑 �넗�겙 沅뚰븳 蹂�寃쎌씠 �븘�슂�븯嫄곕굹 �떊洹� �넗�겙�씠 �븘�슂�븳 寃쎌슦�떆 �궗�슜)
	 * @Company : 짤CODEF corp.
	 * @Author  : notfound404@codef.io
	 * @Date    : Sep 16, 2020 11:58:32 AM
	 * @param serviceType
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public String requestNewToken(EasyCodefServiceType serviceType) throws JsonParseException, JsonMappingException, IOException {
		String clientId = null;
		String clientSecret = null;
		
		if(serviceType.getServiceType() == 0) {
			clientId = properties.getClientId();
			clientSecret = properties.getClientSecret();
		} else if(serviceType.getServiceType() == 1) {
			clientId = properties.getDemoClientId();
			clientSecret = properties.getDemoClientSecret();
		} else {
			clientId = EasyCodefConstant.SANDBOX_CLIENT_ID;
			clientSecret = EasyCodefConstant.SANDBOX_CLIENT_SECRET;
		}
		
		String accessToken = null;
		HashMap<String, Object> tokenMap = EasyCodefConnector.publishToken(clientId, clientSecret);	// �넗�겙 �떊洹� 諛쒓툒
		if(tokenMap != null) {
			accessToken = (String)tokenMap.get("access_token");
			EasyCodefTokenMap.setToken(clientId, accessToken);	// 諛쒓툒 �넗�겙 ���옣
			return accessToken;
		} else {
			return null;
		}
	}
}
