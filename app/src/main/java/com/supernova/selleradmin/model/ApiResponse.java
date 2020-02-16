package com.supernova.selleradmin.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ApiResponse {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("fail_reason")
    @Expose
    private String failReason;
    @SerializedName("user_token")
    @Expose
    private String userToken;
    @SerializedName("bkash")
    @Expose
    private String bkash;
    @SerializedName("nogod")
    @Expose
    private String nogod;
    @SerializedName("rocket")
    @Expose
    private String rocket;
    @SerializedName("chips_price")
    @Expose
    private String chipsPrice;
    @SerializedName("all_users")
    @Expose
    private String allUsers;
    @SerializedName("total_chips")
    @Expose
    private String totalChips;
    @SerializedName("total_amount")
    @Expose
    private String totalAmount;
    @SerializedName("active_users")
    @Expose
    private String activeUsers;
    @SerializedName("current_balance")
    @Expose
    private String currentBalance;
    @SerializedName("transactions")
    @Expose
    private List<Transaction> transactions;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getFailReason() {
        return failReason;
    }

    public void setFailReason(String failReason) {
        this.failReason = failReason;
    }

    public String getUserToken() {
        return userToken;
    }

    public String getChipsPrice() {
        return chipsPrice;
    }

    public void setChipsPrice(String chipsPrice) {
        this.chipsPrice = chipsPrice;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public String getBkash() {
        return bkash;
    }

    public void setBkash(String bkash) {
        this.bkash = bkash;
    }

    public String getNogod() {
        return nogod;
    }

    public void setNogod(String nogod) {
        this.nogod = nogod;
    }

    public String getRocket() {
        return rocket;
    }

    public void setRocket(String rocket) {
        this.rocket = rocket;
    }

    public String getAllUsers() {
        return allUsers;
    }

    public void setAllUsers(String allUsers) {
        this.allUsers = allUsers;
    }

    public String getTotalChips() {
        return totalChips;
    }

    public void setTotalChips(String totalChips) {
        this.totalChips = totalChips;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getActiveUsers() {
        return activeUsers;
    }

    public void setActiveUsers(String activeUsers) {
        this.activeUsers = activeUsers;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public String getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(String currentBalance) {
        this.currentBalance = currentBalance;
    }
}