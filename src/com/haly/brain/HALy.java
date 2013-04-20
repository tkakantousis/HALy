package com.haly.brain;

public class HALy implements Brain
{
    public HALy() {
    }

    @Override
    public BrainStatus sendEvent(BrainEvent event) {
        System.out.println("[BRAIN] Got event with command " + event.getCommand() + " and subject " + event.getSubject() + "!");
        return BrainStatus.OK;
    }
}
