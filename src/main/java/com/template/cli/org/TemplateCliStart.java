package com.template.cli.org;

import com.template.cli.org.configuration.WebServerConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class TemplateCliStart {
    public static void main(String[] args) {
        try {
            final ConfigurableApplicationContext context = SpringApplication.run(WebServerConfig.class, args);
            final var exitCode = SpringApplication.exit(context);
            System.exit(exitCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}