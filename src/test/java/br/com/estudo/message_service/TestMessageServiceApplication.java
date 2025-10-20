package br.com.estudo.message_service;

import org.springframework.boot.SpringApplication;

public class TestMessageServiceApplication {

    public static void main(String[] args) {
        SpringApplication.from(MessageServiceApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
