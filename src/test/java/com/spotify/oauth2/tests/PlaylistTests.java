package com.spotify.oauth2.tests;

import com.fasterxml.jackson.databind.ser.Serializers;
import com.github.javafaker.Faker;
import com.spotify.oauth2.api.StatusCode;
import com.spotify.oauth2.api.applicationApi.PlaylistApi;
import com.spotify.oauth2.pojo.Error;
import com.spotify.oauth2.pojo.InnerError;
import com.spotify.oauth2.pojo.Playlist;
import com.spotify.oauth2.utils.FakersUtils;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static com.spotify.oauth2.utils.ConfigLoader.configLoader;
import static com.spotify.oauth2.utils.DataLoader.dataLoader;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static com.spotify.oauth2.utils.FakersUtils.*;
import static io.restassured.RestAssured.given;
import static com.spotify.oauth2.api.SpecBuilder.*;
public class PlaylistTests extends BaseTests {

    public Playlist playlistBuilder(String name , String description , boolean _public){
        return Playlist.builder()
                        .name(name).description(description)._public(_public).build();
    }

    public void assertPlaylistEqual(Playlist responsePlaylist, Playlist requestPlaylist){
        assertThat(responsePlaylist.getName(), equalTo(requestPlaylist.getName()));
        assertThat(responsePlaylist.getDescription(), equalTo(requestPlaylist.getDescription()));
        assertThat(responsePlaylist.getPublic(), equalTo(requestPlaylist.getPublic()));
    }

    public void assertStatusCode(int actualStatusCode, StatusCode statusCode){
        assertThat(actualStatusCode,equalTo(statusCode.code));
    }

    public void assertError(Error response, StatusCode statusCode){
        assertThat(response.getError().getStatus(), equalTo(statusCode.code));
        assertThat(response.getError().getMessage(), equalTo(statusCode.msg));
    }

    @Test
    public void shouldBeAbleToCreateAPlaylist(){



        Playlist requestPlaylist = playlistBuilder(generateName(),generateDescription(), false);

        Response response = PlaylistApi.post(requestPlaylist);
        assertStatusCode(response.statusCode(), StatusCode.CODE_201);

        Playlist responsePlaylist = response.as(Playlist.class);

        assertPlaylistEqual(responsePlaylist,requestPlaylist);
    }

    @Test
    public void shouldBeAbleToGetAPlaylist(){
        Playlist requestPlaylist = playlistBuilder("Updated Playlist Name","Updated playlist description", true);

        Response response = PlaylistApi.get(dataLoader.getInstance().getPlaylistId());
        assertStatusCode(response.statusCode(),StatusCode.CODE_200);

        Playlist responsePlaylist = response.as(Playlist.class);
        assertPlaylistEqual(responsePlaylist,requestPlaylist);

    }

    @Test
    public void shouldBeAbleToUpdateAPlaylist(){

        Playlist requestPlaylist = playlistBuilder(generateName(),generateDescription(), false);

        Response response = PlaylistApi.update(requestPlaylist,dataLoader.getInstance().getUpdatePlaylistId());
        assertStatusCode(response.statusCode(),StatusCode.CODE_200);

    }

    @Test
    public void shouldNotBeAbleToCreateAPlaylistWithoutName(){
        Playlist requestPlaylist = playlistBuilder("",generateDescription(), false);

        Response response =  PlaylistApi.post(requestPlaylist);
        assertStatusCode(response.statusCode(),StatusCode.CODE_400);

        Error error = response.as(Error.class);

        assertError(error, StatusCode.CODE_400);

    }

    @Test
    public void shouldNotBeAbleToCreateAPlaylistWithExpiredToken(){
        String invalid_token ="12345";
        Playlist requestPlaylist = playlistBuilder("",generateDescription(), false);

        Response response =  PlaylistApi.post(invalid_token, requestPlaylist);
        assertStatusCode(response.statusCode(),StatusCode.CODE_401);

        Error error = response.as(Error.class);
        assertError(error, StatusCode.CODE_401);
    }
}
