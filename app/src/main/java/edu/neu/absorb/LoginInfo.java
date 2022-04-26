package edu.neu.absorb;

import com.google.gson.annotations.SerializedName;

public class LoginInfo {
    @SerializedName("userID")
    private Integer userId;
    @SerializedName("token")
    private String token;

    public LoginInfo(Integer userId, String token) {
        this.userId = userId;
        this.token = token;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
