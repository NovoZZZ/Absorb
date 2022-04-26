package edu.neu.absorb;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FocusHistory {

    @SerializedName("code")
    private String code;
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private List<FocusHistoryDetail> focusHistoryDetailList;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<FocusHistoryDetail> getFocusHistoryDetailList() {
        return focusHistoryDetailList;
    }

    public void setFocusHistoryDetailList(List<FocusHistoryDetail> focusHistoryDetailList) {
        this.focusHistoryDetailList = focusHistoryDetailList;
    }
}
