package me.shiningj.gcm.server;

/**
 * @author: shiningjason
 */
public final class Constants {

    public static final class URL {
        public static final String NOTIFICATION_ENDPOINT = "https://android.googleapis.com/gcm/notification";
        public static final String SEND_ENDPOINT = "https://android.googleapis.com/gcm/send";
    }

    public static final class Header {
        public static final String CONTENT_TYPE = "Content-Type";
        public static final String PROJECT_ID = "project_id";
        public static final String AUTHORIZATION = "Authorization";
    }

    public static final class Method {
        public static final String POST = "POST";
    }

    public static final class ContentType {
        public static final String APPLICATION_JSON = "application/json";
    }
}
