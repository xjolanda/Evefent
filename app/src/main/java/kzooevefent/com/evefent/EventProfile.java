package kzooevefent.com.evefent;

import android.content.Context;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Jakob on 10/13/2015.
 */
public class EventProfile
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
         * @return the validated
         */
        public boolean isValidated() {
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

}
