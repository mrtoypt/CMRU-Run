package cmru.mrtoy.cmrurun;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private double centralLatADouble = 18.8067293, centralLngADouble = 99.0172408;
    private double userLatADouble, userLngADouble;
    private LocationManager locationManager;
    private Criteria criteria;
    private String userIDString, userNameString, goldString;
    private static final String urlEditLocation = "http://swiftcodingthai.com/cmru/edit_location_master.php";
    private boolean ststusABoolean = true;


    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_layout);

        // Setup
        userLatADouble = centralLatADouble;
        userLngADouble = centralLngADouble;
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_COARSE);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);

        //get value form Inten
        userIDString = getIntent().getStringExtra("userID");
        userNameString = getIntent().getStringExtra("Name");
        goldString = getIntent().getStringExtra("Gold");


        Log.d("30JunV1", "userID ==> " + userIDString);
        Log.d("30JunV1", "userName ==> " + userNameString);


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    } // end of main

    public void clickListFrand(View view) {
        startActivity(new Intent(MapsActivity.this, ShowFrandListView.class));
    }

    public void clickExit(View view) {
        finish();
    }

    private class SynLocation extends AsyncTask<Void, Void, String> {
        private static final String urlJSON = "http://swiftcodingthai.com/cmru/get_user_master.php";
        private MyData myData;


        @Override
        protected String doInBackground(Void... voids) {
            try {
                OkHttpClient okHttpClient = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                Request request = builder.url(urlJSON).build();
                Response response = okHttpClient.newCall(request).execute();
                return response.body().string();
            } catch (Exception e) {
                Log.d("30JunV2", "e doIt ==>" + e.toString());
                return null;
            }

        } // end of doInBackground

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("30JunV3", "jSON ==>" + s);

            myData = new MyData();
            int[] intIcon = myData.getAvataInts();

            try {
                JSONArray jsonArray = new JSONArray(s);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String strName = jsonObject.getString("Name");
                    int iconMarker = intIcon[Integer.parseInt(jsonObject.getString("Avata"))];
                    double douLat = Double.parseDouble(jsonObject.getString("Lat"));
                    double douLng = Double.parseDouble(jsonObject.getString("Lng"));
                    LatLng latLng = new LatLng(douLat, douLng);
                    mMap.addMarker(new MarkerOptions()
                            .position(latLng)
                            .icon(BitmapDescriptorFactory.fromResource(iconMarker))
                            .title(strName));


                }
            } catch (Exception e) {
                Log.d("30JunV4", "e onPOST ==>" + e.toString());
            }

        }
    } // end of SynLocation

    @Override
    protected void onStop() {
        super.onStop();
        locationManager.removeUpdates(locationListener);
    }


    @Override
    protected void onResume() {
        super.onResume();

        locationManager.removeUpdates(locationListener);
        Location networkLocation = myFindLocation(LocationManager.NETWORK_PROVIDER);
        if (networkLocation != null) {
            userLatADouble = networkLocation.getLatitude();
            userLngADouble = networkLocation.getLongitude();
        }// if

        Location gpsLocation = myFindLocation(LocationManager.GPS_PROVIDER);
        if (gpsLocation != null) {
            userLatADouble = gpsLocation.getLatitude();
            userLngADouble = gpsLocation.getLongitude();

        }//if


    }

    public Location myFindLocation(String strPrivider) {
        Location location = null;
        if (locationManager.isProviderEnabled(strPrivider)) {
            locationManager.requestLocationUpdates(strPrivider, 1000, 10, locationListener);// 1000 = 1 วินาที
            location = locationManager.getLastKnownLocation(strPrivider);

        } else {
            Log.d("29June", "Cannot Fine Location");
        }// end if

        return location;
    }


    public LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) { // ถ้าขยับ

            userLatADouble = location.getLatitude();
            userLngADouble = location.getLongitude();

        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) { // ต่อได้บ้างไม่ได้บ้าง

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    };//end of LocationListener


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //set position
        LatLng latLng = new LatLng(centralLatADouble, centralLngADouble);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
        creatStationMarker();
        myLoop();


        // Add a marker in Sydney and move the camera
        //  LatLng sydney = new LatLng(-34, 151);
        //  mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        //  mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }// end of onMapReady

    private void myLoop() {
        Log.d("29JuneV1", "userLat == " + userLatADouble);
        Log.d("29JuneV1", "userLng == " + userLngADouble);

        mMap.clear();
        creatStationMarker();
        editLocation();

        checkDistance();

        SynLocation synLocation = new SynLocation();
        synLocation.execute();

        if (ststusABoolean) {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    myLoop();
                }
            }, 3000);
        }
    }

    private void checkDistance() {

        // สูตรคำนวนระยะห่างของ GPS 2 ตัว = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        MyData myData = new MyData();
        double[] latStationDoubles = myData.getLatStationDoubles();
        double[] lngStationDoubles = myData.getLngStationDoubles();

        double douMyDistance = Math.sin(deg2rad(userLatADouble))
                * Math.sin(deg2rad(latStationDoubles[Integer.parseInt(goldString)]))
                + Math.cos(deg2rad(userLatADouble))
                * Math.cos(deg2rad(latStationDoubles[Integer.parseInt(goldString)]))
                * Math.cos(deg2rad((userLngADouble - lngStationDoubles[Integer.parseInt(goldString)])));
        douMyDistance = Math.acos(douMyDistance);
        douMyDistance = rad2deg(douMyDistance);
        douMyDistance = douMyDistance * 60 * 1.1515 * 1.609344;  // หน่วยเป็น Km
        douMyDistance = douMyDistance * 1000;// เปลี่ยนหน่วยเป็น เมตร

        Log.d("30JuneV4", "MyDistance ==> เทียบกับ ฐานที่  " + goldString + " มีค่าเท่ากับ " + douMyDistance);

        if (douMyDistance < 10) {
            confirmDialog();
        }

    }// end checkDistance

    private void confirmDialog() {
        ststusABoolean = false;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setCancelable(false);
        builder.setIcon(R.drawable.doremon48);
        builder.setTitle("คุณถึงด่านที่ " + Integer.toString(Integer.parseInt(goldString) + 1));
        builder.setMessage("คุณต้องทำคะแนน 3/5 ถึงจะผ่านไปได้");
        builder.setPositiveButton("เริ่มตอบคำถาม", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(MapsActivity.this, ExcerciseActivity.class);
                intent.putExtra("userID", userIDString);
                intent.putExtra("Gold", goldString);
                intent.putExtra("Avata", getIntent().getStringExtra("Avata"));
                startActivity(intent);
                finish();
            }
        });
        builder.show();
    } //end confirmDialog

    private double rad2deg(double douMyDistance) {
        double result = 0;
        result = douMyDistance * 180 / Math.PI;

        return result;
    }

    private double deg2rad(double userLatADouble) {

        double result = 0;
        result = userLatADouble * Math.PI / 180;
        return result;

    }// end deg2rad

    private void editLocation() {
        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody requestBody = new FormEncodingBuilder()
                .add("isAdd", "true")
                .add("id", userIDString)
                .add("Lat", Double.toString(userLatADouble))
                .add("Lng", Double.toString(userLngADouble))
                .build();

        Request.Builder builder = new Request.Builder();
        Request request = builder.url(urlEditLocation).post(requestBody).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {

            }
        });

    } // end Edit location

    private void creatStationMarker() {

        MyData myData = new MyData();
        double[] latDoubles = myData.getLatStationDoubles();
        double[] lngDoubles = myData.getLngStationDoubles();
        int[] iconInts = myData.getIconStationInts();


        for (int i = 0; i < latDoubles.length; i++) {
            LatLng latLng = new LatLng(latDoubles[i], lngDoubles[i]);
            mMap.addMarker(new MarkerOptions().position(latLng)
                    .icon(BitmapDescriptorFactory.fromResource(iconInts[i]))
                    .title("มุมที่ " + Integer.toString(i + 1)));
        }//for


    } // end creatStationMarker


} // main
