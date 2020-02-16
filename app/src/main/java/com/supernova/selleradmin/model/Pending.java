package com.supernova.selleradmin.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.supernova.selleradmin.util.Constants;

@Entity(tableName = Constants.TABLE_PENDING)
public class Pending {

    @PrimaryKey
    @NonNull
    private String trxId = "";
    private String playerId;
    private String amount;
    private String phoneNumber;
    private Integer isPending;

    @NonNull
    public String getTrxId() {
        return trxId;
    }

    public void setTrxId(@NonNull String trxId) {
        this.trxId = trxId;
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Integer getIsPending() {
        return isPending;
    }

    public void setIsPending(Integer isPending) {
        this.isPending = isPending;
    }

}