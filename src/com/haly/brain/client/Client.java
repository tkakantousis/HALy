package com.haly.brain.client;

import com.google.gson.JsonObject;
import com.haly.brain.DeviceNotification;


public interface Client
{
    public JsonObject hsSendNotification(DeviceNotification notification);
}
