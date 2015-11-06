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
		try{
			if(checkEvent(event_id))
			{
				Event event = DBConnection.queryEvent(event_id);
				if(event.isValidated()){
					response = Utility.constructJSONForEvent(event);
				}else{
					response = Utility.constructJSONForEvent(event, "There was a problem finding that event!");
				}
			}
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
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

	public String addEvent(@QueryParam("event_name") String event_name)
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

	// HTTP Get Method
	@GET
	// Path: http://localhost/<appln-folder-name>/eventoperations/addevent
	@Path("/updateevent")
	// Produces JSON as response
	@Produces(MediaType.APPLICATION_JSON) 

	public String updateEvent(@QueryParam("event_id") int event_id,@QueryParam("event_name") String event_name)
	{
		System.out.println("Inside updateEvent");
		String response = "";
		try {
			if(checkEvent(event_id))
			{
			Event event = new Event(event_id, event_name, "true");
			DBConnection.updateEvent(event);
			response = Utility.constructJSONForEvent(event);
			//System.out.println("Inside checkCredentials try "+result);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//System.out.println("Inside checkCredentials catch");
			response = "Failure: " + e.getMessage();
		}  	

		return response;
	}
	
	// HTTP Get Method
		@GET
		// Path: http://localhost/<appln-folder-name>/eventoperations/addevent
		@Path("/removeevent")
		// Produces JSON as response
		@Produces(MediaType.APPLICATION_JSON) 

		public String removeEvent(@QueryParam("event_id") int event_id)
		{
			System.out.println("Inside removeEvent");
			String response = "";
			try {
				if(checkEvent(event_id))
				{
				String event = getEvent(event_id);
				if(DBConnection.removeEvent(event_id))
				{
				response = event;
				}
				else
				{
				throw new Exception();
				}
				//System.out.println("Inside checkCredentials try "+result);
				}
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
	private boolean checkEvent(int event_id){
		boolean validated = false;
		System.out.println("Inside checkEvent, looking for Event with id " + event_id);
		if(Utility.isValid(event_id)){
			try {
				Event event = DBConnection.queryEvent(event_id);
				if(event.isValidated())
				{
					validated = true;
				}
				//System.out.println("Inside checkCredentials try "+result);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				//System.out.println("Inside checkCredentials catch");
			}
		}
		return validated;
	}

}