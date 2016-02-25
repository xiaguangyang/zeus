package com.zeus.service;

import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.concurrent.CountDownLatch;

/**
 * 主线程
 */
public class Launcher {
    private static final Logger logger = Logger.getLogger(Launcher.class);

    public static void main(String[] args) {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:spring/spring.xml");
        logger.info("service is started");
        logger.info("java.library.path=" + System.getProperty("java.library.path"));
        CountDownLatch latch = new CountDownLatch(1);
        try {
            latch.await();
        } catch (InterruptedException ignored) {
        }
    }
}