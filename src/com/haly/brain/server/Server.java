package com.haly.brain.server;

public interface Server
{
    public void start();
    public void notifyDevice(String notificationMessage, String notificationType);
}
