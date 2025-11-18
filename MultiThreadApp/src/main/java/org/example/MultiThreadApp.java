package org.example;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class MultiThreadApp {
    public static void main(String[] args) throws InterruptedException {
        long startTime = System.currentTimeMillis();

        BlockingQueue<Message> genToFilter = new LinkedBlockingQueue<>();
        BlockingQueue<Message> filterToPrinter = new LinkedBlockingQueue<>();

        Thread generator = new Thread(
                new Generator(genToFilter, startTime),
                "Generator"
        );
        Thread filter = new Thread(
                new Filter(genToFilter, filterToPrinter, startTime),
                "Filter"
        );
        Thread printer = new Thread(
                new Printer(filterToPrinter, startTime),
                "Printer"
        );

        generator.start();
        filter.start();
        printer.start();

        Thread.sleep(60_000L);

        generator.interrupt();
        filter.interrupt();
        printer.interrupt();

        generator.join();
        filter.join();
        printer.join();
    }
}