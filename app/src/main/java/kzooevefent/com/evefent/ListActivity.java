package kzooevefent.com.evefent;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class ListActivity extends android.app.ListActivity {
    ArrayList<HashMap<String, String>> eventNames = new ArrayList<HashMap<String, String>>();

    ArrayList<EventProfile> currentProfiles = new ArrayList<EventProfile>();
    DatabaseServices dbService; //The database service instance the activity binds to.
    boolean dbBound = false; //Is the activity bound to the service

    private ServiceConnection dbConnection = new ServiceConnection()
    {

        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service)
        {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            DatabaseServices.DatabaseBinder binder = (DatabaseServices.DatabaseBinder) service;
            dbService = binder.getService(); //Store reference to the instance of the service that we bound to.
            dbBound = true;

            dbService.enumerateEventProfiles(); //Sample data request. TODO: Replace with appropriate calls to info that you want
            Toast.makeText(getApplicationContext(), "Database Services Requested", Toast.LENGTH_SHORT).show(); //TODO:Debug
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0)
        {
            dbBound = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);

        LocalBroadcastManager.getInstance(this).registerReceiver(DatabaseProfileEnumerationMessageReceiver,
                new IntentFilter(getApplicationContext().getResources().getString(R.string.EventProfilesEnumeratedMessage)));

        // Get listview
        ListView lv = getListView();
        //TextView eid = (TextView) findViewById(R.id.eid);
        //TextView eventName = (TextView) findViewById(R.id.EventName);

        //loops through eventProfiles retrieved from database

            // on seleting single product
            // launching Edit Product Screen
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener()
            {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id)
                {
                    // getting values from selected ListItem
                    String Eid = ((TextView) view.findViewById(R.id.eid)).getText().toString();
                    String eName = ((TextView) view.findViewById(R.id.EventName)).getText().toString();

                    System.out.println("Eid: " + Eid + " eName: " + eName);

                    // Starting new intent
                    Intent in = new Intent(getApplicationContext(),
                            MainActivity.class);
                    // sending pid to next activity
                    in.putExtra("Eid", Eid);
                    in.putExtra("eName", eName);

                    // starting new activity and expecting some response back
                    startActivity(in);
                }
            });

        }

        @Override
        protected void onStart()
        {
            super.onStart();
            /*
            * To trigger your requests from onServiceConnected, the activity needs to bind to the service.
            * It will make calls to Database methods and then unbind.
            * TODO: Include this code anytime you want to trigger onServiceConnected request code
            * */
            Intent intent = new Intent(this, DatabaseServices.class);
            bindService(intent, dbConnection, Context.BIND_AUTO_CREATE);
        }

        private BroadcastReceiver DatabaseProfileEnumerationMessageReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Toast.makeText(context, "Broadcast Received, Attempting Reassembly", Toast.LENGTH_SHORT).show();//TODO:Debug
                //TODO: Replace this line. Example: intent.getParcelable(context.getResources().getString(R.string.EventScheduleUpdateEnumeratedMessage));
                ArrayList<EventProfile> updatedProfiles = intent.getParcelableArrayListExtra(context.getResources().getString(R.string.EventProfilesEnumeratedMessage));

                if (updatedProfiles == null) {
                    Toast.makeText(context, "Reassembly Failure, Probable Extra Error", Toast.LENGTH_SHORT).show();//TODO:Debug
                } else {
                    Toast.makeText(context, "Reassembly Success", Toast.LENGTH_SHORT).show();//TODO:Debug
                }

                currentProfiles = updatedProfiles;
                /*for (EventProfile e : currentProfiles) {
                    Toast.makeText(context, "Event Created: " + e.toString(), Toast.LENGTH_SHORT).show();//TODO:Debug
                }*/

                // creating new HashMap

                for(int i=0; i<currentProfiles.size(); i++)
                {

                    HashMap<String, String> map = new HashMap<String, String>();

                    // adding each child node to HashMap key => value
                    map.put("eid", ""+currentProfiles.get(i).getId());
                    map.put("name", currentProfiles.get(i).getName());

                    // adding HashList to ArrayList
                    eventNames.add(map);
                }
                runOnUiThread(new Runnable()
                {
                    public void run()
                    {
                        ListAdapter adapter = new SimpleAdapter(
                                getApplicationContext(), eventNames,
                                R.layout.list_item, new String[]{"eid",
                                "name"},
                                new int[]{R.id.eid, R.id.EventName});
                        // updating listview
                        setListAdapter(adapter);
                    }
                });

                //TODO: Make sure to update the display after info has been updated
        /*
        * Example call to imaginary method:
        * UpdateEventPictures(currentProfiles);
        * */
            }
        };

    }
