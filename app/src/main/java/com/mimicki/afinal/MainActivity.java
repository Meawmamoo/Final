package com.mimicki.afinal;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mimicki.afinal.Retrofit.RestManager;
import com.mimicki.afinal.Retrofit.ServiceModelLogin;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity {

    EditText user, pass;
    private ServiceModelLogin serviceModelLogin;
    ProgressDialog progressDialog;
    RestManager restmanager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
//        progressDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);

        TextView button1 = (TextView) findViewById(R.id.buttonLogin);
        TextView button2 = (TextView) findViewById(R.id.forgotPassword);
        TextView button3 = (TextView) findViewById(R.id.driverMain);
        user = (EditText) findViewById(R.id.username);
        pass = (EditText) findViewById(R.id.password);


        restmanager = new RestManager();


//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            if (ContextCompat.checkSelfPermission(getApplication(),
//                    android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//                requestPermissions(new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, MY_REQUEST_CODE2);
//
//            }
//        }





            button1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final String sUser = user.getText().toString();
                    final String sPass = pass.getText().toString();


                    if (sUser.isEmpty() || sUser.length() == 0 || sUser.equals("") || sUser == null) {
                        Toast.makeText(MainActivity.this, "กรุณาใส่ e-mail ของท่าน", Toast.LENGTH_SHORT).show();
                    } else if (sUser.length() < 10) {
                        Toast.makeText(MainActivity.this, "กรุณาใส่ e-mail มากกว่า 10 ตัวอักษร", Toast.LENGTH_SHORT).show();
                    }
//                    else if (sUser.matches("AdminDriver") && sPass.matches("admindriver")) {
//                        Intent i = new Intent(getApplicationContext(), TabTwoActivity.class);
//                        startActivity(i);
//                    }
                    else {
                        if (sPass.isEmpty() || sPass.length() == 0 || sPass.equals("") || sPass == null) {
                            Toast.makeText(MainActivity.this, "กรุณาใส่รหัสผ่านของท่าน", Toast.LENGTH_SHORT).show();
                        } else if (sPass.length() < 6) {
                            Toast.makeText(MainActivity.this, "กรุณาใส่รหัสผ่านมากกว่า 10 ตัวอักษร", Toast.LENGTH_SHORT).show();
                        } else {
//                            try {
//                                apiService();
                            showLoading();

                            restmanager.login(sUser, sPass, new Callback<ServiceModelLogin>() {
                                @Override
                                public void onResponse(Call<ServiceModelLogin> call, Response<ServiceModelLogin> response) {

                                    serviceModelLogin = response.body();
                                    if (response.isSuccessful()) {
//                                        Toast.makeText(MainActivity.this, response.body().toString(), Toast.LENGTH_SHORT).show();
                                        if (response.body().status) {
                                            showMessage("สำเร็จ");
//                                            SaveSharedPreference.setUserName(getApplicationContext(), sUser);
//                                            SaveSharedPreference.setPassWord(getApplicationContext(), sPass);
                                            Intent intent = new Intent(getApplicationContext(), TabActivity.class);
                                            intent.putExtra("Service", new Gson().toJson(serviceModelLogin));
                                            startActivity(intent);
                                            finish();
                                        }

                                        else {

                                        showMessage(String.valueOf(response.body().getErrmsg()));
//                                            Toast.makeText(getApplicationContext(), response.message() , Toast.LENGTH_SHORT).show();
                                        }
                                    } else {

//                                        String message = response.toString();
//                                        showMessage(message);
                                        Toast.makeText(getApplicationContext(), response.message() , Toast.LENGTH_SHORT).show();
                                    }
//                                    progressdialog.dismiss();
                                    hideLoading();
                                }

                                @Override
                                public void onFailure(Call<ServiceModelLogin> call, Throwable t) {
                                    String message = t.getMessage();
                                    Log.d("มีข้อผิดพลาด", message);
                                    showMessage(message);
//                                    progressdialog.dismiss();
                                    hideLoading();
                                }

                            });

                        }
//                            } catch (ParseException e) {
//                                e.printStackTrace();
                    }

//                    }
                }
            });

            button2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {

                    final Dialog dialog = new Dialog(MainActivity.this);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.customdialog);
                    dialog.setCancelable(true);

                    final EditText dialogEmail = (EditText) dialog.findViewById(R.id.dialogEmail);
                    Button buttonSend = (Button) dialog.findViewById(R.id.buttonSend);
                    buttonSend.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {

                            String sDialogEmail = dialogEmail.getText().toString();

                            if (sDialogEmail.isEmpty()) {
                                Toast.makeText(MainActivity.this, "กรุณาใส่ e-mail ของท่าน", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(MainActivity.this, "รหัสผ่านใหม่ได้ถูกส่งไปยัง e-mail เรียบร้อยแล้ว", Toast.LENGTH_LONG).show();
                            }
                        }
                    });

                    Button buttonNotSend = (Button) dialog.findViewById(R.id.buttonNotSend);
                    buttonNotSend.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.cancel();
                        }
                    });

                    dialog.show();

                }
            });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iDriver = new Intent(MainActivity.this, DriverActivity.class);
                startActivity(iDriver);
                finish();
            }
        });

        user.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
                else if (hasFocus) {
                    if (user.getText().toString()
                            .length() < 10) {

                        user.setError("ผิดพลาด");
                    } else {
                        user.setError(null);
                    }
                }
            }
        });



        pass.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
                else if (hasFocus) {
                    if (pass.getText().toString()
                            .length() < 6) {

                        pass.setError("ผิดพลาด");
                    } else {
                        pass.setError(null);
                    }
                }
            }
        });

        ConnectivityManager cn = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo nf = cn.getActiveNetworkInfo();

        final Dialog dialog = new Dialog(MainActivity.this);
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


    private void showMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        Log.d("มีข้อผิดพลาด", message);
    }

    private void showLoading(){

        progressDialog = ProgressDialog.show(this, null, "กำลังเข้าสู่ระบบ...", true);

    }

    private void hideLoading(){
        if(progressDialog != null)
            progressDialog.dismiss();
    }

    //to hide editText when click on the screen
    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    //Retrofit Service
   // private void apiService() throws ParseException {
//        Service mManager = RestManager.getBuilder(this).create(Service.class);
//        Call<ServiceModelLogin> call;

//        progressdialog=new ProgressDialog(this);
//        progressdialog.setMessage("กำลังเข้าสู่ระบบ...");
//        progressdialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//        progressdialog.setIndeterminate(true);
//        progressdialog.show();

//        call = mManager.postServiceModelLogin(user.getText().toString(), pass.getText().toString());


//        restmanager.login(sUser, sPass, new Callback<ServiceModelLogin>() {
//            @Override
//            public void onResponse(Call<ServiceModelLogin> call, Response<ServiceModelLogin> response) {
//
//                serviceModelLogin = response.body();
//                if(response.isSuccessful()){
//                    Toast.makeText(getApplicationContext(), response.body().toString(), Toast.LENGTH_SHORT).show();
//                    if(response.body().status){
//                        SaveSharedPreference.setUserName(getApplicationContext(), user);
//                        Intent intent = new Intent(getApplicationContext(), TabActivity.class);
//                        intent.putExtra("Service", new Gson().toJson(serviceModelLogin));
//                        startActivity(intent);
//                        finish();
//                    }
//                }else{
//
//                    Toast.makeText(getApplicationContext(), response.message(), Toast.LENGTH_SHORT).show();
//                }
//                progressdialog.dismiss();
//            }

        /////////////////////////////////////////////////////////////////////////////////////////////////////////////
//            public void onResponse(Call<ServiceModelLogin> call, Response<ServiceModelLogin> response) {
//                serviceModelLogin = response.body();
//
//                if (response.body().isIsConnectDBSuccess() == true ) {
//                    if(response.body().isStatus() == true) {
//                        Toast.makeText(getApplication(), "สำเร็จ", Toast.LENGTH_SHORT).show();
//                        SaveSharedPreference.setUserName(getApplicationContext(), user);
//
////                    if (CheckPermission.checkPermissionLocation(MainActivity.this)) {
//
//                        Intent intent = new Intent(getApplicationContext(), TabActivity.class);
//                        intent.putExtra("Service", new Gson().toJson(serviceModelLogin));
//                        startActivity(intent);
//                        finish();
//
////                    }
//                    }
//                }

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
//                else {
//                    Toast.makeText(MainActivity.this, String.format(String.valueOf(serviceModelLogin.getUserdata())) , Toast.LENGTH_SHORT).show();
//                    progressdialog.dismiss();
//                }
//
//                progressdialog.dismiss();
//            }


//            @Override
//            public void onFailure(Call<ServiceModelLogin> call, Throwable t) {
//                String message = t.getMessage();
//                Log.d("มีข้อผิดพลาด" , message );
//                progressdialog.dismiss();
//            }
//
//        });
//
//    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
    }

