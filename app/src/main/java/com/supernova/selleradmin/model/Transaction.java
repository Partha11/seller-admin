package com.supernova.selleradmin.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Transaction {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("trx_number")
    @Expose
    private String phoneNumber;
    @SerializedName("player_id")
    @Expose
    private String playerId;
    @SerializedName("trx_id")
    @Expose
    private String trxId;
    @SerializedName("trx_time")
    @Expose
    private String trxTime;
    @SerializedName("trx_amount")
    @Expose
    private String trxAmount;

    public String getPlayerId() {
        return playerId == null ? "" : playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTrxTime() {
        return trxTime;
    }

    public void setTrxTime(String trxTime) {
        this.trxTime = trxTime;
    }

    public String getTrxId() {
        return trxId;
    }

    public void setTrxId(String trxId) {
        this.trxId = trxId;
    }

    public String getTrxAmount() {
        return trxAmount;
    }

    public void setTrxAmount(String trxAmount) {
        this.trxAmount = trxAmount;
    }

    public String getPhoneNumber() {
        return phoneNumber == null ? "" : phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}