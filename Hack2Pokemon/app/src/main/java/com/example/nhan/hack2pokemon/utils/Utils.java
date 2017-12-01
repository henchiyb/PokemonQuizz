package com.example.nhan.hack2pokemon.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.util.DisplayMetrics;

import com.example.nhan.hack2pokemon.constant.Constant;

import java.io.IOException;
import java.io.InputStream;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Nhan on 9/26/2016.
 */

public class Utils {
    public static float getActivityWidthPixel(Activity activity){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }

    public static float getActivityHeightPixel(Activity activity){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }

    public static Bitmap loadBitmapFromAssetFolder(Context context, String fileName){
        Bitmap bitmap = null;
        try {
            InputStream inputStream = context.getAssets().open("images/" + fileName);
            bitmap = BitmapFactory.decodeStream(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    public static Typeface loadFontFromAssetFolder(Context context, String fileName){
        return Typeface.createFromAsset(context.getAssets(), "fonts/" + fileName);
    }

    public static void setDataSourceForMediaPlayer(Context context, MediaPlayer mediaPlayer, String fileName){
        try {
            AssetFileDescriptor descriptor = context.getAssets().openFd("sounds/" + fileName);
            mediaPlayer.setDataSource(descriptor.getFileDescriptor(), descriptor.getStartOffset(), descriptor.getLength());
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (Utils.getIntFromPreference(context, Constant.MUSIC_NAME_PREF) == 0){
            mediaPlayer.setVolume(1.0f, 1.0f);
        } else {
            mediaPlayer.setVolume(0, 0);
        }
    }

    public static void saveIntToPreference(Context context, String namePref, int value){
        SharedPreferences preferences = context.getSharedPreferences(namePref, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(namePref, value);
        editor.apply();
    }

    public static int getIntFromPreference(Context context, String namePref){
        SharedPreferences preferences = context.getSharedPreferences(namePref, MODE_PRIVATE);
        return preferences.getInt(namePref, 0);
    }
}
