package com.haly.ears;

import com.haly.brain.Brain;
import com.haly.brain.BrainEvent;
import edu.cmu.sphinx.frontend.util.Microphone;
import edu.cmu.sphinx.recognizer.Recognizer;
import edu.cmu.sphinx.result.Result;
import edu.cmu.sphinx.util.props.ConfigurationManager;


public class Ears implements Runnable
{
    ConfigurationManager configManager;
    Recognizer recognizer;
    Brain brain;
    
    public Ears(Brain brain) {
        this.brain = brain;
        
        configManager = new ConfigurationManager(Ears.class.getResource("ears.config.xml"));
        recognizer = (Recognizer) configManager.lookup("recognizer");
        recognizer.allocate();
    }

    @Override
    public void run() {
        Microphone microphone = (Microphone) configManager.lookup("microphone");
        if (!microphone.startRecording()) {
            System.err.println("[EARS] Cannot start microphone!");
            recognizer.deallocate();
        }
        
        while(true) {
            Result result = recognizer.recognize();
            
            if (result != null) {
                String command = result.getBestFinalResultNoFiller();
                brain.processEvent(new BrainEvent(command));
            }
        }
    }
}
