package com.haly.brain;

import com.haly.brain.client.Client;
import com.haly.brain.client.HalyClient;
import com.haly.brain.server.HalyServer;
import com.haly.brain.server.Server;
import com.haly.ears.Ears;
import com.haly.eyes.Eyes;
import com.haly.mouth.FreeTTS;
import com.haly.mouth.Mouth;
import java.util.Timer;


public class HALyStarter
{
    public static void main(String[] args) {
        System.out.print("[HALy] Initializing mouth...");
        Mouth mouth = new FreeTTS();
        mouth.speak("Hello, my name is Hal!");
        System.out.println("[OK]");
        
        System.out.print("[HALy Initializing local client...");
        Client client = new HalyClient();
        System.out.println("[OK]");
        
        System.out.print("[HALy] Initializing brain...");
        Brain brain = new HALy(mouth, client);
        System.out.println("[OK]");
        
        System.out.print("[HALy] Initializing ears...");
        Ears ears = new Ears(brain);
        new Thread(ears).start();
        System.out.println("[OK]");
        
        System.out.print("[HALy] Initializing eyes...");
        Eyes eyes = new Eyes(brain);
        Timer scheduler = new Timer();
        scheduler.schedule(eyes, 0, 50);
        System.out.println("[OK]");
        
        System.out.print("[HALy] Initializing local server...");
        Server server = new HalyServer(brain);
        server.start();
        System.out.println("[OK]");
    }
}
