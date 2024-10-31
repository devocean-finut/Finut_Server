package com.finut.finut_server.service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.oauth2.Oauth2;
import com.google.api.services.oauth2.model.Userinfoplus;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.AccessToken;
import com.google.auth.oauth2.GoogleCredentials;
import org.springframework.stereotype.Service;

@Service
public class GoogleAuthService {
    public Userinfoplus getUserInfo(String accessToken) throws IOException, GeneralSecurityException {
        // GoogleCredentials로 Access Token 설정
        GoogleCredentials credentials = GoogleCredentials.create(new AccessToken(accessToken, null));

        // Oauth2 서비스 생성
        Oauth2 oauth2 = new Oauth2.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                GsonFactory.getDefaultInstance(),  // GsonFactory 사용
                new HttpCredentialsAdapter(credentials))
                .setApplicationName("Finut")
                .build();

        // 사용자 정보 요청
        return oauth2.userinfo().get().execute();
    }
}
