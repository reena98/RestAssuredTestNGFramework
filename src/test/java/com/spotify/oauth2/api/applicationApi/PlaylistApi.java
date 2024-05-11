package com.spotify.oauth2.api.applicationApi;

import com.spotify.oauth2.api.RestResource;
import com.spotify.oauth2.pojo.Playlist;
import io.restassured.response.Response;

import static com.spotify.oauth2.api.Route.PLAYLISTS;
import static com.spotify.oauth2.api.Route.USERS;
import static com.spotify.oauth2.api.SpecBuilder.getRequestSpec;
import static com.spotify.oauth2.api.SpecBuilder.getResponseSpec;
import static com.spotify.oauth2.api.TokenManager.getToken;
import static com.spotify.oauth2.api.TokenManager.renewToken;
import static com.spotify.oauth2.utils.ConfigLoader.configLoader;
import static io.restassured.RestAssured.given;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import static com.spotify.oauth2.api.SpecBuilder.*;
public class PlaylistApi {

    public static Response post(Playlist requestPlaylist) {
        return RestResource.post(USERS + "/" +configLoader.getInstance().getUserId() + PLAYLISTS, getToken(), requestPlaylist);
    }
    public static Response post(String token, Playlist requestPlaylist){
        return RestResource.post(USERS + "/" +configLoader.getInstance().getUserId() + PLAYLISTS, token, requestPlaylist);
    }

    public static Response get(String playlistId) {
        return RestResource.get(PLAYLISTS + "/" + playlistId, getToken());
    }

    public static Response update(Playlist requestPlaylist , String playlistId){
        return RestResource.update(PLAYLISTS + "/"+playlistId, getToken() , requestPlaylist);
    }
}
