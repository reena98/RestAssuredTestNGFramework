package com.spotify.oauth2.api;

import com.spotify.oauth2.pojo.Playlist;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.HashMap;

import static com.spotify.oauth2.api.Route.API;
import static com.spotify.oauth2.api.Route.TOKEN;
import static com.spotify.oauth2.api.SpecBuilder.*;
import static io.restassured.RestAssured.given;

public class RestResource {

    static String access_token = "BQCc-1qRjOaCTfSdkFDU6KXS8FCGMYC0ZxrOW6g7r-vSfuB6tvSA78Q_GxBOUchbuhhSuwPIzVrKdhvKG9x3UdT_m9iR6Xw34XXbDgScQGiSR4XexK2f3GKNQRqH-leWn6bBPISPgg7R4j_1GNimvJp4NR8vTNyN4d8BGbMr5fIxK2hXpCKSsYaVHe-rF1Zsdu2mnC1DKMWwyIpRU3e--befDuVQtDDHLcxdFVxZ5IaNqa7p-3RTgACQNM2oGxZBk-Mxr59SOWcsyC4H";

    public static Response post(String path, String token, Object requestPlaylist){
        return given(getRequestSpec()).
                body(requestPlaylist).
                auth().oauth2(token).
            when()
                .post(path).
            then().spec(getResponseSpec())
                .extract().response();
    }

    public static Response get(String path, String token){
        return given(getRequestSpec()).
                auth().oauth2(token).
            when()
                .get(path).
            then().spec(getResponseSpec())
                .extract().response();
    }

    public static Response update(String path, String token, Object requestPlaylist){
        return given(getRequestSpec()).
                auth().oauth2(token)
                .body(requestPlaylist).
                when()
                .put(path).
                then().spec(getResponseSpec())
                .extract().response();
    }

    public static Response postAccount(HashMap<String , String> formParams){
        return given(getAccountRequestSpec()).
                formParams(formParams).
                contentType(ContentType.URLENC).
                when()
                .post(API + TOKEN).
                then().spec(getResponseSpec())
                .extract().response();
    }

}
