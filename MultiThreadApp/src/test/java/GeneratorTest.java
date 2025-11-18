import org.example.Message;
import org.example.Generator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import static org.junit.jupiter.api.Assertions.*;

public class GeneratorTest {

    @Test
    @Timeout(10)
    public void testGeneratorProducesMessages() throws InterruptedException {
        BlockingQueue<Message> queue = new LinkedBlockingQueue<>();
        long startTime = System.currentTimeMillis();
        Generator generator = new Generator(queue, startTime);

        Thread generatorThread = new Thread(generator, "TestGenerator");
        generatorThread.start();

        // Ждем немного чтобы генератор успел создать сообщение
        Thread.sleep(2000);

        generatorThread.interrupt();
        generatorThread.join(1000);

        assertFalse(queue.isEmpty(), "Generator should produce at least one message");
    }

    @Test
    public void testGeneratorInterruption() throws InterruptedException {
        BlockingQueue<Message> queue = new LinkedBlockingQueue<>();
        long startTime = System.currentTimeMillis();
        Generator generator = new Generator(queue, startTime);

        Thread generatorThread = new Thread(generator);
        generatorThread.start();

        // Немного ждем и прерываем
        Thread.sleep(100);
        generatorThread.interrupt();
        generatorThread.join(1000);

        assertFalse(generatorThread.isAlive(), "Generator thread should stop after interruption");
    }
}