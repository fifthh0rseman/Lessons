package Lesson7;

import java.util.ArrayDeque;
import java.util.Deque;

public class ProducerConsumer {
    private static final Deque<String> queue = new ArrayDeque<>();

    private static class Producer extends Thread {
        @Override
        public void run() {
            while(true) {
                String newElement = produce();

                while(true) {
                    synchronized (queue) {
                        while (queue.size() > 1000) { //для защиты от случайного пробуждения нужно использовать
                            //не if, а while
                            doWait();
                        }
                            queue.add(newElement);
                            queue.notify();
                            break;

                    }
                }
            }
        }
        int x = 0;
        private String produce() {
            return String.valueOf(x++);
        }

        private void doWait() {
            try {
                queue.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static class Consumer extends Thread {
        @Override
        public void run() {
            while (true) {
                String s = null;
                s = getElement();
                consume(s);
            }
        }

        private String getElement() {
            while (true) {
                synchronized (queue) {
                    while (queue.isEmpty()) {
                        doWait();
                    }
                    String s = queue.removeFirst();
                    queue.notify();
                    return s;
                }
            }

        }

        public void doWait() {
            try {
                queue.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        private void consume(String s) {
            System.out.println(s);
        }
    }



    public static void main(String[] args) {
        new Producer().start();
        new Consumer().start();
    }
}
