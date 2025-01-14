package com.recomedi.myapp.controller;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.recomedi.myapp.api.EasyCodefConnector;
import com.recomedi.myapp.api.EasyCodefToken;

@Controller
@RequestMapping(value = "/prescription/")
public class PrescriptionController {
	
	
	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(PrescriptionController.class);

	//png file ��ȿ���˻�޼��� 
	
	private boolean isPng(byte[] data) {
	    byte[] pngHeader = new byte[] {(byte) 0x89, 'P', 'N', 'G', (byte) 0x0D, (byte) 0x0A, (byte) 0x1A, (byte) 0x0A};
	    if (data.length < pngHeader.length) {
	        return false;
	    }
	    for (int i = 0; i < pngHeader.length; i++) {
	        if (data[i] != pngHeader[i]) {
	            return false;
	        }
	    }
	    return true;
	}
	
	
	
	@RequestMapping(value = "refreshSecureNo.do", method = RequestMethod.POST)
	@ResponseBody
	
	
	public HashMap<String, Object> refreshSecureNo(HttpSession session) {
		logger.info("refreshno enter?");
	    HashMap<String, Object> response = new HashMap<>();
	    try {
	        // ���ο� ���ȹ��� ���� ���� (��: CODEF API ȣ��)
	        EasyCodefToken tokenService = new EasyCodefToken();
	        String clientId = "fbbcf915-2395-4dfe-9316-a5ce610fab1a";
	        String clientSecret = "2b152335-b63a-4596-bf34-5b44f79b41b0";
	        String accessToken = tokenService.getAccessToken(clientId, clientSecret);

	        // ��û ������ ����
	        HashMap<String, Object> requestData = new HashMap<>();
	        requestData.put("refresh", true); // ���ΰ�ħ �÷���

	        EasyCodefConnector connector = new EasyCodefConnector();
	        ObjectMapper objectMapper = new ObjectMapper();
	        HashMap<String, Object> apiResponse = connector.getRequestProduct(
	                "https://development.codef.io/v1/kr/public/hw/hira-list/my-prescription",
	                accessToken,
	                objectMapper.writeValueAsString(requestData)
	        );

	        System.out.println("���ο� ���ȹ��� ���� ������: " + apiResponse);

	        // ���信�� ���ο� ���ȹ��� ������ ����
	        if (apiResponse.containsKey("data")) {
	            HashMap<String, Object> data = (HashMap<String, Object>) apiResponse.get("data");
	            HashMap<String, Object> extraInfo = (HashMap<String, Object>) data.get("extraInfo");

	            if (extraInfo != null && extraInfo.containsKey("reqSecureNo")) {
	                String reqSecureNo = (String) extraInfo.get("reqSecureNo");
	                response.put("reqSecureNoDecoded", reqSecureNo); // Base64 ���ڵ��� �̹��� ������ ��ȯ
	            } else {
	                response.put("error", "���ο� ���ȹ��ڸ� ������ �� �����ϴ�.");
	            }
	        } else {
	            response.put("error", "API ���信 �����Ͱ� �����ϴ�.");
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	        response.put("error", "���ȹ��� ���� �� ���� �߻�");
	    }
	    return response;
	}

	
	

	
	


	@RequestMapping(value = "certification.do", method = RequestMethod.GET)
	public String certification() {
		return "WEB-INF/prescription/certification";
	}

	@RequestMapping(value = "processCertification.do", method = RequestMethod.POST)
	@ResponseBody
	public HashMap<String, Object> processCertification(
	        @RequestParam("idNumberFront") String idNumberFront,
	        @RequestParam("idNumberBack") String idNumberBack,
	        @RequestParam("name") String name,
	        @RequestParam("phonePrefix") String phonePrefix,
	        @RequestParam("phoneNumber") String phoneNumber,
	        @RequestParam("telecom") String telecom,
	        HttpSession session) throws JsonProcessingException {

	    HashMap<String, Object> response = new HashMap<>();
	    try {
	        // �ֹε�Ϲ�ȣ �� �޴��� ��ȣ ��ġ��
	        String fullIdNumber = idNumberFront + idNumberBack;
	        String fullPhoneNumber = phonePrefix + phoneNumber;

	        // CODEF ��û ������ ����
	        HashMap<String, Object> requestData = new HashMap<>();
	        requestData.put("organization", "0020");
	        requestData.put("loginType", "2");
	        requestData.put("identity", fullIdNumber);
	        requestData.put("loginTypeLevel", "1");
	        requestData.put("userName", name);
	        requestData.put("telecom", telecom);
	        requestData.put("phoneNo", fullPhoneNumber);
	        requestData.put("authMethod", "0"); // SMS ����

	        // CODEF API ȣ�� �غ�
	        EasyCodefToken tokenService = new EasyCodefToken();
	        String clientId = "fbbcf915-2395-4dfe-9316-a5ce610fab1a";
	        String clientSecret = "2b152335-b63a-4596-bf34-5b44f79b41b0";

	        String accessToken = tokenService.getAccessToken(clientId, clientSecret);

	        if (accessToken.isEmpty()) {
	            response.put("error", "��ū �߱� ����");
	            return response;
	        }

	        // API ȣ��
	        EasyCodefConnector connector = new EasyCodefConnector();
	        ObjectMapper objectMapper = new ObjectMapper();

	        String requestBody = objectMapper.writeValueAsString(requestData);

	        HashMap<String, Object> apiResponse = connector.getRequestProduct(
	                "https://development.codef.io/v1/kr/public/hw/hira-list/my-prescription",
	                accessToken,
	                requestBody
	        );

	        System.out.println("CODEF ���� ������: " + apiResponse);

	        // ���ȹ��� ó��
	        if (apiResponse.containsKey("data")) {
	            HashMap<String, Object> data = (HashMap<String, Object>) apiResponse.get("data");

	            if (Boolean.TRUE.equals(data.get("continue2Way"))) {
	                // extraInfo ��ü ����
	                HashMap<String, Object> extraInfo = (HashMap<String, Object>) data.get("extraInfo");

	                if (extraInfo != null) {
	                    System.out.println("[DEBUG] extraInfo ��ü Ȯ��: " + extraInfo);

	                    String reqSecureNo = (String) extraInfo.get("reqSecureNo"); // reqSecureNo ����
	                    System.out.println("[DEBUG] reqSecureNo ��: " + reqSecureNo);

	                    if (reqSecureNo != null && !reqSecureNo.isEmpty()) {
	                        // Base64 ���λ� ����
	                        if (reqSecureNo.startsWith("data:image/png;base64,")) {
	                            reqSecureNo = reqSecureNo.substring("data:image/png;base64,".length());
	                            System.out.println("[DEBUG] Base64 ���λ� ���� �� reqSecureNo ��: " + reqSecureNo);
	                        }

	                        // Base64 ��ȿ�� �˻�
	                        if (!isBase64(reqSecureNo)) {
	                            System.err.println("[ERROR] ��ȿ���� ���� Base64 ���ڿ��Դϴ�.");
	                            response.put("error", "��ȿ���� ���� Base64 ������");
	                            return response; // JSON ���� ��ȯ
	                        }

	                        try {
	                            // Base64 ���ڵ�
	                            byte[] decodedBytes = Base64.getDecoder().decode(reqSecureNo);
	                            System.out.println("[DEBUG] ���ڵ��� ������ ����: " + decodedBytes.length);

	                            // PNG ��� ����
	                            if (!isPng(decodedBytes)) {
	                                System.err.println("[ERROR] ��ȿ���� ���� PNG �̹����Դϴ�.");
	                                response.put("error", "��ȿ���� ���� PNG �̹���");
	                                return response;
	                            }

	                            // ���ڵ��� ������ ���� �׽�Ʈ
	                            try (FileOutputStream fos = new FileOutputStream("decoded_image.png")) {
	                                fos.write(decodedBytes);
	                                System.out.println("[DEBUG] decoded_image.png ���� ���� �Ϸ�");
	                            }

	                            session.setAttribute("secureNoImage", reqSecureNo); // ���ȹ��� �̹��� ����
	                            response.put("redirectToSecureInput", true); // Ŭ���̾�Ʈ���� ó���ϵ��� �÷��� ����
	                            response.put("reqSecureNoDecoded", decodedBytes); // ���ڵ��� �̹��� ������ ��ȯ

	                        } catch (IllegalArgumentException e) {
	                            System.err.println("[ERROR] Base64 ���ڵ� ����: " + e.getMessage());
	                        } catch (IOException e) {
	                            System.err.println("[ERROR] ���� ���� ����: " + e.getMessage());
	                        }
	                    } else {
	                        System.err.println("[ERROR] ���ȹ��� �����Ͱ� �����ϴ�.");
	                    }
	                } else {
	                    System.err.println("[ERROR] extraInfo ��ü�� null�Դϴ�.");
	                }


	                session.setAttribute("jobIndex", data.get("jobIndex"));
	                session.setAttribute("threadIndex", data.get("threadIndex"));
	                session.setAttribute("jti", data.get("jti"));
	                session.setAttribute("twoWayTimestamp", data.get("twoWayTimestamp"));

	                return response; // JSON ���� ��ȯ
	            }

	            response.putAll(data); // �ʿ��� �߰� ������ ����
	            response.put("success", true);
	            return response;
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    response.put("error", "��û ó�� �� ���� �߻�");
	    return response;
	}

	private boolean isBase64(String str) {
	    try {
	        Base64.getDecoder().decode(str); // ���ڵ� �õ�
	        return true; // ���ڵ� ���� �� ��ȿ�� Base64 ���ڿ�
	    } catch (IllegalArgumentException e) {
	        return false; // ���ڵ� ���� �� ��ȿ���� ���� ���ڿ�
	    }
	}

	
	
	@RequestMapping(value = "processSecureInput.do", method = RequestMethod.POST)
	public String processSecureInput(
	        @RequestParam("jobIndex") String jobIndex,
	        @RequestParam("threadIndex") String threadIndex,
	        @RequestParam("jti") String jti,
	        @RequestParam("twoWayTimestamp") String twoWayTimestamp,
	        @RequestParam("secureNo") String secureNo,
	        HttpSession session) throws JsonProcessingException {
	
	    // �߰� ���� ��û ������ ����
	    HashMap<String, Object> twoWayData = new HashMap<>();
	    twoWayData.put("jobIndex", jobIndex);
	    twoWayData.put("threadIndex", threadIndex);
	    twoWayData.put("jti", jti);
	    twoWayData.put("twoWayTimestamp", twoWayTimestamp);
	    twoWayData.put("secureNo", secureNo);

	    // CODEF API ȣ��
	    EasyCodefToken tokenService = new EasyCodefToken();
	    ObjectMapper objectMapper = new ObjectMapper();
	    String clientId = "fbbcf915-2395-4dfe-9316-a5ce610fab1a"; 
	    String clientSecret = "2b152335-b63a-4596-bf34-5b44f79b41b0"; 
	    EasyCodefConnector connector = new EasyCodefConnector();
	    String accessToken = tokenService.getAccessToken(clientId, clientSecret);
	    
	    
	    
	    HashMap<String, Object> response = connector.getRequestProduct(
	        "https://development.codef.io/v1/kr/public/hw/hira-list/my-prescription",
	        accessToken,
	        objectMapper.writeValueAsString(twoWayData)
	    );

	    System.out.println("�߰� ���� ���� ������: " + response);

	    session.setAttribute("response", response);

	    return "WEB-INF/prescription/finalResult"; // ���� ��� �������� �̵�
	}
	
	
	
	@RequestMapping(value = "verifySmsCode.do", method = RequestMethod.POST)
	@ResponseBody
	public HashMap<String, Object> verifySmsCode(
	        @RequestParam("smsAuthNumber") String smsAuthNumber,
	        HttpSession session) {

	   HashMap<String, Object> response = new HashMap<>();
	   String expectedCode = (String) session.getAttribute("smsCode"); 

	   if (expectedCode != null && expectedCode.equals(smsAuthNumber)) {
	       response.put("verified", true); 
	   } else {
	       response.put("verified", false); 
	   }

	   return response; 
	}
	
	
	@RequestMapping(value = "/decodeSecureNo", method = RequestMethod.POST, produces = "image/png")
	@ResponseBody
	public byte[] decodeSecureNo(@RequestParam("base64Image") String base64Image) {
	    try {
	        // Base64 ���ڵ�
	        byte[] decodedBytes = Base64.getDecoder().decode(base64Image);

	        // ���ڵ��� �̹��� �����͸� ��ȯ
	        return decodedBytes;
	    } catch (Exception e) {
	        e.printStackTrace();
	        return null; // ���� �߻� �� null ��ȯ
	    }
	}


	@RequestMapping(value = "additionalCertification.do", method = RequestMethod.GET)
    public String additionalCertification(HttpSession session) {
		 System.out.println("additionalCertification.do enter?");
        // ���ǿ� ����� ���� ������ Ȯ�� (�ʿ� �� ������)
        Object response = session.getAttribute("response");
        System.out.println("�߰� ���� �Ϸ� ���� ������: " + response);

        // �߰� ���� �Ϸ� �������� �̵�
        return "WEB-INF/prescription/additionalCertification";
    }
	
	@RequestMapping(value = "finalResult.do", method = RequestMethod.GET)
    public String finalResult(HttpSession session) {
        // ���ǿ� ����� ���� ������ Ȯ�� (������)
        Object response = session.getAttribute("response");
        System.out.println("���� ��� ���� ������: " + response);

        // ���� ��� �������� �̵�
        return "WEB-INF/prescription/finalResult";
    }
	
}




// �����ڵ�

//@RequestMapping(value = "processCertification.do", method = RequestMethod.POST)
//public String processCertification(
//        @RequestParam("idNumberFront") String idNumberFront,
//        @RequestParam("idNumberBack") String idNumberBack,
//        @RequestParam("name") String name,
//        @RequestParam("phonePrefix") String phonePrefix,
//        @RequestParam("phoneNumber") String phoneNumber,
//        @RequestParam("telecom") String telecom,
//        HttpSession session) throws JsonProcessingException {
//	
//	try {
//        // �Է� ������ Ȯ��
//        System.out.println("�Է� ������:");
//        System.out.println("�ֹι�ȣ ���ڸ�: " + idNumberFront);
//        System.out.println("�ֹι�ȣ ���ڸ�: " + idNumberBack);
//        System.out.println("�̸�: " + name);
//        System.out.println("�޴��� ��ȣ: " + phonePrefix + phoneNumber);
//
//        // CODEF API ȣ�� ���� ����...
//    } catch (Exception e) {
//        System.err.println("���� �߻�:");
//        e.printStackTrace();
//    }
//
//
//    // �ֹε�Ϲ�ȣ �� �޴��� ��ȣ ��ġ��
//    String fullIdNumber = idNumberFront + idNumberBack; 
//    String fullPhoneNumber = phonePrefix + phoneNumber;
//
//    // CODEF ��û ������ ����
//    HashMap<String, Object> requestData = new HashMap<>();
//    requestData.put("organization", "0020");
//    requestData.put("loginType", "2");
//    requestData.put("identity", fullIdNumber);
//    requestData.put("loginTypeLevel", "1");
//    requestData.put("userName", name);
//    requestData.put("telecom", telecom);
//    requestData.put("phoneNo", fullPhoneNumber);
//    requestData.put("authMethod", "0"); // SMS ����
//
//    // CODEF API ȣ�� �غ�
//    EasyCodefToken tokenService = new EasyCodefToken();
//    String clientId = "fbbcf915-2395-4dfe-9316-a5ce610fab1a"; 
//    String clientSecret = "2b152335-b63a-4596-bf34-5b44f79b41b0"; 
//    
//    String accessToken = tokenService.getAccessToken(clientId, clientSecret);
//    
//    if (accessToken.isEmpty()) {
//        System.out.println("��ū �߱� ����");
//        return "WEB-INF/prescription/error";
//    }
//
//    // API ȣ��
//    EasyCodefConnector connector = new EasyCodefConnector();
//    ObjectMapper objectMapper = new ObjectMapper();
//    
//    String requestBody = objectMapper.writeValueAsString(requestData);
//
//    HashMap<String, Object> response = connector.getRequestProduct(
//        "https://development.codef.io/v1/kr/public/hw/hira-list/my-prescription",
//        accessToken,
//        requestBody
//    );
//
//    System.out.println("CODEF ���� ������: " + response);
//
//    // ���ȹ��� ó��
//    if (response.containsKey("data")) {
//        HashMap<String, Object> data = (HashMap<String, Object>) response.get("data");
//        if (data != null && data.containsKey("reqSecureNo") && data.get("reqSecureNo") != null) {
//            session.setAttribute("secureNoImage", data.get("reqSecureNo")); // ���ȹ��� �̹��� ����
//            session.setAttribute("jobIndex", data.get("jobIndex"));
//            session.setAttribute("threadIndex", data.get("threadIndex"));
//            session.setAttribute("jti", data.get("jti"));
//            session.setAttribute("twoWayTimestamp", data.get("twoWayTimestamp"));
//            return "WEB-INF/prescription/secureInput"; // ���ȹ��� �Է� �������� �̵�
//        }
//    }
//
//    // �߰� ������ �ʿ����� ���� ���
//    session.setAttribute("response", response);
//    
//    return "WEB-INF/prescription/additionalCertification";
//}