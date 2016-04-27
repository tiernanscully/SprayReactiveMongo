package com.lemur.utils;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;

/**
 * Created by djhurley on 02/04/16.
 */
public class GoogleVerifier {
    public static boolean verify(String idTokenString) {
        try {
            NetHttpTransport transport = GoogleNetHttpTransport.newTrustedTransport();
            JacksonFactory jsonFactory = JacksonFactory.getDefaultInstance();
            String clientId = "822785640622-f0tj5dvauvn97hieujjpbtfbffvr5uvc.apps.googleusercontent.com";
            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
                    .setAudience(Arrays.asList(clientId))
                    .setIssuer("https://accounts.google.com")
                    .build();

            GoogleIdToken idToken = verifier.verify(idTokenString);

            if (idToken != null) {
                GoogleIdToken.Payload payload = idToken.getPayload();

                System.out.println(payload);

                return true;

            } else {
                System.out.println("Invalid ID token: " + idTokenString);
            }
        }catch(IOException e){
            System.out.println(e.getMessage());
        }catch(GeneralSecurityException e){
            System.out.println(e.getMessage());
        }catch(IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        return false;
    }
}