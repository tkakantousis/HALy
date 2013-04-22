package com.haly.brain.client;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;


public class ServiceRequester
{
    public ServiceRequester() {
    }

    public JsonObject request(String url, List<NameValuePair> params) {
        JsonObject jsonResponse = null;

        try {
            // Build a POST request with the provided parameters
            HttpPost postRequest = new HttpPost(url);
            postRequest.setEntity(new UrlEncodedFormEntity(params));

            // Send the request and get the response
            DefaultHttpClient client = SingletonHttpClient.getHttpclient();
            HttpResponse response = client.execute(postRequest);
            HttpEntity responseEntity = response.getEntity();
            StringBuilder sbResponse;
            try (InputStream responseContent = responseEntity.getContent()) {
                BufferedReader responseReader = new BufferedReader(new InputStreamReader(responseContent, "utf-8"), 4);
                sbResponse = new StringBuilder();
                String line;
                while ((line = responseReader.readLine()) != null) {
                    sbResponse.append(line);
                }
            }
            String strResponse = sbResponse.toString();
            
            JsonParser parser = new JsonParser();
            jsonResponse = (JsonObject)parser.parse(strResponse);
        }
        catch (IOException ex) {
            System.err.println("[CLIENT] IOException thrown while sending POST request!");
        }

        return jsonResponse;
    }
}
