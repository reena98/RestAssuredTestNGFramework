package com.spotify.oauth2.utils;

import java.util.Properties;

public class DataLoader {
    private final Properties properties;
    public static DataLoader dataLoader;

    public static DataLoader getInstance(){
        if (dataLoader ==null ){
            dataLoader = new DataLoader();
        }
        return dataLoader;
    }

    private DataLoader(){
        properties = PropertyUtils.propertyLoader("src/test/resources/data.properties");
    }


    public String getPlaylistId(){
        String prop = (String) properties.get("get_playlist_id");
        if(prop!=null) return prop;
        else throw new RuntimeException("Property get_playlist_id is not specified");
    }
    public String getUpdatePlaylistId(){
        String prop = (String) properties.get("update_playlist_id");
        if(prop!=null) return prop;
        else throw new RuntimeException("Property update_playlist_id is not specified");
    }
}
