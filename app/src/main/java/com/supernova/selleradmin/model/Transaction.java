package com.supernova.selleradmin.model;

import android.util.Log;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.supernova.selleradmin.util.Constants;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    @SerializedName("trx_type")
    @Expose
    private int trxType;

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

        Log.d("Length", "" + this.trxId.length());

        if (this.trxId.length() == 10) {

            Matcher m = Pattern.compile("\\b[0-9]{10}\\b").matcher(this.trxId);

            if (m.find()) {

                this.setTrxType(Constants.TYPE_ROCKET);

            } else {

                this.setTrxType(Constants.TYPE_BKASH);
            }

        } else if (this.trxId.length() == 8) {

            this.setTrxType(Constants.TYPE_NOGOD);
        }
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

        if (phoneNumber.length() == 11) {

            this.phoneNumber = phoneNumber;

        } else if (phoneNumber.length() == 12) {

            this.phoneNumber = phoneNumber.substring(0, phoneNumber.length() - 1);
        }
    }

    public int getTrxType() {
        return trxType;
    }

    public void setTrxType(int trxType) {
        this.trxType = trxType;
    }
}