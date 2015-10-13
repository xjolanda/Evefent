package kzooevefent.com.evefent;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class DatabaseService extends Service
{
    private final IBinder mBinder = new DatabaseBinder();

    boolean eventsInitialized = false;
    int eventsDBVersion;
    //ProgressDialog prgDialog;

    public class DatabaseBinder extends Binder
    {
        DatabaseService getService() {
            // Return this instance of LocalService so clients can call public methods
            return DatabaseService.this;
        }
    }

    public DatabaseService()
    {
        super();
        // Instantiate Progress Dialog object
        //prgDialog = new ProgressDialog(this);
        // Set Progress Dialog Text
        //prgDialog.setMessage("Refreshing...");
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Let it continue running until it is stopped.
        Toast.makeText(this, "Service Started", Toast.LENGTH_SHORT).show();
        return super.onStartCommand(intent,flags,startId);
    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, "Service Destroyed", Toast.LENGTH_SHORT).show();
        super.onDestroy();
    }

    public void updateEventProfile(final ArrayList<EventProfile> profiles)
    {
        if(!eventsInitialized)
        {
            queryEventProfile(profiles, new OnJSONResponseCallback()
            {
                @Override
                public void onJSONResponse(boolean success, Context context, JSONObject response) //TODO: Create JSON parsing utility
                {
                    int size = 0;
                    try
                    {
                        size = response.getInt("size");
                    }
                    catch (JSONException e)
                    {

                    }
                    for(int i = 0; i < size; i++)
                    {
                        int index;
                        try
                        {

                        }
                        catch(Exception e)
                        {

                        }
                    }
                }
            });
        }
        else if(queryDBVersion())
        {

        }
        else
        {

        }
    }

    public void updateEventProfile(final EventProfile profile)
    {
        queryEventProfile(profile.getId(), new DatabaseService.OnJSONResponseCallback()
        {
            @Override
            public void onJSONResponse(boolean success, Context context, JSONObject response)
            {
                try
                {
                    profile.setProfile(response.getInt(context.getString(R.string.EventProfile_ID)),
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
        if(validateEventID(event_id))
        {
            // Put Http parameter name with value of event_id
            params.put("event_id", String.valueOf(event_id));
            // Invoke RESTful Web Service with Http parameters

            invokeWebServices(params, getString(R.string.getEventUrl), callback);
        }
    }

    public void queryEventProfile(ArrayList<EventProfile> profiles, final OnJSONResponseCallback callback)
    {
            // Put Http parameter name with value of event_id
             RequestParams params = new RequestParams();
            // Invoke RESTful Web Service with Http parameters
            invokeWebServices(params, getString(R.string.getAllEventsUrl), callback);
    }

    public boolean queryDBVersion()
    {
        return true; //TODO: implement
    }

    private void invokeWebServices(RequestParams params, String HTTPSrequest, final OnJSONResponseCallback callback){
        // Show Progress Dialog
        //prgDialog.show();
        // Make RESTful webservice call using AsyncHttpClient object
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(HTTPSrequest, params ,new AsyncHttpResponseHandler() {
            // When the response returned by REST has Http response code '200'
            @Override
            public void onSuccess(String response) {
                // Hide Progress Dialog
                //prgDialog.hide();
                try {
                    // JSON Object
                    JSONObject obj = new JSONObject(response);
                    // When the JSON response has validation status set to true
                    if(obj.getBoolean("validated")){
                        Toast.makeText(getApplicationContext(), "Data Recieved", Toast.LENGTH_SHORT).show();
                        callback.onJSONResponse(true, getApplicationContext(), obj);
                    }
                    // Else display error message
                    else{
                        Toast.makeText(getApplicationContext(), obj.getString("error_msg"), Toast.LENGTH_SHORT).show();
                        //TODO: catch callback error
                    }
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), "Error Occured [Server's JSON response might be invalid]!", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();

                }
            }
            // When the response returned by REST has Http response code other than '200'
            @Override
            public void onFailure(int statusCode, Throwable error,
                                  String content) {
                // Hide Progress Dialog
                //prgDialog.hide();
                // When Http response code is '404'
                if(statusCode == 404){
                    Toast.makeText(getApplicationContext(), "Requested resource not found", Toast.LENGTH_SHORT).show();
                }
                // When Http response code is '500'
                else if(statusCode == 500){
                    Toast.makeText(getApplicationContext(), "Something went wrong at server end", Toast.LENGTH_SHORT).show();
                }
                // When Http response code other than 404, 500
                else{
                    Toast.makeText(getApplicationContext(), "Unexpected Error occcured! [Most common Error: Device might not be connected to Internet or remote server is not up and running]", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public interface OnJSONResponseCallback {
        public void onJSONResponse(boolean success, Context context, JSONObject response);
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
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            return false;
        }

    }
}
