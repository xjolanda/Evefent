package kzooevefent.com.evefent;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Author: Jakob Rodseth
 * Date: 10/13/2015
 *
 * TODO: Documentation
 */
public class EventProfile implements Parcelable
{
    private int id;
    private String name;
    private int attendeeListID;
    private boolean validated;

    public EventProfile(int id, String name, int attendeeListID, Boolean validated)
    {
        setProfile(id, name, attendeeListID, validated);
    }

    protected void setProfile(int id, String name, int attendeeListID, Boolean validated)
    {
        this.id = id;
        this.name = name;
        this.attendeeListID = attendeeListID;
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
     */
    public int getAttendeeListID()
    {
        return attendeeListID;
    }

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
                + "\tAttendee List ID: " + this.attendeeListID
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
        out.writeInt(attendeeListID);
        out.writeByte((byte) (validated ? 1 : 0));
    }

    public Parcelable.Creator<EventProfile> CREATOR
            = new Parcelable.Creator<EventProfile>()
    {
        public EventProfile createFromParcel(Parcel in)
        {
            return new EventProfile(in.readInt(), in.readString(), in.readInt(), (in.readByte() != 0));
        }

        public EventProfile[] newArray(int size)
        {
            return new EventProfile[size];
        }

    };

}
