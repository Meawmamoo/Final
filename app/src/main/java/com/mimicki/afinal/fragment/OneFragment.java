package com.mimicki.afinal.fragment;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mimicki.afinal.MainActivity;
import com.mimicki.afinal.MapActivity;
import com.mimicki.afinal.R;
import com.mimicki.afinal.Retrofit.RestManager;
import com.mimicki.afinal.Retrofit.ServiceModelCall;
import com.mimicki.afinal.utils.SaveSharedPreference;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by DELL on 7/4/2016.
 */
public class OneFragment extends Fragment {

    TextView amstatus, statussubject;
    ServiceModelCall serviceModelCall;
    private ProgressDialog progressDialog;
    private RestManager restmanager;

    public static OneFragment newInstance() {
        OneFragment fragment = new OneFragment();
        return fragment;
    }

    public OneFragment() {
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.patient_ff, container, false);
        return rootView;
    }

    public void onViewCreated(final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageView ffLogout = (ImageView) view.findViewById(R.id.ffLogout);
        final ImageView ffHelp = (ImageView) view.findViewById(R.id.ffHelp);
        amstatus = (TextView) view.findViewById(R.id.amStatus);
        statussubject = (TextView) view.findViewById(R.id.statusSubject);
        // ImageButton buttonCamera = (ImageButton) view.findViewById(R.id.buttonCamera);

        restmanager = new RestManager();

        ////รอเเซต รับค่าจาก webserver
        amstatus.setVisibility(View.INVISIBLE);
        statussubject.setVisibility(View.INVISIBLE);


        ffLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(getActivity());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.customdialog_logout);
                dialog.setCancelable(true);

                Button buttonYes = (Button) dialog.findViewById(R.id.buttonYes);
                buttonYes.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        Toast.makeText(getActivity(), "ออกจากระบบ", Toast.LENGTH_SHORT).show();
//                        SaveSharedPreference.clearUserName(getContext());
                        getActivity().finish();
                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        startActivity(intent);

                    }
                });

                Button buttonNo = (Button) dialog.findViewById(R.id.buttonNo);
                buttonNo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        dialog.cancel();
                    }
                });
                dialog.show();
            }
        });


        ffHelp.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {


                ///////รอรับค่าจาก model อีกที
                final String user = SaveSharedPreference.getUserName(getActivity());
                final String id = "";
                final String lat = "";
                final String lng = "";

//                final String sUserCall = user.getText().toString;

                final Dialog dialog = new Dialog(getActivity());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.customdialog_help);
                dialog.setCancelable(true);


                TextView buttonYesHelp = (TextView) dialog.findViewById(R.id.buttonYesHelp);
                buttonYesHelp.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {

                        Toast.makeText(getActivity(), "ขอความช่วยเหลือ", Toast.LENGTH_SHORT).show();

                        restmanager.call(user, id, lat, lng, new Callback<ServiceModelCall>() {
                            @Override
                            public void onResponse(Call<ServiceModelCall> call, Response<ServiceModelCall> response) {

                                serviceModelCall = response.body();
                                if (response.isSuccessful()) {
                                    showLoading();
//                                        Toast.makeText(MainActivity.this, response.body().toString(), Toast.LENGTH_SHORT).show();
                                    if (response.body().status) {
                                        showMessage("สำเร็จ");
                                        dialog.dismiss();
                                    } else {

                                        showMessage(String.valueOf(response.body().getErrmsg()));
                                    }
                                } else {
                                    Toast.makeText(getActivity(), response.message(), Toast.LENGTH_SHORT).show();
                                }
                                hideLoading();
                            }

                            @Override
                            public void onFailure(Call<ServiceModelCall> call, Throwable t) {
                                String message = t.getMessage();
                                Log.d("มีข้อผิดพลาด", message);
                                showMessage(message);
                                hideLoading();
                            }

                        });
                    }
                });

                TextView buttonNoHelp = (TextView) dialog.findViewById(R.id.buttonNoHelp);
                buttonNoHelp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getActivity(), "กรุณาเลือก", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(getActivity(), MapActivity.class);
                        startActivity(i);
                        dialog.dismiss();
                    }
                });
                dialog.show();


                return true;
            }
        });

//                    final Dialog dialog = new Dialog(getActivity());
//                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//                    dialog.setContentView(R.layout.customdialog_help);
//                    dialog.setCancelable(true);
//
//
//                    TextView buttonYesHelp = (TextView) dialog.findViewById(R.id.buttonYesHelp);
//                    buttonYesHelp.setOnClickListener(new View.OnClickListener() {
//                        public void onClick(View v) {
//                            // Toast.makeText(getActivity(), "Ask for Camera", Toast.LENGTH_SHORT).show();
//                            dialog.dismiss();
//
//                            final Dialog dialog2 = new Dialog(OneFragment.this.getActivity());
//                            dialog2.requestWindowFeature(Window.FEATURE_NO_TITLE);
//                            dialog2.setContentView(R.layout.customdialog_askcam);
//                            dialog2.setCancelable(true);
//
//                            dialog2.show();
//
//                            final TextView yescam = (TextView) dialog2.findViewById(R.id.buttonYesCam);
//
//                            yescam.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//
//                                    Toast.makeText(getActivity(), "Ask for Camera", Toast.LENGTH_SHORT).show();
//
////                                    if (CheckPermission.checkPermissionCamera(getActivity())) {
//                                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//
//                                        String timeStamp =
//                                                new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//                                        String imageFileName = "IMG_" + timeStamp + ".jpg";
//
//                                        File f = new File(Environment.getExternalStorageDirectory()
//                                                , "DCIM/Camera/" + imageFileName);
//                                        uri = Uri.fromFile(f);
//
//                                        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
//                                        startActivityForResult(Intent.createChooser(intent
//                                                , "Take a picture with"), REQUEST_CAMERA);
////                                    }
////
//
//                                    dialog2.dismiss();
//
//
//                                }
//                            });
//
//
//                            final TextView nocam = (TextView) dialog2.findViewById(R.id.buttonNoCam);
//                            nocam.setOnClickListener(new View.OnClickListener() {
//
//
//                                @Override
//                                public void onClick(View v) {
//
//                                    Toast.makeText(getActivity(), "Send Help", Toast.LENGTH_SHORT).show();
//                                    dialog2.dismiss();
//                                }
//
//
//                            });
//
//                        }
//                    });
//
//                    TextView buttonNoHelp = (TextView) dialog.findViewById(R.id.buttonNoHelp);
//                    buttonNoHelp.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            Toast.makeText(getActivity(), "Select your current location", Toast.LENGTH_SHORT).show();
//                            Intent i = new Intent(getActivity(), MapActivity.class);
//                            startActivity(i);
//                            dialog.dismiss();
//                        }
//                    });
//                    dialog.show();
//                }
//
//
//            }
//
//        });

        ConnectivityManager cn = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo nf = cn.getActiveNetworkInfo();

        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.customdialog_nonetwork);
        dialog.setCancelable(true);

        if (nf != null && nf.isConnected() == true) {


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
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
        Log.d("มีข้อผิดพลาด", message);
    }

    private void showLoading() {

        progressDialog = ProgressDialog.show(getActivity(), null, "กำลังเข้าสู่ระบบ...", true);

    }

    private void hideLoading() {
        if (progressDialog != null)
            progressDialog.dismiss();
    }

}


