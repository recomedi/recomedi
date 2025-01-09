package com.recomedi.myapp.api;

/**
 * <pre>
 * io.codef.easycodef
 *   |_ EasyCodefProperties.java
 * </pre>
 * 
 * Desc : 肄붾뱶�뿉�봽�쓽 �돩�슫 �궗�슜�쓣 �쐞�븳 �봽濡쒗띁�떚 �겢�옒�뒪 
 * @Company : 짤CODEF corp.
 * @Author  : notfound404@codef.io
 * @Date    : Jun 26, 2020 3:36:51 PM
 */
public class EasyCodefProperties {
	
	//	�뜲紐� �뿊�꽭�뒪 �넗�겙 諛쒓툒�쓣 �쐞�븳 �겢�씪�씠�뼵�듃 �븘�씠�뵒
	private String demoClientId 	= "";
	
	//	�뜲紐� �뿊�꽭�뒪 �넗�겙 諛쒓툒�쓣 �쐞�븳 �겢�씪�씠�뼵�듃 �떆�겕由�
	private String demoClientSecret 	= "";	
	
	//	OAUTH2.0 �뜲紐� �넗�겙
	private String demoAccessToken = "";
	
	//	�젙�떇 �뿊�꽭�뒪 �넗�겙 諛쒓툒�쓣 �쐞�븳 �겢�씪�씠�뼵�듃 �븘�씠�뵒
	private String clientId 	= "";
	
	//	�젙�떇 �뿊�꽭�뒪 �넗�겙 諛쒓툒�쓣 �쐞�븳 �겢�씪�씠�뼵�듃 �떆�겕由�
	private String clientSecret 	= "";	
	
	//	OAUTH2.0 �넗�겙
	private String accessToken = "";
	
	//	RSA�븫�샇�솕瑜� �쐞�븳 �띁釉붾┃�궎
	private String publicKey 	= "";

	
	/**
	 * Desc : �젙�떇�꽌踰� �궗�슜�쓣 �쐞�븳 �겢�씪�씠�뼵�듃 �젙蹂� �꽕�젙
	 * @Company : 짤CODEF corp.
	 * @Author  : notfound404@codef.io
	 * @Date    : Jun 26, 2020 3:37:02 PM
	 * @param clientId
	 * @param clientSecret
	 */
	public void setClientInfo(String clientId, String clientSecret) {
		this.clientId = clientId;
		this.clientSecret = clientSecret;
	}
	
	/**
	 * Desc : �뜲紐⑥꽌踰� �궗�슜�쓣 �쐞�븳 �겢�씪�씠�뼵�듃 �젙蹂� �꽕�젙
	 * @Company : 짤CODEF corp.
	 * @Author  : notfound404@codef.io
	 * @Date    : Jun 26, 2020 3:37:10 PM
	 * @param demoClientId
	 * @param demoClientSecret
	 */
	public void setClientInfoForDemo(String demoClientId, String demoClientSecret) {
		this.demoClientId = demoClientId;
		this.demoClientSecret = demoClientSecret;
	}
	
	/**
	 * Desc : �뜲紐� �겢�씪�씠�뼵�듃 �븘�씠�뵒 諛섑솚
	 * @Company : 짤CODEF corp.
	 * @Author  : notfound404@codef.io
	 * @Date    : Jun 26, 2020 3:37:17 PM
	 * @return
	 */
	public String getDemoClientId() {
		return demoClientId;
	}

	/**
	 * Desc : �뜲紐� �겢�씪�씠�뼵�듃 �떆�겕由� 諛섑솚
	 * @Company : 짤CODEF corp.
	 * @Author  : notfound404@codef.io
	 * @Date    : Jun 26, 2020 3:37:23 PM
	 * @return
	 */
	public String getDemoClientSecret() {
		return demoClientSecret;
	}

	/**
	 * Desc : �뜲紐� �젒�냽 �넗�겙 諛섑솚
	 * @Company : 짤CODEF corp.
	 * @Author  : notfound404@codef.io
	 * @Date    : Jun 26, 2020 3:37:30 PM
	 * @return
	 */
	public String getDemoAccessToken() {
		return demoAccessToken;
	}

	/**
	 * Desc : �뜲紐� �겢�씪�씠�뼵�듃 �떆�겕由� 諛섑솚
	 * @Company : 짤CODEF corp.
	 * @Author  : notfound404@codef.io
	 * @Date    : Jun 26, 2020 3:37:36 PM
	 * @Version : 1.0.1
	 * @return
	 */
	public String getClientId() {
		return clientId;
	}

	/**
	 * Desc : API �겢�씪�씠�뼵�듃 �떆�겕由� 諛섑솚
	 * @Company : 짤CODEF corp.
	 * @Author  : notfound404@codef.io
	 * @Date    : Jun 26, 2020 3:37:44 PM
	 * @return
	 */
	public String getClientSecret() {
		return clientSecret;
	}

	/**
	 * Desc : API �젒�냽 �넗�겙 諛섑솚
	 * @Company : 짤CODEF corp.
	 * @Author  : notfound404@codef.io
	 * @Date    : Jun 26, 2020 3:37:50 PM
	 * @Version : 1.0.1
	 * @return
	 */
	public String getAccessToken() {
		return accessToken;
	}

	/**
	 * Desc : RSA�븫�샇�솕瑜� �쐞�븳 �띁釉붾┃�궎 諛섑솚
	 * @Company : 짤CODEF corp.
	 * @Author  : notfound404@codef.io
	 * @Date    : Jun 26, 2020 3:37:59 PM
	 * @return
	 */
	public String getPublicKey() {
		return publicKey;
	}

	/**
	 * Desc : RSA�븫�샇�솕瑜� �쐞�븳 �띁釉붾┃�궎 �꽕�젙
	 * @Company : 짤CODEF corp.
	 * @Author  : notfound404@codef.io
	 * @Date    : Jun 26, 2020 3:38:07 PM
	 * @Version : 1.0.1
	 * @param publicKey
	 */
	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}

	/**
	 * Desc : �뜲紐� �젒�냽 �넗�겙 �꽕�젙
	 * @Company : 짤CODEF corp.
	 * @Author  : notfound404@codef.io
	 * @Date    : Jun 26, 2020 3:38:14 PM
	 * @param demoAccessToken
	 */
	public void setDemoAccessToken(String demoAccessToken) {
		this.demoAccessToken = demoAccessToken;
	}

	/**
	 * Desc : API �젒�냽 �넗�겙 �꽕�젙
	 * @Company : 짤CODEF corp.
	 * @Author  : notfound404@codef.io
	 * @Date    : Jun 26, 2020 3:38:21 PM
	 * @param accessToken
	 */
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	
	
}
