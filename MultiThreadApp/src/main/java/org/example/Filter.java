package org.example;

import java.util.concurrent.BlockingQueue;

public class Filter implements Runnable {
    private final BlockingQueue<Message> inQueue;
    private final BlockingQueue<Message> outQueue;
    private final long startTimeMillis;

    public Filter(BlockingQueue<Message> inQueue,
                  BlockingQueue<Message> outQueue,
                  long startTimeMillis) {
        this.inQueue = inQueue;
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
                Message msg = inQueue.take();
                int value = msg.info();

                if (value < 0 || value % 2 != 0) {
                    log("message skipped " + value);
                } else {
                    log("message delivered " + value);
                    outQueue.put(msg);
                }
            }
        } catch (InterruptedException e) {
            // выходим
        }
    }
}