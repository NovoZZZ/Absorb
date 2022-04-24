package edu.neu.absorb;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserInfo {
    @SerializedName("code")
    int code;
    @SerializedName("message")
    String message;
    @SerializedName("data")
    List<User> userList;

    public UserInfo(int code, String message, List<User> userList) {
        this.code = code;
        this.message = message;
        this.userList = userList;
    }

    public  List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
