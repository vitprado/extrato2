/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.exacta.json;

import com.google.gson.Gson;
import java.util.Map;
import org.json.simple.JSONObject;

/**
 *
 * @author Thales
 */
public class utilJSON {
    
    /**
     *
     * @param objeto
     * @return
     */
    public static String toJson(Object objeto) {
        Gson gson = new Gson();
        return gson.toJson(objeto);
    }

    /**
     *
     * @param json
     * @return
     */
    public static boolean isJSONValid(String json) {
        try {
            new JSONObject(json);
        } catch (JSONException ex) {
            return false;
        }
        return true;
    }

    public static Map fromJson(String jsonString) throws Exception {
        if(jsonString == null) {
            return null;
        }
        if(!utilJSON.isJSONValid(jsonString)) {
            throw new Exception(jsonString);
        }
        Map result = new Gson().fromJson(jsonString, Map.class);
        return result;
    }
    
}
