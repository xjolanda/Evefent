package com.kzooevefent.jersey;

import java.util.ArrayList;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
 
public class Utility {
    /**
     * Null check Method
     * 
     * @param txt
     * @return
     */
    public static boolean isValid(String txt) {
        // System.out.println("Inside isNotNull");
        return txt != null && txt.trim().length() >= 0 ? true : false;
    }
    public static boolean isValid(int id) {
        // System.out.println("Inside isNotNull");
        return id > 0 ? true : false;
    }
 
    /**
     * Method to construct JSON for an event
     * 
     * @param event
     * @return
     */
    public static String constructJSONForEvent(Event event) {
        JSONObject obj = new JSONObject();
        try {
        	obj.put("event_id", event.getId());
            obj.put("event_name", event.getName());
            //obj.put("event_attendeesListID", event.getAttendeeListID());
            obj.put("validated", event.isValidated());
        } catch (JSONException e) {
            // TODO Auto-generated catch block
        }
        return obj.toString();
    }
 
    /**
     * Method to construct JSON for an event with Error Msg
     * 
     * @param event
     * @param err_msg
     * @return
     */
    public static String constructJSONForEvent(Event event, String err_msg) {
        JSONObject obj = new JSONObject();
        try {
        	obj.put("event_id", event.getId());
            obj.put("event_name", event.getName());
            //obj.put("event_attendeesListID", event.getAttendeeListID());
            obj.put("validated", event.isValidated());
            obj.put("error_msg", err_msg);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
        }
        return obj.toString();
    }
    
    public static String constructJSONForEventArray(ArrayList<Event> events) {
        JSONObject obj = new JSONObject();
        int i = 0;
        for(Event e: events)
        {
        	System.out.println(i);
        	try {
            	obj.put(""+i, constructJSONForEvent(e));
            } catch (JSONException e1) 
        	{
            	/*try {  
            		System.out.println("Enumeration Error");
            	obj.put("index", i);
            	obj.put("e", constructJSONForEvent(e, e1.getMessage()));
                // TODO Auto-generated catch block
            	}
            	catch(JSONException e2)
            	{
            		System.out.println("Enumeration Error");
            		//TODO:handle this
            	}*/
            }
        	i++;
        }
        try
        {
        obj.put("size", i);
        }
        catch(JSONException e)
        {
        	
        }
        return obj.toString();
    }
}
 