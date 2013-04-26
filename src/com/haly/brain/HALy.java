package com.haly.brain;

import com.haly.brain.client.Client;
import com.haly.mouth.Mouth;
import java.util.ArrayList;
import java.util.List;


public class HALy implements Brain
{
    Mouth mouth;
    Client client;
    User userInView;
    List<User> authorizationList;
    
    public HALy(Mouth mouth, Client client) {
        this.mouth = mouth;
        this.client = client;
        authorizationList = new ArrayList<>();
        authorizationList.add(new User(0, "Unknown-0", null));
    }

    @Override
    public synchronized BrainStatus processEvent(BrainEvent event) {
        System.out.println("[BRAIN] Got event with command " + event.getCommand() + " and subject " + event.getSubject() + "!");
        return BrainStatus.OK;
    }
}
