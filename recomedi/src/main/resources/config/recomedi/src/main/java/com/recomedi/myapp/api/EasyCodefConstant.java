package com.recomedi.myapp.api;

/**
 * <pre>
 * io.codef.easycodef
 *   |_ EasyCodefConstant.java
 * </pre>
 * 
 * Desc : EasyCodef瑜� �궗�슜�븯湲� �쐞�빐 �븘�슂�븳 �긽�뭹 �슂泥� 愿��젴 �젙蹂� �겢�옒�뒪
 * @Company : 짤CODEF corp.
 * @Author  : notfound404@codef.io
 * @Date    : Jun 26, 2020 3:36:32 PM
 */
public class EasyCodefConstant {
	
	/**	OAUTH �꽌踰� �룄硫붿씤	*/
	protected static final String OAUTH_DOMAIN = "https://oauth.codef.io";
	
	/**	OAUTH �뿊�꽭�뒪 �넗�겙 諛쒓툒 URL PATH	*/
	protected static final String GET_TOKEN = "/oauth/token";
	
	
	/**	�깒�뱶諛뺤뒪 �꽌踰� �룄硫붿씤	*/
	protected static final String SANDBOX_DOMAIN = "https://sandbox.codef.io";
	
	/**	�깒�뱶諛뺤뒪 �뿊�꽭�뒪 �넗�겙 諛쒓툒�쓣 �쐞�븳 �겢�씪�씠�뼵�듃 �븘�씠�뵒	*/
	protected static final String SANDBOX_CLIENT_ID 	= "ef27cfaa-10c1-4470-adac-60ba476273f9";
	
	/**	�깒�뱶諛뺤뒪 �뿊�꽭�뒪 �넗�겙 諛쒓툒�쓣 �쐞�븳 �겢�씪�씠�뼵�듃 �떆�겕由�	*/
	protected static final String SANDBOX_CLIENT_SECRET 	= "83160c33-9045-4915-86d8-809473cdf5c3";
	
	
	/**	�뜲紐� �꽌踰� �룄硫붿씤	*/
	protected static final String DEMO_DOMAIN = "https://development.codef.io";
	
	/**	�젙�떇 �꽌踰� �룄硫붿씤	*/
	protected static final String API_DOMAIN = "https://api.codef.io";
	
	
	/** �쓳�떟遺� �닔�뻾 寃곌낵 �궎�썙�뱶	*/
	protected static final String RESULT = "result";
	
	/** �쓳�떟遺� �닔�뻾 寃곌낵 硫붿떆吏� 肄붾뱶 �궎�썙�뱶	*/
	protected static final String CODE = "code";

	/** �쓳�떟遺� �닔�뻾 寃곌낵 硫붿떆吏� �궎�썙�뱶	*/
	protected static final String MESSAGE = "message";
	
	/** �쓳�떟遺� �닔�뻾 寃곌낵 異붽� 硫붿떆吏� �궎�썙�뱶	*/
	protected static final String EXTRA_MESSAGE = "extraMessage";
	
	/**	�쓳�떟遺� �닔�뻾 寃곌낵 �뜲�씠�꽣 �궎�썙�뱶	*/
	protected static final String DATA = "data";
	
	/** 怨꾩젙 紐⑸줉  �궎�썙�뱶	*/
	protected static final String ACCOUNT_LIST = "accountList";
	
	protected static final String CONNECTED_ID = "connectedId";
	
	
	/**	�뿊�꽭�뒪 �넗�겙 嫄곗젅 �궗�쑀1	*/
	protected static String INVALID_TOKEN = "invalid_token";
	
	/**	�뿊�꽭�뒪 �넗�겙 嫄곗젅 �궗�쑀2	*/
	protected static String ACCESS_DENIED = "access_denied";
	
	/**	怨꾩젙 �벑濡� URL	*/
	protected static final String CREATE_ACCOUNT = "/v1/account/create";
	
	/**	怨꾩젙 異붽� URL	*/
	protected static final String ADD_ACCOUNT = "/v1/account/add";
	
	/**	怨꾩젙 �닔�젙 URL	*/
	protected static final String UPDATE_ACCOUNT = "/v1/account/update";
	
	/**	怨꾩젙 �궘�젣 URL	*/
	protected static final String DELETE_ACCOUNT = "/v1/account/delete";
	
	/**	怨꾩젙 紐⑸줉 議고쉶 URL	*/
	protected static final String GET_ACCOUNT_LIST = "/v1/account/list";
	
	/**	而ㅻ꽖�떚�뱶 �븘�씠�뵒 紐⑸줉 議고쉶 URL	*/
	protected static final String GET_CID_LIST = "/v1/account/connectedId-list"; 
	
}
