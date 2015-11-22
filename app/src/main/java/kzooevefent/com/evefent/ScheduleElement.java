package kzooevefent.com.evefent;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by G on 10/21/15.
 */
public class ScheduleElement implements Parcelable{

        private int id;
        private String time;
        private String location;
        private String description;
        //private GPSCoords coords;


        public ScheduleElement(int id, String time, String location, String description)
        {
            setProfile(id, time, location, description);
        }


        protected void setProfile(int id, String time, String location, String description)
        {
            this.id = id;
            this.time = time;
            this.location = location;
            this.description = description;
            //this.coords = coords;
        }

        /**
         * @return the id
         */
        public int getId()
        {
            return id;
        }

        /**
         * @return the time
         */
        public String getTime()
        {
            return time;
        }

        /**
         * @return the name
         */
        public String getLocation()
        {
            return location;
        }

        /**
         * @return the description
         */
        public String getDescription()
        {
            return description;
        }

        /**
         * @return the coords
         */
        /*public GPSCoords getCoords()
        {
            return coords;
        }*/



        public String toString()
        {
            String s = "Event " + this.getId() + ": "
                    + "\tTime: " + this.time
                    + "\nLocation: " + this.location
                    + "\tDescription: " + this.description;
            /*if(coords != null)
            {
                s += "\t Coordinates: "
                        + coords.getDecimalDegrees()[0]
                        + ","
                        + coords.getDecimalDegrees()[1] ;
            }*/
            return s;
        }

        public int describeContents()
        {
            return 0;
        }

        public void writeToParcel(Parcel out, int flags)
        {
            //out.writeByte((byte) (coords != null ? 1 : 0));
            out.writeInt(id);
            out.writeString(time);
            out.writeString(location);
            out.writeString(description);
            /*if(coords !=null)
            {
                out.writeParcelable(coords, 0);
            }*/

        }

    public Parcelable.Creator<ScheduleElement> CREATOR
            = new Parcelable.Creator<ScheduleElement>()
    {
        public ScheduleElement createFromParcel(Parcel in)
        {
            if(in.readByte() == (byte) 1)
            {
                return new ScheduleElement(in.readInt(), in.readString(), in.readString(), in.readString()/*, in.<GPSCoords>readParcelable(GPSCoords.class.getClassLoader())*/);
            }
            else
            {
                return new ScheduleElement(in.readInt(), in.readString(),in.readString(), in.readString());
            }
        }

        public ScheduleElement[] newArray(int size)
        {
            return new ScheduleElement[size];
        }

    };


}
