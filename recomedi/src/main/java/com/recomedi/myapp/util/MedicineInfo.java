package com.recomedi.myapp.util;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.io.BufferedReader;
import java.io.IOException;

public class MedicineInfo {

	private String type = "";
	private String serviceKey = "=MIUwPe5jMuFpyqWE6gezF%2B1SKYcqK8kkqpQBLQDBiPHykkG81%2Byz7q%2FQgiS8e%2F02z%2Bm%2FhFIWb7AzXPsEL%2FNyLQ%3D%3D";
	private String pageNo = "";
	private String numOfRows = "";
	private String itemSeq = "";
	private String searchType = "";
	private String keyword = "";
	
	public MedicineInfo(String type, String pageNo, String numOfRows, String searchType, String keyword) {
		this.type = type;
		this.pageNo = pageNo;
		this.numOfRows = numOfRows;
		this.searchType = searchType;
		this.keyword = keyword;
	}
	
	public MedicineInfo(String type, String itemSeq) {
		this.type = type;
		this.itemSeq = itemSeq;
	}
	
	public String getMedicineInfo() throws IOException {

		StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1471000/DrbEasyDrugInfoService/getDrbEasyDrugList"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + serviceKey); /*Service Key*/
        
		if(type.equals("list")) {

	        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode(pageNo, "UTF-8")); /*페이지번호*/
	        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode(numOfRows, "UTF-8")); /*한 페이지 결과 수*/ 
	        
			if(searchType.equals("itemName")) {
		        urlBuilder.append("&" + URLEncoder.encode("itemName","UTF-8") + "=" + URLEncoder.encode(keyword, "UTF-8")); /*제품명*/
				
			} else if (searchType.equals("efcyQesitm")) {
				urlBuilder.append("&" + URLEncoder.encode("efcyQesitm","UTF-8") + "=" + URLEncoder.encode(keyword, "UTF-8")); /*이 약의 효능은 무엇입니까?*/
			}
		
		} else if(type.equals("contents")) {
	        urlBuilder.append("&" + URLEncoder.encode("itemSeq","UTF-8") + "=" + URLEncoder.encode(itemSeq, "UTF-8")); /*품목기준코드*/			
		}
		
        urlBuilder.append("&" + URLEncoder.encode("type","UTF-8") + "=" + URLEncoder.encode("json", "UTF-8")); /*응답데이터 형식(xml/json) Default:xml*/
        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        // System.out.println("Response code: " + conn.getResponseCode());
        BufferedReader rd;
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream(),"UTF-8"));
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();
        System.out.println(sb.toString());
        // System.out.println(sb.getClass().getName());
        return sb.toString();
    }
	
}