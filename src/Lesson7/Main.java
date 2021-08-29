package Lesson7;

public class Main {
    static final Object lock = new Object();
    static int x = 0;

    public static void main(String[] args) throws InterruptedException {
        //объявление потоков
        /*Runnable runnable = () -> {
            for (int i = 0; i < 1000; i++) {
                System.out.println(i + ": " + Thread.currentThread().getName());
            }
            System.out.println("End");
        };*/

        Runnable runnable = () -> {
            for (int i = 0; i < 1000000; i++) {
                //++x;
                /*Программа не всегда выдает ожидаемые значения. Почему?
                Инкремент ++х - три операции:
                1. Счет значения х
                2. Увеличение значения на 1
                3. Запись значеня в х
                */
                increment();
            }
        };
        Thread thread1 = new Thread(runnable);
        Thread thread2 = new Thread(runnable);
        thread1.start();
        thread2.start();
        //thread1.setDaemon(true); //если останутся только такие потоки, JVM завершается.
        // Daemon - фоновый поток
        thread1.join(); //ждать, пока завершится поток 1
        thread2.join();

        //thread.stop() - завершить текущий поток. НЕ рекомендуется к использованию, так как текущий поток
        // может оставить какие-то системные переменные в незавершенном виде

        // Все, что ниже, идет в главном потоке

        System.out.println("X = " + x);
    }

    /*private static synchronized void increment() { //synchronized - в метод входит только один поток
        //один поток входит в метод, остальные его ждут. Потом в поток входит случайный метод.
        ++x;
    }*/

    private static void increment() {
        synchronized (lock) { //секция захватывает монитор объекта
            ++x;
        }
        //равносильно тому, что выше
    }

    synchronized void run() { //при объявлении метода захватывается монитор объекта, надо которым
        // выполняется данный метод
        synchronized (this) { //если же в методе нет объектов, то this
            ++x;
        }
    }

    synchronized static void run1() {//при объявлении метода захватывается монитор объекта, надо которым
        // выполняется данный метод
        synchronized (Main.class) { //если метод статический, то захватывается экзмепляр класса этого метода
            ++x;
        }
    }
}
