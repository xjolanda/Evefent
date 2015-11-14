package com.kzooevefent.jersey;

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
            obj.put("even_attendeesListID", event.getAttendeeListID());
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
            obj.put("event_attendeesListID", event.getAttendeeListID());
            obj.put("error_msg", err_msg);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
        }
        return obj.toString();
    }
}
 