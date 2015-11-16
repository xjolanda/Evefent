package kzooevefent.com.evefent;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Emily on 11/12/2015.
 */
public class AttendeeList extends Activity {
     int ID;
    DatabaseServices dbService; //The database service instance the activity binds to.
    boolean dbBound = false; //Is the activity bound to the service
    EventProfile eve;

    /*private ServiceConnection dbConnection = new ServiceConnection()
    {

        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service)
        {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            DatabaseServices.DatabaseBinder binder = (DatabaseServices.DatabaseBinder) service;
            dbService = binder.getService(); //Store reference to the instance of the service that we bound to.
            dbBound = true;

            //eve=dbService.updateEventProfile(ID); //Sample data request. TODO: Replace with appropriate calls to info that you want
            Toast.makeText(getApplicationContext(), "Database Services Requested", Toast.LENGTH_SHORT).show(); //TODO:Debug
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0)
        {
            dbBound = false;
        }
    };*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendee_list);
        System.out.println("Created");

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
        //LocalBroadcastManager.getInstance(this).registerReceiver(DatabaseProfileEnumerationMessageReceiver,
          //      new IntentFilter(getApplicationContext().getResources().getString(R.string.EventProfilesEnumeratedMessage)));
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
        //Intent intent = new Intent(this, DatabaseServices.class);
        //bindService(intent, dbConnection, Context.BIND_AUTO_CREATE);
        BufferedReader buf;
        ArrayList<String> lines = new ArrayList<String>();
        lines.add("Tommy Pickles");
        lines.add("Dil Pickles");
        lines.add("Chuckie Finster");
        lines.add("Phil Deville");
        lines.add("Lil Deville");
        lines.add("Angelica Pickles");

        System.out.println("Starting");

        /*try
        {
            buf = new BufferedReader(new FileReader("assets\\AttendeeList.txt"));
            try
            {
                String temp="";
                temp=buf.readLine();
                while(temp!=null)
                {
                    lines.add(temp);
                    temp=buf.readLine();
                }
            }
            catch(IOException e)
            {
                System.out.println("IOException");
            }
        }
        catch(FileNotFoundException e)
        {
            System.out.println("File not found");
        }*/

        //EventProfile eve = updateEventProfile(ID);

        TableRow row;

        TableLayout table = (TableLayout) findViewById(R.id.list_table);

        for (int i = 0; i < lines.size(); i++) {

            LayoutInflater inflater = LayoutInflater.from(AttendeeList.this);
            row = (TableRow) inflater.inflate(R.layout.my_row, null);
            System.out.println("row: " + row);
            final String name = lines.get(i);

            setContentView(R.layout.my_row);
            TextView text = (TextView) findViewById(R.id.myText);
            System.out.println("text: " + text);
            text.setText(name);
            table.addView(row);
        }
    }
}
