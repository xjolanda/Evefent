package kzooevefent.com.evefent;


import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;



public class MapActivity extends FragmentActivity
        implements OnMapReadyCallback{
    LatLng eventLoc;
    String address;
    String eventName;
    Bundle info = getIntent().getExtras();
    LatLng KzooSW = new LatLng(42.2889, -85.6039);
    LatLng KzooNE = new LatLng(42.2917, -85.5961);
    //TODO: KEY NAMES



    LatLng kalamazoo = new LatLng(42.290, -85.6);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Initiates a google maps sync.
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map1);
        mapFragment.getMapAsync(this);
       //If the info bundle isn't null, all requisite variables are updated.
         if (info != null) {
          int[] locArray = info.getIntArray("TEST");
           eventLoc = new LatLng(locArray[0], locArray[1]);

           address = info.getString("desc");
           eventName = info.getString("name");
            }
     /*   eventLoc = new LatLng(42.295, -85.8);
        address = "idfk";
        eventName = "test"; */
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_map, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /*
    onMapReady: When the map is created, this method acts on it to produce the markers we want. It binds the viewport to kalamazoo
    college as closely as possible.
    @parameters:
       GoogleMap map - the map object itself
     */
    @Override
    public void onMapReady(GoogleMap map) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            map.setMyLocationEnabled(true);
        } else {
            // Show rationale and request permission.
        }
      //this code dynamically resizes the map to include kalamazoo college & the event
        LatLngBounds basicBound = new LatLngBounds(KzooSW, KzooNE);
        if (!basicBound.contains(eventLoc)) {
            LatLngBounds.Builder boundsBuild = new LatLngBounds.Builder();
            boundsBuild.include(KzooNE);
            boundsBuild.include(KzooSW);
            boundsBuild.include(eventLoc);
            //sets a marker at kalamazoo college and moves the camera there.
            LatLngBounds bounds = boundsBuild.build();

            map.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 0));
        }
        else {
            map.moveCamera(CameraUpdateFactory.newLatLngBounds(basicBound, 0));
        }
      Marker eventMark =  map.addMarker(new MarkerOptions()
              .position(eventLoc)
                .title(eventName)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET))
                .snippet(address));
        eventMark.showInfoWindow();

    }

}
