package com.mimicki.afinal;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.mimicki.afinal.Retrofit.RestManager;
import com.mimicki.afinal.Retrofit.ServiceModel;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by DELL on 6/28/2016.
 */
public class RegisterActivity extends AppCompatActivity {

    private Calendar calendar;
    private TextView dateView;
    private int year, month, day;
    public EditText fname, lname, phone, ephone, userRegis, passRegis, passConRegis;
    //            userAdd, userLat ,userLng;
    public RadioGroup radioGroup;
    public RadioButton btnMale, btnFemale;
    String gender = null;
    RestManager restmanager;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstantState) {
        super.onCreate(savedInstantState);
        setContentView(R.layout.activity_register);

        LinearLayout scrollView = (LinearLayout) findViewById(R.id.scrollView);
        fname = (EditText) findViewById(R.id.buttonFname);
        lname = (EditText) findViewById(R.id.buttonLname);
        phone = (EditText) findViewById(R.id.buttonPhone);
        ephone = (EditText) findViewById(R.id.buttonEmPhone);
        userRegis = (EditText) findViewById(R.id.usernameRegis);
        passRegis = (EditText) findViewById(R.id.passwordRegis);
        passConRegis = (EditText) findViewById(R.id.passwordConfirmRegis);
        TextView confirm = (TextView) findViewById(R.id.buttonConfirm);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroupGen);
        btnMale = (RadioButton) findViewById(R.id.buttonMale);
        btnFemale = (RadioButton) findViewById(R.id.buttonFemale);
        TextView allready = (TextView) findViewById(R.id.allReadyRegis);

        restmanager = new RestManager();

//        mHelper = new DBHelper(this);


//        TextView is1 = (TextView) findViewById(R.id.issue1);
//        TextView is2 = (TextView) findViewById(R.id.issue2);
//        Typeface typeface = Typeface.createFromAsset(getAssets(), "RSU_BOLD.ttf");
//
//        is1.setTypeface(typeface);
//        is2.setTypeface(typeface);


//      set การมองเห็น ถ้ามองไม่เห้ฯไปเซตใน xml ได้
//      phone.setVisibility(View.VISIBLE);

        dateView = (TextView) findViewById(R.id.buttonDOB);

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        showDate(year, month + 1, day);


        scrollView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard(v);
            }
        });

        fname.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        lname.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        phone.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        ephone.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        userRegis.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                } else if (hasFocus) {
                    if (userRegis.getText().toString()
                            .length() < 10) {

                        userRegis.setError("Failed");
                    } else {
                        userRegis.setError(null);
                    }
                }
            }
        });

        passRegis.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                } else if (hasFocus) {
                    if (passRegis.getText().toString()
                            .length() < 6) {
                        passRegis.setError("Failed");
                    } else {
                        passRegis.setError(null);
                    }
                }
            }
        });

        passConRegis.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sFname = fname.getText().toString();
                String sLname = lname.getText().toString();
                String sPhone = phone.getText().toString();
                String sEphone = ephone.getText().toString();
                final String sUserRegis = userRegis.getText().toString();
                final String sRegis = passRegis.getText().toString();
                String sConRegis = passConRegis.getText().toString();

                if (btnMale.isChecked()) {
                    gender = "M";
                } else {
                    gender = "F";
                }

                DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy"); // Make sure user insert date into edittext in this format.

                String dob_var = (dateView.getText().toString());

                Date dateObject = null;

                try {
                    dateObject = formatter.parse(dob_var);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                String date = new SimpleDateFormat("yyyy-MM-dd").format(dateObject);

                String userAdd = "empty";

                if (sFname.isEmpty() || sFname.length() == 0 || sFname.equals("") || sFname == null) {
                    Toast.makeText(RegisterActivity.this, "กรุณาใส่ชื่อจริง", Toast.LENGTH_SHORT).show();
                } else if (sLname.isEmpty() || sLname.length() == 0 || sLname.equals("") || sLname == null) {
                    Toast.makeText(RegisterActivity.this, "กรุณาใส่นามสกุล", Toast.LENGTH_SHORT).show();
                } else if (sPhone.isEmpty() || sPhone.length() == 0 || sPhone.equals("") || sPhone == null) {
                    Toast.makeText(RegisterActivity.this, "กรุณาใส่เบอร์โทรศัพท์ของท่าน", Toast.LENGTH_SHORT).show();
                } else if (sEphone.isEmpty() || sEphone.length() == 0 || sEphone.equals("") || sEphone == null) {
                    Toast.makeText(RegisterActivity.this, "กรุณาใส่เบอร์โทรศัพท์ของบุคคลใกล้ชิด", Toast.LENGTH_SHORT).show();
                } else if (sUserRegis.isEmpty() || sUserRegis.length() == 0 || sUserRegis.equals("") || sUserRegis == null) {
                    Toast.makeText(RegisterActivity.this, "กรุณาใส่ e-mail", Toast.LENGTH_SHORT).show();
                } else if (sUserRegis.length() < 10) {
                    Toast.makeText(RegisterActivity.this, "กรุณาใส่ e-mail มากกว่า 10 ตัวอักษร", Toast.LENGTH_SHORT).show();
                } else if (sRegis.isEmpty() || sRegis.length() == 0 || sRegis.equals("") || sRegis == null) {
                    Toast.makeText(RegisterActivity.this, "กรุณาใส่รหัสผ่าน", Toast.LENGTH_SHORT).show();
                } else if (sRegis.length() < 6) {
                    Toast.makeText(RegisterActivity.this, "กรุณาใส่รหัสผ่านมากกว่า 10 ตัวอักษร", Toast.LENGTH_SHORT).show();
                } else if (sConRegis.isEmpty() || sConRegis.length() == 0 || sConRegis.equals("") || sConRegis == null) {
                    Toast.makeText(RegisterActivity.this, "กรุณาใส่ยืนยันรหัสผ่าน", Toast.LENGTH_SHORT).show();
                } else if (sRegis.equals(sConRegis)) {
                    //Toast.makeText(RegisterActivity.this, "Confirm", Toast.LENGTH_SHORT).show();
//                    apiService();

                    showLoading();

                    restmanager.register(userRegis.getText().toString(), passRegis.getText().toString(), gender, phone.getText().toString(), ephone.getText().toString(), userAdd, date, fname.getText().toString(), lname.getText().toString(), new Callback<ServiceModel>() {
                        @Override
                        public void onResponse(Call<ServiceModel> call, Response<ServiceModel> response) {

                            ServiceModel serviceModel = response.body();
                            if (response.isSuccessful()) {

//                                Toast.makeText(getApplicationContext(), response.body().toString(), Toast.LENGTH_SHORT).show();
                                if (response.body().status) {

                                        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                        //intent.putExtra("Service", new Gson().toJson(serviceModel));
                                        startActivity(intent);
                                        finish();
                                } else {

//                                    showMessage(String.valueOf(response.body().getMsg()));
                                    Toast.makeText(RegisterActivity.this, String.valueOf(response.body().getErrmsg()), Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                showMessage(String.valueOf(response.body().getErrmsg()));
//                                Toast.makeText(getApplicationContext(), response.message(), Toast.LENGTH_SHORT).show();
                            }
                            hideLoading();
                        }

                        @Override
                        public void onFailure(Call<ServiceModel> call, Throwable t) {

                            Log.d("มีข้อผิดพลาด", t.getMessage());
                            Toast.makeText(getApplication(), t.getMessage(), Toast.LENGTH_SHORT).show();
                            hideLoading();
                        }
                    });


                } else {
                    Toast.makeText(RegisterActivity.this, "รหัสผ่านและยืนยันรหัสผ่านไม่ตรงกัน", Toast.LENGTH_SHORT).show();
                }
            }
        });

        allready.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        ConnectivityManager cn = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo nf = cn.getActiveNetworkInfo();

        final Dialog dialog = new Dialog(RegisterActivity.this);
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
    }

    private void showLoading() {

        progressDialog = ProgressDialog.show(this, null, "กำลังเข้าสู่ระบบ...", true);

    }

    private void hideLoading() {
        if (progressDialog != null)
            progressDialog.dismiss();
    }


//    private void apiService() throws ParseException {


//        Service mManager = RestManager.getBuilder(this).create(Service.class);
//        Call<ServiceModel> call;

//        btnFemale,btnMale
//        if (btnMale.isChecked()) {
//            gender = "M";
//        } else {
//            gender = "F";
//        }
//
//        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy"); // Make sure user insert date into edittext in this format.
//
//        Date dateObject;
//
//        String dob_var = (dateView.getText().toString());
//        dateObject = formatter.parse(dob_var);
//        String date = new SimpleDateFormat("dd/MM/yyyy").format(dateObject);
//
//        String userAdd = "empty";

//ของ @Part
//        RequestBody UserID = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(SharedPrefs.getInstance(activity).getUserId()));
//        RequestBody UserRegis = RequestBody.create(MediaType.parse("text/plain"), userRegis.getText().toString());
//        RequestBody PassRegis = RequestBody.create(MediaType.parse("text/plain"), passRegis.getText().toString());
//        RequestBody Gender = RequestBody.create(MediaType.parse("text/plain"), gender);
//        RequestBody Phone = RequestBody.create(MediaType.parse("text/plain"), phone.getText().toString());
//        RequestBody Ephone = RequestBody.create(MediaType.parse("text/plain"), userAdd);
//        RequestBody DatePicker = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(date));
//        RequestBody Firstname = RequestBody.create(MediaType.parse("text/plain"), fname.getText().toString());
//        RequestBody Lastname = RequestBody.create(MediaType.parse("text/plain"), lname.getText().toString());

//        call = mManager.postServiceModel(UserRegis,PassRegis,Gender, Phone, Ephone,
//                DatePicker, Firstname, Lastname);

// @Field
//        call = mManager.postServiceModel(userRegis.getText().toString(), passRegis.getText().toString(), gender, phone.getText().toString(), ephone.getText().toString(),
//                userAdd, date, fname.getText().toString(), lname.getText().toString());

//        restmanager.register(userRegis.getText().toString(),passRegis.getText().toString(),gender,phone.getText().toString(),ephone.getText().toString(),userAdd,date,fname.getText().toString(),lname.getText().toString(),new Callback<ServiceModel>() {
//            @Override
//            public void onResponse(Call<ServiceModel> call, Response<ServiceModel> response) {

//                ServiceModel serviceModel = null;
//                if(response.isSuccessful()){
//                    Toast.makeText(getApplicationContext(), response.body().toString(), Toast.LENGTH_SHORT).show();
//                    if(response.body().status){
//                        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
//////                        intent.putExtra("Service", new Gson().toJson(serviceModel));
//                        startActivity(intent);
//                        finish();
//                    }
//                }else{
//
//                    Toast.makeText(getApplicationContext(), response.message(), Toast.LENGTH_SHORT).show();
//                }
//               progressdialog.dismiss();
//            }
//////////////////////////////////////////////////////////////////

//                if (response.body().isIsConnectDBSuccess() == true ) {
//                    if(response.body().isStatus() == true) {
//                        Toast.makeText(getApplication(), "สำเร็จ", Toast.LENGTH_SHORT).show();
//                        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
//                        intent.putExtra("Service", new Gson().toJson(serviceModel));
//                        startActivity(intent);
//                        finish();
//                    }
//                } else {
//                    Toast.makeText(getApplication(), "มีข้อผิดพลาด", Toast.LENGTH_SHORT).show();
//
//                }
//            }

//            @Override
//            public void onFailure(Call<ServiceModel> call, Throwable t) {
//
//                Log.d("มีข้อผิดพลาด", t.getMessage());
//                Toast.makeText(getApplication(), t.getMessage(), Toast.LENGTH_SHORT).show();
//
//            }
//        });

//    }

    public void setDate(View view) {
        showDialog(999);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(this, myDateListener, year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
            // TODO Auto-generated method stub
            // arg1 = year
            // arg2 = month
            // arg3 = day

            showDate(arg1, arg2 + 1, arg3);
        }
    };

    private void showDate(int year, int month, int day) {
        dateView.setText(new StringBuilder().append(day).append("/")
                .append(month).append("/").append(year));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    //to hide editText when click on the screen
    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

}
