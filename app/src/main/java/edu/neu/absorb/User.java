package edu.neu.absorb;

import com.google.gson.annotations.SerializedName;

import java.sql.Time;

public class User {


    @SerializedName("userID")
    private String userID;
    @SerializedName("username")
    private String userName;
    @SerializedName("creatTime")
    private Time createTime;
    @SerializedName("totalHour")
    private double totalHour;
    @SerializedName("focusCount")
    private int count;


    public User(String userID, double hour, int count) {
        this.userID = userID;
        this.totalHour = hour;
        this.count = count;
    }

    public User(String userID, String userName, Time createTime, double totalHour, int count) {
        this.userID = userID;
        this.userName = userName;
        this.createTime = createTime;
        this.totalHour = totalHour;
        this.count = count;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public Time getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Time createTime) {
        this.createTime = createTime;
    }

    public double getTotalHour() {
        return totalHour;
    }

    public void setTotalHour(double totalHour) {
        this.totalHour = totalHour;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
