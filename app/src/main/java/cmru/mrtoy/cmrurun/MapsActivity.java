package cmru.mrtoy.cmrurun;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private double centralLatADouble = 18.8067293, centralLngADouble = 99.0172408;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_layout);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    } // end of main


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //set position
        LatLng latLng = new LatLng(centralLatADouble, centralLngADouble);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
        creatStationMarker();
        // Add a marker in Sydney and move the camera
        //  LatLng sydney = new LatLng(-34, 151);
        //  mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        //  mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }// end of onMapReady

    private void creatStationMarker() {

        MyData myData = new MyData();
        double[] latDoubles = myData.getLatStationDoubles();
        double[] lngDoubles = myData.getLngStationDoubles();
        int[] iconInts = myData.getIconStationInts();



        for (int i = 0; i < latDoubles.length; i++) {
            LatLng latLng = new LatLng(latDoubles[i], lngDoubles[i]);
            mMap.addMarker(new MarkerOptions().position(latLng)
                    .icon(BitmapDescriptorFactory.fromResource(iconInts[i]))
            .title("มุมที่ " + Integer.toString(i+1)));
        }//for



    } // end creatStationMarker


} // main
