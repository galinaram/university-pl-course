import org.example.Filter;
import org.example.Message;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import static org.junit.jupiter.api.Assertions.*;

public class FilterTest {

    @Test
    @Timeout(5)
    public void testFilterAcceptsEvenPositiveNumbers() throws InterruptedException {
        BlockingQueue<Message> inputQueue = new LinkedBlockingQueue<>();
        BlockingQueue<Message> outputQueue = new LinkedBlockingQueue<>();
        long startTime = System.currentTimeMillis();

        Filter filter = new Filter(inputQueue, outputQueue, startTime);
        Thread filterThread = new Thread(filter);
        filterThread.start();

        // Отправляем четное положительное число - должно пройти
        Message goodMessage = new Message(42);
        inputQueue.put(goodMessage);

        Message result = outputQueue.poll(2, TimeUnit.SECONDS);
        assertNotNull(result, "Filter should deliver even positive numbers");
        assertEquals(42, result.info());

        filterThread.interrupt();
        filterThread.join();
    }

    @Test
    @Timeout(5)
    public void testFilterRejectsOddNumbers() throws InterruptedException {
        BlockingQueue<Message> inputQueue = new LinkedBlockingQueue<>();
        BlockingQueue<Message> outputQueue = new LinkedBlockingQueue<>();
        long startTime = System.currentTimeMillis();

        Filter filter = new Filter(inputQueue, outputQueue, startTime);
        Thread filterThread = new Thread(filter);
        filterThread.start();

        // Отправляем нечетное число - должно быть отклонено
        Message oddMessage = new Message(41);
        inputQueue.put(oddMessage);

        // Ждем немного и проверяем что в выходной очереди ничего нет
        Thread.sleep(100);
        assertTrue(outputQueue.isEmpty(), "Filter should reject odd numbers");

        filterThread.interrupt();
        filterThread.join();
    }

    @Test
    @Timeout(5)
    public void testFilterRejectsNegativeNumbers() throws InterruptedException {
        BlockingQueue<Message> inputQueue = new LinkedBlockingQueue<>();
        BlockingQueue<Message> outputQueue = new LinkedBlockingQueue<>();
        long startTime = System.currentTimeMillis();

        Filter filter = new Filter(inputQueue, outputQueue, startTime);
        Thread filterThread = new Thread(filter);
        filterThread.start();

        // Отправляем отрицательное число - должно быть отклонено
        Message negativeMessage = new Message(-10);
        inputQueue.put(negativeMessage);

        // Ждем немного и проверяем что в выходной очереди ничего нет
        Thread.sleep(100);
        assertTrue(outputQueue.isEmpty(), "Filter should reject negative numbers");

        filterThread.interrupt();
        filterThread.join();
    }

    @Test
    @Timeout(5)
    public void testFilterRejectsNegativeOddNumbers() throws InterruptedException {
        BlockingQueue<Message> inputQueue = new LinkedBlockingQueue<>();
        BlockingQueue<Message> outputQueue = new LinkedBlockingQueue<>();
        long startTime = System.currentTimeMillis();

        Filter filter = new Filter(inputQueue, outputQueue, startTime);
        Thread filterThread = new Thread(filter);
        filterThread.start();

        // Отправляем отрицательное нечетное число - должно быть отклонено
        Message badMessage = new Message(-7);
        inputQueue.put(badMessage);

        Thread.sleep(100);
        assertTrue(outputQueue.isEmpty(), "Filter should reject negative odd numbers");

        filterThread.interrupt();
        filterThread.join();
    }
}