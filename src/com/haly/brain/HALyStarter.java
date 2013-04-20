package com.haly.brain;

import com.haly.ears.Ears;
import com.haly.mouth.FreeTTS;
import com.haly.mouth.Mouth;


public class HALyStarter
{
    public static void main(String[] args) {
        System.out.print("[HALy] Initializing brain...");
        Brain brain = new HALy();
        System.out.println("[OK]");
        
        System.out.print("[HALy] Initializing mouth...");
        Mouth mouth = new FreeTTS();
        mouth.speak("Hello, my name is Hal!");
        System.out.println("[OK]");
        
        System.out.print("[HALy] Initializing ears...");
        Ears ears = new Ears(brain);
        new Thread(ears).start();
        System.out.println("[OK]");
        
        System.out.print("[HALy] Initializing eyes...");
        // TODO Initialize eyes thread
        System.out.println("[OK]");
    }
}
