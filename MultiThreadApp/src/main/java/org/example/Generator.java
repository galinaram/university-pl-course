package org.example;

import java.util.Random;
import java.util.concurrent.BlockingQueue;

public class Generator implements Runnable {
    private final BlockingQueue<Message> outQueue;
    private final long startTimeMillis;
    private final Random random = new Random();

    public Generator(BlockingQueue<Message> outQueue, long startTimeMillis) {
        this.outQueue = outQueue;
        this.startTimeMillis = startTimeMillis;
    }

    private void log(String text) {
        long seconds = (System.currentTimeMillis() - startTimeMillis) / 1000;
        System.out.println(Thread.currentThread().getName() + " at " + seconds + ": " + text);
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                int value = random.nextInt();
                Message msg = new Message(value);

                log("message generated " + value);
                outQueue.put(msg);

                int sleepSeconds = 1 + random.nextInt(5);
                Thread.sleep(sleepSeconds * 1000L);
            }
        } catch (InterruptedException e) {
            // выходим
        }
    }
}