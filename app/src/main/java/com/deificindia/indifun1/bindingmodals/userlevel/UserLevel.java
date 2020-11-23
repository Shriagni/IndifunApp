package com.deificindia.indifun1.bindingmodals.userlevel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserLevel {

@SerializedName("result")
@Expose
private UserLevelResult result;
@SerializedName("message")
@Expose
private String message;
@SerializedName("status")
@Expose
private Integer status;

public UserLevelResult getResult() {
return result;
}

public void setResult(UserLevelResult result) {
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