package com.example.nhan.hack2pokemon.fragments;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.nhan.hack2pokemon.R;
import com.example.nhan.hack2pokemon.activities.HomeActivity;
import com.example.nhan.hack2pokemon.activities.SettingActivity;
import com.example.nhan.hack2pokemon.constant.Constant;
import com.example.nhan.hack2pokemon.utils.Utils;

public class HomeFragment extends Fragment implements View.OnClickListener {

    private Button btnPlay;
    private Button btnSetting;
    private TextView tvHomeScore;
    private TextView tvHighScore;
    private int homeScore;
    private int highScore;
    private MediaPlayer mediaPlayer;
    private FrameLayout rootLayout;


    private void openFragment(android.app.Fragment fragment) {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.container, fragment).addToBackStack(fragment.getClass().getName())
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.addToBackStack(fragment.getClass().getName());
        fragmentTransaction.commit();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view=inflater.inflate(R.layout.fragment_home,container,false);
        initLayout(view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mediaPlayer = new MediaPlayer();
        Utils.setDataSourceForMediaPlayer(this.getActivity(), mediaPlayer, Constant.MUSIC_HOME);
        mediaPlayer.start();
    }

    @Override
    public void onPause() {
        super.onPause();
        mediaPlayer.release();
    }

    private void initLayout(View view){
        rootLayout = (FrameLayout) view.findViewById(R.id.root_layout_home);
        btnPlay = (Button) view.findViewById(R.id.btn_play);
        btnSetting = (Button) view.findViewById(R.id.btn_setting);

        btnPlay.setOnClickListener(this);
        btnSetting.setOnClickListener(this);

        //get high score from preference
        highScore = Utils.getIntFromPreference(view.getContext(), Constant.HIGH_SCORE_NAME_PREF);

        //set size and position of btn Play
        int size = (int) (Utils.getActivityWidthPixel(this.getActivity()) * 0.4f);

        btnPlay.setLayoutParams(new RelativeLayout.LayoutParams(size, size));
        btnPlay.setX(Utils.getActivityWidthPixel(this.getActivity()) / 2 - size / 2);
        btnPlay.setY(Utils.getActivityHeightPixel(this.getActivity()) * 5.05f / 8.1f - size / 2);

        tvHomeScore = (TextView) view.findViewById(R.id.tv_home_score);
        tvHighScore = (TextView) view.findViewById(R.id.tv_high_score);
        tvHomeScore.setTypeface(Utils.loadFontFromAssetFolder(view.getContext(), Constant.FONT_SCORE));
        tvHighScore.setTypeface(Utils.loadFontFromAssetFolder(view.getContext(), Constant.FONT_HIGH_SCORE));

        if (highScore == 0){
            tvHighScore.setText(R.string.highscore);
        } else {
            tvHighScore.setText(getString(R.string.highscore) + " " +String.valueOf(highScore));
        }
    }


    @Override
    public void onClick(final View v) {
        switch (v.getId()){
            case R.id.btn_play:
                openFragment(new PlayFragment());
                mediaPlayer.release();
                break;
            case R.id.btn_setting:
                Intent intentSetting = new Intent(this.getActivity(), SettingActivity.class);
                startActivity(intentSetting);
                break;
        }
    }
}
