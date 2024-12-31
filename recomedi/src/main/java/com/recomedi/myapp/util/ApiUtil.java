package com.recomedi.myapp.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class ApiUtil {
	
	private static final String API_KEY="MIUwPe5jMuFpyqWE6gezF%2B1SKYcqK8kkqpQBLQDBiPHykkG81%2Byz7q%2FQgiS8e%2F02z%2Bm%2FhFIWb7AzXPsEL%2FNyLQ%3D%3D";
	
    public String callpublicApi(String pageNo, String numOfRows) {
    	
    	try {
    		StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1471000/DrbEasyDrugInfoService/getDrbEasyDrugList");
    		urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=" + API_KEY);
    		urlBuilder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode(pageNo, "UTF-8"));
    		urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode(numOfRows,"UTF-8"));
    		urlBuilder.append("&" + URLEncoder.encode("type", "UTF-8") + "=" + URLEncoder.encode("json", "UTF-8"));
    		
    		URL url = new URL(urlBuilder.toString());
    		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
    		conn.setRequestMethod("GET");
    		conn.setRequestProperty("Content-type", "application/json");
    		
    		BufferedReader rd;
    		if (conn.getResponseCode() >= 200 && conn.getResponseCode() <=300) {
    			rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
    		} else {
    			rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
    		}
    		
    		StringBuilder response = new StringBuilder();
    		String line;
    		while((line = rd.readLine()) != null) {
    			response.append(line);
    		}
    		rd.close();
    		conn.disconnect();
    		return response.toString(); // API 응답 반
    		
    	} catch(Exception e) {
    		e.printStackTrace();
    		return null;
    	}
    	
    }
}