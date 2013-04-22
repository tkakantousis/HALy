package com.haly.eyes;

import com.haly.brain.Brain;
import java.util.TimerTask;


public class Eyes extends TimerTask
{
    Brain brain;
    
    public Eyes(Brain brain) {
        this.brain = brain;
    }

    @Override
    public void run() {
        // TODO Implement eyes FSM to dispatch events to the brain
    }
}
