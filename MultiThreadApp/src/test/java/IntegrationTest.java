import org.example.Filter;
import org.example.Generator;
import org.example.Message;
import org.example.Printer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import static org.junit.jupiter.api.Assertions.*;

public class IntegrationTest {

    @Test
    @Timeout(10)
    public void testFullPipeline() throws InterruptedException {
        long startTime = System.currentTimeMillis();

        BlockingQueue<Message> genToFilter = new LinkedBlockingQueue<>();
        BlockingQueue<Message> filterToPrinter = new LinkedBlockingQueue<>();

        // Создаем и запускаем всю цепочку
        Generator generator = new Generator(genToFilter, startTime);
        Filter filter = new Filter(genToFilter, filterToPrinter, startTime);
        Printer printer = new Printer(filterToPrinter, startTime);

        Thread genThread = new Thread(generator, "Generator");
        Thread filterThread = new Thread(filter, "Filter");
        Thread printerThread = new Thread(printer, "Printer");

        genThread.start();
        filterThread.start();
        printerThread.start();

        // Ждем пока система обработает несколько сообщений
        Thread.sleep(3000);

        // Останавливаем все потоки
        genThread.interrupt();
        filterThread.interrupt();
        printerThread.interrupt();

        genThread.join(1000);
        filterThread.join(1000);
        printerThread.join(1000);

        assertFalse(genThread.isAlive(), "Generator should stop");
        assertFalse(filterThread.isAlive(), "Filter should stop");
        assertFalse(printerThread.isAlive(), "Printer should stop");
    }
}