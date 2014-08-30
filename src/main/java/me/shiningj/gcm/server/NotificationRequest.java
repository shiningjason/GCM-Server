package me.shiningj.gcm.server;

import com.google.gson.annotations.SerializedName;

import java.util.Arrays;
import java.util.List;

import static me.shiningj.gcm.server.NotificationRequest.Operation.*;

/**
 * @author: shiningjason
 */
public final class NotificationRequest {

    public enum Operation {
        CREATE, ADD, REMOVE
    }

    private Operation operation;

    @SerializedName("notification_key_name")
    private String notificationKeyName;

    @SerializedName("notification_key")
    private String notificationKey;

    @SerializedName("registration_ids")
    private List<String> registrationIds;

    public static NotificationRequest createOperation(String notificationKeyName, String... registrationIds) {
        return new NotificationRequest(CREATE, notificationKeyName, null, registrationIds);
    }

    public static NotificationRequest addOperation(String notificationKeyName, String notificationKey, String... registrationIds) {
        return new NotificationRequest(ADD, notificationKeyName, notificationKey, registrationIds);
    }

    public static NotificationRequest removeOperation(String notificationKeyName, String notificationKey, String... registrationIds) {
        return new NotificationRequest(REMOVE, notificationKeyName, notificationKey, registrationIds);
    }

    private NotificationRequest(Operation operation, String notificationKeyName, String notificationKey, String... registrationIds) {
        this.operation = operation;
        this.notificationKey = notificationKey;
        this.notificationKeyName = notificationKeyName;
        this.registrationIds = Arrays.asList(registrationIds);
    }
}
