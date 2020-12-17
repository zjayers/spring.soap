package io.ayers.spring_soap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("io.ayers")
public class SpringSoapApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringSoapApplication.class, args);
    }

}
