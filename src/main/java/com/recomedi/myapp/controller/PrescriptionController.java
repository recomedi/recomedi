package com.recomedi.myapp.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.System.Logger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.recomedi.myapp.api.EasyCodefConnector;
import com.recomedi.myapp.api.EasyCodefToken;

@Controller
@RequestMapping(value = "/medicine/")
public class PrescriptionController {
	
	 private HashMap<String, Object> requestData = new HashMap<>();
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
	
	
	@RequestMapping(value = "/saveDecodedImage", method = RequestMethod.POST)
	@ResponseBody
	public String saveDecodedImage(@RequestParam("base64Image") String base64Image) {
	    try {
	        // Base64 디코딩
	        byte[] decodedBytes = Base64.getDecoder().decode(base64Image);

	        // PNG 헤더 검증
	        byte[] pngHeader = new byte[] {(byte) 0x89, 'P', 'N', 'G', (byte) 0x0D, (byte) 0x0A, (byte) 0x1A, (byte) 0x0A};
	        for (int i = 0; i < pngHeader.length; i++) {
	            if (decodedBytes[i] != pngHeader[i]) {
	                throw new IllegalArgumentException("유효하지 않은 PNG 파일입니다.");
	            }
	        }

	        // 저장 경로 설정
	        String uploadDirPath = "src/main/webapp/resources/decoded_images";
	        File uploadDir = new File(uploadDirPath);

	        // 디렉토리가 없으면 생성
	        if (!uploadDir.exists()) {
	            boolean created = uploadDir.mkdirs();
	            if (!created) {
	                throw new IOException("디렉토리를 생성할 수 없습니다: " + uploadDirPath);
	            }
	        }

	        // 파일 저장
	        String fileName = "decoded_image.png";
	        File outputFile = new File(uploadDir, fileName);
	        try (FileOutputStream fos = new FileOutputStream(outputFile)) {
	            fos.write(decodedBytes);
	            System.out.println("[DEBUG] 파일 저장 완료: " + outputFile.getAbsolutePath());
	        }

	        return "파일이 성공적으로 저장되었습니다: " + outputFile.getAbsolutePath();

	    } catch (IllegalArgumentException e) {
	        System.err.println("[ERROR] " + e.getMessage());
	        return "유효하지 않은 PNG 데이터입니다.";
	    } catch (IOException e) {
	        e.printStackTrace();
	        return "파일 저장 중 오류가 발생했습니다.";
	    }
	}

	
	
	
	@RequestMapping(value = "refreshSecureNo.do", method = RequestMethod.POST)
	@ResponseBody
	public HashMap<String, Object> refreshSecureNo(HttpSession session) {
	    logger.info("[DEBUG] refreshSecureNo enter?");
	    HashMap<String, Object> response = new HashMap<>();
	    try {
	        // 세션에서 필수 데이터 가져오기
	        @SuppressWarnings("unchecked")
	        HashMap<String, Object> requestData = (HashMap<String, Object>) session.getAttribute("secureNoRequestData");

	        if (requestData == null) {
	            response.put("error", "필수 입력값이 누락되었습니다. 다시 인증을 진행하세요.");
	            return response;
	        }

	        // 새로고침 플래그 추가
	        requestData.put("refresh", true);

	        // CODEF API 호출 준비
	        EasyCodefToken tokenService = new EasyCodefToken();
	        String clientId = "fbbcf915-2395-4dfe-9316-a5ce610fab1a";
	        String clientSecret = "2b152335-b63a-4596-bf34-5b44f79b41b0";
	        String accessToken = tokenService.getAccessToken(clientId, clientSecret);

	        if (accessToken.isEmpty()) {
	            response.put("error", "토큰 발급 실패");
	            return response;
	        }

	        // CODEF API 호출
	        EasyCodefConnector connector = new EasyCodefConnector();
	        ObjectMapper objectMapper = new ObjectMapper();
	        String requestBody = objectMapper.writeValueAsString(requestData);

	        HashMap<String, Object> apiResponse = connector.getRequestProduct(
	                "https://development.codef.io/v1/kr/public/hw/hira-list/my-medicine",
	                accessToken,
	                requestBody
	        );

	        System.out.println("[DEBUG] CODEF 응답 데이터: " + apiResponse);

	        // 응답에서 새로운 보안문자 데이터 추출
	        if (apiResponse.containsKey("data")) {
	            HashMap<String, Object> data = (HashMap<String, Object>) apiResponse.get("data");
	            HashMap<String, Object> extraInfo = (HashMap<String, Object>) data.get("extraInfo");

	            if (extraInfo != null && extraInfo.containsKey("reqSecureNo")) {
	                String reqSecureNo = (String) extraInfo.get("reqSecureNo");

	                // 순수 Base64 데이터만 반환
	                response.put("reqSecureNoDecoded", reqSecureNo);
	                logger.info("[DEBUG] 새로운 보안문자 생성 성공.");
	            } else {
	                response.put("error", "새로운 보안문자를 가져올 수 없습니다.");
	                logger.error("[ERROR] 새로운 보안문자 생성 실패.");
	            }


	        } else {
	            response.put("error", "API 응답에 데이터가 없습니다.");
	            logger.error("[ERROR] API 응답에 데이터가 없습니다.");
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	        response.put("error", "보안문자 생성 중 오류 발생");
	    }
	    return response;
	}

	
	public HashMap<String, Object> callCodefApi(String accessToken, HashMap<String, Object> requestData) {
	    HashMap<String, Object> response = new HashMap<>();
	    try {
	        EasyCodefConnector connector = new EasyCodefConnector();
	        ObjectMapper objectMapper = new ObjectMapper();

	        // CODEF API 호출
	        HashMap<String, Object> apiResponse = connector.getRequestProduct(
	                "https://development.codef.io/v1/kr/public/hw/hira-list/my-medicine",
	                accessToken,
	                objectMapper.writeValueAsString(requestData)
	        );

	        System.out.println("[DEBUG] CODEF 응답 데이터: " + apiResponse);

	        // 응답 처리
	        if (apiResponse.containsKey("data")) {
	            HashMap<String, Object> data = (HashMap<String, Object>) apiResponse.get("data");
	            response.putAll(data);
	        } else {
	            response.put("error", "API 응답에 데이터가 없습니다.");
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        response.put("error", "CODEF API 호출 중 오류 발생: " + e.getMessage());
	    }
	    return response;
	}

	
	


	@RequestMapping(value = "certification.do", method = RequestMethod.GET)
	public String certification() {
		return "WEB-INF/medicine/certification";
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
	        requestData.put("organization", "0020"); // 기관 코드
	        requestData.put("loginType", "2");
	        requestData.put("identity", fullIdNumber); // 주민등록번호
	        requestData.put("loginTypeLevel", "1");
	        requestData.put("userName", name);
	        requestData.put("telecom", telecom);
	        requestData.put("phoneNo", fullPhoneNumber);
	        requestData.put("authMethod", "0"); // SMS 인증

	        // 세션에 필수 데이터 저장 (보안문자 새로고침용)
	        session.setAttribute("secureNoRequestData", requestData);

	        // CODEF API 호출 준비
	        EasyCodefToken tokenService = new EasyCodefToken();
	        String clientId = "fbbcf915-2395-4dfe-9316-a5ce610fab1a";
	        String clientSecret = "2b152335-b63a-4596-bf34-5b44f79b41b0";
	        String accessToken = tokenService.getAccessToken(clientId, clientSecret);
	        
	        

	        if (accessToken.isEmpty()) {
	            response.put("error", "토큰 발급 실패");
	            return response;
	        }

	        // CODEF API 호출
	        EasyCodefConnector connector = new EasyCodefConnector();
	        ObjectMapper objectMapper = new ObjectMapper();

	        String requestBody = objectMapper.writeValueAsString(requestData);

	        HashMap<String, Object> apiResponse = connector.getRequestProduct(
	                "https://development.codef.io/v1/kr/public/hw/hira-list/my-medicine",
	                accessToken,
	                requestBody
	        );

	        System.out.println("[DEBUG] CODEF 응답 데이터: " + apiResponse);

	        // 보안문자 처리
	        if (apiResponse.containsKey("data")) {
	            HashMap<String, Object> data = (HashMap<String, Object>) apiResponse.get("data");

	            if (Boolean.TRUE.equals(data.get("continue2Way"))) {
	                // 추가 인증이 필요한 경우
	                HashMap<String, Object> extraInfo = (HashMap<String, Object>) data.get("extraInfo");

	                if (extraInfo != null) {
	                    System.out.println("[DEBUG] extraInfo 객체 확인: " + extraInfo);

	                    String reqSecureNo = (String) extraInfo.get("reqSecureNo"); // 보안문자 데이터 추출
	                    System.out.println("[DEBUG] reqSecureNo 값: " + reqSecureNo);

	                    if (reqSecureNo != null && !reqSecureNo.isEmpty()) {
	                        if (reqSecureNo.startsWith("data:image/png;base64,")) {
	                            reqSecureNo = reqSecureNo.substring("data:image/png;base64,".length());
	                        }

	                        byte[] decodedBytes = Base64.getDecoder().decode(reqSecureNo);

	                        if (!isPng(decodedBytes)) {
	                            response.put("error", "유효하지 않은 PNG 이미지");
	                            return response;
	                        }

	                        String reEncodedBase64 = "data:image/png;base64," + Base64.getEncoder().encodeToString(decodedBytes);
	                        response.put("reqSecureNoDecoded", reEncodedBase64); // 클라이언트로 반환
	                        response.put("redirectToSecureInput", true); // 추가 인증 플래그 설정

	                        // 세션에 추가 인증 관련 데이터 저장
	                        session.setAttribute("jobIndex", data.get("jobIndex"));
	                        session.setAttribute("threadIndex", data.get("threadIndex"));
	                        session.setAttribute("jti", data.get("jti"));
	                        session.setAttribute("twoWayTimestamp", data.get("twoWayTimestamp"));
	                    } else {
	                        System.err.println("[ERROR] 보안문자 데이터가 없습니다.");
	                    }
	                } else {
	                    System.err.println("[ERROR] extraInfo 객체가 null입니다.");
	                }
	            } else {
	                // 추가 인증이 필요하지 않은 경우
	                response.put("redirectToSecureInput", false);
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
	@ResponseBody
	public HashMap<String, Object> processSecureInput(
	        @RequestParam("secureNo") String secureNo,
	        @RequestParam("secureNoRefresh") String secureNoRefresh,
	        @RequestParam("is2Way") boolean is2Way,
	        HttpSession session) throws JsonProcessingException {

	    HashMap<String, Object> response = new HashMap<>();
	    try {
	        // 세션에서 필수 데이터 가져오기
	        Integer jobIndex = (Integer) session.getAttribute("jobIndex");
	        Integer threadIndex = (Integer) session.getAttribute("threadIndex");
	        String jti = (String) session.getAttribute("jti");
	        Long twoWayTimestamp = (Long) session.getAttribute("twoWayTimestamp");

	        if (jobIndex == null || threadIndex == null || jti == null || twoWayTimestamp == null) {
	            response.put("error", "필수 입력값이 누락되었습니다. 다시 인증을 진행하세요.");
	            return response;
	        }

	        // 세션에 저장된 요청 데이터 가져오기
	        @SuppressWarnings("unchecked")
	        HashMap<String, Object> requestData = (HashMap<String, Object>) session.getAttribute("secureNoRequestData");

	        if (requestData == null || !requestData.containsKey("organization")) {
	            response.put("error", "필수 입력값이 누락되었습니다.");
	            return response;
	        }

	        // 추가 인증 요청 데이터 구성
	        HashMap<String, Object> twoWayData = new HashMap<>();
	        twoWayData.put("secureNo", secureNo); // 보안문자 정보
	        twoWayData.put("secureNoRefresh", secureNoRefresh); // 새로고침 정보
	        twoWayData.put("is2Way", true); // 추가 요청 여부

	        // 1차 요청 응답 값(twoWayInfo)에 포함
	        HashMap<String, Object> twoWayInfo = new HashMap<>();
	        twoWayInfo.put("jobIndex", jobIndex);
	        twoWayInfo.put("threadIndex", threadIndex);
	        twoWayInfo.put("jti", jti);
	        twoWayInfo.put("twoWayTimestamp", twoWayTimestamp);
	        response.put("jti", jti);

	        // 두웨이 정보 포함
	        twoWayData.put("twoWayInfo", twoWayInfo);

	        // 필수 파라미터 추가
	        twoWayData.put("organization", requestData.get("organization")); // 세션에서 가져온 organization 값

	        // CODEF API 호출 준비
	        EasyCodefToken tokenService = new EasyCodefToken();
	        String clientId = "fbbcf915-2395-4dfe-9316-a5ce610fab1a";
	        String clientSecret = "2b152335-b63a-4596-bf34-5b44f79b41b0";
	        String accessToken = tokenService.getAccessToken(clientId, clientSecret);

	        if (accessToken.isEmpty()) {
	            response.put("error", "토큰 발급 실패");
	            return response;
	        }

	        // 디버깅 로그
	        System.out.println("[DEBUG] 추가 인증 요청 데이터: " + twoWayData);

	        // CODEF API 호출
	        EasyCodefConnector connector = new EasyCodefConnector();
	        ObjectMapper objectMapper = new ObjectMapper();

	        String requestBody = objectMapper.writeValueAsString(twoWayData);

	        HashMap<String, Object> apiResponse = connector.getRequestProduct(
	                "https://development.codef.io/v1/kr/public/hw/hira-list/my-medicine",
	                accessToken,
	                requestBody
	        );

	        System.out.println("[DEBUG] 추가 인증 응답 데이터: " + apiResponse);

	        // 응답 처리
	        if (apiResponse.containsKey("result")) {
	            HashMap<String, Object> result = (HashMap<String, Object>) apiResponse.get("result");
	            if ("CF-00000".equals(result.get("code"))) { // 성공 코드
	                response.put("success", true);
	                response.putAll(apiResponse);
	            } else {
	                response.put("success", false);
	                response.put("errorMessage", result.getOrDefault("extraMessage", "추가 인증 실패"));
	            }
	            return response;
	        } else {
	            response.put("success", false);
	            response.put("errorMessage", "API 응답에 result 데이터가 없습니다.");
	            return response;
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	        response.put("success", false);
	        response.put("errorMessage", "추가 인증 중 오류 발생: " + e.getMessage());
	    }

	    return response;
	}




	
	
	
	@RequestMapping(value = "verifySmsCode.do", method = RequestMethod.POST)
	@ResponseBody
	public HashMap<String, Object> verifySmsCode(
	        @RequestParam("smsAuthNo") String smsAuthNo,
	        @RequestParam("is2Way") boolean is2Way,
	        HttpSession session) {

	    HashMap<String, Object> response = new HashMap<>();
	    try {
	        // 세션에서 추가 인증 정보 가져오기
	        Integer jobIndex = (Integer) session.getAttribute("jobIndex");
	        Integer threadIndex = (Integer) session.getAttribute("threadIndex");
	        String jti = (String) session.getAttribute("jti");
	        Long twoWayTimestamp = (Long) session.getAttribute("twoWayTimestamp");

	        // 디버깅 로그: 세션 값 검증
	        System.out.println("[DEBUG] 세션 값 검증:");
	        System.out.println("jobIndex: " + jobIndex);
	        System.out.println("threadIndex: " + threadIndex);
	        System.out.println("jti: " + jti);
	        System.out.println("twoWayTimestamp: " + twoWayTimestamp);

	        // 세션 값 검증 실패 시 처리
	        if (jobIndex == null || threadIndex == null || jti == null || twoWayTimestamp == null) {
	            response.put("verified", false);
	            response.put("message", "필수 인증 정보가 누락되었습니다. 다시 시도하세요.");
	            System.err.println("[ERROR] 세션 값이 누락되었습니다.");
	            return response;
	        }

	        // 세션에서 secureNoRequestData 가져오기
	        @SuppressWarnings("unchecked")
	        HashMap<String, Object> requestData = (HashMap<String, Object>) session.getAttribute("secureNoRequestData");
	        System.out.println("[DEBUG] 세션에서 가져온 requestData: " + requestData);

	        if (requestData == null || !requestData.containsKey("organization")) {
	            response.put("verified", false);
	            response.put("message", "필수 입력값이 누락되었습니다. 다시 시도하세요.");
	            System.err.println("[ERROR] 세션 데이터 누락 또는 organization 키 없음.");
	            return response;
	        }

	        // 문자 발송 서버에 보낼 데이터 구성
	        HashMap<String, Object> verificationRequest = new HashMap<>();
	        verificationRequest.put("smsAuthNo", smsAuthNo);
	        verificationRequest.put("is2Way", is2Way);

	        HashMap<String, Object> twoWayInfo = new HashMap<>();
	        twoWayInfo.put("jobIndex", jobIndex);
	        twoWayInfo.put("threadIndex", threadIndex);
	        twoWayInfo.put("jti", jti);
	        twoWayInfo.put("twoWayTimestamp", twoWayTimestamp);

	        verificationRequest.put("twoWayInfo", twoWayInfo); // 두웨이 정보 포함

	        // 필수 파라미터 추가
	        verificationRequest.put("organization", requestData.get("organization")); // 조직 정보 추가

	        // 디버깅 로그: CODEF API 요청 데이터 출력
	        System.out.println("[DEBUG] CODEF API 요청 데이터: " + verificationRequest);

	        // CODEF API 호출 준비
	        EasyCodefToken tokenService = new EasyCodefToken();
	        String clientId = "fbbcf915-2395-4dfe-9316-a5ce610fab1a";
	        String clientSecret = "2b152335-b63a-4596-bf34-5b44f79b41b0";
	        String accessToken = tokenService.getAccessToken(clientId, clientSecret);

	        if (accessToken.isEmpty()) {
	            response.put("error", "토큰 발급 실패");
	            return response;
	        }

	        // CODEF API 호출
	        EasyCodefConnector connector = new EasyCodefConnector();
	        ObjectMapper objectMapper = new ObjectMapper();
	        
	        String requestBody = objectMapper.writeValueAsString(verificationRequest);

	        HashMap<String, Object> apiResponse = connector.getRequestProduct(
	                "https://development.codef.io/v1/kr/public/hw/hira-list/my-medicine",
	                accessToken,
	                requestBody
	        );

	        // 디버깅 로그: CODEF API 응답 데이터 출력
	        System.out.println("[DEBUG] CODEF API 응답 데이터: " + apiResponse);

	     // CODEF API 응답 처리
	        HashMap<String, Object> result = (HashMap<String, Object>) apiResponse.get("result");
	        if (result != null && "CF-00000".equals(result.get("code"))) { // 성공 코드 확인
	            response.put("verified", true);
	            response.put("message", "SMS 인증 성공");
	            response.put("data", apiResponse.get("data")); // 필요한 데이터를 클라이언트로 반환
	            System.out.println("[DEBUG] SMS 인증 성공");
	        } else {
	            String errorMessage = (String) result.getOrDefault("message", "CODEF API 요청 실패");
	            response.put("verified", false);
	            response.put("message", errorMessage);
	            System.err.println("[ERROR] SMS 인증 실패: " + errorMessage);
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	        response.put("verified", false);
	        response.put("message", "SMS 인증 처리 중 오류 발생: " + e.getMessage());
	    }

	    return response;
	}






	
	
	@RequestMapping(value = "/decodeSecureNo", method = RequestMethod.POST, produces = "image/png")
	@ResponseBody
	public byte[] decodeSecureNo(@RequestParam("base64Image") String base64Image) {
	    try {
	        // 입력 데이터 검증
	        if (base64Image == null || base64Image.isEmpty()) {
	            throw new IllegalArgumentException("Base64 이미지 데이터가 비어 있습니다.");
	        }

	        // Base64 디코딩
	        byte[] decodedBytes = Base64.getDecoder().decode(base64Image);

	        // PNG 헤더 검증
	        byte[] pngHeader = new byte[] {(byte) 0x89, 'P', 'N', 'G', (byte) 0x0D, (byte) 0x0A, (byte) 0x1A, (byte) 0x0A};
	        for (int i = 0; i < pngHeader.length; i++) {
	            if (decodedBytes[i] != pngHeader[i]) {
	                throw new IllegalArgumentException("유효하지 않은 PNG 파일입니다.");
	            }
	        }

	        // 디코딩된 데이터 반환
	        return decodedBytes;

	    } catch (IllegalArgumentException e) {
	        System.err.println("[ERROR] " + e.getMessage());
	        return null; // 클라이언트에 적절한 오류 메시지를 반환하도록 수정 가능
	    } catch (Exception e) {
	        e.printStackTrace();
	        return null;
	    }
	}



	
	// 추가인증
	@RequestMapping(value = "additionalCertification.do", method = RequestMethod.GET)
    public String additionalCertification(HttpSession session) {
		 System.out.println("additionalCertification.do enter?");
        // 세션에 저장된 응답 데이터 확인 (필요 시 디버깅용)
        Object response = session.getAttribute("response");
        System.out.println("추가 인증 완료 응답 데이터: " + response);

        // 추가 인증 완료 페이지로 이동
        return "WEB-INF/medicine/additionalCertification";
    }
	
	
	
	@RequestMapping(value = "finalResult.do", method = RequestMethod.GET)
	public String finalResult(HttpSession session, Model model) {
	    List<HashMap<String, Object>> finalResultData = (List<HashMap<String, Object>>) session.getAttribute("finalResultData");
	    model.addAttribute("finalResultData", finalResultData); // 데이터를 모델에 추가
	    return "WEB-INF/medicine/finalResult";
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
//        return "WEB-INF/medicine/error";
//    }
//
//    // API 호출
//    EasyCodefConnector connector = new EasyCodefConnector();
//    ObjectMapper objectMapper = new ObjectMapper();
//    
//    String requestBody = objectMapper.writeValueAsString(requestData);
//
//    HashMap<String, Object> response = connector.getRequestProduct(
//        "https://development.codef.io/v1/kr/public/hw/hira-list/my-medicine",
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
//            return "WEB-INF/medicine/secureInput"; // 보안문자 입력 페이지로 이동
//        }
//    }
//
//    // 추가 인증이 필요하지 않은 경우
//    session.setAttribute("response", response);
//    
//    return "WEB-INF/medicine/additionalCertification";
//}