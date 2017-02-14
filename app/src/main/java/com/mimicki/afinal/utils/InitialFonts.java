package com.mimicki.afinal.utils;

import android.app.Application;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by DELL on 9/22/2016.
 */
public class InitialFonts extends Application {


    @Override
    public void onCreate() {
        super.onCreate();

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/RSU_Regular.ttf").setFontAttrId(uk.co.chrisjenx.calligraphy.R.attr.fontPath).build());


    }

}
