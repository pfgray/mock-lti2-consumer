package net.paulgray.mocklti2;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 * Created by paul on 3/23/16.
 */
@Configuration
@ComponentScan//("net.paulgray.mocklti2.*")
@ImportResource("spring/applicationContext.xml")
@EnableAutoConfiguration
@SpringBootApplication
public class MockLti2App {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(MockLti2App.class, args);
    }
}
