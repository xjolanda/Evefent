package kzooevefent.com.evefent;


import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;



public class MapActivity extends FragmentActivity
        implements OnMapReadyCallback{
    LatLng eventLoc;
    String desc;
    String eventName;
    Bundle info = getIntent().getExtras();

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
        int[] locArray = info.getIntArray("TEST");
       // eventLoc = new LatLng(locArray[0], locArray[1]);
        eventLoc = new LatLng(42.295, -85.8);
        desc = info.getString("desc");
        eventName = info.getString("name");

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
    @Override
    public void onMapReady(GoogleMap map) {
      LatLngBounds.Builder  boundsBuild = new LatLngBounds.Builder();
        boundsBuild.include(eventLoc);
        boundsBuild.include(kalamazoo);
        //sets a marker at kalamazoo college and moves the camera there.
        LatLngBounds bounds = boundsBuild.build();
        map.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 0));
        map.addMarker(new MarkerOptions()
                .position(eventLoc)
                .title(eventName));
        map.addMarker(new MarkerOptions()
                 .position(kalamazoo));
        CameraUpdateFactory.newLatLng(eventLoc);
    }

}
