package com.deificindia.indifun1.pojo.goldencoins;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class GoldenCoinResponse{

	@SerializedName("result")
	private List<ResultItem> result;

	@SerializedName("message")
	private String message;

	@SerializedName("status")
	private int status;

	public void setResult(List<ResultItem> result){
		this.result = result;
	}

	public List<ResultItem> getResult(){
		return result;
	}

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}

	public void setStatus(int status){
		this.status = status;
	}

	public int getStatus(){
		return status;
	}

	@Override
 	public String toString(){
		return 
			"GoldenCoinResponse{" + 
			"result = '" + result + '\'' + 
			",message = '" + message + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}