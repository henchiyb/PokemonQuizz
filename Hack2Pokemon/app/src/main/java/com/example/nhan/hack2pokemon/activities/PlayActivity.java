package com.example.nhan.hack2pokemon.activities;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.nhan.hack2pokemon.R;
import com.example.nhan.hack2pokemon.constant.Constant;
import com.example.nhan.hack2pokemon.database.DatabaseAccess;
import com.example.nhan.hack2pokemon.models.Pokemon;
import com.example.nhan.hack2pokemon.utils.SoundEffects;
import com.example.nhan.hack2pokemon.utils.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PlayActivity extends AppCompatActivity{


//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        overridePendingTransition(R.anim.anim_not_move, R.anim.anim_not_move);
//        setContentView(R.layout.activity_play);
//        Constant.listGenPicked.add("1");
//        rootLayout = (FrameLayout) findViewById(R.id.root_layout);
//        if (savedInstanceState == null){
//            rootLayout.setVisibility(View.INVISIBLE);
//            ViewTreeObserver viewTreeObserver = rootLayout.getViewTreeObserver();
//            if (viewTreeObserver.isAlive()){
//                viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//                    @Override
//                    public void onGlobalLayout() {
//                        createCircularReveal(0, rootLayout.getHeight());
//                        rootLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
//                    }
//                });
//            }
//        }
//        initLayout();
//        createQuestion();
//    }
//
    @Override
    protected void onResume() {
        super.onResume();
//        controlTimeAndProgressBar();
//        mediaPlayer = new MediaPlayer();
//        Utils.setDataSourceForMediaPlayer(this, mediaPlayer, Constant.MUSIC_PLAY);
//        mediaPlayer.start();
    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        mediaPlayer.release();
//    }
//
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
//
//
//    private void createQuestion(){
//        setClickAbleButtonAnswer(true);
//        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
//        databaseAccess.open();
//        pokemon = databaseAccess.getRandomOnePokemon(Constant.listTagSeen, Constant.listGenPicked);
//        List<String> listNameForWrongAnswer = databaseAccess.getListNameForWrongAnswer(Constant.listTagSeen, Constant.listGenPicked);
//        databaseAccess.close();
//
//        Bitmap bitmapShadow = createShadowBitmap(Utils.loadBitmapFromAssetFolder(this, pokemon.getImg()));
//        imageViewPokemon.setImageBitmap(bitmapShadow);
//        tvTagName.setText("");
//
//        Collections.shuffle(listButtonAnswer);
//
//        listButtonAnswer.get(0).setText(pokemon.getName());
//        listButtonAnswer.get(1).setText(listNameForWrongAnswer.get(0));
//        listButtonAnswer.get(2).setText(listNameForWrongAnswer.get(1));
//        listButtonAnswer.get(3).setText(listNameForWrongAnswer.get(2));
//
//        btnAnswerA.setBackgroundResource(R.drawable.custom_view_circular_white);
//        btnAnswerB.setBackgroundResource(R.drawable.custom_view_circular_white);
//        btnAnswerC.setBackgroundResource(R.drawable.custom_view_circular_white);
//        btnAnswerD.setBackgroundResource(R.drawable.custom_view_circular_white);
//
//        layoutBackground.setBackgroundColor(Color.parseColor(pokemon.getColor()));
//
//    }
//
//    private Bitmap createShadowBitmap(Bitmap bitmap){
//        Bitmap bm = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());
//        for (int i =0; i < bitmap.getWidth(); i++){
//            for (int j =0; j < bitmap.getHeight(); j++){
//                int p = bitmap.getPixel(i, j);
//                int alpha = Color.alpha(p);
//                if (alpha != 0){
//                    bm.setPixel(i, j, Color.BLACK);
//                }
//            }
//        }
//        return bm;
//    }
//
//    private void checkCorrectAnswer(){
//        for (Button button : listButtonAnswer){
//            if (pokemon.getName().equals(button.getText())){
//                button.setBackgroundResource(R.drawable.custom_view_circular_green);
//            }
//        }
//    }
//
//    private void checkIncorrectAnswer(Button button){
//        if (pokemon.getName().equals(button.getText())){
//            button.setBackgroundResource(R.drawable.custom_view_circular_green);
//            playScore++;
//            tvPlayScore.setText(String.valueOf(playScore));
//            soundEffects.playSoundCorrect();
//        } else {
//            button.setBackgroundResource(R.drawable.custom_view_circular_red);
//            soundEffects.playSoundIncorrect();
//        }
//    }
//
//    private void setClickAbleButtonAnswer(Boolean check){
//        btnAnswerA.setClickable(check);
//        btnAnswerB.setClickable(check);
//        btnAnswerC.setClickable(check);
//        btnAnswerD.setClickable(check);
//    }
//
//    private void controlTimeAndProgressBar(){
//        CountDownTimer timer = new CountDownTimer(Constant.TIME_PLAY * 1000, 10) {
//            @Override
//            public void onTick(long millisUntilFinished) {
//                progressBar.setProgress(Constant.TIME_PLAY * 1000 - (int)millisUntilFinished);
//            }
//
//            @Override
//            public void onFinish() {
//                Intent intent = new Intent();
//                intent.putExtra(Constant.SCORE_KEY, playScore);
//                setResult(RESULT_OK, intent);
//                rootLayout.setVisibility(View.INVISIBLE);
//                ViewTreeObserver viewTreeObserver = rootLayout.getViewTreeObserver();
//                if (viewTreeObserver.isAlive()){
//                    viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//                        @Override
//                        public void onGlobalLayout() {
//                            createCircularReveal(rootLayout.getHeight(), 0);
//                            rootLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
//                        }
//                    });
//                }
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        overridePendingTransition(R.anim.anim_not_move, R.anim.anim_not_move);
//                        finish();
//                    }
//                },500);
//
//
//            }
//        };
//        timer.start();
//    }
//
//    @Override
//    public void onClick(View v) {
//        setClickAbleButtonAnswer(false);
//        ObjectAnimator objectAnimatorStart = ObjectAnimator.ofFloat(imageViewPokemon, "rotationY" , 0, 90);
//        final ObjectAnimator objectAnimatorEnd = ObjectAnimator.ofFloat(imageViewPokemon, "rotationY" , 90, 180);
//
//        objectAnimatorStart.setDuration(150);
//        objectAnimatorEnd.setDuration(150);
//        final TranslateAnimation animationOut = new TranslateAnimation(0, -Utils.getActivityWidthPixel(this), 0, 0);
//        animationOut.setDuration(200);
//        animationOut.setStartOffset(500);
//
//        final TranslateAnimation animationIn = new TranslateAnimation(Utils.getActivityWidthPixel(this), 0, 0, 0);
//        animationIn.setDuration(200);
//
//        objectAnimatorStart.start();
//        objectAnimatorStart.addListener(new Animator.AnimatorListener() {
//            @Override
//            public void onAnimationStart(Animator animation) {
//
//            }
//
//            @Override
//            public void onAnimationEnd(Animator animation) {
//                imageViewPokemon.setImageBitmap(Utils.loadBitmapFromAssetFolder(PlayActivity.this, pokemon.getImg()));
//                objectAnimatorEnd.start();
//            }
//
//            @Override
//            public void onAnimationCancel(Animator animation) {
//
//            }
//
//            @Override
//            public void onAnimationRepeat(Animator animation) {
//
//            }
//        });
//
//        objectAnimatorEnd.addListener(new Animator.AnimatorListener() {
//            @Override
//            public void onAnimationStart(Animator animation) {
//
//            }
//
//            @Override
//            public void onAnimationEnd(Animator animation) {
//                tvTagName.setText(String.format("%s %s", pokemon.getTag(), pokemon.getName()));
//                layoutImage.startAnimation(animationOut);
//            }
//
//            @Override
//            public void onAnimationCancel(Animator animation) {
//
//            }
//
//            @Override
//            public void onAnimationRepeat(Animator animation) {
//
//            }
//        });
//
//        animationOut.setAnimationListener(new Animation.AnimationListener() {
//            @Override
//            public void onAnimationStart(Animation animation) {
//
//            }
//
//            @Override
//            public void onAnimationEnd(Animation animation) {
//                imageViewPokemon.setRotationY(0);
//                createQuestion();
//                layoutButton.setVisibility(View.INVISIBLE);
//                layoutImage.startAnimation(animationIn);
//
//            }
//
//            @Override
//            public void onAnimationRepeat(Animation animation) {
//
//            }
//        });
//
//        animationIn.setAnimationListener(new Animation.AnimationListener() {
//            @Override
//            public void onAnimationStart(Animation animation) {
//
//            }
//
//            @Override
//            public void onAnimationEnd(Animation animation) {
//                layoutButton.setVisibility(View.VISIBLE);
//            }
//
//            @Override
//            public void onAnimationRepeat(Animation animation) {
//
//            }
//        });
//        checkCorrectAnswer();
//        switch (v.getId()){
//            case R.id.btn_answer_a:
//                checkIncorrectAnswer(btnAnswerA);
//                break;
//            case R.id.btn_answer_b:
//                checkIncorrectAnswer(btnAnswerB);
//                break;
//            case R.id.btn_answer_c:
//                checkIncorrectAnswer(btnAnswerC);
//                break;
//            case R.id.btn_answer_d:
//                checkIncorrectAnswer(btnAnswerD);
//                break;
//        }
//    }
//
//    @Override
//    public void onBackPressed() {
//
//        createCircularReveal(rootLayout.getHeight(), 0);
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                finish();
//                overridePendingTransition(R.anim.anim_not_move, R.anim.anim_not_move);
//
//            }
//        },500);
//    }
}
