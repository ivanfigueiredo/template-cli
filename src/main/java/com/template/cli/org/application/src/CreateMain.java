package com.template.cli.org.application.src;

import org.springframework.stereotype.Service;
import picocli.CommandLine;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
@CommandLine.Command(name = "create-main", description = "Create Main Class")
public class CreateMain implements Runnable{
    @CommandLine.Option(names = {"-s","--service"}, description = "Service Name")
    private String serviceName;

    @Override
    public void run() {
        final String firstName = serviceName.split("-")[0];
        final String lastName = serviceName.split("-")[1];
        final String newServiceName = capitalize(firstName) + capitalize(lastName) + "ServiceApplication";
        final String classTemplate = classTemplateFactory();
        final var mainClassContent = String.format(classTemplate,newServiceName,newServiceName);
        final String baseDir = "./";
        final var nameRepository = String.format("bff-%s-service",serviceName);
        final Path basePackage = Paths.get(baseDir, nameRepository);
        try {
            Files.writeString(basePackage.resolve(String.format("src/main/java/br/com/%s/%s/%s.java",firstName,lastName,newServiceName)), mainClassContent);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String capitalize(final String input) {
        return input.substring(0, 1).toUpperCase() + input.substring(1);
    }

    private String classTemplateFactory() {
        return "package br.com.meubay.esim;\n\n" +
                "import org.springframework.boot.SpringApplication;\n" +
                "import org.springframework.boot.autoconfigure.SpringBootApplication;\n\n" +
                "@SpringBootApplication\n" +
                "public class %s {\n\n" +
                "\tpublic static void main(String[] args) { SpringApplication.run(%sServiceApplication.class, args); }\n" +
                "}\n";
    }
}
