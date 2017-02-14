package com.mimicki.afinal.fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.mimicki.afinal.R;
import com.mimicki.afinal.Retrofit.ServiceModelLogin;
import com.mimicki.afinal.TabActivity;

/**
 * Created by DELL on 7/4/2016.
 */
public class TwoFragment extends Fragment implements LocationListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    Marker marker;
    private GoogleMap mMap;
    private double lat, lng;
    GoogleApiClient googleApiClient;
    ToggleButton sw;
    LinearLayout scrollView2;
    TextView proName, editPhone, editEmPhone, editAddress, editSave;
    Button changeT;
    ImageView btnLocation;
    public static String LOGINMODEL = "loginModel";
    private ServiceModelLogin serviceModelLogin;


    //    TextView t1,t2,t3;
    public static TwoFragment newInstance(String gsonstring) {
        Bundle args = new Bundle();
        args.putString(LOGINMODEL, gsonstring);

        TwoFragment fragment = new TwoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public TwoFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();


        if (bundle != null) {
            String loginModel = bundle.getString(LOGINMODEL);
            if (loginModel != null) {
                Gson gson = new Gson();
//                serviceModelLogin = gson.fromJson(loginModel, ServiceModelLogin.class);

            }
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.patient_sf, container, false);

        setUpMapIfNeeded();
        GoogleApiClient();

        return rootView;
    }


    public void onViewCreated(final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        mUserInfo = mHelper.getUserInfo(id);
//
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            if (ContextCompat.checkSelfPermission(getActivity(),
//                    android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                requestPermissions(new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION}, MY_REQUEST_CODE);
//            return;
//            }
//        }

        scrollView2 = (LinearLayout) view.findViewById(R.id.scrollView2);
        proName = (TextView) view.findViewById(R.id.profileName);
        editPhone = (TextView) view.findViewById(R.id.editPhone);
        editEmPhone = (TextView) view.findViewById(R.id.editEmPhone);
        editAddress = (TextView) view.findViewById(R.id.editAddress);

        editSave = (TextView) view.findViewById(R.id.editSave);
        changeT = (Button) view.findViewById(R.id.changeType);
        sw = (ToggleButton) view.findViewById(R.id.switchEdit);
        btnLocation = (ImageView) view.findViewById(R.id.btnLocation);
//        final TextView t1 = (TextView) view.findViewById(R.id.textEditNo);
//        final TextView t2 = (TextView) view.findViewById(R.id.textEditNoEm);
//        final TextView t3 = (TextView) view.findViewById(R.id.textEditAd);

        editPhone.setEnabled(false);
        editEmPhone.setEnabled(false);
        editAddress.setEnabled(false);
        btnLocation.setEnabled(false);


        editSave.setVisibility(View.INVISIBLE);
//        editCancel.setVisibility(View.INVISIBLE);

        // getData ทำไมเป็น null ????  TT cry a lot ka
//        proName.setText(serviceModelLogin.getUserdata().getFirstnameEplsuser());
//        editPhone.setText(serviceModelLogin.getUserdata().getPhoneEplsuser());
//        editEmPhone.setText(serviceModelLogin.getUserdata().getEmerphoneEplsuser());
//        editAddress.setText(serviceModelLogin.getUserdata().getAddressEplsuser());


        init(serviceModelLogin);

    }

    private void init(final ServiceModelLogin serviceModelLogin) {
        scrollView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard(v);
            }
        });


        sw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean check = sw.isChecked();

                if (check) {
                    Toast.makeText(getActivity(), "เปิดการแก้ไข", Toast.LENGTH_SHORT).show();
                    editPhone.setEnabled(true);
                    editEmPhone.setEnabled(true);
                    editAddress.setEnabled(true);
                    btnLocation.setEnabled(true);

                    editPhone.setTextColor(Color.parseColor("#414141"));
                    editEmPhone.setTextColor(Color.parseColor("#414141"));
                    editAddress.setTextColor(Color.parseColor("#414141"));

                    Intent updateIntent = new Intent(getActivity(),
                            TabActivity.class);

                    startActivity(updateIntent);


                    mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {

                        @Override
                        public void onMapLongClick(LatLng arg0) {
                            if (marker != null) {
                                marker.remove();
                            }
                            marker = mMap.addMarker(new MarkerOptions()
                                    .position(
                                            new LatLng(arg0.latitude,
                                                    arg0.longitude)).title("บ้านของฉัน")
                                    .draggable(true).visible(true));
                            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(arg0.latitude, arg0.longitude), 19.0f));
                            mMap.getUiSettings().setZoomControlsEnabled(true);
                        }
                    });

                    editSave.setVisibility(View.VISIBLE);

                } else {
                    Toast.makeText(getActivity(), "ปิดการแก้ไข", Toast.LENGTH_SHORT).show();
                    editPhone.setEnabled(false);
                    editEmPhone.setEnabled(false);
                    editAddress.setEnabled(false);
                    btnLocation.setEnabled(false);

                    editPhone.setTextColor(Color.parseColor("#8a8a97"));
                    editEmPhone.setTextColor(Color.parseColor("#8a8a97"));
                    editAddress.setTextColor(Color.parseColor("#8a8a97"));

                    mMap.setOnMapLongClickListener(null);

                    // Cancel
//                    proName.setText(serviceModelLogin.getUserdata().getFirstnameEplsuser());
//                    editPhone.setText(serviceModelLogin.getUserdata().getPhoneEplsuser());
//                    editEmPhone.setText(serviceModelLogin.getUserdata().getEmerphoneEplsuser());
//                    editAddress.setText(serviceModelLogin.getUserdata().getAddressEplsuser());

                    editSave.setVisibility(View.INVISIBLE);
//                    editCancel.setVisibility(View.INVISIBLE);
                }
            }
        });

        editSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String sEditPhone = editPhone.getText().toString();
                String sEditEmPhone = editEmPhone.getText().toString();
                String sEditAddress = editAddress.getText().toString();

                if (sEditPhone.isEmpty() || sEditPhone.length() == 0 || sEditPhone.equals("") || sEditPhone == null) {
                    Toast.makeText(getActivity(), "กรุณาใส่เบอร์โทรศัพท์ของท่าน ", Toast.LENGTH_SHORT).show();
                } else if (sEditEmPhone.isEmpty() || sEditEmPhone.length() == 0 || sEditEmPhone.equals("") || sEditEmPhone == null) {
                    Toast.makeText(getActivity(), "กรุณาใส่เบอร์โทรศัพท์ฉุกเฉินของท่าน ", Toast.LENGTH_SHORT).show();
                } else if (sEditAddress.isEmpty() || sEditAddress.length() == 0 || sEditAddress.equals("") || sEditAddress == null) {
                    Toast.makeText(getActivity(), "กรุณาใส่ที่อยู่ของท่าน ", Toast.LENGTH_SHORT).show();
                } else {

                    Toast.makeText(getActivity(), "บันทึกและปิดการแก้ไข", Toast.LENGTH_SHORT).show();
                    sw.setChecked(false);
                    editSave.setVisibility(View.INVISIBLE);
                    editPhone.setEnabled(false);
                    editEmPhone.setEnabled(false);
                    editAddress.setEnabled(false);
                    btnLocation.setEnabled(false);

                    editPhone.setTextColor(Color.parseColor("#8a8a97"));
                    editEmPhone.setTextColor(Color.parseColor("#8a8a97"));
                    editAddress.setTextColor(Color.parseColor("#8a8a97"));


                    mMap.setOnMapLongClickListener(null);
                }

            }
        });


        changeT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mMap.getMapType() == GoogleMap.MAP_TYPE_NORMAL) {
                    mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                } else
                    mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            }
        });

        btnLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (marker != null) {
                    marker.remove();
                }
                marker = mMap.addMarker(new MarkerOptions()
                        .position(
                                new LatLng(lat, lng)).title("บ้านของฉัน")
                        .draggable(true).visible(true));
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lng), 19.0f));

            }

        });
    }


    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    public void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }


    private void setUpMapIfNeeded() {

        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map))
                    .getMap();


            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();


//                mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
//
//                    @Override
//                    public void onMapLongClick(LatLng arg0) {
//                        if (marker != null) {
//                            marker.remove();
//                        }
//                        marker = mMap.addMarker(new MarkerOptions()
//                                .position(
//                                        new LatLng(arg0.latitude,
//                                                arg0.longitude)).title("บ้านของฉัน")
//                                .draggable(true).visible(true));
//                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(arg0.latitude,arg0.longitude), 19.0f));
//                        mMap.getUiSettings().setZoomControlsEnabled(true);
//                    }
//                });

                mMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {

                    @Override
                    public void onMyLocationChange(Location arg0) {

                        lat = arg0.getLatitude();
                        lng = arg0.getLongitude();

                        final LatLng latLng = new LatLng(lat, lng);

                        Log.d(String.valueOf(lat), "latitude");
                        Log.d(String.valueOf(lng), "latitude");

                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(arg0.getLatitude(), arg0.getLongitude()), 19.0f));
//                        mMap.addMarker(new MarkerOptions().position(latLng).title("บ้านของฉัน"));
                        mMap.getUiSettings().setZoomControlsEnabled(true);


                    }
                });
            }


//            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
//                public boolean onMarkerClick(Marker arg0) {
//                    arg0.remove();
//                    //               Toast.makeText(getApplicationContext(), "Remove Marker" + String.valueOf(arg0.getId()), Toast.LENGTH_SHORT).show();
//                    //Toast.makeText(getContext(), "Remove Maker", Toast.LENGTH_SHORT);
//                    return true;
//                }
//            });

        }
    }

    private void setUpMap() {

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);

    }


    @Override
    public void onLocationChanged(Location location) {
    }

    public void GoogleApiClient() {

        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(getActivity())
                    .addApi(LocationServices.API)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this).build();
            googleApiClient.connect();

            LocationRequest locationRequest = LocationRequest.create();
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            locationRequest.setInterval(30 * 1000);
            locationRequest.setFastestInterval(5 * 1000);
            LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                    .addLocationRequest(locationRequest);

            //**************************
            builder.setAlwaysShow(true); //this is the key ingredient
            //**************************

            PendingResult<LocationSettingsResult> result =
                    LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());
            result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
                @Override
                public void onResult(LocationSettingsResult result) {
                    final Status status = result.getStatus();
                    final LocationSettingsStates state = result.getLocationSettingsStates();
                    switch (status.getStatusCode()) {
                        case LocationSettingsStatusCodes.SUCCESS:
                            // All location settings are satisfied. The client can initialize location
                            // requests here.
                            break;
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                            // Location settings are not satisfied. But could be fixed by showing the user
                            // a dialog.
                            try {
                                // Show the dialog by calling startResolutionForResult(),
                                // and check the result in onActivityResult().
                                status.startResolutionForResult(
                                        getActivity(), 1000);
                            } catch (IntentSender.SendIntentException e) {
                                // Ignore the error.
                            }
                            break;
                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                            // Location settings are not satisfied. However, we have no way to fix the
                            // settings so we won't show the dialog.
                            break;
                    }
                }
            });
        }
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
//        editCancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                proName.setText(serviceModelLogin.getData().getFirstname());
//                editPhone.setText(serviceModelLogin.getData().getPhone());
//                editEmPhone.setText(serviceModelLogin.getData().getEmerphone());
//                Toast.makeText(getActivity(), "Cancel", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        editSave.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                String sEditPhone = editPhone.getText().toString();
//                String sEditEmPhone = editEmPhone.getText().toString();
//                String sEditAddress = editAddress.getText().toString();
//
//                if (sEditPhone.isEmpty() || sEditPhone.length() == 0 || sEditPhone.equals("") || sEditPhone == null) {
//                    Toast.makeText(getActivity(), "Please insert your Phone no. ", Toast.LENGTH_SHORT).show();
//                } else if (sEditEmPhone.isEmpty() || sEditEmPhone.length() == 0 || sEditEmPhone.equals("") || sEditEmPhone == null) {
//                    Toast.makeText(getActivity(), "Please insert your Phone no. for Emergency case ", Toast.LENGTH_SHORT).show();
//                } else if (sEditAddress.isEmpty() || sEditAddress.length() == 0 || sEditAddress.equals("") || sEditAddress == null) {
//                    Toast.makeText(getActivity(), "Please insert your Address ", Toast.LENGTH_SHORT).show();
//                } else {
//
//                    Toast.makeText(getActivity(), "Save", Toast.LENGTH_SHORT).show();
//                }
//
//            }
//        });

//        btnLocation.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if (marker != null) {
//                    marker.remove();
//                }
//                marker = mMap.addMarker(new MarkerOptions()
//                        .position(
//                                new LatLng(lat,lng)).title("I'm Here")
//                        .draggable(true).visible(true));
//                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat,lng), 20.0f));
//
//            }
//
//        });
}


