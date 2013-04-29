package com.haly.brain;

import com.haly.brain.server.Server;


public interface Brain
{
    public BrainStatus processEvent(BrainEvent event);
    public void setServer(Server server);
}