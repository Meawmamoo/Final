package com.mimicki.afinal;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by DELL on 9/20/2016.
 */
public class IntroActivity extends AppIntro {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setSlideOverAnimation();

        // Add your slide fragments here.
        // AppIntro will automatically generate the dots indicator and buttons.
 //       addSlide(IntroActivity.new(R.layout.activity_map));
//        addSlide(secondFragment);
//        addSlide(thirdFragment);
//        addSlide(fourthFragment);

        // Instead of fragments, you can also use our default slide
        // Just set a title, description, background and image. AppIntro will do the rest.
        //อย่าลืมทำ  fill pic ของแต่ละ slide รอ retrofit / push noti เสร็จก่อน
        addSlide(AppIntroFragment.newInstance("ขอต้อนเข้ารับสู่ EPLS", "ระบบระบุตำแหน่งผู้ป่วยฉุกเฉิน           ระบบที่จะช่วยแจ้งที่อยู่ของท่านอย่างแม่นยำและรวดเร็วมากยิ่งขึ้น" , R.drawable.pic_fill , Color.parseColor("#e65da3a7")));
        addSlide(AppIntroFragment.newInstance("อันดับแรก", "เเมื่อข้าสู่ระแบแล้วให้ไปที่หน้าที่สอง           เพื่อเติมข้อมูลที่อยู่ของท่านพร้อมกับระบุตำแหน่งบนแผนที่ตามคำแนะนำใต้แผนที่" , R.drawable.pic_fill , Color.parseColor("#e65da3a7")));
        addSlide(AppIntroFragment.newInstance("", "ปุ่มรูปหมุดสำหรับปักมุดลงบนแผนที่ตามวงกลมสีฟ้า" , R.drawable.pic_fill , Color.parseColor("#e65da3a7")));
        addSlide(AppIntroFragment.newInstance("", "กดค้างที่แผนที่เพื่อปักหมุดได้เหมือนกัน" , R.drawable.pic_fill , Color.parseColor("#e65da3a7")));
        addSlide(AppIntroFragment.newInstance("ปุ่มสีแดงในหน้าแรก", "กดค้างเพื่อเรียกรถพยาบาล" , R.drawable.pic_fill , Color.parseColor("#e65da3a7")));
        addSlide(AppIntroFragment.newInstance("", "เมื่อได้รับการยืนยันและรถพยาบาลถูกส่งออกมาแล้ว           กดที่'ติดตามสถานะรถ'เพื่อติดตามเส้นทางการเดินของรถได้" , R.drawable.pic_fill , Color.parseColor("#e65da3a7")));

      //  setIndicatorColor(Color.parseColor("#ff0000"), Color.parseColor("#00ff00"));

        askForPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
        askForPermissions(new String[]{Manifest.permission.CAMERA , Manifest.permission.READ_EXTERNAL_STORAGE}, 2);

        // OPTIONAL METHODS
        // Override bar/separator color.
        setBarColor(Color.parseColor("#ffffff"));
        setSeparatorColor(Color.parseColor("#ffffff"));

        // Hide Skip/Done button.
        showSkipButton(true);
        setProgressButtonEnabled(true);

        // Turn vibration on and set intensity.
        // NOTE: you will probably need to ask VIBRATE permission in Manifest.
        setVibrate(false);
//        setVibrateIntensity(30);

        setIndicatorColor(Color.parseColor("#000000"), Color.parseColor("#414141"));

        setSkipText("ข้าม");
        setDoneText("เสร็จสิ้น");
        setColorSkipButton(Color.parseColor("#414141"));
        setColorDoneText(Color.parseColor("#414141"));
        setNextArrowColor(Color.parseColor("#414141"));
    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        Intent intent = new Intent(IntroActivity.this, RegisterActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        // Do something when users tap on Done button.
        Intent intent = new Intent(IntroActivity.this, RegisterActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onSlideChanged(@Nullable Fragment oldFragment, @Nullable Fragment newFragment) {
        super.onSlideChanged(oldFragment, newFragment);
        // Do something when the slide changes.
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

}
