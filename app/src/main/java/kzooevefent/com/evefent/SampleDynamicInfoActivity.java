package kzooevefent.com.evefent;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Map;

/*
 * Author: Jakob Rodseth
 * Date: 10/15/2015
 *
 * This is an example bound activity that requests an ArrayList of EventProfile objects
 * from DatabaseServices.
 *
 * Use this as a template to add database communication to your activity.
 * It may be confusing at first!
 * That's okay!
 *
 */


public class SampleDynamicInfoActivity extends FragmentActivity implements View.OnClickListener {
    ArrayList<EventProfile> currentProfiles = new ArrayList<EventProfile>();
    ArrayList<ScheduleElement> currentSchedule = new ArrayList<ScheduleElement>();

    DatabaseServices dbService; //The database service instance the activity binds to.
    boolean dbBound = false; //Is the activity bound to the service

    ScheduleElement e;
    TextView EventID;
    TextView EventTime;
    TextView EventLocation;
    TextView EventDescription;
    TextView Schedule;
    Button Refresh;
    EditText Event;

    Button Join;
    Button Attendee;
    Button Map;


    /* When the activity binds to a service, the ServiceConnection moderates the communication.
     *  This is where you should put all of your async data requests through DatabaseServices methods
     */
    private ServiceConnection dbConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            DatabaseServices.DatabaseBinder binder = (DatabaseServices.DatabaseBinder) service;
            dbService = binder.getService(); //Store reference to the instance of the service that we bound to.
            dbBound = true;

            //dbService.getAllEventProfiles(); //Sample data request. TODO: Replace with appropriate calls to info that you want
            int i;
            for (i = 0; i < 1; i++) {
                dbService.getSchedule(i);
            }
            Toast.makeText(getApplicationContext(), "Database Services Requested", Toast.LENGTH_SHORT).show(); //TODO:Debug
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            dbBound = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setUpFragment();
        //init();
        //Toast.makeText(this, "debuginit", Toast.LENGTH_SHORT).show();
        /*
        * The LocalBroadcastManager will register your activity as a listener for a specific message from DatabaseServices.
        *
        * //TODO: replace DatabaseProfileEnumerationMessageReceiver with your listener.
        * //TODO: replace R.string.EventProfilesEnumeratedMessage with the message you want.
        *
        * IMPORTANT: If you need to listen for several different types of updates, you will need to register multiple
        * different BroadcastReceivers with different IntentFilters.
        *
        * Example: I need to get both schedule information and GPS location information.
        * 1. Create two different BroadcastReceivers that will parse your request correctly.
        *       One will parse an array of scheduleInfo objects, called DatabaseScheduleUpdateMessageReceiver
        *       One will parse a GPSCoords object, called DatabaseGPSUpdateMessageReceiver
        *
        * 2. Register them in the manner below
        * LocalBroadcastManager.getInstance(this).registerReceiver(DatabaseScheduleUpdateMessageReceiver,
        *        new IntentFilter(getApplicationContext().getResources().getString(R.string.EventScheduleUpdateEnumeratedMessage)));
        *  LocalBroadcastManager.getInstance(this).registerReceiver(DatabaseGPSUpdateMessageReceiver,
        *        new IntentFilter(getApplicationContext().getResources().getString(R.string.EventGPSUpdateMessageEnumeratedMessage)));
        * */
        LocalBroadcastManager.getInstance(this).registerReceiver(DatabaseProfileEnumerationMessageReceiver,
                new IntentFilter(getApplicationContext().getResources().getString(R.string.EventArrayMessage)));
        LocalBroadcastManager.getInstance(this).registerReceiver(DatabaseScheduleElementMessageReceiver,
                new IntentFilter(getApplicationContext().getResources().getString(R.string.ScheduleElementMessage)));

    }

    void setUpFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        SlidingFragment fragment = new SlidingFragment();
        fragment1 f = new fragment1();
        transaction.replace(R.id.fragment1, f);
        transaction.commit();
    }


    public void onClick(View v) {
//        if (v == findViewById(R.id.Join)) {
//            Intent intent = new Intent(this, Map.class);
//            //intent.putExtra("student_Id", 0);
//            startActivity(intent);
        }

//    }

    public void init() {
        TableLayout ll = (TableLayout) findViewById(R.id.tableLayout1);
        //TableRow tbrow0 = new TableRow(this);

        TextView tv0 = new TextView(this);
        tv0.setText("Event Schedule");
        tv0.setTextColor(Color.BLUE);
        tv0.setTextSize(24);
        tv0.setGravity(Gravity.CENTER);
        //tbrow0.addView(tv0);

        ll.addView(tv0);




        //for (int i = 1; i < currentSchedule.size();i++)
        for (int i = 1; i < 33; i+=3) {

            TableRow row = new TableRow(this);
            TableRow row1 = new TableRow(this);
            TableRow row2 = new TableRow(this);

            EventID = new TextView(this);
            EventID.setText("  ID: " + (i/3+1));
            EventTime = new TextView(this);
            EventTime.setText("  Time: " + "11/11/15");
            EventLocation = new TextView(this);
            EventLocation.setText("  Loca: " + e.getLocation());
            EventDescription = new TextView(this);
            EventDescription.setText("  Desc: " + e.getDescription());

            Join = new Button(this);
            Join.setText("Join");
            Attendee = new Button(this);
            Attendee.setText("Attendees");
            Map = new Button(this);
            Map.setText("Map");

            row.addView(EventID);
            row1.addView(EventTime);
            row.addView(EventLocation);
            row1.addView(EventDescription);
            row2.addView(Join);
            row2.addView(Attendee);
            row2.addView(Map);


            if ((i/3) % 2 == 0) {
                EventID.setTextColor(Color.BLUE);
                EventTime.setTextColor(Color.BLUE);
                EventLocation.setTextColor(Color.BLUE);
                EventDescription.setTextColor(Color.BLUE);

            }



            ll.addView(row, i);
            ll.addView(row1, i+1);
            ll.addView(row2, i+2);

        }
    }

    public void searchEvents(View view)
    {
        String sEvent = Event.getText().toString();
        System.out.println(sEvent);

        /*EventProfile event = new EventProfile();
        try
        {
            dbService.updateEventProfile(Integer.parseInt(regEvent));
        }*/

        boolean found=false;
        for(int i=0; i<currentProfiles.size()&&!found; i++)
        {
            if(currentProfiles.get(i).getId()==Integer.parseInt(sEvent))
            {
                final Toast successToast = Toast.makeText(getApplicationContext(), "Call main event activity for event " + currentProfiles.get(i).getName(), Toast.LENGTH_LONG);
                successToast.show();
                found=true;
                /*Intent intent = new Intent(this, AttendeeListActivity);
                Bundle extras = intent.getExtras();
                extras.putInt("regCode", Integer.parseInt(regEvent));*/
                break;
            }
        }
        if(!found)
        {
            final Toast failToast = Toast.makeText(getApplicationContext(), "Did not find event with id " + sEvent, Toast.LENGTH_LONG);
            failToast.show();
        }

    }


    @Override
    protected void onStart() {
        super.onStart();
        /*
        * To trigger your requests from onServiceConnected, the activity needs to bind to the service.
        * It will make calls to Database methods and then unbind.
        * TODO: Include this code anytime you want to trigger onServiceConnected request code
        * */
        Intent intent = new Intent(this, DatabaseServices.class);
        bindService(intent, dbConnection, Context.BIND_AUTO_CREATE);
    }

    public void onRefreshEventsButtonPress() //Example situation in which you would bind to the service
    {
        //TODO: Include this code anytime you want to trigger onServiceConnected request code
        Intent intent = new Intent(this, DatabaseServices.class);
        bindService(intent, dbConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
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
    * Example BroadcastReceiver. You should make your own BroadcastReceiver to get info in a way that makes sense for your activity
    * */
    private BroadcastReceiver DatabaseProfileEnumerationMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(context, "Broadcast Received, Attempting Reassembly", Toast.LENGTH_SHORT).show();//TODO:Debug
            //TODO: Replace this line. Example: intent.getParcelable(context.getResources().getString(R.string.EventScheduleUpdateEnumeratedMessage));
            ArrayList<EventProfile> updatedProfiles = intent.getParcelableArrayListExtra(context.getResources().getString(R.string.EventArrayMessage));

            if (updatedProfiles == null) {
                Toast.makeText(context, "Reassembly Failure, Probable Extra Error", Toast.LENGTH_SHORT).show();//TODO:Debug
            } else {
                Toast.makeText(context, "Reassembly Success", Toast.LENGTH_SHORT).show();//TODO:Debug
            }

            currentProfiles = updatedProfiles;
            for (EventProfile e : currentProfiles) {
                Toast.makeText(context, "Event Created: " + e.toString(), Toast.LENGTH_SHORT).show();//TODO:Debug
            }

            //TODO: Make sure to update the display after info has been updated
            /*
            * Example call to imaginary method:
            * UpdateEventPictures(currentProfiles);
            * */
        }
    };

    private BroadcastReceiver DatabaseScheduleElementMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(context, "Broadcast Received, Attempting Reassembly", Toast.LENGTH_SHORT).show();//TODO:Debug
            //TODO: Replace this line. Example: intent.getParcelable(context.getResources().getString(R.string.EventScheduleUpdateEnumeratedMessage));
            ArrayList<ScheduleElement> elements = intent.getParcelableArrayListExtra(context.getResources().getString(R.string.ScheduleElementMessage));

            if (elements == null) {
                Toast.makeText(context, "Reassembly Failure, Probable Extra Error", Toast.LENGTH_SHORT).show();//TODO:Debug
            } else {
                Toast.makeText(context, "Reassembly Success", Toast.LENGTH_SHORT).show();//TODO:Debug
            }

            currentSchedule = elements;

            for (ScheduleElement e : currentSchedule) {
                Toast.makeText(context, "Schedule Element Created: " + e.toString(), Toast.LENGTH_SHORT).show();//TODO:Debug

            }

            //TODO: Make sure to update the display after info has been updated
            /*
            * Example call to imaginary method:
            * UpdateEventPictures(currentProfiles);
            * */
        }
    };

}