package com.mimicki.afinal.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
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
import com.mimicki.afinal.DriverActivity;
import com.mimicki.afinal.R;
import com.mimicki.afinal.Retrofit.ServiceModelLogin;
import com.mimicki.afinal.utils.CheckPermission;
import com.mimicki.afinal.utils.SaveSharedPreference;

/**
 * Created by DELL on 7/5/2016.
 */

public class DriverFragment extends Fragment implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    Marker marker;
    private GoogleMap mMap;
    private double lat, lng;
    GoogleApiClient googleApiClient;
    public static String LOGINMODELDRIVER = "loginModeldriver";
    private ServiceModelLogin serviceModelLoginDriver;

    ImageView fillPic;
    Uri uri;
    private LinearLayout scrollView3;
    private ImageView driverLogout;
    private Button changeTypeD;

    public static DriverFragment newInstance(String gsonstring) {
        Bundle args = new Bundle();
        args.putString(LOGINMODELDRIVER, gsonstring);

        DriverFragment fragment = new DriverFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public DriverFragment() {
    }

//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        View rootView = inflater.inflate(R.layout.driver_ff, container, false);
//        setUpMapIfNeeded();
//        GoogleApiClient();
//        return rootView;
//    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            String loginModelDriver = bundle.getString(LOGINMODELDRIVER);
            if (loginModelDriver != null) {
                Gson gson = new Gson();
//                serviceModelLogin = gson.fromJson(String.valueOf(loginModel), ServiceModelLogin.class);

            }
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.driver_ff, container, false);

        setUpMapIfNeeded();
        GoogleApiClient();

        return rootView;
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        scrollView3 = (LinearLayout) view.findViewById(R.id.scrollView3);
        driverLogout = (ImageView) view.findViewById(R.id.driverLogout);
        changeTypeD = (Button) view.findViewById(R.id.changeTypeDriver);
//        fillPic = (ImageView) view.findViewById(R.id.fillPic);

        init(serviceModelLoginDriver);

    }

    private void init(final ServiceModelLogin serviceModelLoginDriver) {

        scrollView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard(v);
            }
        });

        driverLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(getActivity());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.customdialog_logout);
                dialog.setCancelable(true);

                TextView buttonYes = (TextView) dialog.findViewById(R.id.buttonYes);
                buttonYes.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        Toast.makeText(getActivity(), "ออกจากระบบ", Toast.LENGTH_SHORT).show();
                        SaveSharedPreference.clearUserNameDriver(getContext());
                        getActivity().finish();
                        Intent intentDriver = new Intent(getActivity(), DriverActivity.class);
                        startActivity(intentDriver);
                    }
                });

                TextView buttonNo = (TextView) dialog.findViewById(R.id.buttonNo);
                buttonNo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });

                dialog.show();
            }
        });

        changeTypeD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mMap.getMapType() == GoogleMap.MAP_TYPE_NORMAL) {
                    mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                } else
                    mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
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
            mMap = ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapFragment))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();

            }

            mMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {

                @Override
                public void onMyLocationChange(Location arg0) {

                    lat = arg0.getLatitude();
                    lng = arg0.getLongitude();

                    final LatLng latLng = new LatLng(lat, lng);

                    Log.d(String.valueOf(lat), "latitude");
                    Log.d(String.valueOf(lng), "latitude");

                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(arg0.getLatitude(), arg0.getLongitude()), 19.0f));

                    mMap.getUiSettings().setZoomControlsEnabled(true);


                }
            });


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


        }
    }

    private void setUpMap() {
        if (CheckPermission.checkPermissionLocation(getActivity())) {
            mMap.setMyLocationEnabled(true);
        }

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
}

//if (CheckPermission.checkPermissionCamera(getActivity())) {
//        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//
//        String timeStamp =
//        new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//        String imageFileName = "IMG_" + timeStamp + ".jpg";
//
//        File f = new File(Environment.getExternalStorageDirectory()
//        , "DCIM/Camera/" + imageFileName);
//        uri = Uri.fromFile(f);
//
//        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
//        startActivityForResult(Intent.createChooser(intent
//        , "Take a picture with"), REQUEST_CAMERA);
////                                    }

//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
////                Bitmap photo = (Bitmap) data.getExtras().get("data");
////                fillPic.setImageBitmap(photo);
//        fillPic.setVisibility(View.VISIBLE);
//
//
//
//        getActivity().getContentResolver().notifyChange(uri, null);
//        ContentResolver cr = getActivity().getContentResolver();
//        try {
//            Bitmap bitmap = MediaStore.Images.Media.getBitmap(cr, uri);
//            fillPic.setImageBitmap(bitmap);
//            Toast.makeText(getActivity()
//                    , uri.getPath(), Toast.LENGTH_SHORT).show();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }