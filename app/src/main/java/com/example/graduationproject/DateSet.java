package com.example.graduationproject;

public class DateSet  {
    private static String driver = "com.mysql.jdbc.Driver" ;
    private static String url = "jdbc:mysql://192.168.199.239:3306/graduationproject";
    private static String user = "liudong";
    private static String password = "5692573";

    public static String getDriver(){
        return driver;
    }
    public static String getUrl(){
        return url;
    }
    public static String getUser(){
        return user;
    }
    public static String getPassword(){
        return password;
    }
}
