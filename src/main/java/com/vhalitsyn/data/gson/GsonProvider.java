package com.vhalitsyn.data.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Provides centralized Gson config.
 */
public class GsonProvider {
    public static Gson getGSON()
    {
        if(System.getenv().containsKey("MIXTAPE_PRETTY_JSON")){
            return new GsonBuilder().setPrettyPrinting().create();
        }
        return new Gson();
    }
}
