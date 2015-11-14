package kzooevefent.com.evefent;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Jakob on 10/25/2015.
 */
public class ScheduleElement implements Parcelable
{
    private int id;
    private String location;
    private String description;
    private GPSCoords coords;

    public ScheduleElement(int id, String location, String description, GPSCoords coords)
    {
        setParameters(id, location, description, coords);

    }

    public ScheduleElement(int id, String location, String description)
    {
        setParameters(id, location, description);

    }

    protected void setParameters(int id, String location, String description, GPSCoords coords)
    {
        this.id = id;
        this.location = location;
        this.description = description;
        this.coords = coords;
    }

    protected void setParameters(int id, String location, String description)
    {
        this.id = id;
        this.location = location;
        this.description = description;
    }

    /**
     * @return id
     */
    public int getId()
    {
        return id;
    }

    /**
     * @return location
     */
    public String getLocation()
    {
        return location;
    }

    /**
     * @return description
     */
    public String getDescription()
    {
        return description;
    }

    /**
     * @return GPS coords
     */
    public GPSCoords getCoords ()
    {
        return coords;
    }

    public String toString()
    {
        String s = "Event " + this.getId() + ": "
                + "\nLocation: " + this.location
                + "\tDescription: " + this.description;
                if(coords != null)
                {
                   s += "\t Coordinates: "
                           + coords.getDecimalDegrees()[0]
                           + ","
                           + coords.getDecimalDegrees()[1] ;
                }

        return s;
    }

    public int describeContents()
    {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags)
    {
        out.writeByte((byte) (coords !=null ? 1 : 0));
        out.writeInt(id);
        out.writeString(location);
        out.writeString(description);
        if(coords !=null)
        {
            out.writeParcelable(coords, 0);
        }
    }

    public Parcelable.Creator<ScheduleElement> CREATOR
            = new Parcelable.Creator<ScheduleElement>()
    {
        public ScheduleElement createFromParcel(Parcel in)
        {
            if(in.readByte() == (byte) 1)
            {
                return new ScheduleElement(in.readInt(), in.readString(), in.readString(), in.<GPSCoords>readParcelable(GPSCoords.class.getClassLoader()));
            }
            else
            {
                return new ScheduleElement(in.readInt(), in.readString(), in.readString());
            }
        }

        public ScheduleElement[] newArray(int size)
        {
            return new ScheduleElement[size];
        }

    };
}
