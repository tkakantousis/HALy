package com.haly.brain;

import com.haly.brain.client.Client;
import com.haly.mouth.Mouth;


public class HALy implements Brain
{
    Mouth mouth;
    Client client;
    
    public HALy(Mouth mouth, Client client) {
        this.mouth = mouth;
        this.client = client;
    }

    @Override
    public synchronized BrainStatus processEvent(BrainEvent event) {
        System.out.println("[BRAIN] Got event with command " + event.getCommand() + " and subject " + event.getSubject() + "!");
        return BrainStatus.OK;
    }
}
