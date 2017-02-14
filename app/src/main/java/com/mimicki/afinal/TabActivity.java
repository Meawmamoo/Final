package com.mimicki.afinal;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.util.Log;
import android.widget.Toast;

import com.mimicki.afinal.Retrofit.RestManager;
import com.mimicki.afinal.Retrofit.ServiceModelLogin;
import com.mimicki.afinal.SlidingTab.SlidingTabLayout;
import com.mimicki.afinal.fragment.OneFragment;
import com.mimicki.afinal.fragment.ThreeFragment;
import com.mimicki.afinal.fragment.TwoFragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by DELL on 6/30/2016.
 */
public class TabActivity extends FragmentActivity {

    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    // private static final int MY_REQUEST_CODE = 1;
    private static final int MY_REQUEST_CODE3 = 1;
    private ViewPager mPager;
    private SlidingTabLayout mTabs;
    public static FragmentManager fragmentManager;
    private ProgressDialog progressDialog;
    private RestManager restmanager;
    private ServiceModelLogin serviceModelLogin;
    private String loginModel;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);

        fragmentManager = getSupportFragmentManager();

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            if (ContextCompat.checkSelfPermission(TabActivity.this,
//                    android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                requestPermissions(new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION}, MY_REQUEST_CODE3);
//            return;
//            }
//        }

        mPager = (ViewPager) findViewById(R.id.pager);

        restmanager = new RestManager();

        String username = getIntent().getStringExtra(USERNAME);
        String password = getIntent().getStringExtra(PASSWORD);

        if (username != null && password != null) {
            apiService(username, password);
        } else {
            Intent intent = getIntent();
            loginModel = intent.getStringExtra("Service");
            setPagerAdapter();
        }


    }

    private void setPagerAdapter() {
        mPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        mTabs = (SlidingTabLayout) findViewById(R.id.tabs);
        mTabs.setCustomTabView(R.layout.custom_tab_view, R.id.tabText);
        mTabs.setDistributeEvenly(true);

        mTabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.mainGreen);
            }
        });
        mTabs.setViewPager(mPager);

    }

    private void apiService(String username, String password) {
        showLoading();

        restmanager.login(username, password, new Callback<ServiceModelLogin>() {
            @Override
            public void onResponse(Call<ServiceModelLogin> call, Response<ServiceModelLogin> response) {

                serviceModelLogin = response.body();
                if (response.isSuccessful()) {
                    if (response.body().status) {
                        Log.d(USERNAME, response.body().toString());

//                        loginModel = response.message();
                        loginModel = String.valueOf(response.body().getUserdata());

                        setPagerAdapter();
                    } else {
                        showMessage(String.valueOf(response.body().getErrmsg()));
                    }
                } else {

                    Toast.makeText(getApplicationContext(), response.message(), Toast.LENGTH_SHORT).show();
                }
                hideLoading();
//                setPagerAdapter();
            }

            @Override
            public void onFailure(Call<ServiceModelLogin> call, Throwable t) {
                String message = t.getMessage();
                Log.d("ข้อผิดพลาด", message);
                showMessage(message);
                hideLoading();
            }

        });

    }

    private void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        //Log.d("¡’¢ÈÕº‘¥æ≈“¥", message);
    }

    private void showLoading() {

        progressDialog = ProgressDialog.show(TabActivity.this, null, "กำลังส่งข้อมูล...", true);

    }

    private void hideLoading() {
        if (progressDialog != null)
            progressDialog.dismiss();
    }


    class MyPagerAdapter extends FragmentPagerAdapter {

        int icons[] = {R.drawable.tab_help, R.drawable.tab_edit, R.drawable.tab_info};
        String[] tabText = getResources().getStringArray(R.array.tabs);

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
            tabText = getResources().getStringArray(R.array.tabs);
        }


        @Override
        public Fragment getItem(int position) {

            if (position == 0)
                return OneFragment.newInstance();
            else if (position == 1)
                return TwoFragment.newInstance(loginModel);
            else if (position == 2)
                return ThreeFragment.newInstance();

            return null;
        }


        public CharSequence getPageTitle(int position) {

            Drawable drawable = getResources().getDrawable(icons[position]);
            //ColorStateList draw =  getResources().getColorStateList(R.color.tab_text_color);
            drawable.setBounds(10, 10, 80, 80);
            //ColorStateList.createFromXml( ,R.color.tab_text_color);
            ImageSpan imageSpan = new ImageSpan(drawable);
            SpannableString spannableString = new SpannableString(" ");
            spannableString.setSpan(imageSpan, 0, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            return spannableString;
        }


        @Override
        public int getCount() {
            return icons.length;
        }

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
