package Lesson7;

public class SemMain {
    static Semaphore semaphore = new Semaphore(5);
    public void main(String[] args) {
        run();
    }

    public void run() {
        semaphore.lock();
        try {
            someLogic();
        } finally {
            semaphore.unlock();
        }

    }

    private void someLogic() {
        for (int i = 0; i < 10; i++) {
            System.out.println(i + ": " + Thread.currentThread().getName());

        }
    }
}
