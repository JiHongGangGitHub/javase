package org.example;

public class  Test implements Runnable {
private int value = 0;
    private static final Object lock = new Object();

    @Override
    public void run() {
        for (int i = 0; i < 10000; i++) {
            synchronized (lock) {
                value += 1;
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Test test = new Test();
        Thread thread1 = new Thread(test);
        thread1.setName("Thread-1");
        Thread thread2 = new Thread(test);
        thread2.setName("Thread-2");
        thread1.start();
        thread2.start();
        // 等待两个线程执行完毕
        thread1.join();
        thread2.join();
        System.out.println(test.value);
    }



}
