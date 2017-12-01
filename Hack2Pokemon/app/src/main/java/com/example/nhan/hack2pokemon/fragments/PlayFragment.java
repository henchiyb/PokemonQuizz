package com.example.nhan.hack2pokemon.fragments;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
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

import static android.app.Activity.RESULT_OK;


public class PlayFragment extends Fragment implements View.OnClickListener {
    private LinearLayout layoutBackground;
    private FrameLayout rootLayout;
    private RelativeLayout layoutImage;
    private LinearLayout layoutButton;

    private ProgressBar progressBar;
    private TextView tvPlayScore;
    private ImageView imageViewPokemon;
    private TextView tvTagName;

    private List<Button> listButtonAnswer;
    private Button btnAnswerA;
    private Button btnAnswerB;
    private Button btnAnswerC;
    private Button btnAnswerD;

    private int playScore;
    private MediaPlayer mediaPlayer;
    private Pokemon pokemon;
    private SoundEffects soundEffects;

    Animator animator;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private Animator createCircularReveal(View rootLayout, float startRadius, float finalRadius){
        int cx = (rootLayout.getWidth() / 2);
        int cy = (int) (rootLayout.getHeight() * 5.05f / 8.1f);
        Animator animator = ViewAnimationUtils.createCircularReveal(rootLayout, cx, cy, startRadius, finalRadius);
        animator.setDuration(500);
        rootLayout.setVisibility(View.VISIBLE);
        return animator;
    }
    private void openFragment(android.app.Fragment fragment) {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment).addToBackStack(fragment.getClass().getName())
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.addToBackStack(fragment.getClass().getName());
        fragmentTransaction.commit();
    }

    private void onBackPressed(){
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if( event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK )
                {
                    Animator animator = createCircularReveal(rootLayout, rootLayout.getHeight(), 0);
                    animator.start();
                    animator.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {

                            getFragmentManager().popBackStack();
//                            getFragmentManager().popBackStackImmediate();
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    });

                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        onBackPressed();
        controlTimeAndProgressBar();
        mediaPlayer = new MediaPlayer();
        Utils.setDataSourceForMediaPlayer(this.getActivity(), mediaPlayer, Constant.MUSIC_PLAY);
        mediaPlayer.start();
    }

    @Override
    public void onPause() {
        super.onPause();
        mediaPlayer.release();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mediaPlayer.release();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Constant.listGenPicked.add("1");
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_play, container, false);
        initLayout(view);
        rootLayout.setVisibility(View.INVISIBLE);
        ViewTreeObserver viewTreeObserver = rootLayout.getViewTreeObserver();
        if (viewTreeObserver.isAlive()) {
            viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    animator = createCircularReveal(rootLayout, 0, rootLayout.getHeight());
                    animator.start();
                    rootLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
            });
        }
        createQuestion();
        return view;
    }

    private void initLayout(View view){

        rootLayout = (FrameLayout) view.findViewById(R.id.root_layout);
        layoutBackground = (LinearLayout) view.findViewById(R.id.layout_background);
        soundEffects = SoundEffects.getInstance(view.getContext());
        layoutImage = (RelativeLayout) view.findViewById(R.id.layout_image);
        layoutButton = (LinearLayout) view.findViewById(R.id.layout_btn_answer);

        progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
        progressBar.setMax(Constant.TIME_PLAY * 1000);

        tvPlayScore = (TextView) view.findViewById(R.id.tv_play_score);
        tvPlayScore.setTypeface(Utils.loadFontFromAssetFolder(view.getContext(), Constant.FONT_SCORE));

        imageViewPokemon = (ImageView) view.findViewById(R.id.image_view_pokemon);
        tvTagName = (TextView) view.findViewById(R.id.tv_tag_name);
        tvTagName.setTypeface(Utils.loadFontFromAssetFolder(view.getContext(), Constant.FONT_HIGH_SCORE));

        listButtonAnswer = new ArrayList<>();
        btnAnswerA = (Button) view.findViewById(R.id.btn_answer_a);
        btnAnswerB = (Button) view.findViewById(R.id.btn_answer_b);
        btnAnswerC = (Button) view.findViewById(R.id.btn_answer_c);
        btnAnswerD = (Button) view.findViewById(R.id.btn_answer_d);

        btnAnswerA.setOnClickListener(this);
        btnAnswerB.setOnClickListener(this);
        btnAnswerC.setOnClickListener(this);
        btnAnswerD.setOnClickListener(this);

        listButtonAnswer.add(btnAnswerA);
        listButtonAnswer.add(btnAnswerB);
        listButtonAnswer.add(btnAnswerC);
        listButtonAnswer.add(btnAnswerD);
    }

    private void createQuestion(){
        setClickAbleButtonAnswer(true);
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this.getActivity());
        databaseAccess.open();
        pokemon = databaseAccess.getRandomOnePokemon(Constant.listTagSeen, Constant.listGenPicked);
        List<String> listNameForWrongAnswer = databaseAccess.getListNameForWrongAnswer(Constant.listTagSeen, Constant.listGenPicked);
        databaseAccess.close();

        Bitmap bitmapShadow = createShadowBitmap(Utils.loadBitmapFromAssetFolder(this.getActivity(), pokemon.getImg()));
        imageViewPokemon.setImageBitmap(bitmapShadow);
        tvTagName.setText("");

        Collections.shuffle(listButtonAnswer);

        listButtonAnswer.get(0).setText(pokemon.getName());
        listButtonAnswer.get(1).setText(listNameForWrongAnswer.get(0));
        listButtonAnswer.get(2).setText(listNameForWrongAnswer.get(1));
        listButtonAnswer.get(3).setText(listNameForWrongAnswer.get(2));

        btnAnswerA.setBackgroundResource(R.drawable.custom_view_circular_white);
        btnAnswerB.setBackgroundResource(R.drawable.custom_view_circular_white);
        btnAnswerC.setBackgroundResource(R.drawable.custom_view_circular_white);
        btnAnswerD.setBackgroundResource(R.drawable.custom_view_circular_white);

        layoutBackground.setBackgroundColor(Color.parseColor(pokemon.getColor()));

    }

    private Bitmap createShadowBitmap(Bitmap bitmap){
        Bitmap bm = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());
        for (int i =0; i < bitmap.getWidth(); i++){
            for (int j =0; j < bitmap.getHeight(); j++){
                int p = bitmap.getPixel(i, j);
                int alpha = Color.alpha(p);
                if (alpha != 0){
                    bm.setPixel(i, j, Color.BLACK);
                }
            }
        }
        return bm;
    }

    private void checkCorrectAnswer(){
        for (Button button : listButtonAnswer){
            if (pokemon.getName().equals(button.getText())){
                button.setBackgroundResource(R.drawable.custom_view_circular_green);
            }
        }
    }

    private void checkIncorrectAnswer(Button button){
        if (pokemon.getName().equals(button.getText())){
            button.setBackgroundResource(R.drawable.custom_view_circular_green);
            playScore++;
            tvPlayScore.setText(String.valueOf(playScore));
            soundEffects.playSoundCorrect();
        } else {
            button.setBackgroundResource(R.drawable.custom_view_circular_red);
            soundEffects.playSoundIncorrect();
        }
    }

    private void setClickAbleButtonAnswer(Boolean check){
        btnAnswerA.setClickable(check);
        btnAnswerB.setClickable(check);
        btnAnswerC.setClickable(check);
        btnAnswerD.setClickable(check);
    }

    private void controlTimeAndProgressBar(){
        CountDownTimer timer = new CountDownTimer(Constant.TIME_PLAY * 1000, 10) {
            @Override
            public void onTick(long millisUntilFinished) {
                progressBar.setProgress(Constant.TIME_PLAY * 1000 - (int)millisUntilFinished);
            }

            @Override
            public void onFinish() {
                Intent intent = new Intent();
                intent.putExtra(Constant.SCORE_KEY, playScore);
//                setResult(RESULT_OK, intent);
                rootLayout.setVisibility(View.INVISIBLE);
                ViewTreeObserver viewTreeObserver = rootLayout.getViewTreeObserver();
                if (viewTreeObserver.isAlive()){
                    viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                        @Override
                        public void onGlobalLayout() {
                            createCircularReveal(rootLayout, rootLayout.getHeight(), 0);
                            rootLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        }
                    });
                }
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getFragmentManager().popBackStackImmediate();
                    }
                },500);


            }
        };
        timer.start();
    }

    @Override
    public void onClick(View v) {
        setClickAbleButtonAnswer(false);
        ObjectAnimator objectAnimatorStart = ObjectAnimator.ofFloat(imageViewPokemon, "rotationY" , 0, 90);
        final ObjectAnimator objectAnimatorEnd = ObjectAnimator.ofFloat(imageViewPokemon, "rotationY" , 90, 180);

        objectAnimatorStart.setDuration(150);
        objectAnimatorEnd.setDuration(150);
        final TranslateAnimation animationOut = new TranslateAnimation(0, -Utils.getActivityWidthPixel(this.getActivity()), 0, 0);
        animationOut.setDuration(200);
        animationOut.setStartOffset(500);

        final TranslateAnimation animationIn = new TranslateAnimation(Utils.getActivityWidthPixel(this.getActivity()), 0, 0, 0);
        animationIn.setDuration(200);

        objectAnimatorStart.start();
        objectAnimatorStart.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                imageViewPokemon.setImageBitmap(Utils.loadBitmapFromAssetFolder(PlayFragment.this.getActivity(), pokemon.getImg()));
                objectAnimatorEnd.start();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        objectAnimatorEnd.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                tvTagName.setText(String.format("%s %s", pokemon.getTag(), pokemon.getName()));
                layoutImage.startAnimation(animationOut);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        animationOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                imageViewPokemon.setRotationY(0);
                createQuestion();
                layoutButton.setVisibility(View.INVISIBLE);
                layoutImage.startAnimation(animationIn);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        animationIn.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                layoutButton.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        checkCorrectAnswer();
        switch (v.getId()){
            case R.id.btn_answer_a:
                checkIncorrectAnswer(btnAnswerA);
                break;
            case R.id.btn_answer_b:
                checkIncorrectAnswer(btnAnswerB);
                break;
            case R.id.btn_answer_c:
                checkIncorrectAnswer(btnAnswerC);
                break;
            case R.id.btn_answer_d:
                checkIncorrectAnswer(btnAnswerD);
                break;
        }
    }
}
