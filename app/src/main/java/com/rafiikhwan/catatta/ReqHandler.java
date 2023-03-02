package com.rafiikhwan.catatta;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

public class ReqHandler {
    public String sendPostRequest(String requestURL, HashMap<String, String > postDataParams){
        URL url;

        StringBuilder sb = new StringBuilder();
        try {
            url = new URL(requestURL);
            HttpURLConnection connection = (HttpURLConnection)
                    url.openConnection();
            connection.setReadTimeout(15000);
            connection.setConnectTimeout(15000);
            connection.setRequestMethod("POST");
            connection.setDoInput(true);
            connection.setDoOutput(true);

            OutputStream os = connection.getOutputStream();

            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, StandardCharsets.UTF_8));
            writer.write(getPostDataString(postDataParams));

            writer.flush();
            writer.close();
            os.close();
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpsURLConnection.HTTP_OK){
                BufferedReader bufferedReader = new BufferedReader(
                        new InputStreamReader(connection.getInputStream()));
                sb = new StringBuilder();
                String response;

                while ((response = bufferedReader.readLine()) != null){
                    sb.append(response);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public String sendGetRequest(String requestURL){
        StringBuilder stringBuilder = new StringBuilder();
        try {
            URL url = new URL(requestURL);
            HttpURLConnection connection = (HttpURLConnection)
                    url.openConnection();
            BufferedReader bufferedReader = new BufferedReader(new
                    InputStreamReader(connection.getInputStream()));
            String s;
            while ((s = bufferedReader.readLine()) != null){
                stringBuilder.append(s).append("\n");
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    public String sendGetRequestParam(String requestURL, String id){
        StringBuilder stringBuilder = new StringBuilder();
        try{
            URL url = new URL(requestURL + id);
            HttpURLConnection connection = (HttpURLConnection)
                    url.openConnection();
            BufferedReader bufferedReader = new BufferedReader(new
                    InputStreamReader(connection.getInputStream()));

            String s;
            while ((s = bufferedReader.readLine())!=null){
                stringBuilder.append(s).append("\n");
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    private String getPostDataString(HashMap<String, String>
                                             params) throws UnsupportedEncodingException {
        StringBuilder result    = new StringBuilder();
        boolean first           = true;
        for (Map.Entry<String, String> entry : params.entrySet()){
            if (first){
                first = false;
            }else {
                result.append("&");
            }
            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(),"UTF-8"));
        }
        return result.toString();
    }
}
