package me.shiningj.gcm.server;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author: shiningjason
 */
public class MulticastResponse {

    private int success;

    private int failure;

    @SerializedName("failed_registration_ids")
    private List<String> failedRegistrationIds;

    @Override
    public String toString() {
        return "MulticastResponse{" +
                "success=" + success +
                ", failure=" + failure +
                ", failedRegistrationIds=" + failedRegistrationIds +
                '}';
    }
}
