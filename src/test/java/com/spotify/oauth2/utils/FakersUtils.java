package com.spotify.oauth2.utils;

import com.github.javafaker.Faker;

public class FakersUtils {

    public static String generateName(){
        Faker faker = new Faker();
        return "Playlist name " + faker.name();
    }

    public static String generateDescription(){
        Faker faker = new Faker();
        return "Playlist description " + faker.business();
    }
}
