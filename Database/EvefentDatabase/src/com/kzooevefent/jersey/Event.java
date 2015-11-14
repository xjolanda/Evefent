package com.kzooevefent.jersey;

public class Event {
	
	private int id;
	private String name;
	private int attendeeListID;
	private boolean validated;
	
	public Event(int id, String name, int attendeeListID, boolean validated)
	{
		this.id = id;
		this.name = name;
		this.attendeeListID = attendeeListID;
		this.validated = validated;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the attendeeListID
	 */
	public int getAttendeeListID() {
		return attendeeListID;
	}
	
	/**
	 * @return the attendeeListID
	 */
	public boolean isValidated() {
		return validated;
	}
	
	

}
