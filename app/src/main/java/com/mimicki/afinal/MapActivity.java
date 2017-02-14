package com.mimicki.afinal;

import android.content.Context;
import android.content.IntentSender;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
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
import com.mimicki.afinal.utils.CheckPermission;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by DELL on 9/7/2016.
 */
public class MapActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    Marker marker;
    private GoogleMap mMap;
    private double lat, lng;
    GoogleApiClient googleApiClient;
//    ImageView fillPicCam;
//    Uri uri;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_map);

//        ImageButton buttonCamera = (ImageButton) findViewById(R.id.buttonCameraMap);
        TextView notsend = (TextView) findViewById(R.id.buttonNotSendMap);
        TextView send = (TextView) findViewById(R.id.buttonSendMap);
//        fillPicCam = (ImageView) findViewById(R.id.fillPicMap);
        ImageView btnLocationmap = (ImageView) findViewById(R.id.buttonLocationMap);
        Button btnTypemap = (Button) findViewById(R.id.changeTypeMap);

        setUpMapIfNeeded();
        GoogleApiClient();

        btnLocationmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (marker != null) {
                    marker.remove();
                }
                marker = mMap.addMarker(new MarkerOptions()
                        .position(
                                new LatLng(lat,lng)).title("บ้านของฉัน")
                        .draggable(true).visible(true));
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat,lng), 19.0f));

            }

        });

        btnTypemap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mMap.getMapType() == GoogleMap.MAP_TYPE_NORMAL) {
                    mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                } else
                    mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            }
        });

//        buttonCamera.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//
//                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//
//                String timeStamp =
//                        new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//                String imageFileName = "IMG_" + timeStamp + ".jpg";
//
//                File f = new File(Environment.getExternalStorageDirectory()
//                        , "DCIM/Camera/" + imageFileName);
//                uri = Uri.fromFile(f);
//
//                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
//                startActivityForResult(Intent.createChooser(intent
//                        , "Take a picture with"), REQUEST_CAMERA);
//
//            }
//        });

        notsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplication(), "send api", Toast.LENGTH_SHORT).show();
            }
        });

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
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapMap))
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
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(arg0.latitude,arg0.longitude), 19.0f));
                    mMap.getUiSettings().setZoomControlsEnabled(true);
                }
            });


        }
    }

    private void setUpMap() {
        if (CheckPermission.checkPermissionLocation(getApplication())) {
            mMap.setMyLocationEnabled(true);
        }

    }


    public void GoogleApiClient() {

        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(getApplication())
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
                                        MapActivity.this, 1000);
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

//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//
//        getApplicationContext().getContentResolver().notifyChange(uri, null);
//        ContentResolver cr = getApplicationContext().getContentResolver();
//        try {
//            Bitmap bitmap = MediaStore.Images.Media.getBitmap(cr, uri);
//            fillPicCam.setImageBitmap(bitmap);
//            Toast.makeText(getApplicationContext()
//                    , uri.getPath(), Toast.LENGTH_SHORT).show();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

}
