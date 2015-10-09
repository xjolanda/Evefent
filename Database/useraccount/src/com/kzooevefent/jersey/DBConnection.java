package com.kzooevefent.jersey;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
 
public class DBConnection {
    /**
     * Method to create DB Connection
     * 
     * @return
     * @throws Exception
     */
    @SuppressWarnings("finally")
    public static Connection createConnection() throws Exception {
        Connection con = null;
        try {
            Class.forName(Constants.dbClass).newInstance();
            con = DriverManager.getConnection(Constants.dbUrl + "?user=root&password=COMP489/490");
            System.out.println("Database connection success");
        } catch (Exception e) {
        	System.out.println("Database connection failure");
        	System.out.println(e.toString());
            throw e;
        } finally {
            return con;
        }
    }
    
    /**
     * 
     * 
     * @param event_id
     * @return
     * @throws Exception
     */
    
    public static boolean enumerateEvents() throws Exception
    {
    	//TODO:Create method
    	
		return false;//TODO: change to real value
    }
    
    public static Event validateEvent(int event_id) throws Exception {
    	System.out.println("Inside validateEvent");
    	Event event = new Event(0, "", 0, false);
        Connection dbConn = null;
        //validatingEvent
        try {
            try {
            	System.out.println("Attempting database connection");
                dbConn = DBConnection.createConnection();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            System.out.println("Querying for event");
            Statement stmt = dbConn.createStatement();
            String query = "SELECT * FROM events WHERE event_id = " + event_id;
            System.out.println(query);
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                //System.out.println(rs.getString(1) + rs.getString(2) + rs.getString(3));
            	event = new Event(rs.getInt(1), rs.getString(2),rs.getInt(3), true);
            }
        } catch (SQLException sqle) {
        	System.out.println("Database Query Error");
        	System.out.println(sqle.toString());
            throw sqle;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            if (dbConn != null) {
                dbConn.close();
            }
            throw e;
        } finally {
            if (dbConn != null) {
                dbConn.close();
            }
        }
        return event;
    }    
    
    /*
    /**
     * Method to check whether uname and pwd combination are correct
     * 
     * @param uname
     * @param pwd
     * @return
     * @throws Exception
     *
    
    public static boolean checkLogin(String uname, String pwd) throws Exception {
        boolean isUserAvailable = false;
        Connection dbConn = null;
        try {
            try {
                dbConn = DBConnection.createConnection();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            Statement stmt = dbConn.createStatement();
            String query = "SELECT * FROM user WHERE username = '" + uname
                    + "' AND password=" + "'" + pwd + "'";
            //System.out.println(query);
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                //System.out.println(rs.getString(1) + rs.getString(2) + rs.getString(3));
                isUserAvailable = true;
            }
        } catch (SQLException sqle) {
            throw sqle;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            if (dbConn != null) {
                dbConn.close();
            }
            throw e;
        } finally {
            if (dbConn != null) {
                dbConn.close();
            }
        }
        return isUserAvailable;
    }
    /**
     * Method to insert uname and pwd in DB
     * 
     * @param name
     * @param uname
     * @param pwd
     * @return
     * @throws SQLException
     * @throws Exception
     *
    public static boolean insertUser(String name, String uname, String pwd) throws SQLException, Exception {
        boolean insertStatus = false;
        Connection dbConn = null;
        try {
            try {
                dbConn = DBConnection.createConnection();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            Statement stmt = dbConn.createStatement();
            String query = "INSERT into user(name, username, password) values('"+name+ "',"+"'"
                    + uname + "','" + pwd + "')";
            //System.out.println(query);
            int records = stmt.executeUpdate(query);
            //System.out.println(records);
            //When record is successfully inserted
            if (records > 0) {
                insertStatus = true;
            }
        } catch (SQLException sqle) {
            //sqle.printStackTrace();
            throw sqle;
        } catch (Exception e) {
            //e.printStackTrace();
            // TODO Auto-generated catch block
            if (dbConn != null) {
                dbConn.close();
            }
            throw e;
        } finally {
            if (dbConn != null) {
                dbConn.close();
            }
        }
        return insertStatus;
    }
    */
}