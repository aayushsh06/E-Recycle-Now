package app.ij.mlwithtensorflowlite;

import static androidx.constraintlayout.widget.ConstraintLayoutStates.TAG;

import android.Manifest;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
//import android.location.LocationListener;
import android.location.LocationManager;
//import android.location.LocationRequest;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.constraintlayout.helper.widget.MotionEffect;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    LocationRequest mLocationRequest;
    // private GoogleMap mMap;
    public String check ;
    public GoogleMap mMap;
    // public ActivityMapsBinding binding;


    LatLng Socrra = new LatLng(42.539480, -83.186010);
    LatLng eCycle = new LatLng(42.507370, -83.223980);

    LatLng Great_Lakes = new LatLng(42.6345177, -83.198590);

    LatLng BatteryGiant = new LatLng(42.682270, -83.150350);
    LatLng High_Tech = new LatLng(42.671860, -83.301420);

    LatLng G_T_Battrey = new LatLng(42.495180, -82.897160);


    public Marker markerSocrra;
    public Marker ecycle;
    public Marker markerGreat_Lakese;
    public Marker markerBatteryGiant;
    public Marker markerHigh_Tech;
    public Marker markerG_T_Battrey;
    Button homeButton;

    public ArrayList<LatLng> locationArrayList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        homeButton = findViewById(R.id.homeButton);

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(MapsActivity.this, MainActivity.class);
                i.putExtra("Text",check);
                //    Log.i(TAG, "mapsctivity location value1: " + mLastLocation.getLatitude());
                startActivity(i);

            }
        });





        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        }
        SupportMapFragment mapFragment = (SupportMapFragment)
                getSupportFragmentManager()
                        .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);
        Log.i(TAG, "classifyImageonactivityRead1: " + mapFragment);
    }

    /**
     * Manipulates the map once available.

     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setZoomGesturesEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);
        //Initialize Google Play Services
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
                mMap.setMyLocationEnabled(true);
            }
        } else {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }
    }
    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }
    @Override
    public void onConnected(Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,
                    mLocationRequest, this);
        }
    }
    @Override
    public void onConnectionSuspended(int i) {
    }
    @SuppressLint("SuspiciousIndentation")
    @Override
    public void onLocationChanged(Location location) {
        Intent i = getIntent();

        mLastLocation = location;

        Log.i(TAG, "mapsctivity location value2: " + location.getLatitude());
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }
//Showing Current Location Marker on Map
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());






        locationArrayList = new ArrayList<>();
        locationArrayList.add(latLng);
        locationArrayList.add(eCycle);
        locationArrayList.add(Socrra);
        locationArrayList.add(High_Tech);
        locationArrayList.add(Great_Lakes);
        locationArrayList.add(G_T_Battrey);
        locationArrayList.add(BatteryGiant);





        MarkerOptions markerOptions = new MarkerOptions();
        //  markerOptions.position(latLng);




        LocationManager locationManager = (LocationManager)
                getSystemService(Context.LOCATION_SERVICE);
        String provider = locationManager.getBestProvider(new Criteria(), true);
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Location locations = locationManager.getLastKnownLocation(provider);
        List<String> providerList = locationManager.getAllProviders();
        if (null != locations && null != providerList && providerList.size() > 0) {
            double longitude = locations.getLongitude();
            double latitude = locations.getLatitude();
            Geocoder geocoder = new Geocoder(getApplicationContext(),
                    Locale.getDefault());
            try {
                List<Address> listAddresses = geocoder.getFromLocation(latitude,
                        longitude, 1);
                if (null != listAddresses && listAddresses.size() > 0) {
                    String state = listAddresses.get(0).getAdminArea();
                    String country = listAddresses.get(0).getCountryName();
                    String subLocality = listAddresses.get(0).getSubLocality();
                    markerOptions.title("" + latLng + "," + subLocality + "," + state
                            + "," + country);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Bundle extras = getIntent().getExtras();
        // String ITEM = null;
        if (extras != null) {
          check = extras.getString("ITem");
            Log.i(MotionEffect.TAG, "check VALUE: " + check);
            //The key argument here must match that used in the other activity
        }


        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));

        // for (int i = 0; i < locationArrayList.size(); i++) {
        Log.i(TAG, "check VALUE1: " + check);

        if ( check.equals("Phone")  ) {

            // below line is use to add marker to each location of our array list.
            mMap.addMarker(new MarkerOptions().position(locationArrayList.get(0)).title("Current Location"));
            mMap.addMarker(new MarkerOptions().position(locationArrayList.get(1)).title("eCycle Opportunities").snippet("\"https://info@ecycleopps.org\""));
            mMap.addMarker(new MarkerOptions().position(locationArrayList.get(2)).title("Socrra").snippet("\"https://www.socrra.org\""));
            mMap.addMarker(new MarkerOptions().position(locationArrayList.get(3)).title("High tech recycling LLC ").snippet("\"https://www.socrra.org\""));
            mMap.addMarker(new MarkerOptions().position(locationArrayList.get(4)).title("Great Lakes Recycling ").snippet("\"https://www.glrescrap.com\""));
            // below lin is use to zoom our camera on map.
            /*mMap.animateCamera(CameraUpdateFactory.zoomTo(18.0f));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(locationArrayList.get(0)));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(locationArrayList.get(1)));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(locationArrayList.get(2)));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(locationArrayList.get(3)));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(locationArrayList.get(4)));*/
            float zoomLevel = 10.0f; //This goes up to 21
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoomLevel));


        }
        if (check.equals("Battery") ) {

            mMap.addMarker(new MarkerOptions().position(locationArrayList.get(0)).title("Current Location"));
            mMap.addMarker(new MarkerOptions().position(locationArrayList.get(1)).title("eCycle Opportunities").snippet("\"https://info@ecycleopps.org\""));
            mMap.addMarker(new MarkerOptions().position(locationArrayList.get(2)).title("Socrra ").snippet("\"https://www.socrra.org\""));
            mMap.addMarker(new MarkerOptions().position(locationArrayList.get(3)).title("High tech recycling LLC ").snippet("\"https://www.hightechrecyclingmi.com\""));
            mMap.addMarker(new MarkerOptions().position(locationArrayList.get(4)).title("Great Lakes Recycling ").snippet("\"https://www.glrescrap.com\""));
            mMap.addMarker(new MarkerOptions().position(locationArrayList.get(5)).title("Great Lakes Battery ").snippet("\"https://greatlakesbattery.com\""));
            mMap.addMarker(new MarkerOptions().position(locationArrayList.get(6)).title("Battery Giant ").snippet("\"https://batterygiantrochesterhills.com\""));
           /* mMap.animateCamera(CameraUpdateFactory.zoomTo(18.0f));

            // below line is use to move our camera to the specific location.
            mMap.moveCamera(CameraUpdateFactory.newLatLng(locationArrayList.get(0)));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(locationArrayList.get(1)));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(locationArrayList.get(1)));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(locationArrayList.get(2)));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(locationArrayList.get(3)));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(locationArrayList.get(4)));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(locationArrayList.get(5)));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(locationArrayList.get(6)));*/
            float zoomLevel = 10.0f; //This goes up to 21
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoomLevel));

        }
        if ( check.equals("Mouse")  ) {

            // below line is use to add marker to each location of our array list.
            mMap.addMarker(new MarkerOptions().position(locationArrayList.get(0)).title("Current Location"));
            mMap.addMarker(new MarkerOptions().position(locationArrayList.get(1)).title("eCycle Opportunities ").snippet("\"https://info@ecycleopps.org\""));
            mMap.addMarker(new MarkerOptions().position(locationArrayList.get(2)).title("Socrra ").snippet("\"https://www.socrra.org\""));
            mMap.addMarker(new MarkerOptions().position(locationArrayList.get(3)).title("High tech recycling LLC ").snippet("\"https://www.hightechrecyclingmi.com\""));
            mMap.addMarker(new MarkerOptions().position(locationArrayList.get(4)).title("Great Lakes Recycling ").snippet("\"https://www.glrescrap.com\""));
            /*mMap.animateCamera(CameraUpdateFactory.zoomTo(18.0f));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(locationArrayList.get(0)));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(locationArrayList.get(1)));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(locationArrayList.get(2)));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(locationArrayList.get(3)));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(locationArrayList.get(4)));*/
            float zoomLevel = 10.0f; //This goes up to 21
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoomLevel));


        }
        if ( check.equals("Cable")  ) {

            // below line is use to add marker to each location of our array list.
            mMap.addMarker(new MarkerOptions().position(locationArrayList.get(0)).title("Current Location"));
            mMap.addMarker(new MarkerOptions().position(locationArrayList.get(1)).title("eCycle Opportunities ").snippet("\"https://info@ecycleopps.org\""));
            mMap.addMarker(new MarkerOptions().position(locationArrayList.get(2)).title("Socrra ").snippet("\"https://www.socrra.org\""));
            mMap.addMarker(new MarkerOptions().position(locationArrayList.get(3)).title("High tech recycling LLC ").snippet("\"https://www.hightechrecyclingmi.com\""));
            mMap.addMarker(new MarkerOptions().position(locationArrayList.get(4)).title("Great Lakes Recycling ").snippet("\"https://www.glrescrap.com\""));
            // below lin is use to zoom our camera on map.
            /*mMap.animateCamera(CameraUpdateFactory.zoomTo(18.0f));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(locationArrayList.get(0)));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(locationArrayList.get(1)));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(locationArrayList.get(2)));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(locationArrayList.get(3)));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(locationArrayList.get(4)));*/
            float zoomLevel = 10.0f; //This goes up to 21
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoomLevel));


        }

        // below lin is use to zoom our camera on map.


        //    }

        Log.i(TAG, "mapsctivity location value3: " + mMap.toString());
        //   mCurrLocationMarker = mMap.addMarker(markerOptions);
        //    mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        //   mMap.animateCamera(CameraUpdateFactory.zoomTo(11));
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient,
                    this);
        }
    }
    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
    }
    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {
                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                    }
                } else {
                    Toast.makeText(this, "permission denied",
                            Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }

}