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
import com.mimicki.afinal.Retrofit.ServiceModelLoginDriver;
import com.mimicki.afinal.SlidingTab.SlidingTabLayout;
import com.mimicki.afinal.fragment.DriverFragment;
import com.mimicki.afinal.fragment.DriverTwoFragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by DELL on 7/5/2016.
 */
public class TabTwoActivity extends FragmentActivity {

    private ViewPager mPager;
    private SlidingTabLayout mTabs;
    public static FragmentManager fragmentManager;
    private ProgressDialog progressDialog;
    private RestManager restmanager;
    private ServiceModelLoginDriver serviceModelLoginDriver;
    private String loginModelDriver;
    public static final String USERNAMEDRIVER = "usernamedriver";
    public static final String PASSWORDDRIVER = "passworddriver";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);

        fragmentManager = getSupportFragmentManager();

        mPager = (ViewPager) findViewById(R.id.pager);

        restmanager = new RestManager();

        String usernamedriver = getIntent().getStringExtra(USERNAMEDRIVER);
        String passworddriver = getIntent().getStringExtra(PASSWORDDRIVER);

        if (usernamedriver != null && passworddriver != null) {
            apiService(usernamedriver, passworddriver);
        } else {
            Intent intent = getIntent();
            loginModelDriver = intent.getStringExtra("Service");
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

    private void apiService(String usernamedriver, String passworddriver) {
        showLoading();

        restmanager.driver(usernamedriver, passworddriver, new Callback<ServiceModelLoginDriver>() {
            @Override
            public void onResponse(Call<ServiceModelLoginDriver> call, Response<ServiceModelLoginDriver> response) {

                serviceModelLoginDriver = response.body();
                if (response.isSuccessful()) {
                    if (response.body().status) {
                        Log.d(USERNAMEDRIVER, response.body().toString());

//                        loginModel = serviceModelLogin.getUserdata().getIdeplsuser();

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
            public void onFailure(Call<ServiceModelLoginDriver> call, Throwable t) {
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

        progressDialog = ProgressDialog.show(TabTwoActivity.this, null, "กำลังส่งข้อมูล...", true);

    }

    private void hideLoading() {
        if (progressDialog != null)
            progressDialog.dismiss();
    }


    class MyPagerAdapter extends FragmentPagerAdapter {

        int icons[] = {R.drawable.tab_help, R.drawable.tab_info};
        String[] tabText = getResources().getStringArray(R.array.tabs);

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
            tabText = getResources().getStringArray(R.array.tabs);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0)
                return DriverFragment.newInstance(loginModelDriver);
            else if (position == 1)
                return DriverTwoFragment.newInstance();

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

//    public static class MyFragment extends Fragment{
//
//        private TextView textView;
//
//        public static MyFragment getInstance(int position){
//            MyFragment myFragment = new MyFragment();
//            Bundle args = new Bundle();
//            args.putInt("position", position);
//            myFragment.setArguments(args);
//            return myFragment;
//        }
//
//    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
