import org.example.Message;
import org.example.Printer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import static org.junit.jupiter.api.Assertions.*;

public class PrinterTest {

    @Test
    @Timeout(5)
    public void testPrinterOutput() throws InterruptedException {
        BlockingQueue<Message> inputQueue = new LinkedBlockingQueue<>();
        long startTime = System.currentTimeMillis();

        // Перехватываем вывод в консоль
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        try {
            Printer printer = new Printer(inputQueue, startTime);
            Thread printerThread = new Thread(printer, "Printer");
            printerThread.start();

            // Отправляем сообщение в принтер
            Message message = new Message(123);
            inputQueue.put(message);

            // Даем время на обработку
            Thread.sleep(100);

            printerThread.interrupt();
            printerThread.join();

            String output = outputStream.toString();
            assertTrue(output.contains("Printing a message 123"),
                    "Printer should output the correct message");
            assertTrue(output.contains("Printer at"),
                    "Printer output should contain thread name and time");

        } finally {
            System.setOut(originalOut);
        }
    }

    @Test
    public void testPrinterInterruption() throws InterruptedException {
        BlockingQueue<Message> inputQueue = new LinkedBlockingQueue<>();
        long startTime = System.currentTimeMillis();

        Printer printer = new Printer(inputQueue, startTime);
        Thread printerThread = new Thread(printer);
        printerThread.start();

        printerThread.interrupt();
        printerThread.join(1000);

        assertFalse(printerThread.isAlive(), "Printer thread should stop after interruption");
    }
}