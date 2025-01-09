package com.recomedi.myapp.api;

/**
 * <pre>
 * io.codef.easycodef
 *   |_ EasyCodefMessageConstant.java
 * </pre>
 * 
 * Desc : EasyCodef�뿉�꽌 �궗�슜�릺�뒗 硫붿떆吏� 肄붾뱶 �겢�옒�뒪
 * @Company : 짤CODEF corp.
 * @Author  : notfound404@codef.io
 * @Date    : Jun 26, 2020 3:36:41 PM
 */
public enum EasyCodefMessageConstant {

	OK("CF-00000", "�꽦怨�"),
	
	INVALID_JSON("CF-00002", "json�삎�떇�씠 �삱諛붾Ⅴ吏� �븡�뒿�땲�떎."),
	INVALID_PARAMETER("CF-00007", "�슂泥� �뙆�씪誘명꽣媛� �삱諛붾Ⅴ吏� �븡�뒿�땲�떎."),
	UNSUPPORTED_ENCODING("CF-00009", "吏��썝�븯吏� �븡�뒗 �삎�떇�쑝濡� �씤肄붾뵫�맂 臾몄옄�뿴�엯�땲�떎."),
	
	EMPTY_CLIENT_INFO("CF-00014", "�긽�뭹 �슂泥��쓣 �쐞�빐�꽌�뒗 �겢�씪�씠�뼵�듃 �젙蹂닿� �븘�슂�빀�땲�떎. �겢�씪�씠�뼵�듃 �븘�씠�뵒�� �떆�겕由� �젙蹂대�� �꽕�젙�븯�꽭�슂."),
	EMPTY_PUBLIC_KEY("CF-00015", "�긽�뭹 �슂泥��쓣 �쐞�빐�꽌�뒗 �띁釉붾┃�궎媛� �븘�슂�빀�땲�떎. �띁釉붾┃�궎 �젙蹂대�� �꽕�젙�븯�꽭�슂."),
	
	INVALID_2WAY_INFO("CF-03003", "2WAY �슂泥� 泥섎━瑜� �쐞�븳 �젙蹂닿� �삱諛붾Ⅴ吏� �븡�뒿�땲�떎. �쓳�떟�쑝濡� 諛쏆� �빆紐⑹쓣 洹몃�濡� 2way�슂泥� �빆紐⑹뿉 �룷�븿�빐�빞 �빀�땲�떎."),
	INVALID_2WAY_KEYWORD("CF-03004", "異붽� �씤利�(2Way)�쓣 �쐞�븳 �슂泥��� requestCertification硫붿꽌�뱶瑜� �궗�슜�빐�빞 �빀�땲�떎."),
	
	BAD_REQUEST("CF-00400", "�겢�씪�씠�뼵�듃 �슂泥� �삤瑜섎줈 �씤�빐 �슂泥��쓣 泥섎━ �븷 �닔 �뗢�뗭뾾�뒿�땲�떎."),
	UNAUTHORIZED("CF-00401", "�슂泥� 沅뚰븳�씠 �뾾�뒿�땲�떎."),
	FORBIDDEN("CF-00403", "�옒紐삳맂 �슂泥��엯�땲�떎."),
	NOT_FOUND("CF-00404", "�슂泥��븯�떊 �럹�씠吏�(Resource)瑜� 李얠쓣 �닔 �뾾�뒿�땲�떎."),
	METHOD_NOT_ALLOWED("CF-00405", "�슂泥��븯�떊 諛⑸쾿(Method)�씠 �옒紐삳릺�뿀�뒿�땲�떎."),
	
	
	LIBRARY_SENDER_ERROR("CF-09980", "�넻�떊 �슂泥��뿉 �떎�뙣�뻽�뒿�땲�떎. �쓳�떟�젙蹂대�� �솗�씤�븯�떆怨� �삱諛붾Ⅸ �슂泥��쓣 �떆�룄�븯�꽭�슂."),
	SERVER_ERROR("CF-09999", "�꽌踰� 泥섎━以� �뿉�윭媛� 諛쒖깮 �뻽�뒿�땲�떎. 愿�由ъ옄�뿉寃� 臾몄쓽�븯�꽭�슂."),
	
	;
	
	
	
	private String code;
	private String message;
	private String extraMessage;
	
	private EasyCodefMessageConstant(String code, String message) {
		this.code = code;
		this.message = message;
	}

	protected String getCode() {
		return code;
	}

	protected String getMessage() {
		return message;
	}

	protected void setExtraMessage(String extraMessage) {
		this.extraMessage = extraMessage;
	}

	protected String getExtraMessage() {
		return extraMessage;
	}
}
