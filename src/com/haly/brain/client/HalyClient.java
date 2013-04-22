package com.haly.brain.client;

import com.google.gson.JsonObject;
import com.haly.brain.DeviceNotification;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;


public class HalyClient implements Client
{
    private static final String serviceEndpoint = "http://192.168.1.6:8084/TestGCM/TestServlet";
    
    private ServiceRequester sr;
    
    public HalyClient() {
        this.sr = new ServiceRequester();
    }
    
    @Override
    public JsonObject hsSendNotification(DeviceNotification notification) {
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("notification", notification.toString()));

        return sr.request(serviceEndpoint, params);
    }
}
