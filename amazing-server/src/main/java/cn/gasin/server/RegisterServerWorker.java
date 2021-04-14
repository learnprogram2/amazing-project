package cn.gasin.server;

import cn.gasin.server.registry.Registry;
import cn.gasin.server.registry.RegistryExpel;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

public class RegisterServerWorker extends Thread {

    public static void main(String[] args) {
        new RegisterServerWorker().start();
    }

    @Override
    public void run() {
        super.run();
        // receive register request:
        // start a web server
        ConfigurableApplicationContext context = SpringApplication.run(Application.class);


        // check unlive instance
        // internal 60s, timeout 90s.
        new RegistryExpel(context.getBean(Registry.class)).start();

    }
}
