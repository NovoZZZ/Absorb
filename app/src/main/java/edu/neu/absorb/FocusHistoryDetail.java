package edu.neu.absorb;

import com.google.gson.annotations.SerializedName;

import java.sql.Time;

public class FocusHistoryDetail {

    @SerializedName("historyId")
    Integer historyId;
    @SerializedName("userId")
    Integer userId;
    @SerializedName("description")
    String description;
    @SerializedName("startTime")
    String startTime;
    @SerializedName("endTime")
    String endTime;
    @SerializedName("createTime")
    String createTime;
    @SerializedName("updateTime")
    String updateTime;

    public FocusHistoryDetail(Integer historyId, Integer userId, String description,
                              String startTime, String endTime, String createTime, String updateTime) {
        this.historyId = historyId;
        this.userId = userId;
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public Integer getHistoryId() {
        return historyId;
    }

    public void setHistoryId(Integer historyId) {
        this.historyId = historyId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}
