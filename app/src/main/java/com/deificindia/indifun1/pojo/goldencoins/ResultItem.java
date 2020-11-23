package com.deificindia.indifun1.pojo.goldencoins;

import com.google.gson.annotations.SerializedName;

public class ResultItem {

    @SerializedName("coin_amount")
    private String coinAmount;

    @SerializedName("entrydate")
    private String entrydate;

    @SerializedName("coin_type")
    private String coinType;

    @SerializedName("id")
    private String id;

    @SerializedName("coin_point")
    private String coin_point;


    public void setCoinAmount(String coinAmount) {
        this.coinAmount = coinAmount;
    }

    public String getCoinAmount() {
        return coinAmount;
    }

    public void setEntrydate(String entrydate) {
        this.entrydate = entrydate;
    }

    public String getEntrydate() {
        return entrydate;
    }

    public void setCoinType(String coinType) {
        this.coinType = coinType;
    }

    public String getCoinType() {
        return coinType;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

	public String getCoin_point() {
		return coin_point;
	}

	public void setCoin_point(String coin_point) {
		this.coin_point = coin_point;
	}

	@Override
    public String toString() {
        return
                "ResultItem{" +
                        "coin_amount = '" + coinAmount + '\'' +
                        ",entrydate = '" + entrydate + '\'' +
                        ",coin_type = '" + coinType + '\'' +
                        ",id = '" + id + '\'' +
                        ",coin_point = '" + coin_point + '\'' +
                        "}";
    }
}