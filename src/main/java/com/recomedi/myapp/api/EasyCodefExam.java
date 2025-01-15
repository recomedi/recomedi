package com.recomedi.myapp.api;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

public class EasyCodefExam {

    public static void main(String[] args) throws JsonParseException, JsonMappingException, IOException {
        // EasyCodef ��ü ����
        EasyCodef codef = new EasyCodef();

        // ���� Ŭ���̾�Ʈ ���� ���� (Codef���� ������ Client ID�� Secret ���)
        String demoClientId = "fbbcf915-2395-4dfe-9316-a5ce610fab1a";
		String demoClientSecret = "2b152335-b63a-4596-bf34-5b44f79b41b0";
		
        // Ŭ���̾�Ʈ ���� ����
        codef.setClientInfoForDemo(demoClientId, demoClientSecret);

        // RSA �ۺ�Ű ���� (Codef���� ������ Public Key ���)
        String publicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAtphbcADV32+LTG5pki0L89WxekjcQpZNy4skAu5Ncxo4u2TKGe1+OM23zw02u8Huwfo62Y7xqc1GtFkoz/KK8J660y5K7xGRsEnptrmd4Uox3IqzJ8eIU4xCEoPLDrTK5CKbVFaoZhIxBEGwFvpNB3pA46khTc15f+FgXdHGZ7n0TWIZvZbJo+LPH4znxlcIUHqu9NCcGkkTljDXBOnGvbbCAS8X9AHKOg+pIo8jQiwDnokAfD0CxmzcREO5aUhe3I9hV3mnxzUqIaETlhRYyr2pfQHB/kH2yCceO8uT6VT1hQ91EmsGniAZYPVYkNMsC5j+1OXZY4mJ7hg/z0jU8QIDAQAB";
        codef.setPublicKey(publicKey);
        
        // ���� Ÿ���� DEMO�� ����
        String accessToken = codef.requestToken(EasyCodefServiceType.DEMO);
        System.out.println("Access Token: " + accessToken);
    }
}