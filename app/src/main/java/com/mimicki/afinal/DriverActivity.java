package com.mimicki.afinal;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mimicki.afinal.Retrofit.RestManager;
import com.mimicki.afinal.Retrofit.ServiceModelLoginDriver;
import com.mimicki.afinal.utils.SaveSharedPreference;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by DELL on 11/21/2016.
 */
public class DriverActivity extends AppCompatActivity {

    EditText userDriver, passDriver;
    private ServiceModelLoginDriver serviceModelLoginDriver;
    ProgressDialog progressDialog;
    RestManager restmanager;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_driver_main);

        TextView buttonDriver = (TextView) findViewById(R.id.buttonLoginDriver);
        TextView buttonBackMain = (TextView) findViewById(R.id.backUserMain);
        userDriver = (EditText) findViewById(R.id.usernameDriver);
        passDriver = (EditText) findViewById(R.id.passwordDriver);

        restmanager = new RestManager();


        buttonDriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String sUserDriver = userDriver.getText().toString();
                final String sPassDriver = passDriver.getText().toString();

                if (sUserDriver.isEmpty() || sUserDriver.length() == 0 || sUserDriver.equals("") || sUserDriver == null) {
                    Toast.makeText(DriverActivity.this, "กรุณาใส่ชื่อผู้ใช้คนขับ", Toast.LENGTH_SHORT).show();
                } else {
                    if (sPassDriver.isEmpty() || sPassDriver.length() == 0 || sPassDriver.equals("") || sPassDriver == null) {
                        Toast.makeText(DriverActivity.this, "กรุณาใส่รหัสผ่านของท่าน", Toast.LENGTH_SHORT).show();
                    } else if (sPassDriver.length() < 3) {
                        Toast.makeText(DriverActivity.this, "กรุณาใส่รหัสผ่านมากกว่า 3 ตัวอักษร", Toast.LENGTH_SHORT).show();
                    } else {

                        showLoading();

                        restmanager.driver(sUserDriver, sPassDriver, new Callback<ServiceModelLoginDriver>() {
                            @Override
                            public void onResponse(Call<ServiceModelLoginDriver> call, Response<ServiceModelLoginDriver> response) {

                                serviceModelLoginDriver = response.body();
                                if (response.isSuccessful()) {
//                                        Toast.makeText(MainActivity.this, response.body().toString(), Toast.LENGTH_SHORT).show();
                                    if (response.body().status) {
                                        showMessage("สำเร็จ");
                                        SaveSharedPreference.setUserNameDriver(getApplicationContext(), sUserDriver);
                                        SaveSharedPreference.setPassWordDriver(getApplicationContext(), sPassDriver);
                                        Intent intent = new Intent(getApplicationContext(), TabTwoActivity.class);
                                        intent.putExtra("Service", new Gson().toJson(serviceModelLoginDriver));
                                        startActivity(intent);
                                        finish();
                                    } else {

                                        showMessage(String.valueOf(response.body().getErrmsg()));
//                                            Toast.makeText(getApplicationContext(), response.message() , Toast.LENGTH_SHORT).show();
                                    }
                                } else {


                                    Toast.makeText(getApplicationContext(), response.message(), Toast.LENGTH_SHORT).show();
                                }
                                hideLoading();
                            }

                            @Override
                            public void onFailure(Call<ServiceModelLoginDriver> call, Throwable t) {
                                String message = t.getMessage();
                                Log.d("มีข้อผิดพลาด", message);
                                showMessage(message);
//                                    progressdialog.dismiss();
                                hideLoading();
                            }

                        });


                    }
                }
            }
        });

        userDriver.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                } else if (hasFocus) {
                    if (userDriver.getText().toString()
                            .length() < 10) {

                        userDriver.setError("ผิดพลาด");
                    } else {
                        userDriver.setError(null);
                    }
                }
            }
        });


        passDriver.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                } else if (hasFocus) {
                    if (passDriver.getText().toString()
                            .length() < 6) {

                        passDriver.setError("ผิดพลาด");
                    } else {
                        userDriver.setError(null);
                    }
                }
            }
        });

        buttonBackMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iMain = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(iMain);
                finish();
            }
        });

        userDriver.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                } else if (hasFocus) {
                    if (userDriver.getText().toString()
                            .length() < 10) {

                        userDriver.setError("ผิดพลาด");
                    } else {
                        userDriver.setError(null);
                    }
                }
            }
        });


        passDriver.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                } else if (hasFocus) {
                    if (passDriver.getText().toString()
                            .length() < 6) {

                        passDriver.setError("ผิดพลาด");
                    } else {
                        passDriver.setError(null);
                    }
                }
            }
        });

        ConnectivityManager cn = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo nf = cn.getActiveNetworkInfo();

        final Dialog dialog = new Dialog(DriverActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.customdialog_nonetwork);
        dialog.setCancelable(true);

        if (nf != null && nf.isConnected() == true) {
            Toast.makeText(this, "เชื่อมต่ออินเตอร์เน็ตเรียบร้อยแล้ว", Toast.LENGTH_LONG).show();

        } else {
            TextView buttonOk = (TextView) dialog.findViewById(R.id.buttonOk);
            buttonOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.cancel();
                }
            });
            dialog.show();
        }


    }

    private void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        Log.d("มีข้อผิดพลาด", message);
    }

    private void showLoading() {

        progressDialog = ProgressDialog.show(this, null, "กำลังเข้าสู่ระบบ...", true);

    }

    private void hideLoading() {
        if (progressDialog != null)
            progressDialog.dismiss();
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
