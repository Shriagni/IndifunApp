
package com.deificindia.indifun1.Model.retro;

import com.deificindia.indifun1.bindingmodals.UserProfileResult;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserProfile {

    @SerializedName("result")
    @Expose
    private UserProfileResult result;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private Integer status;

    public UserProfileResult getResult() {
        return result;
    }

    public void setResult(UserProfileResult result) {
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
