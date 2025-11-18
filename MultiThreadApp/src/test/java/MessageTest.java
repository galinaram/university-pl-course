import org.example.Message;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MessageTest {

    @Test
    public void testMessageCreation() {
        Message message = new Message(42);
        assertEquals(42, message.info());
    }

    @Test
    public void testMessageEquality() {
        Message message1 = new Message(42);
        Message message2 = new Message(42);
        Message message3 = new Message(24);

        assertEquals(message1, message2);
        assertNotEquals(message1, message3);
    }

    @Test
    public void testMessageToString() {
        Message message = new Message(42);
        assertNotNull(message.toString());
    }
}