package com.mimicki.afinal;

import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import com.hanks.htextview.HTextView;
import com.hanks.htextview.HTextViewType;
import com.mimicki.afinal.utils.SaveSharedPreference;

/**
 * Created by DELL on 7/8/2016.
 */
public class SplashScreenActivity extends AppCompatActivity {
    Runnable runnable;
    long delay_time;
    long time = 1000L;
    HTextView hTextView;

//    AnimationDrawable splash;
//    ImageView image;
//    Timer timer;
//    MyTimerTask myTimerTask;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFormat(PixelFormat.RGBA_8888);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.splashscreen);


        hTextView = (HTextView) findViewById(R.id.textSplash);
        //hTextView.setTypeface(FontManager.getInstance(getAssets()).getFont("fonts/font-name.ttf"));
// be sure to set custom typeface before setting the animate type, otherwise the font may not be updated.
        hTextView.setAnimateType(HTextViewType.LINE);
        hTextView.animateText("E P L S"); // animate


//        splash = (AnimationDrawable) image.getBackground();
//        image.post(new Starter());

        runnable = new Runnable() {
            public void run() {
//                Intent intent = new Intent(SplashScreenActivity.this, IntroActivity.class);
//                startActivity(intent);
//              overridePendingTransition(R.anim.mainfadein, R.anim.splashfadeout);
//                finish();

                if (SaveSharedPreference.getFirstTime(SplashScreenActivity.this)) {
                    SaveSharedPreference.setFirstTime(SplashScreenActivity.this , false);
                    Intent intent = new Intent(
                            SplashScreenActivity.this, IntroActivity.class);
                    //overridePendingTransition(R.anim.splashfadeout, R.anim.mainfadein);
                    startActivity(intent);
                    finish();
                } else {
                    if (!SaveSharedPreference.getUserName(SplashScreenActivity.this).equals("") && !SaveSharedPreference.getPassWord(SplashScreenActivity.this).equals("")) {
                        Intent intent = new Intent(
                                SplashScreenActivity.this, TabActivity.class);
                        intent.putExtra(TabActivity.USERNAME, SaveSharedPreference.getUserName(SplashScreenActivity.this));
                        intent.putExtra(TabActivity.PASSWORD, SaveSharedPreference.getPassWord(SplashScreenActivity.this));
//                        overridePendingTransition(R.anim.splashfadeout, R.anim.mainfadein);
                        startActivity(intent);
                        finish();
                    }
                    else if (!SaveSharedPreference.getUserNameDriver(SplashScreenActivity.this).equals("") && !SaveSharedPreference.getPassWordDriver(SplashScreenActivity.this).equals("")) {
                        Intent intent = new Intent(
                                SplashScreenActivity.this, TabTwoActivity.class);
                        intent.putExtra(TabTwoActivity.USERNAMEDRIVER, SaveSharedPreference.getUserNameDriver(SplashScreenActivity.this));
                        intent.putExtra(TabTwoActivity.PASSWORDDRIVER, SaveSharedPreference.getPassWordDriver(SplashScreenActivity.this));
                        //overridePendingTransition(R.anim.splashfadeout, R.anim.mainfadein);
                        startActivity(intent);
                        finish();
                    }else {
                        Intent intent2 = new Intent(
                                SplashScreenActivity.this, MainActivity.class);
                        //overridePendingTransition(R.anim.splashfadeout, R.anim.mainfadein);
                        startActivity(intent2);
                        finish();
                    }

                }


                //Calculate the total duration
//        int duration = 0;
//        for (int i = 0; i < splash.getNumberOfFrames(); i++) {
//            duration += splash.getDuration(i);
//        }
//
//        timer = new Timer();
//        myTimerTask = new MyTimerTask();
//        timer.schedule(myTimerTask, duration);
//    }

//    class MyTimerTask extends TimerTask {

//        @Override
//        public void run() {
//
//            timer.cancel();
//            if (SaveSharedPreference.getUserName(SplashScreenActivity.this).length() == 0) {
//                Intent intent = new Intent(
//                        SplashScreenActivity.this, MainActivity.class);
//                //overridePendingTransition(R.anim.splashfadeout, R.anim.mainfadein);
//                startActivity(intent);
//                finish();
//            } else {
//                Intent intent2 = new Intent(
//                        SplashScreenActivity.this, TabActivity.class);
//                //overridePendingTransition(R.anim.splashfadeout, R.anim.mainfadein);
//                startActivity(intent2);
//                finish();
//            }
            }
            };
    }

    public void onResume() {
        super.onResume();
        delay_time = time;
        hTextView.postDelayed(runnable, delay_time);
        time = System.currentTimeMillis();
    }

    public void onPause() {
        super.onPause();
        hTextView.removeCallbacks(runnable);
        time = delay_time - (System.currentTimeMillis() - time);
    }


//    class Starter implements Runnable {
//        public void run() {
//
//            splash.start();
//        }
//    }

//    public boolean onTouchEvent(MotionEvent event) {
//        if (event.getAction() == MotionEvent.ACTION_DOWN) {
//            Intent intent = new Intent(getApplicationContext()
//                    , MainActivity.class);
//            startActivity(intent);
//            this.finish();
//            return true;
//        }
//        return super.onTouchEvent(event);
//    }


}