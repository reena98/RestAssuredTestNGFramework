package com.spotify.oauth2.utils;

import java.util.Properties;

public class ConfigLoader {
    private final Properties properties;
    public static ConfigLoader configLoader;

    public static ConfigLoader getInstance(){
        if (configLoader ==null ){
            configLoader = new ConfigLoader();
        }
        return configLoader;
    }

    private ConfigLoader(){
        properties = PropertyUtils.propertyLoader("src/test/resources/config.properties");
    }


    public String getClientId(){
        String prop = (String) properties.get("client_id");
        if(prop!=null) return prop;
        else throw new RuntimeException("Property client_id is not specified");
    }
    public String getClientSecret(){
        String prop = (String) properties.get("client_secret");
        if(prop!=null) return prop;
        else throw new RuntimeException("Property client_secret is not specified");
    }
    public String getGrantType(){
        String prop = (String) properties.get("grant_type");
        if(prop!=null) return prop;
        else throw new RuntimeException("Property grant_type is not specified");
    }
    public String getRefreshToken(){
        String prop = (String) properties.get("refresh_token");
        if(prop!=null) return prop;
        else throw new RuntimeException("Property refresh_token is not specified");
    }
    public String getUserId(){
        String prop = (String) properties.get("user_id");
        if(prop!=null) return prop;
        else throw new RuntimeException("Property user_id is not specified");
    }
}
