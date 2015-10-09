package com.kzooevefent.jersey;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
//Path: http://localhost/<appln-folder-name>/event
@Path("/eventprofile")
public class EventProfile {
    // HTTP Get Method
    @GET
    // Path: http://localhost/<appln-folder-name>/eventprofile/geteventprofile
    @Path("/geteventprofile")
    // Produces JSON as response
    @Produces(MediaType.APPLICATION_JSON) 
    // Query parameters are parameters: http://localhost/<appln-folder-name>/eventprofile/geteventprofile?event_id=0001
    public String getEventProfile(@QueryParam("event_id") int event_id){
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
 
    /**
     * Method to check whether the entered credential is valid
     * 
     * @param uname
     * @param pwd
     * @return
     */
    private Event checkEvent(int event_id){
        System.out.println("Inside checkEvent");
        Event event = new Event(0, "", 0, false);
        if(Utility.isValid(event_id)){
            try {
            	event = DBConnection.validateEvent(event_id);
                //System.out.println("Inside checkCredentials try "+result);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                //System.out.println("Inside checkCredentials catch");
            }
        }
        
        return event;
    }
 
}