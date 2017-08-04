package com.example.zain.doctorrecommendersystem;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.location.Location;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.akexorcist.googledirection.DirectionCallback;
import com.akexorcist.googledirection.GoogleDirection;
import com.akexorcist.googledirection.constant.Language;
import com.akexorcist.googledirection.constant.TransportMode;
import com.akexorcist.googledirection.model.Direction;
import com.akexorcist.googledirection.util.DirectionConverter;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;


     private String serverKey = "AIzaSyDHG92vdk3vx3Wm9BkFgnRJ8D1f8VXRxGU";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        SharedPreferences sharedPreferences = getSharedPreferences("Doctor_Location", Context.MODE_PRIVATE);
        final String doctorLatitudeString = sharedPreferences.getString("Doctor_Latitude", "");
        final String doctorLongitudeString = sharedPreferences.getString("Doctor_Longitude", "");
        final String currentLocationLatitideString = sharedPreferences.getString("Current_Location_Latitude", "");
        final String currentLocationLongitudeString = sharedPreferences.getString("Current_Location_Longitude", "");

        double doctorLatitude = Double.parseDouble(doctorLatitudeString);
        double doctorLongitude = Double.parseDouble(doctorLongitudeString);
        double currentLocationLatitide = Double.parseDouble(currentLocationLatitideString);
        double currentLoactionLongitude = Double.parseDouble(currentLocationLongitudeString);

        final LatLng doctorLocation = new LatLng(doctorLatitude, doctorLongitude);
        final LatLng currentLocation = new LatLng(currentLocationLatitide, currentLoactionLongitude);

        GoogleDirection.withServerKey(serverKey).from(currentLocation).to(doctorLocation).transportMode(TransportMode.DRIVING)
                .language(Language.ENGLISH)
                .execute(new DirectionCallback() {
                    @Override
                    public void onDirectionSuccess(Direction direction, String rawBody) {
                     //   Toast.makeText(MapsActivity.this, "Success with status : " + direction.getStatus(), Toast.LENGTH_LONG).show();
                        if (direction.isOK()) {

                            Toast.makeText(MapsActivity.this,"Loading Map...", Toast.LENGTH_SHORT).show();
                            mMap.addMarker(new MarkerOptions().position(doctorLocation).title("Doctor"));
                            mMap.addMarker(new MarkerOptions().position(currentLocation).title("Patient"));

                            ArrayList<LatLng> directionPositionList = direction.getRouteList().get(0).getLegList().get(0).getDirectionPoint();

                            mMap.addPolyline(DirectionConverter.createPolyline(MapsActivity.this, directionPositionList, 5, Color.RED));

                            CameraUpdate center =
                                    CameraUpdateFactory.newLatLng(new LatLng(31.4283,
                                            74.2678));
                            CameraUpdate zoom = CameraUpdateFactory.zoomTo(11);

                            mMap.moveCamera(center);
                            mMap.animateCamera(zoom);

                            double doctorLatitude = Double.parseDouble(doctorLatitudeString);
                            double doctorLongitude = Double.parseDouble(doctorLongitudeString);
                            double currentLocationLatitide = Double.parseDouble(currentLocationLatitideString);
                            double currentLoactionLongitude = Double.parseDouble(currentLocationLongitudeString);

                            float[] results = new float[1];
                            Location.distanceBetween(currentLocationLatitide, currentLoactionLongitude,
                                    doctorLatitude, doctorLongitude, results);

                            int km = (int) (results[0] / 1000);
                            Toast.makeText(MapsActivity.this, "You Distance from Doctor is : "+km+ "Km", Toast.LENGTH_LONG).show();


                        }
                    }

                    @Override
                    public void onDirectionFailure(Throwable t) {

                    }
                });
    }


}
