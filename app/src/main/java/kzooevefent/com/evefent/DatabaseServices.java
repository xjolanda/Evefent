package kzooevefent.com.evefent;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;

/*
* Author: Jakob Rodseth
* Date: 10/15/2015
*
* TODO: Complete Documentation
* */

public class DatabaseServices extends Service
{
    private final IBinder mBinder = new DatabaseBinder();
    int eventsDBVersion;//TODO:Implement versioning

    public class DatabaseBinder extends Binder
    {
        DatabaseServices getService()
        {
            // Return this instance of LocalService so clients can call public methods
            return DatabaseServices.this;
        }
    }

    public DatabaseServices()
    {
        super();
    }

    @Override
    public IBinder onBind(Intent intent)
    {
        return mBinder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        // Let it continue running until it is stopped.
        Toast.makeText(this, "Service Started", Toast.LENGTH_SHORT).show(); //TODO:Debug
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy()
    {
        Toast.makeText(this, "Service Destroyed", Toast.LENGTH_SHORT).show(); //TODO:Debug
        super.onDestroy();
    }

    public void enumerateEventProfiles()
    {
        queryAllEventProfiles(new OnJSONResponseCallback()
        {
            @Override
            public void onJSONResponse(boolean success, Context context, JSONObject response)//TODO: Create JSON parsing utility, clean up code
            {
                ArrayList<EventProfile> profiles = new ArrayList<EventProfile>();
                try
                {
                    int size = response.getInt("size");
                    for (int i = 0; i < size; i++)//TODO:Create JSON assembly utility
                    {
                        JSONObject receivedEventProfile = new JSONObject(response.getString(String.valueOf(i)));

                        EventProfile newEventProfile = new EventProfile(receivedEventProfile.getInt(context.getString(R.string.EventProfile_ID)),
                                receivedEventProfile.getString(context.getString(R.string.EventProfile_Name)),
                                receivedEventProfile.getInt(context.getString(R.string.EventProfile_AttendeesListID)),
                                receivedEventProfile.getBoolean(context.getString(R.string.EventProfile_Validated)));
                        profiles.add(i, newEventProfile);

                    }
                    //Toast.makeText(context, "Events Enumerated", Toast.LENGTH_LONG).show();
                    sendUpdatedEvents(profiles);
                } catch (JSONException e)
                {
                    Toast.makeText(context, "Parsing Error, " + e.getMessage(), Toast.LENGTH_LONG).show();//TODO:Debug
                }
            }
        });

    }

    public void updateAllEventProfiles(final ArrayList<EventProfile> existingProfiles)
    {

        if (queryDBVersion()) //if DB has been updated, check for differences and update TODO: Update instead of overwrite
        {
            queryAllEventProfiles(new OnJSONResponseCallback()
            {
                @Override
                public void onJSONResponse(boolean success, Context context, JSONObject response)//TODO: Create JSON parsing utility, clean up code
                {
                    ArrayList<EventProfile> profiles = new ArrayList<EventProfile>();
                    try
                    {
                        int size = response.getInt("size");
                        for (int i = 0; i < size; i++)
                        {
                            JSONObject receivedEventProfile = new JSONObject(response.getString(String.valueOf(i)));

                            EventProfile newEventProfile = new EventProfile(receivedEventProfile.getInt(context.getString(R.string.EventProfile_ID)),
                                    receivedEventProfile.getString(context.getString(R.string.EventProfile_Name)),
                                    receivedEventProfile.getInt(context.getString(R.string.EventProfile_AttendeesListID)),
                                    receivedEventProfile.getBoolean(context.getString(R.string.EventProfile_Validated)));
                            profiles.add(i, newEventProfile);
                            Toast.makeText(context, "Event Created: " + profiles.get(i).toString(), Toast.LENGTH_SHORT).show();//TODO:Debug

                        }
                    } catch (JSONException e)
                    {
                        Toast.makeText(context, "Parsing Error, " + e.getMessage(), Toast.LENGTH_LONG).show();//TODO:Debug
                    }
                }
            });
        } else //Everything is fine, send up to date message
        {
            //TODO: implement
        }
    }

    public void updateEventProfile(int event_id)
    {
        queryEventProfile(event_id, new DatabaseServices.OnJSONResponseCallback()
        {
            @Override
            public void onJSONResponse(boolean success, Context context, JSONObject response)
            {
                try
                {
                    EventProfile event = new EventProfile(response.getInt(context.getString(R.string.EventProfile_ID)),
                            response.getString(context.getString(R.string.EventProfile_Name)),
                            response.getInt(context.getString(R.string.EventProfile_AttendeesListID)),
                            response.getBoolean(context.getString(R.string.EventProfile_Validated)));
                } catch (JSONException e)
                {
                    Toast.makeText(context, "ERROR", Toast.LENGTH_LONG).show();
                    //TODO: Catch format error
                }
            }
        });
    }

    public void queryEventProfile(int event_id, final OnJSONResponseCallback callback)
    {
        RequestParams params = new RequestParams();
        if (validateEventID(event_id))
        {
            // Put Http parameter name with value of event_id
            params.put("event_id", String.valueOf(event_id));
            // Invoke RESTful Web Service with Http parameters
            invokeWebServices(params, getString(R.string.getEventUrl), callback); //TODO: Don't hardcode calls
        }
    }

    public void queryAllEventProfiles(final OnJSONResponseCallback callback)
    {
        // Put Http parameter name with value of event_id
        RequestParams params = new RequestParams();
        // Invoke RESTful Web Service with Http parameters
        invokeWebServices(params, getString(R.string.getAllEventsUrl), callback);
    }


    private void sendUpdatedEvents(ArrayList<EventProfile> updateProfiles)
    {
        Intent intent = new Intent(getApplicationContext().getResources().getString(R.string.EventProfilesEnumeratedMessage));
        intent.putExtra(getApplicationContext().getResources().getString(R.string.EventProfilesEnumeratedMessage), updateProfiles);

        Toast.makeText(getApplicationContext(), "Broadcasting Profiles", Toast.LENGTH_SHORT).show();//TODO:Debug
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    private boolean validateEventID(int event_id)
    {
        try
        {
            if (event_id >= 0)
            {
                return true;
            } else
            {
                throw new Exception("event_id" + event_id + "is not a valid id.");
            }
        } catch (Exception e)
        {
            System.out.println(e.getMessage());
            return false;
        }

    }

    public boolean queryDBVersion()
    {
        return true; //TODO: implement
    }


    private void invokeWebServices(RequestParams params, String HTTPSrequest, final OnJSONResponseCallback callback)
    {
        // Show Progress Dialog
        //prgDialog.show();
        // Make RESTful webservice call using AsyncHttpClient object
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(HTTPSrequest, params, new AsyncHttpResponseHandler()
        {
            // When the response returned by REST has Http response code '200'
            @Override
            public void onSuccess(String response)
            {
                // Hide Progress Dialog
                //prgDialog.hide();
                try
                {
                    // JSON Object
                    JSONObject obj = new JSONObject(response);
                    // When the JSON response has validation status set to true
                    Toast.makeText(getApplicationContext(), "Data Received", Toast.LENGTH_SHORT).show();//TODO:Debug
                    callback.onJSONResponse(true, getApplicationContext(), obj);

                       /* Toast.makeText(getApplicationContext(), obj.getString("error_msg"), Toast.LENGTH_SHORT).show();
                        //TODO: catch callback error*/

                } catch (JSONException e)
                {
                    Toast.makeText(getApplicationContext(), "Error Occured [Server's JSON response might be invalid]!", Toast.LENGTH_SHORT).show();//TODO:Debug
                    e.printStackTrace();

                }
            }

            // When the response returned by REST has Http response code other than '200'
            @Override
            public void onFailure(int statusCode, Throwable error,
                                  String content)
            {
                // Hide Progress Dialog
                //prgDialog.hide();
                // When Http response code is '404'
                if (statusCode == 404)
                {
                    Toast.makeText(getApplicationContext(), "Requested resource not found", Toast.LENGTH_SHORT).show();//TODO:Debug
                }
                // When Http response code is '500'
                else if (statusCode == 500)
                {
                    Toast.makeText(getApplicationContext(), "Something went wrong at server end", Toast.LENGTH_SHORT).show();//TODO:Debug
                }
                // When Http response code other than 404, 500
                else
                {
                    Toast.makeText(getApplicationContext(), "Unexpected Error occurred! [Most common Error: Device might not be connected to Internet or remote server is not up and running]", Toast.LENGTH_LONG).show();//TODO:Debug
                }
            }
        });
    }

    public interface OnJSONResponseCallback
    {
        public void onJSONResponse(boolean success, Context context, JSONObject response);
    }

}
