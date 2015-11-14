package com.kzooevefent.jersey;

public class Constants {
	public static String dbClass = "com.mysql.jdbc.Driver";
    private static String dbName= "events_db";
    public static String dbUrl = "jdbc:mysql://localhost:3306/"+dbName;
    public static String dbUser = "root"; //TODO:should be private
    public static String dbPassword = "COMP489/490"; //TODO:should be private
    public static int event_params = 4;
}
