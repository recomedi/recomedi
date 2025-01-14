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

	//png file 유효성검사메서드 
	
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
	        // 새로운 보안문자 생성 로직 (예: CODEF API 호출)
	        EasyCodefToken tokenService = new EasyCodefToken();
	        String clientId = "fbbcf915-2395-4dfe-9316-a5ce610fab1a";
	        String clientSecret = "2b152335-b63a-4596-bf34-5b44f79b41b0";
	        String accessToken = tokenService.getAccessToken(clientId, clientSecret);

	        // 요청 데이터 구성
	        HashMap<String, Object> requestData = new HashMap<>();
	        requestData.put("refresh", true); // 새로고침 플래그

	        EasyCodefConnector connector = new EasyCodefConnector();
	        ObjectMapper objectMapper = new ObjectMapper();
	        HashMap<String, Object> apiResponse = connector.getRequestProduct(
	                "https://development.codef.io/v1/kr/public/hw/hira-list/my-prescription",
	                accessToken,
	                objectMapper.writeValueAsString(requestData)
	        );

	        System.out.println("새로운 보안문자 응답 데이터: " + apiResponse);

	        // 응답에서 새로운 보안문자 데이터 추출
	        if (apiResponse.containsKey("data")) {
	            HashMap<String, Object> data = (HashMap<String, Object>) apiResponse.get("data");
	            HashMap<String, Object> extraInfo = (HashMap<String, Object>) data.get("extraInfo");

	            if (extraInfo != null && extraInfo.containsKey("reqSecureNo")) {
	                String reqSecureNo = (String) extraInfo.get("reqSecureNo");
	                response.put("reqSecureNoDecoded", reqSecureNo); // Base64 인코딩된 이미지 데이터 반환
	            } else {
	                response.put("error", "새로운 보안문자를 가져올 수 없습니다.");
	            }
	        } else {
	            response.put("error", "API 응답에 데이터가 없습니다.");
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	        response.put("error", "보안문자 생성 중 오류 발생");
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
	        // 주민등록번호 및 휴대폰 번호 합치기
	        String fullIdNumber = idNumberFront + idNumberBack;
	        String fullPhoneNumber = phonePrefix + phoneNumber;

	        // CODEF 요청 데이터 구성
	        HashMap<String, Object> requestData = new HashMap<>();
	        requestData.put("organization", "0020");
	        requestData.put("loginType", "2");
	        requestData.put("identity", fullIdNumber);
	        requestData.put("loginTypeLevel", "1");
	        requestData.put("userName", name);
	        requestData.put("telecom", telecom);
	        requestData.put("phoneNo", fullPhoneNumber);
	        requestData.put("authMethod", "0"); // SMS 인증

	        // CODEF API 호출 준비
	        EasyCodefToken tokenService = new EasyCodefToken();
	        String clientId = "fbbcf915-2395-4dfe-9316-a5ce610fab1a";
	        String clientSecret = "2b152335-b63a-4596-bf34-5b44f79b41b0";

	        String accessToken = tokenService.getAccessToken(clientId, clientSecret);

	        if (accessToken.isEmpty()) {
	            response.put("error", "토큰 발급 실패");
	            return response;
	        }

	        // API 호출
	        EasyCodefConnector connector = new EasyCodefConnector();
	        ObjectMapper objectMapper = new ObjectMapper();

	        String requestBody = objectMapper.writeValueAsString(requestData);

	        HashMap<String, Object> apiResponse = connector.getRequestProduct(
	                "https://development.codef.io/v1/kr/public/hw/hira-list/my-prescription",
	                accessToken,
	                requestBody
	        );

	        System.out.println("CODEF 응답 데이터: " + apiResponse);

	        // 보안문자 처리
	        if (apiResponse.containsKey("data")) {
	            HashMap<String, Object> data = (HashMap<String, Object>) apiResponse.get("data");

	            if (Boolean.TRUE.equals(data.get("continue2Way"))) {
	                // extraInfo 객체 추출
	                HashMap<String, Object> extraInfo = (HashMap<String, Object>) data.get("extraInfo");

	                if (extraInfo != null) {
	                    System.out.println("[DEBUG] extraInfo 객체 확인: " + extraInfo);

	                    String reqSecureNo = (String) extraInfo.get("reqSecureNo"); // reqSecureNo 추출
	                    System.out.println("[DEBUG] reqSecureNo 값: " + reqSecureNo);

	                    if (reqSecureNo != null && !reqSecureNo.isEmpty()) {
	                        // Base64 접두사 제거
	                        if (reqSecureNo.startsWith("data:image/png;base64,")) {
	                            reqSecureNo = reqSecureNo.substring("data:image/png;base64,".length());
	                            System.out.println("[DEBUG] Base64 접두사 제거 후 reqSecureNo 값: " + reqSecureNo);
	                        }

	                        // Base64 유효성 검사
	                        if (!isBase64(reqSecureNo)) {
	                            System.err.println("[ERROR] 유효하지 않은 Base64 문자열입니다.");
	                            response.put("error", "유효하지 않은 Base64 데이터");
	                            return response; // JSON 응답 반환
	                        }

	                        try {
	                            // Base64 디코딩
	                            byte[] decodedBytes = Base64.getDecoder().decode(reqSecureNo);
	                            System.out.println("[DEBUG] 디코딩된 데이터 길이: " + decodedBytes.length);

	                            // PNG 헤더 검증
	                            if (!isPng(decodedBytes)) {
	                                System.err.println("[ERROR] 유효하지 않은 PNG 이미지입니다.");
	                                response.put("error", "유효하지 않은 PNG 이미지");
	                                return response;
	                            }

	                            // 디코딩된 데이터 저장 테스트
	                            try (FileOutputStream fos = new FileOutputStream("decoded_image.png")) {
	                                fos.write(decodedBytes);
	                                System.out.println("[DEBUG] decoded_image.png 파일 저장 완료");
	                            }

	                            session.setAttribute("secureNoImage", reqSecureNo); // 보안문자 이미지 저장
	                            response.put("redirectToSecureInput", true); // 클라이언트에서 처리하도록 플래그 설정
	                            response.put("reqSecureNoDecoded", decodedBytes); // 디코딩된 이미지 데이터 반환

	                        } catch (IllegalArgumentException e) {
	                            System.err.println("[ERROR] Base64 디코딩 실패: " + e.getMessage());
	                        } catch (IOException e) {
	                            System.err.println("[ERROR] 파일 쓰기 실패: " + e.getMessage());
	                        }
	                    } else {
	                        System.err.println("[ERROR] 보안문자 데이터가 없습니다.");
	                    }
	                } else {
	                    System.err.println("[ERROR] extraInfo 객체가 null입니다.");
	                }


	                session.setAttribute("jobIndex", data.get("jobIndex"));
	                session.setAttribute("threadIndex", data.get("threadIndex"));
	                session.setAttribute("jti", data.get("jti"));
	                session.setAttribute("twoWayTimestamp", data.get("twoWayTimestamp"));

	                return response; // JSON 응답 반환
	            }

	            response.putAll(data); // 필요한 추가 데이터 포함
	            response.put("success", true);
	            return response;
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    response.put("error", "요청 처리 중 오류 발생");
	    return response;
	}

	private boolean isBase64(String str) {
	    try {
	        Base64.getDecoder().decode(str); // 디코딩 시도
	        return true; // 디코딩 성공 시 유효한 Base64 문자열
	    } catch (IllegalArgumentException e) {
	        return false; // 디코딩 실패 시 유효하지 않은 문자열
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
	
	    // 추가 인증 요청 데이터 구성
	    HashMap<String, Object> twoWayData = new HashMap<>();
	    twoWayData.put("jobIndex", jobIndex);
	    twoWayData.put("threadIndex", threadIndex);
	    twoWayData.put("jti", jti);
	    twoWayData.put("twoWayTimestamp", twoWayTimestamp);
	    twoWayData.put("secureNo", secureNo);

	    // CODEF API 호출
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

	    System.out.println("추가 인증 응답 데이터: " + response);

	    session.setAttribute("response", response);

	    return "WEB-INF/prescription/finalResult"; // 최종 결과 페이지로 이동
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
	        // Base64 디코딩
	        byte[] decodedBytes = Base64.getDecoder().decode(base64Image);

	        // 디코딩된 이미지 데이터를 반환
	        return decodedBytes;
	    } catch (Exception e) {
	        e.printStackTrace();
	        return null; // 오류 발생 시 null 반환
	    }
	}


	@RequestMapping(value = "additionalCertification.do", method = RequestMethod.GET)
    public String additionalCertification(HttpSession session) {
		 System.out.println("additionalCertification.do enter?");
        // 세션에 저장된 응답 데이터 확인 (필요 시 디버깅용)
        Object response = session.getAttribute("response");
        System.out.println("추가 인증 완료 응답 데이터: " + response);

        // 추가 인증 완료 페이지로 이동
        return "WEB-INF/prescription/additionalCertification";
    }
	
	@RequestMapping(value = "finalResult.do", method = RequestMethod.GET)
    public String finalResult(HttpSession session) {
        // 세션에 저장된 응답 데이터 확인 (디버깅용)
        Object response = session.getAttribute("response");
        System.out.println("최종 결과 응답 데이터: " + response);

        // 최종 결과 페이지로 이동
        return "WEB-INF/prescription/finalResult";
    }
	
}




// 기존코드

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
//        // 입력 데이터 확인
//        System.out.println("입력 데이터:");
//        System.out.println("주민번호 앞자리: " + idNumberFront);
//        System.out.println("주민번호 뒷자리: " + idNumberBack);
//        System.out.println("이름: " + name);
//        System.out.println("휴대폰 번호: " + phonePrefix + phoneNumber);
//
//        // CODEF API 호출 로직 실행...
//    } catch (Exception e) {
//        System.err.println("예외 발생:");
//        e.printStackTrace();
//    }
//
//
//    // 주민등록번호 및 휴대폰 번호 합치기
//    String fullIdNumber = idNumberFront + idNumberBack; 
//    String fullPhoneNumber = phonePrefix + phoneNumber;
//
//    // CODEF 요청 데이터 구성
//    HashMap<String, Object> requestData = new HashMap<>();
//    requestData.put("organization", "0020");
//    requestData.put("loginType", "2");
//    requestData.put("identity", fullIdNumber);
//    requestData.put("loginTypeLevel", "1");
//    requestData.put("userName", name);
//    requestData.put("telecom", telecom);
//    requestData.put("phoneNo", fullPhoneNumber);
//    requestData.put("authMethod", "0"); // SMS 인증
//
//    // CODEF API 호출 준비
//    EasyCodefToken tokenService = new EasyCodefToken();
//    String clientId = "fbbcf915-2395-4dfe-9316-a5ce610fab1a"; 
//    String clientSecret = "2b152335-b63a-4596-bf34-5b44f79b41b0"; 
//    
//    String accessToken = tokenService.getAccessToken(clientId, clientSecret);
//    
//    if (accessToken.isEmpty()) {
//        System.out.println("토큰 발급 실패");
//        return "WEB-INF/prescription/error";
//    }
//
//    // API 호출
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
//    System.out.println("CODEF 응답 데이터: " + response);
//
//    // 보안문자 처리
//    if (response.containsKey("data")) {
//        HashMap<String, Object> data = (HashMap<String, Object>) response.get("data");
//        if (data != null && data.containsKey("reqSecureNo") && data.get("reqSecureNo") != null) {
//            session.setAttribute("secureNoImage", data.get("reqSecureNo")); // 보안문자 이미지 저장
//            session.setAttribute("jobIndex", data.get("jobIndex"));
//            session.setAttribute("threadIndex", data.get("threadIndex"));
//            session.setAttribute("jti", data.get("jti"));
//            session.setAttribute("twoWayTimestamp", data.get("twoWayTimestamp"));
//            return "WEB-INF/prescription/secureInput"; // 보안문자 입력 페이지로 이동
//        }
//    }
//
//    // 추가 인증이 필요하지 않은 경우
//    session.setAttribute("response", response);
//    
//    return "WEB-INF/prescription/additionalCertification";
//}