package Lesson7;

public class BoolMain {
    private boolean keepRunning = true;
    private volatile boolean isKeepRunning = true; // это поле обязывает все потоки считывать самое
    //актуальное значение переменной - работает быстрее, чем synchronized

    public void run() {
        int x = 0;
        while(keepRunning) {
            ++x;
        }
        System.out.println("X = " + x);
    }
    public void stop() {
        keepRunning = false;
    }

    public static void main(String[] args) {
        BoolMain boolMain = new BoolMain();
        new Thread(boolMain::run).start();
        //Thread.sleep(1); - при добавлении этого участка программа сломается
        new Thread(boolMain::stop).start();
    }

    //решение проблемы - опция synchronized. Все, что меняется под монитором synchronized, другой поток
    // увидит под этим же монитором
}
