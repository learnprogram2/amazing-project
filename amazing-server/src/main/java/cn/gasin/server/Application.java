package cn.gasin.server;

import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    static int flag = 0;

    public static void main(String[] args) throws InterruptedException {

        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                flag++;
            }
        });
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                flag++;
            }
        });
        long l = System.currentTimeMillis();
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println(System.currentTimeMillis() - l);
        System.out.println(flag);
    }

}
