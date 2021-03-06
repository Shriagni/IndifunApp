
package com.deificindia.indifun1.Model.retro;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChatModel_Response {

    @SerializedName("result")
    @Expose
    private List<ChatModel_Result> result = null;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private Integer status;

    public List<ChatModel_Result> getResult() {
        return result;
    }

    public void setResult(List<ChatModel_Result> result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}
