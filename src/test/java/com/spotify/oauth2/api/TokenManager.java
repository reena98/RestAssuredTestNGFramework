package com.spotify.oauth2.api;

import com.spotify.oauth2.utils.ConfigLoader;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.time.Instant;
import java.util.HashMap;

import static com.spotify.oauth2.api.SpecBuilder.getResponseSpec;
import static com.spotify.oauth2.utils.ConfigLoader.configLoader;
import static com.spotify.oauth2.utils.ConfigLoader.getInstance;
import static io.restassured.RestAssured.given;

public class TokenManager {

    static private String access_token;
    static private Instant expiry_time;

    public synchronized static String getToken(){
        try{
            if(access_token == null || Instant.now().isAfter(expiry_time)){
                System.out.println("Renewing token");
                Response response = renewToken();
                access_token = response.path("access_token");
                int expiryDurationInSeconds = response.path("expires_in");
                expiry_time = Instant.now().plusSeconds(expiryDurationInSeconds - 300);
            }else {
                System.out.println("Token is good to use");
            }
        }
        catch(Exception e){
            throw new RuntimeException("Abort!! Failed to get token");
        }
        return access_token;
    }

    public static Response renewToken() {
        HashMap<String, String> formParams = new HashMap<String, String>();
        formParams.put("client_id", configLoader.getInstance().getClientId() );
        formParams.put("client_secret", configLoader.getInstance().getClientSecret());
        formParams.put("grant_type", configLoader.getInstance().getGrantType());
        formParams.put("refresh_token", configLoader.getInstance().getRefreshToken());

        Response response = given()
                .baseUri("https://accounts.spotify.com")
                .contentType(ContentType.URLENC)
                .formParams(formParams)
                .log().all()
        .when()
                .post("/api/token")
        .then()
                .spec(getResponseSpec())
                .extract().response();

        if(response.statusCode() != 200){
            throw new RuntimeException("ABORT!! Renew token failed");
        }
        return response;
    }
}
