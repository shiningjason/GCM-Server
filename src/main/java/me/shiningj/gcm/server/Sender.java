package me.shiningj.gcm.server;

import com.google.gson.Gson;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

import static java.net.HttpURLConnection.HTTP_OK;
import static me.shiningj.gcm.server.Constants.ContentType.APPLICATION_JSON;
import static me.shiningj.gcm.server.Constants.Header.*;
import static me.shiningj.gcm.server.Constants.Method.POST;
import static me.shiningj.gcm.server.Constants.URL.NOTIFICATION_ENDPOINT;
import static me.shiningj.gcm.server.Constants.URL.SEND_ENDPOINT;

/**
 * @author: shiningjason
 */
public class Sender {

    private Gson gson = new Gson();
    private String projectNumber;
    private String apiKey;

    public Sender(String projectNumber, String apiKey) {
        this.projectNumber = checkNotNull(projectNumber);
        this.apiKey = checkNotNull(apiKey);
    }

    public NotificationResponse createNotificationKey(String notificationKeyName, String... registrationIds) throws IOException {
        checkNotNull(notificationKeyName);
        checkNotNull(registrationIds);

        String requestBody = gson.toJson(NotificationRequest.createOperation(notificationKeyName, registrationIds));
        return sendNotificationOperation(requestBody);
    }

    public NotificationResponse addRegistrationId(String notificationKeyName, String notificationKey, String... registrationIds) throws IOException {
        checkNotNull(notificationKeyName);
        checkNotNull(notificationKey);
        checkNotNull(registrationIds);

        String requestBody = gson.toJson(NotificationRequest.addOperation(notificationKeyName, notificationKey, registrationIds));
        return sendNotificationOperation(requestBody);
    }

    public NotificationResponse removeRegistrationId(String notificationKeyName, String notificationKey, String... registrationIds) throws IOException {
        checkNotNull(notificationKeyName);
        checkNotNull(notificationKey);
        checkNotNull(registrationIds);

        String requestBody = gson.toJson(NotificationRequest.removeOperation(notificationKeyName, notificationKey, registrationIds));
        return sendNotificationOperation(requestBody);
    }

    public MulticastResponse send(String notificationKey, Object data) throws IOException {
        checkNotNull(notificationKey);

        String requestBody = gson.toJson(new MulticastRequest(notificationKey, data));

        HttpURLConnection connection = getConnection(SEND_ENDPOINT);
        connection.setRequestMethod(POST);
        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.setRequestProperty(CONTENT_TYPE, APPLICATION_JSON);
        connection.setRequestProperty(AUTHORIZATION, getAuthorization());

        String responseBody = connect(connection, requestBody);

        return gson.fromJson(responseBody, MulticastResponse.class);
    }

    private NotificationResponse sendNotificationOperation(String requestBody) throws IOException {
        HttpURLConnection connection = getConnection(NOTIFICATION_ENDPOINT);
        connection.setRequestMethod(POST);
        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.setRequestProperty(CONTENT_TYPE, APPLICATION_JSON);
        connection.setRequestProperty(PROJECT_ID, getProjectNumber());
        connection.setRequestProperty(AUTHORIZATION, getAuthorization());

        String responseBody = connect(connection, requestBody);

        return gson.fromJson(responseBody, NotificationResponse.class);
    }

    private String connect(HttpURLConnection connection, String requestBody) throws IOException {
        writeAndClose(connection.getOutputStream(), requestBody.getBytes());

        int status = connection.getResponseCode();
        String responseBody;
        if (status == HTTP_OK)
            responseBody = readAndClose(connection.getInputStream());
        else
            responseBody = readAndClose(connection.getErrorStream());

        return responseBody;
    }

    private String getProjectNumber() {
        return projectNumber;
    }

    private String getAuthorization() {
        return "key=" + apiKey;
    }

    // ---------- Static Methods ----------

    private static HttpURLConnection getConnection(String url) throws IOException {
        return (HttpURLConnection) new URL(url).openConnection();
    }

    private static String readAndClose(InputStream in) throws IOException {
        if (in == null) return "";

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder content = new StringBuilder();
            String newLine;

            do {
                newLine = reader.readLine();
                if (newLine != null) content.append(newLine).append('\n');
            } while (newLine != null);

            if (content.length() > 0) content.setLength(content.length() - 1);

            return content.toString();
        } finally {
            close(in);
        }
    }

    private static void writeAndClose(OutputStream out, byte[] data) throws IOException {
        if (out != null) {
            try {
                if (data != null) out.write(data);
            } finally {
                close(out);
            }
        }
    }

    private static void close(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                // Ignore exception.
            }
        }
    }

    private static <T> T checkNotNull(T argument) {
        if (argument == null) throw new IllegalArgumentException("Argument cannot be null");
        return argument;
    }
}
