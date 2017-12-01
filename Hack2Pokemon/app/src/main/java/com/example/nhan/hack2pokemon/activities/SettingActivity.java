package com.example.nhan.hack2pokemon.activities;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.nhan.hack2pokemon.R;
import com.example.nhan.hack2pokemon.constant.Constant;
import com.example.nhan.hack2pokemon.utils.SoundEffects;
import com.example.nhan.hack2pokemon.utils.Utils;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener {

    private CheckBox checkBoxMusic;
    private CheckBox checkBoxSound;

    private MediaPlayer mediaPlayer;
    private SoundEffects soundEffects;

    private TextView tvGeneration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initLayout();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Utils.setDataSourceForMediaPlayer(this, mediaPlayer, Constant.MUSIC_HOME);
        mediaPlayer.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mediaPlayer.release();
    }

    private void initLayout(){
        mediaPlayer = new MediaPlayer();
        soundEffects = SoundEffects.getInstance(this);

        tvGeneration = (TextView) findViewById(R.id.tv_generation);
        tvGeneration.setTypeface(Utils.loadFontFromAssetFolder(this, Constant.FONT_HIGH_SCORE));
        checkBoxMusic = (CheckBox) findViewById(R.id.checkbox_music);
        checkBoxSound = (CheckBox) findViewById(R.id.checkbox_sound_effects);

        checkBoxSound.setOnClickListener(this);
        checkBoxMusic.setOnClickListener(this);

        // 0 -> ON; 1 -> OFF
        if (Utils.getIntFromPreference(this, Constant.MUSIC_NAME_PREF) == 0){
            checkBoxMusic.setChecked(true);
            mediaPlayer.setVolume(1.0f, 1.0f);
        } else {
            checkBoxMusic.setChecked(false);
            mediaPlayer.setVolume(0, 0);
        }

        if (Utils.getIntFromPreference(this, Constant.SOUND_NAME_PREF) == 0){
            checkBoxSound.setChecked(true);
            soundEffects.setSoundEffectOn(true);
        } else {
            checkBoxSound.setChecked(false);
            soundEffects.setSoundEffectOn(false);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.checkbox_music:
                if(checkBoxMusic.isChecked()){
                    Utils.saveIntToPreference(this, Constant.MUSIC_NAME_PREF, 0);
                    mediaPlayer.setVolume(1.0f, 1.0f);
                } else {
                    Utils.saveIntToPreference(this, Constant.MUSIC_NAME_PREF, 1);
                    mediaPlayer.setVolume(0, 0);
                }
                soundEffects.playSoundClick();
                break;

            case R.id.checkbox_sound_effects:
                if(checkBoxSound.isChecked()){
                    Utils.saveIntToPreference(this, Constant.SOUND_NAME_PREF, 0);
                    soundEffects.setSoundEffectOn(true);
                } else {
                    Utils.saveIntToPreference(this, Constant.SOUND_NAME_PREF, 1);
                    soundEffects.setSoundEffectOn(false);
                }
                soundEffects.playSoundClick();
                break;
        }
    }
}
