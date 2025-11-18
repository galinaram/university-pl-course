package org.example;

import java.util.concurrent.*;
import java.util.Random;

public class MultiThreadApp {
    private volatile boolean running = true;
    private final BlockingQueue<Message> genToFilter = new LinkedBlockingQueue<>();
    private final BlockingQueue<Message> filterToPrinter = new LinkedBlockingQueue<>();
    private final long startTime = System.currentTimeMillis();

    public static void main(String[] args) {
        MultiThreadApp app = new MultiThreadApp();
        app.start();
    }

    public void start() {
        // Создаем и запускаем потоки
        Thread generator = new Thread(new Generator(), "Generator");
        Thread filter = new Thread(new Filter(), "Filter");
        Thread printer = new Thread(new Printer(), "Printer");

        generator.start();
        filter.start();
        printer.start();

        // Ждем 1 минуту
        try {
            Thread.sleep(60000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Останавливаем потоки
        running = false;

        // Прерываем потоки на случай ожидания
        generator.interrupt();
        filter.interrupt();
        printer.interrupt();

        // Ждем завершения потоков
        try {
            generator.join();
            filter.join();
            printer.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("Program finished");
    }

    // Класс сообщения
    public record Message(int info) {}

    // Генератор
    class Generator implements Runnable {
        private final Random random = new Random();

        @Override
        public void run() {
            while (running && !Thread.currentThread().isInterrupted()) {
                try {
                    // Случайная задержка 1-5 секунд
                    int delay = random.nextInt(5) + 1;
                    Thread.sleep(delay * 1000);

                    // Генерация случайного сообщения
                    Message message = new Message(random.nextInt());
                    long currentTime = (System.currentTimeMillis() - startTime) / 1000;

                    // Логирование
                    System.out.printf("Generator at %d: message generated %d%n",
                            currentTime, message.info());

                    // Передача сообщения фильтру
                    genToFilter.put(message);

                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }
    }

    // Фильтр
    class Filter implements Runnable {
        @Override
        public void run() {
            while (running && !Thread.currentThread().isInterrupted()) {
                try {
                    // Получение сообщения от генератора
                    Message message = genToFilter.take();
                    long currentTime = (System.currentTimeMillis() - startTime) / 1000;

                    // Проверка условия: четное и неотрицательное
                    if (message.info() >= 0 && message.info() % 2 == 0) {
                        // Логирование доставки
                        System.out.printf("Filter at %d: message delivered %d%n",
                                currentTime, message.info());
                        // Передача принтеру
                        filterToPrinter.put(message);
                    } else {
                        // Логирование пропуска
                        System.out.printf("Filter at %d: message skipped %d%n",
                                currentTime, message.info());
                    }

                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }
    }

    // Принтер
    class Printer implements Runnable {
        @Override
        public void run() {
            while (running && !Thread.currentThread().isInterrupted()) {
                try {
                    // Получение сообщения от фильтра
                    Message message = filterToPrinter.take();
                    long currentTime = (System.currentTimeMillis() - startTime) / 1000;

                    // Печать сообщения
                    System.out.printf("Printer at %d: Printing a message %d%n",
                            currentTime, message.info());

                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }
    }
}