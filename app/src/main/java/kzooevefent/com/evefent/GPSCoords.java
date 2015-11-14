package kzooevefent.com.evefent;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Jakob on 10/25/2015.
 */
public class GPSCoords implements Parcelable
{
    private int id;
    private String locationName;
    private double[] decimalDegrees;

    public GPSCoords(int id, String locationName, double[] decimalDegrees)
    {
        setParameters(id, locationName, decimalDegrees);

    }

    protected void setParameters(int id, String locationName, double[] decimalDegrees)
    {
        this.decimalDegrees = decimalDegrees;
        this.locationName = locationName;
        this.id = id;
    }

    /**
     * @return id
     */
    public int getId()
    {
        return id;
    }

    /**
     * @return locationName
     */
    public String getLocationName()
    {
        return locationName;
    }

    /**
     * @return decimalDegrees
     */
    public double[] getDecimalDegrees()
    {
        return decimalDegrees;
    }

    public String toString()
    {
        String s = "GPS location: "
                + "\nLocation: " + this.locationName
                + "\tID: " + this.id
                + "\t Coordinates: " + this.decimalDegrees [0] + "," + this.decimalDegrees[1];
        return s;
    }

    public int describeContents()
    {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags)
    {
        out.writeInt(id);
        out.writeString(locationName);
        out.writeDoubleArray(decimalDegrees);
    }

    public Parcelable.Creator<GPSCoords> CREATOR
            = new Parcelable.Creator<GPSCoords>()
    {
        public GPSCoords createFromParcel(Parcel in)
        {
            return new GPSCoords(in.readInt(), in.readString(), in.createDoubleArray());
        }

        public GPSCoords[] newArray(int size)
        {
            return new GPSCoords[size];
        }

    };
}
