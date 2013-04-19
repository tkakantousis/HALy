package com.haly.brain;

import com.haly.mouth.FreeTTS;
import com.haly.mouth.Mouth;


public class HALy
{
    private static Mouth mouth;

    public static void main(String[] args) {
        System.out.println("HALy is waking up...");
        
        System.out.println("HALy is initializing his mouth...");
        mouth = new FreeTTS();
        mouth.speak("Goodmorning, my name is HAL!");
    }
}
