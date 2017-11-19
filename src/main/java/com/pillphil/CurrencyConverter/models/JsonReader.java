package com.pillphil.CurrencyConverter.models;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import org.apache.http.util.EntityUtils;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.IOException;

public class JsonReader {
    static private CloseableHttpClient httpClient = HttpClients.createDefault();
    static private HttpGet httpGet;

    public static JSONObject getJson(String url) throws IOException {
        httpGet = new HttpGet(url);
        CloseableHttpResponse response = httpClient.execute(httpGet);
        JSONObject json = new JSONObject();

        try {
            HttpEntity entity = response.getEntity();
            String responseString = EntityUtils.toString(entity, "UTF-8");
            JSONParser parser;
            parser = new JSONParser();

            json = (JSONObject) parser.parse(responseString);
            EntityUtils.consume(entity);
        }
        catch (Exception e) {
            // TODO implement catch
        }
        finally {
            response.close();
        }

        return json;
    }

}
