package gkjcompany.cityproblems_v11;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.LinearLayout;


import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Map extends BaseActivity implements TextWatcher,
        CompoundButton.OnCheckedChangeListener {

    private SupportMapFragment mapFragment;
    LatLng latAndLong;
    ArrayList<LatLng> LatLongArray;
    ArrayList <String> descriptionArray;
    String description;
    double a, b;
    private FusedLocationProviderClient client;
    LocationManager locationManager ;
    boolean GpsStatus ;
    private static Context context;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        String user_email  = intent.getExtras().getString("user_email");//Εmail χρηστη για το ανεβασμα

        LatLongArray = new ArrayList<LatLng>();
        descriptionArray = new ArrayList <String>();
        context = Map.this;

        //mapFragment = (SupportMapFragment) getSupportFragmentManager()
        //.findFragmentById(R.id.map);
        //mapFragment.getMapAsync(this);



        if(GPSStatus() == false)
        {
            Intent intent1 = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent1);
        }
        //client = LocationServices.getFusedLocationProviderClient(this);
        requestPermissions(new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1);


    }

    public static Context getAppContext() {
        return Map.context;
    }

    public boolean GPSStatus(){
        locationManager = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) || locationManager.isProviderEnabled(LocationManager.PASSIVE_PROVIDER);
    }

    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    OkHttpClient client = new OkHttpClient();
                    String url = "https://icsdmobileprojectapi.000webhostapp.com/display.php?displayType=displayAll";

                    Request request = new Request.Builder()
                            .url(url)
                            .build();

                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            e.printStackTrace();
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            if (response.isSuccessful()) {

                                try {

                                    final String myResponseBody = response.body().string();
                                    JSONObject reader = new JSONObject(myResponseBody);

                                    final JSONObject event = reader.getJSONObject("event");

                                    for (int i = 0; i < event.length(); i++) {
                                        JSONObject thisevent = null;

                                        try {

                                            thisevent = event.getJSONObject(String.valueOf(i));
//                                                thisevent.getJSONObject("location").getDouble("latitude") ;
//                                                thisevent.getJSONObject("location").getDouble("longtitude");

                                            latAndLong = new LatLng(thisevent.getJSONObject("location").getDouble("longtitude"), thisevent.getJSONObject("location").getDouble("latitude"));
                                            description = thisevent.getString("description");
                                            LatLongArray.add(latAndLong);
                                            descriptionArray.add(description);


                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

                                    }

                                    Map.this.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            //TODO APO EDW
                                            //TODO APO EDW
                                            if (mapFragment == null) {

                                                mapFragment = SupportMapFragment.newInstance();
                                                mapFragment.getMapAsync(new OnMapReadyCallback() {

                                                    @SuppressLint("MissingPermission")
                                                    @Override
                                                    public void onMapReady(final GoogleMap googleMap) {
                                                        googleMap.setMyLocationEnabled(true);



                                                        for (int i = 0; i < LatLongArray.size(); i++) {
                                                            googleMap.addMarker(new MarkerOptions().position(LatLongArray.get(i)).title(descriptionArray.get(i)));

                                                        }

                                                        // ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                                                        // requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

                                                        new android.os.Handler().postDelayed(
                                                                new Runnable() {
                                                                    public void run() {
//                                                                        Criteria criteria = new Criteria();
//                                                                        LocationManager locationManager = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);
//                                                                        String provider = locationManager.getBestProvider(criteria, true);
                                                                        LocationManager locationManager = (LocationManager) Map.getAppContext().getSystemService(LOCATION_SERVICE);
                                                                        Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                                                                        //Location location = googleMap.getMyLocation();
                                                                        Log.e("lat", ""+ location + " ");
                                                                        if(location!=null)
                                                                        {
                                                                            double latitude = location.getLatitude();
                                                                            double longitude = location.getLongitude();
                                                                            LatLng  myPosition = new LatLng(latitude, longitude);
                                                                            Log.e("Giorgos",""+myPosition);
                                                                            googleMap.moveCamera(CameraUpdateFactory.newLatLng(myPosition));
                                                                            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(myPosition, 16));
                                                                        }

                                                                    }
                                                                },
                                                                0);

//
//                                                        Criteria criteria = new Criteria();
//                                                        LocationManager locationManager = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);
//                                                        String provider = locationManager.getBestProvider(criteria, true);
//                                                        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                                                            //    ActivityCompat#requestPermissions
//                                                            // here to request the missing permissions, and then overriding
//                                                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                                                            //                                          int[] grantResults)
//                                                            // to handle the case where the user grants the permission. See the documentation
//                                                            // for ActivityCompat#requestPermissions for more details.
//                                                            return;
//                                                        }

                                                    }
                                                });
                                            }
                                            // R.id.map is a FrameLayout, not a Fragment
                                            FragmentManager fm = getSupportFragmentManager();
                                            fm.beginTransaction().replace(R.id.map, mapFragment).commit();
                                            //TODO EDW
                                            //TODO EDW


                                        }
                                    });


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }


                            }
                        }


                    });


                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

    }

    @Override
    int getContentViewId() {
        return R.layout.activity_map;
    }

    @Override
    int getNavigationMenuItemId() {
        return R.id.map_Object;
    }

}