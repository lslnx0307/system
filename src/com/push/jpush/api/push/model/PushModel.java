package com.push.jpush.api.push.model;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

public interface PushModel {

    public static Gson gson = new Gson();
    public JsonElement toJSON();
    
}
