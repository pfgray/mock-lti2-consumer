package net.paulgray.mocklti2;

import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.module.scala.DefaultScalaModule;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
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

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer addJdk8Module() {
        return (mapperBuilder) -> {
            mapperBuilder.modules(new Jdk8Module(), new DefaultScalaModule());
        };
    }
}
