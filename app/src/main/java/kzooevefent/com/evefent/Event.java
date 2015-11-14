package kzooevefent.com.evefent;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Author: Jakob Rodseth
 * Date: 10/13/2015
 * <p/>
 * TODO: Documentation
 */
public class Event implements Parcelable
{
    private int id;
    private String name;
    //private int attendeeListID;
    private boolean validated;

    public Event(int id, String name, Boolean validated)
    {
        setParameters(id, name, validated);
    }

    protected void setParameters(int id, String name, Boolean validated)
    {
        this.id = id;
        this.name = name;
        //this.attendeeListID = attendeeListID;
        this.validated = validated;
    }

    /**
     * @return the id
     */
    public int getId()
    {
        return id;
    }

    /**
     * @return the name
     */
    public String getName()
    {
        return name;
    }

    /**
     * @return the attendeeListID
    public int getAttendeeListID()
    {
        return attendeeListID;
    }
*/
    /**
     * @return the validated
     */
    public boolean isValidated()
    {
        return validated;
    }

    public String toString()
    {
        String s = "Event " + this.name
                + "\nID: " + this.id
                //+ "\tAttendee List ID: " + this.attendeeListID
                + "\t Validation: " + this.validated;
        return s;
    }

    public int describeContents()
    {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags)
    {
        out.writeInt(id);
        out.writeString(name);
       // out.writeInt(attendeeListID);
        out.writeByte((byte) (validated ? 1 : 0));
    }

    public Parcelable.Creator<Event> CREATOR
            = new Parcelable.Creator<Event>()
    {
        public Event createFromParcel(Parcel in)
        {
            return new Event(in.readInt(),
                    in.readString(),
                    //in.readInt(),
                    (in.readByte() != 0));
        }

        public Event[] newArray(int size)
        {
            return new Event[size];
        }

    };

}
