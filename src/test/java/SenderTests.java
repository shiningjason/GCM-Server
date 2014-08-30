import me.shiningj.gcm.server.MulticastResponse;
import me.shiningj.gcm.server.NotificationResponse;
import me.shiningj.gcm.server.Sender;
import org.junit.Test;

import java.io.IOException;

/**
 * @author: shiningjason
 */
public class SenderTests {

    private static final String NOTIFICATION_KEY_NAME = "<Notification Key Name>";
    private static final String NOTIFICATION_KEY = "<Notification Key>";
    private static final String PROJECT_NUMBER = "<Project Number>";
    private static final String API_KEY = "<API Key>";
    private static final String REG_ID = "<Registration ID>";

    @Test
    public void testCreateNotificationKey() throws IOException {
        Sender sender = new Sender(PROJECT_NUMBER, API_KEY);
        NotificationResponse response = sender.createNotificationKey(NOTIFICATION_KEY_NAME, REG_ID);
        System.out.println(response);
    }

    @Test
    public void testAddRegistrationId() throws IOException {
        Sender sender = new Sender(PROJECT_NUMBER, API_KEY);
        NotificationResponse response = sender.addRegistrationId(NOTIFICATION_KEY_NAME, NOTIFICATION_KEY, REG_ID);
        System.out.println(response);
    }

    @Test
    public void testRemoveNotificationKey() throws IOException {
        Sender sender = new Sender(PROJECT_NUMBER, API_KEY);
        NotificationResponse response = sender.removeRegistrationId(NOTIFICATION_KEY_NAME, NOTIFICATION_KEY, REG_ID);
        System.out.println(response);
    }

    @Test
    public void testSend() throws IOException {
        Sender sender = new Sender(PROJECT_NUMBER, API_KEY);
        MulticastResponse response = sender.send(NOTIFICATION_KEY, new TestData());
        System.out.println(response);
    }

    public class TestData {
        private String test = "hello";
    }
}
