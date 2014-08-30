package me.shiningj.gcm.server;

import com.google.gson.annotations.SerializedName;

/**
 * @author: shiningjason
 */
public final class NotificationResponse {

    private String error;

    @SerializedName("notification_key")
    private String notificationKey;

    public String getError() {
        return error;
    }

    public String getNotificationKey() {
        return notificationKey;
    }

    @Override
    public String toString() {
        return (error == null) ? notificationKey : error;
    }
}
