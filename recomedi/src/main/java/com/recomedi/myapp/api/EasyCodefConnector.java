package com.recomedi.myapp.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;

import org.apache.commons.codec.binary.Base64;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * <pre>
 * io.codef.easycodef
 *   |_ EasyCodefConnector.java
 * </pre>
 * 
 * Desc : CODEF �뿊�꽭�뒪 �넗�겙 諛� �긽�뭹 議고쉶瑜� �쐞�븳 HTTP �슂泥� �겢�옒�뒪
 * @Company : 짤CODEF corp.
 * @Author  : notfound404@codef.io
 * @Date    : Jun 26, 2020 3:35:17 PM
 */
public class EasyCodefConnector {
	private static ObjectMapper mapper = new ObjectMapper();
	private static final int REPEAT_COUNT = 3;
	
	/**
	 * Desc : CODEF �긽�뭹 議고쉶 �슂泥�
	 * @Company : 짤CODEF corp.
	 * @Author  : notfound404@codef.io
	 * @Date    : Jun 26, 2020 3:35:26 PM
	 * @param urlPath
	 * @param serviceType
	 * @param bodyMap
	 * @param properties
	 * @return
	 * @throws InterruptedException
	 */
	@SuppressWarnings("unchecked")
	protected static EasyCodefResponse execute(String urlPath, int serviceType, HashMap<String, Object> bodyMap, EasyCodefProperties properties) throws InterruptedException {
		/**	#1.�넗�겙 泥댄겕	*/
		String domain;
		String clientId;
		String clientSecret;

		if(serviceType == 0) {
			domain = EasyCodefConstant.API_DOMAIN;
			clientId = properties.getClientId();
			clientSecret = properties.getClientSecret();
		} else if(serviceType == 1) {
			domain = EasyCodefConstant.DEMO_DOMAIN;
			clientId = properties.getDemoClientId();
			clientSecret = properties.getDemoClientSecret();
		} else {
			domain = EasyCodefConstant.SANDBOX_DOMAIN;
			clientId = EasyCodefConstant.SANDBOX_CLIENT_ID;
			clientSecret = EasyCodefConstant.SANDBOX_CLIENT_SECRET;
		}
		
		String accessToken = getToken(clientId, clientSecret); // �넗�겙 諛섑솚
		
		/**	#2.�슂泥� �뙆�씪誘명꽣 �씤肄붾뵫	*/
		String bodyString;
		try {
			bodyString = mapper.writeValueAsString(bodyMap);
			bodyString = URLEncoder.encode(bodyString, "UTF-8");
		} catch (JsonProcessingException e) {
			EasyCodefResponse response = new EasyCodefResponse(EasyCodefMessageConstant.INVALID_JSON); 
			return response;
		} catch (UnsupportedEncodingException e) {
			EasyCodefResponse response = new EasyCodefResponse(EasyCodefMessageConstant.UNSUPPORTED_ENCODING); 
			return response;
		}
		
		/**	#3.�긽�뭹 議고쉶 �슂泥�	*/
		HashMap<String, Object> responseMap = requestProduct(domain + urlPath, accessToken, bodyString);
		if(EasyCodefConstant.INVALID_TOKEN.equals(responseMap.get("error")) || "CF-00401".equals(((HashMap<String, Object>)responseMap.get(EasyCodefConstant.RESULT)).get(EasyCodefConstant.CODE))){	// �븸�꽭�뒪 �넗�겙 �쑀�슚湲곌컙 留뚮즺�릺�뿀�쓣 寃쎌슦 �넗�겙 �옱諛쒓툒 �썑 �긽�뭹 議고쉶 �슂泥� 吏꾪뻾
			EasyCodefTokenMap.setToken(clientId, null);		// �넗�겙 �젙蹂� 珥덇린�솕
			accessToken = getToken(clientId, clientSecret); // �넗�겙 �꽕�젙
			responseMap = requestProduct(domain + urlPath, accessToken, bodyString);
		} else if (EasyCodefConstant.ACCESS_DENIED.equals(responseMap.get("error")) || "CF-00403".equals(((HashMap<String, Object>)responseMap.get(EasyCodefConstant.RESULT)).get(EasyCodefConstant.CODE))) {	// �젒洹� 沅뚰븳�씠 �뾾�뒗 寃쎌슦 - �삤瑜섏퐫�뱶 諛섑솚
			EasyCodefResponse response = new EasyCodefResponse(EasyCodefMessageConstant.UNAUTHORIZED, EasyCodefConstant.ACCESS_DENIED); 
			return response;
		}
		
		/**	#4.�긽�뭹 議고쉶 寃곌낵 諛섑솚	*/
		EasyCodefResponse response = new EasyCodefResponse(responseMap); 
		return response;
	}
	
	/**
	 * Desc : CODEF HTTP POST �슂泥�
	 * @Company : 짤CODEF corp.
	 * @Author  : notfound404@codef.io
	 * @Date    : Jun 26, 2020 3:35:34 PM
	 * @param urlPath
	 * @param token
	 * @param bodyString
	 * @return
	 */
	private static HashMap<String, Object> requestProduct(String urlPath, String token, String bodyString) {
		BufferedReader br = null;
		try {
			// HTTP �슂泥��쓣 �쐞�븳 URL �삤釉뚯젥�듃 �깮�꽦
			URL url = new URL(urlPath);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setDoOutput(true);
			con.setRequestMethod("POST");
			con.setRequestProperty("Accept", "application/json");

			if (token != null && !"".equals(token)) {
				con.setRequestProperty("Authorization", "Bearer " + token);		// �뿊�꽭�뒪 �넗�겙 �뿤�뜑 �꽕�젙
			}

			// 由ы�섏뒪�듃 諛붾뵒 �쟾�넚
			OutputStream os = con.getOutputStream();
			if (bodyString != null && !"".equals(bodyString)) {
				os.write(bodyString.getBytes());
			}
			os.flush();
			os.close();

			// �쓳�떟 肄붾뱶 �솗�씤
			int responseCode = con.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) {
				br = new BufferedReader(new InputStreamReader(con.getInputStream())); 
			} else if (responseCode == HttpURLConnection.HTTP_BAD_REQUEST) {
				EasyCodefResponse response = new EasyCodefResponse(EasyCodefMessageConstant.BAD_REQUEST, urlPath); 
				return response;
			} else if (responseCode == HttpURLConnection.HTTP_UNAUTHORIZED) {
				EasyCodefResponse response = new EasyCodefResponse(EasyCodefMessageConstant.UNAUTHORIZED, urlPath); 
				return response; 
			} else if (responseCode == HttpURLConnection.HTTP_FORBIDDEN) {
				EasyCodefResponse response = new EasyCodefResponse(EasyCodefMessageConstant.FORBIDDEN, urlPath); 
				return response; 
			} else if (responseCode == HttpURLConnection.HTTP_NOT_FOUND) {
				EasyCodefResponse response = new EasyCodefResponse(EasyCodefMessageConstant.NOT_FOUND, urlPath); 
				return response; 
			} else {
				EasyCodefResponse response = new EasyCodefResponse(EasyCodefMessageConstant.SERVER_ERROR, urlPath); 
				return response;
			}
			
			// �쓳�떟 諛붾뵒 read
			String inputLine;
			StringBuffer responseStr = new StringBuffer();
			while ((inputLine = br.readLine()) != null) {
				responseStr.append(inputLine);
			}
			br.close();
			
			// 寃곌낵 諛섑솚
			return mapper.readValue(URLDecoder.decode(responseStr.toString(), "UTF-8"), new TypeReference<HashMap<String, Object>>(){});	
		} catch (Exception e) {
			EasyCodefResponse response = new EasyCodefResponse(EasyCodefMessageConstant.LIBRARY_SENDER_ERROR, e.getMessage()); 
			return response;
		} finally {
			if(br != null) {
				try {
					br.close();
				} catch (IOException e) {}
			}
		}
	}
	
	/**
	 * Desc : �뿊�꽭�뒪 �넗�겙 諛섑솚
	 * @Company : 짤CODEF corp.
	 * @Author  : notfound404@codef.io
	 * @Date    : Jun 26, 2020 3:35:47 PM
	 * @param clientId
	 * @param clientSecret
	 * @return
	 * @throws InterruptedException
	 */
	private static String getToken(String clientId, String clientSecret) throws InterruptedException {
		int i = 0;
		String accessToken = EasyCodefTokenMap.getToken(clientId);
		if(accessToken == null || "".equals(accessToken) || !checkToken(accessToken)) { //留뚮즺 議곌굔 異붽�
			while(i < REPEAT_COUNT) {	// �넗�겙 諛쒓툒 �슂泥��� 理쒕� 3�쉶源뚯� �옱�떆�룄
				HashMap<String, Object> tokenMap = publishToken(clientId, clientSecret);	// �넗�겙 諛쒓툒 �슂泥�
				if(tokenMap != null) {
					String newToken = (String)tokenMap.get("access_token");
					EasyCodefTokenMap.setToken(clientId, newToken);	// �넗�겙 ���옣
					accessToken = newToken;
				}
				
				if(accessToken != null || !"".equals(accessToken)) {
					break;	// �젙�긽 諛쒓툒�떆 諛섎났臾� 醫낅즺
				}
				
				Thread.sleep(20);
				i++;
			}
		}
		
		return accessToken;
	}
	
	/**
	 * Desc : CODEF �뿊�꽭�뒪 �넗�겙 諛쒓툒 �슂泥�
	 * @Company : 짤CODEF corp.
	 * @Author  : notfound404@codef.io
	 * @Date    : Jun 26, 2020 3:36:01 PM
	 * @param clientId
	 * @param clientSecret
	 * @return
	 */
	protected static HashMap<String, Object> publishToken(String clientId, String clientSecret) {
		BufferedReader br = null;
		try {
			// HTTP �슂泥��쓣 �쐞�븳 URL �삤釉뚯젥�듃 �깮�꽦
			URL url = new URL(EasyCodefConstant.OAUTH_DOMAIN + EasyCodefConstant.GET_TOKEN);
			String params = "grant_type=client_credentials&scope=read";	// Oauth2.0 �궗�슜�옄 �옄寃⑹쬆紐� 諛⑹떇(client_credentials) �넗�겙 �슂泥� �꽕�젙
			
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("POST");
			con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			
			// �겢�씪�씠�뼵�듃�븘�씠�뵒, �떆�겕由우퐫�뱶 Base64 �씤肄붾뵫
			String auth = clientId + ":" + clientSecret;
			byte[] authEncBytes = Base64.encodeBase64(auth.getBytes());
			String authStringEnc = new String(authEncBytes);
			String authHeader = "Basic " + authStringEnc;
			
			con.setRequestProperty("Authorization", authHeader);
			con.setDoInput(true);
			con.setDoOutput(true);
			
			// 由ы�섏뒪�듃 諛붾뵒 �쟾�넚
			OutputStream os = con.getOutputStream();
			os.write(params.getBytes());
			os.flush();
			os.close();
	
			// �쓳�떟 肄붾뱶 �솗�씤
			int responseCode = con.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) {	// �젙�긽 �쓳�떟
				br = new BufferedReader(new InputStreamReader(con.getInputStream()));
			} else {	 // �뿉�윭 諛쒖깮
				return null;
			}
			
			// �쓳�떟 諛붾뵒 read
			String inputLine;
			StringBuffer responseStr = new StringBuffer();
			while ((inputLine = br.readLine()) != null) {
				responseStr.append(inputLine);
			}
			br.close();
			
			HashMap<String, Object> tokenMap = mapper.readValue(URLDecoder.decode(responseStr.toString(), "UTF-8"), new TypeReference<HashMap<String, Object>>(){});
			return tokenMap;
		} catch (Exception e) {
			return null;
		} finally {
			if(br != null) {
				try {
					br.close();
				} catch (IOException e) { }
			}
		}
	}

	/**
	 * �넗�겙 �쑀�슚湲곌컙 �솗�씤
	 * @param accessToken
	 * @return
	 */
	private static boolean checkToken(String accessToken) {
        HashMap<String, Object> tokenMap = null;
        try {
            tokenMap = EasyCodefUtil.getTokenMap(accessToken);
        } catch (IOException e) {
            // �솗�씤 以� �삤瑜� 諛쒖깮 �떆
            return false;
        }
        // �넗�겙�쓽 �쑀�슚 湲곌컙 �솗�씤
        return EasyCodefUtil.checkValidity((int) (tokenMap.get("exp")));
    }
}
