package com.example.fliest.freshnews.Utils;

import com.example.fliest.freshnews.bean.topnews.TopnewsDetailBean;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Fliest on 2017/7/9.
 */

public class NewsJsonUtil {
    private static Gson gson = new Gson();

    public static TopnewsDetailBean readJsonToBean(JsonObject jsonObject, String docId) {
        TopnewsDetailBean newsDetailBean = null;

        try {
            //JsonParser parser = new JsonParser();
            //JsonObject jsonObj = parser.parse(jsonString).getAsJsonObject();
            JsonElement jsonElement = jsonObject.get(docId);
            if (jsonElement == null) {
                return null;
            }

            newsDetailBean = gson.fromJson(jsonElement.getAsJsonObject(),TopnewsDetailBean.class);
        } catch (Exception e) {
            System.out.println("error in util:" +e.toString());
        }

        return newsDetailBean;
    }

    public static <T> T deserialize(JsonObject json, Class<T> clz) throws JsonSyntaxException {
        return gson.fromJson(json, clz);
    }

    public static <T> T deserialize(String json, Type type) throws JsonSyntaxException {
        return gson.fromJson(json, type);
    }

    public static void readJsonFromWeb() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://c.m.163.com/nc/article/CP30P3DD0001899N/full.html");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setReadTimeout(5000);
                    connection.setConnectTimeout(5000);
                    connection.connect();

                    if (connection.getResponseCode() == 200) {
                        InputStream in = connection.getInputStream();
                        //System.out.println("result:" + convertStreamToString(in));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    public static String convertStreamToString(InputStream is) {

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return sb.toString();
    }
}
