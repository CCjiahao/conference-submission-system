package com.ccjiahao.dto;

import com.google.gson.Gson;
import org.springframework.lang.Nullable;

import java.util.Dictionary;
import java.util.Hashtable;

public class Feedback {
    private static final Gson gson = new Gson();

    public static String info(Object data) {
        Dictionary<String, Object> ret = new Hashtable<>();
        ret.put("errno", 0);
        ret.put("data", data);
        return gson.toJson(ret);
    }

    public static String error(@Nullable String errmsg) {
        Dictionary<String, Object> ret = new Hashtable<>();
        ret.put("errno", 1);
        if(errmsg != null) {
            ret.put("errmsg", errmsg);
        }
        return gson.toJson(ret);
    }
}
