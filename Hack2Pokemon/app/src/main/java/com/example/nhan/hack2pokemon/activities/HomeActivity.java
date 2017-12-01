package com.example.nhan.hack2pokemon.activities;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.nhan.hack2pokemon.R;
import com.example.nhan.hack2pokemon.constant.Constant;
import com.example.nhan.hack2pokemon.fragments.HomeFragment;
import com.example.nhan.hack2pokemon.utils.Utils;

public class HomeActivity extends AppCompatActivity{

    private void openFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment).addToBackStack(fragment.getClass().getName())
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.addToBackStack(fragment.getClass().getName());
        fragmentTransaction.commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.anim_not_move, R.anim.anim_not_move);
        setContentView(R.layout.activity_home);
        openFragment(new HomeFragment());
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
//        mediaPlayer.release();
    }



//    @Override
//    public void onClick(View v) {
//        switch (v.getId()){
//            case R.id.btn_play:
//                Intent intentPlay = new Intent(HomeActivity.this, PlayActivity.class);
//                startActivityForResult(intentPlay, RESULT_FIRST_USER);
//                break;
//            case R.id.btn_setting:
//                Intent intentSetting = new Intent(HomeActivity.this, SettingActivity.class);
//                startActivity(intentSetting);
//                break;
//        }
//    }

//    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
//    private void createCircularReveal(float startRadius, float finalRadius){
//        int cx = (int) (Utils.getActivityWidthPixel(this) / 2);
//        int cy = (int) (Utils.getActivityHeightPixel(this) * 5.05f / 8.1f);
//        Animator animator = ViewAnimationUtils.createCircularReveal(rootLayout, cx, cy, startRadius, finalRadius);
//        animator.setDuration(500);
//        rootLayout.setVisibility(View.VISIBLE);
//        animator.start();
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == RESULT_OK){
//            homeScore = data.getIntExtra(Constant.SCORE_KEY, 0);
//            tvHomeScore.setText(String.valueOf(homeScore));
//            if (homeScore > Utils.getIntFromPreference(this, Constant.HIGH_SCORE_NAME_PREF)){
//                highScore = homeScore;
//                tvHighScore.setText(R.string.new_high_score);
//                Utils.saveIntToPreference(this, Constant.HIGH_SCORE_NAME_PREF, highScore);
//            }
//        } else {
//            tvHighScore.setText(getString(R.string.highscore) + ": " +String.valueOf(highScore));
//        }
//    }


}
