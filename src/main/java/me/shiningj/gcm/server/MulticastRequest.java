package me.shiningj.gcm.server;

/**
 * @author: shiningjason
 */
public class MulticastRequest {

    private String to;
    private Object data;

    public MulticastRequest(String to, Object data) {
        this.to = to;
        this.data = data;
    }
}
