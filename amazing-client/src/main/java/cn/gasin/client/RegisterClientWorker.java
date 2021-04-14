package cn.gasin.client;

import cn.gasin.api.domain.Response;
import cn.gasin.api.domain.ResponseStatus;
import cn.gasin.client.domain.heartbeat.HeartbeatRequest;
import cn.gasin.client.domain.register.RegisterRequest;
import cn.gasin.client.http.HttpClient;
import lombok.extern.log4j.Log4j2;

import java.util.concurrent.atomic.AtomicBoolean;


/**
 * 这个是client组件的入口
 */
@Log4j2
public class RegisterClientWorker extends Thread {

    // private static final Logger logger = LogManager.getLogger(RegisterClientWorker.class);

    // config
    String SERVICE_NAME = "demoProject";
    String INSTANCE_ID = "instance01";
    String INSTANCE_IP = "192.168.0.1";
    Integer INSTANCE_PORT = 9001;

    AtomicBoolean alive = new AtomicBoolean(true);

    public static void main(String[] args) {
        // 一旦启动了这个组件之后，他就负责在服务上干两个事情
        // 第一个事情，就是开启一个线程向register-server去发送请求，注册这个服务
        // 第二个事情，就是在注册成功之后，就会开启另外一个线程去发送心跳

        // 我们来简化一下这个模型
        // 我们在register-client这块就开启一个线程
        // 这个线程刚启动的时候，第一个事情就是完成注册
        // 如果注册完成了之后，他就会进入一个while true死循环
        // 每隔30秒就发送一个请求去进行心跳

        new RegisterClientWorker().start();
    }

    @Override
    public void run() {
        super.run();

        // register
        // 1. 读取配置, 拼装注册请求
        RegisterRequest registerRequest = RegisterRequest.builder()
                .serviceName(SERVICE_NAME).instanceId(INSTANCE_ID)
                .instanceIp(INSTANCE_IP).instancePort(INSTANCE_PORT).build();
        // 2. 使用工具发送请求.
        Response response = HttpClient.sendRegisterRequest(registerRequest);
        if (ResponseStatus.SUCCESS.equals(response.getStatus())) {
            log.info("register success");
        } else {
            // 3. 重试机制, 或者异常机制
            log.error("register failed:{}", response.getMessage());
            return;
        }


        // heartbeat
        while (alive.get()) {
            // 1. 睡一小会, 发送心跳请求
            try {
                Thread.sleep(30 * 1000);

                HeartbeatRequest heartbeatRequest =
                        HeartbeatRequest.builder().instanceId(INSTANCE_ID).serviceName(SERVICE_NAME).build();
                Response hbResponse = HttpClient.sendHeartbeat(heartbeatRequest);
                if (ResponseStatus.SUCCESS.equals(hbResponse.getStatus())) {
                    log.info("heartbeat succ: {}", System.currentTimeMillis());
                } else {
                    // 异常处理: 这里如果注册不成功, 要有很多事情做了
                    log.error("heartbeat failed: {}: {}", System.currentTimeMillis(), hbResponse.getMessage());
                }
            } catch (InterruptedException e) {
                log.error("sleep interrupt:{}, ", Thread.interrupted(), e);
            }
        }

        // enable unregister request sender


    }
}
