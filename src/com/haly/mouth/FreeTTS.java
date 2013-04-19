package com.haly.mouth;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;


public class FreeTTS implements Mouth
{
    public static final String MBROLA_ENGLISH_FEMALE = "mbrola_us1";
    public static final String MBROLA_ENGLISH_MALE_1 = "mbrola_us2";
    public static final String MBROLA_ENGLISH_MALE_2 = "mbrola_us3";
    
    private Voice voice;
    
    public FreeTTS() {
        this("kevin");
    }
    
    public FreeTTS(String voiceName) {
        VoiceManager voiceManager = VoiceManager.getInstance();
        this.voice = voiceManager.getVoice(voiceName);
        voice.allocate();
    }
    
    @Override
    public void speak(String words) {
        voice.speak(words);
    }
}
