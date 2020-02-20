package com.supernova.selleradmin.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NotificationItem {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("notification_title")
    @Expose
    private String notificationTitle;
    @SerializedName("notification_content")
    @Expose
    private String notificationContent;
    @SerializedName("time")
    @Expose
    private String time;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNotificationTitle() {
        return notificationTitle;
    }

    public void setNotificationTitle(String notificationTitle) {
        this.notificationTitle = notificationTitle;
    }

    public String getNotificationContent() {
        return notificationContent;
    }

    public void setNotificationContent(String notificationContent) {
        this.notificationContent = notificationContent;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
