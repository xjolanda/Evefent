package com.kzooevefent.jersey;

import java.util.ArrayList;

import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
//Path: http://localhost/<appln-folder-name>/event
@Path("/eventoperations")
public class EventOperations {
    // HTTP Get Method
    @GET
    // Path: http://localhost/<appln-folder-name>/eventoperations/getevent
    @Path("/getevent")
    // Produces JSON as response
    @Produces(MediaType.APPLICATION_JSON) 
    // Query parameters are parameters: http://localhost/<appln-folder-name>/eventoperations/getevent?event_id=1
    public String getEvent(@QueryParam("event_id") int event_id){
    	System.out.println("Inside getEventProfile");
        String response = "";
        Event event = checkEvent(event_id);
        if(event.isValidated()){
            response = Utility.constructJSONForEvent(event);
        }else{
            response = Utility.constructJSONForEvent(event, "There was a problem finding that event!");
        }
    return response;        
    }
    
 // HTTP Get Method
    @GET
    // Path: http://localhost/<appln-folder-name>/eventoperations/getallevents
    @Path("/getallevents")
    // Produces JSON as response
    @Produces(MediaType.APPLICATION_JSON) 
    
    public String getAllEvents()
    {
    	System.out.println("Inside getEvent");
    	String response = "";
    	ArrayList<Event> events = new ArrayList<Event>();
    	
    	try {
    		events = DBConnection.enumerateEvents();
            //System.out.println("Inside checkCredentials try "+result);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            //System.out.println("Inside checkCredentials catch");
        }
    	
    	response = Utility.constructJSONForEventArray(events);
    	
    	return response;
    }
    
    // HTTP Get Method
    @GET
    // Path: http://localhost/<appln-folder-name>/eventoperations/addevent
    @Path("/addevent")
    // Produces JSON as response
    @Produces(MediaType.APPLICATION_JSON) 
    
    public String addEventProfile(@QueryParam("event_name") String event_name)
    {
    	System.out.println("Inside addEvent");
    	String response = "";
    	try {
    		Event event = new Event(DBConnection.getMaxID()+1, event_name, "true");
    		DBConnection.insertEvent(event);
    		response = Utility.constructJSONForEvent(event);
            //System.out.println("Inside checkCredentials try "+result);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            //System.out.println("Inside checkCredentials catch");
        	response = "Failure: " + e.getMessage();
        }  	
    	
    	return response;
    }
 
    /**
     * Method to check whether the entered event exists and is valid
     * 
     * @param event_id
     * @return
     */
    private Event checkEvent(int event_id){
        System.out.println("Inside checkEvent, looking for Event with id " + event_id);
        Event event = new Event(0, "", "false");
        if(Utility.isValid(event_id)){
            try {
            	event = DBConnection.queryEvent(event_id);
                //System.out.println("Inside checkCredentials try "+result);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                //System.out.println("Inside checkCredentials catch");
            }
        }
        return event;
    }
 
}