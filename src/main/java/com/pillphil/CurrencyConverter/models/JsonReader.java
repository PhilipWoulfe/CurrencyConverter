package com.pillphil.CurrencyConverter.models;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import org.apache.http.util.EntityUtils;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.jsoup.helper.Validate;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;

public class JsonReader {

    public static JSONObject getJson(String url) throws IOException {
        Validate.notNull(url, "URL can't be null");

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);

        CloseableHttpResponse response = httpClient.execute(httpGet);
        String responseString = "";

        try {
            HttpEntity entity = response.getEntity();
            responseString = EntityUtils.toString(entity, "UTF-8");
            EntityUtils.consume(entity);
        }
        catch (Exception e) {
            // TODO implement catch
        }
        finally {
            response.close();
        }

        return stringToJson(responseString);
    }

    public static JSONObject stringToJson(String jsonString) {
        Validate.notNull(jsonString, "JSON string can't be null");
        JSONObject json = new JSONObject();
        JSONParser parser = new JSONParser();

        try {
            json = (JSONObject) parser.parse(jsonString);
        }
        catch (ParseException e) {
            // TODO handle Exception
        }

        return json;
    }

    public static HashMap<String, BigDecimal> jsonToHashMap(JSONObject json) {
        Validate.notNull(json, "JSON Object can't be null");
        Object[] keys = json.keySet().toArray();
        HashMap<String, BigDecimal> map = new HashMap<>();

        for (int i = 0; i < keys.length; i++) {
            String key = keys[i].toString();
            BigDecimal value = BigDecimal.valueOf((double)json.get(key));
            map.put(key, value);
        }

        return map;
    }
}
