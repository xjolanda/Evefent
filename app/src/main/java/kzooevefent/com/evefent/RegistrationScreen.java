package kzooevefent.com.evefent;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;



public class RegistrationScreen extends Activity {
    EditText Reg;

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
            System.out.println("Do the thing");
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
        setContentView(R.layout.activity_registration_screen);
        Reg = (EditText) findViewById(R.id.Name);
        Reg.setBackgroundColor(Color.parseColor("#734C8F"));

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
                new IntentFilter(getApplicationContext().getResources().getString(R.string.EventProfilesEnumeratedMessage)));
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_registration_screen, menu);
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

    public void addName(View view)
    {
        EditText names;
        names = (EditText) findViewById(R.id.Name);
        String name = names.getText().toString();
        names.setBackgroundColor(Color.parseColor("#734C8F"));

        final Toast addNameToast = Toast.makeText(getApplicationContext(), "Add " + name + " to list", Toast.LENGTH_LONG);
        addNameToast.show();
    }

    public void searchEvents(View view)
    {
        String regEvent = Reg.getText().toString();
        System.out.println(regEvent);

        /*EventProfile event = new EventProfile();
        try
        {
            dbService.updateEventProfile(Integer.parseInt(regEvent));
        }*/

        boolean found=false;
        for(int i=0; i<currentProfiles.size()&&!found; i++)
        {
            if(currentProfiles.get(i).getId()==Integer.parseInt(regEvent))
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
            final Toast failToast = Toast.makeText(getApplicationContext(), "Did not find event with id " + regEvent, Toast.LENGTH_LONG);
            failToast.show();
        }

    }

    /*
   * Example BroadcastReceiver. You should make your own BroadcastReceiver to get info in a way that makes sense for your activity
   * */
    private BroadcastReceiver DatabaseProfileEnumerationMessageReceiver = new BroadcastReceiver()
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            Toast.makeText(context, "Broadcast Received, Attempting Reassembly", Toast.LENGTH_SHORT).show();//TODO:Debug
            //TODO: Replace this line. Example: intent.getParcelable(context.getResources().getString(R.string.EventScheduleUpdateEnumeratedMessage));
            ArrayList<EventProfile> updatedProfiles = intent.getParcelableArrayListExtra(context.getResources().getString(R.string.EventProfilesEnumeratedMessage));

            if (updatedProfiles == null)
            {
                Toast.makeText(context, "Reassembly Failure, Probable Extra Error", Toast.LENGTH_SHORT).show();//TODO:Debug
            } else
            {
                Toast.makeText(context, "Reassembly Success", Toast.LENGTH_SHORT).show();//TODO:Debug
            }

            currentProfiles = updatedProfiles;
            for (EventProfile e : currentProfiles)
            {
                Toast.makeText(context, "Event Created: " + e.toString(), Toast.LENGTH_SHORT).show();//TODO:Debug
            }

            //TODO: Make sure to update the display after info has been updated
            /*
            * Example call to imaginary method:
            * UpdateEventPictures(currentProfiles);
            * */
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
