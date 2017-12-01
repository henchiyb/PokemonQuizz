package com.example.nhan.hack2pokemon.utils;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

import java.io.IOException;

/**
 * Created by Nhan on 9/26/2016.
 */

public class SoundEffects {
    private SoundPool soundPool;
    private int soundCorrect;
    private int soundIncorrect;
    private int soundClick;
    private static SoundEffects instance;
    private Boolean soundEffectOn = true;

    private SoundEffects(Context context){
        this.soundPool = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
        loadSoundToPool(context);
    }

    public static SoundEffects getInstance(Context context) {
        if (instance == null)
            instance = new SoundEffects(context);
        return instance;
    }

    private void loadSoundToPool(Context context){
        try {
            soundIncorrect = soundPool.load(context.getAssets().openFd("sounds/firered_00a3.wav"), 0);
            soundCorrect = soundPool.load(context.getAssets().openFd("sounds/firered_00fa.wav"), 0);
            soundClick = soundPool.load(context.getAssets().openFd("sounds/UIClick.wav"), 0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void playSoundCorrect(){
        if (soundEffectOn)
            soundPool.play(soundCorrect, 1.0f, 1.0f, 0, 0, 1.0f);
    }

    public void playSoundIncorrect(){
        if (soundEffectOn)
            soundPool.play(soundIncorrect, 1.0f, 1.0f, 0, 0, 1.0f);
    }

    public void playSoundClick(){
        if (soundEffectOn)
            soundPool.play(soundClick, 1.0f, 1.0f, 0, 0, 1.0f);
    }

    public void setSoundEffectOn(Boolean soundEffectOn) {
        this.soundEffectOn = soundEffectOn;
    }
}
