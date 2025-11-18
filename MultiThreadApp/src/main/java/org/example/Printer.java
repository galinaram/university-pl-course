package org.example;

import java.util.concurrent.BlockingQueue;

public class Printer implements Runnable {
    private final BlockingQueue<Message> inQueue;
    private final long startTimeMillis;

    public Printer(BlockingQueue<Message> inQueue, long startTimeMillis) {
        this.inQueue = inQueue;
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
                log("Printing a message " + value);
            }
        } catch (InterruptedException e) {
            // выходим
        }
    }
}