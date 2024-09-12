package com.template.cli.org.application.createpom;

import org.springframework.stereotype.Service;
import picocli.CommandLine;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@CommandLine.Command(name = "create-pom", description = "Create Pom")
public class CreatePom implements Runnable {
    @CommandLine.Option(names = {"-s","--service"}, description = "Service Name")
    private String serviceName;

    @Override
    public void run() {
        final String firstName = serviceName.split("-")[0];
        final String lastName = serviceName.split("-")[1];
        final String template = createTemplatePom();
        final String templatePom = String.format(template,firstName,lastName,serviceName);
        final String baseDir = "./";
        final var nameRepository = String.format("bff-%s-service",serviceName);
        final Path basePackage = Paths.get(baseDir, nameRepository);
        try {
            Files.writeString(basePackage.resolve("./pom.xml"), templatePom);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String createTemplatePom() {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<project xmlns=\"http://maven.apache.org/POM/4.0.0\"\n" +
                "\t\txmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n" +
                "\t\txsi:schemaLocation=\"http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd\">\n" +
                "\t<modelVersion>4.0.0</modelVersion>\n" +
                "\t<parent>\n" +
                "\t\t<groupId>org.springframework.boot</groupId>\n" +
                "\t\t<artifactId>spring-boot-starter-parent</artifactId>\n" +
                "\t\t<version>3.3.2</version>\n" +
                "\t</parent>\n" +
                "\t<groupId>br.com.meubay.%s.%s</groupId>\n" +
                "\t<artifactId>bff-%s-service</artifactId>\n" +
                "\t<properties>\n" +
                "\t\t<java.version>17</java.version>\n" +
                "\t\t<maven.compiler.source>17</maven.compiler.source>\n" +
                "\t\t<maven.compiler.target>17</maven.compiler.target>\n" +
                "\t\t<project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>\n" +
                "\t</properties>\n\n" +
                "\t<dependencies>\n" +
                "\t\t<dependency>\n" +
                "\t\t\t<groupId>org.springframework.boot</groupId>\n" +
                "\t\t\t<artifactId>spring-boot-starter</artifactId>\n" +
                "\t\t</dependency>\n" +
                "\t\t<dependency>\n" +
                "\t\t\t<groupId>org.springframework.boot</groupId>\n" +
                "\t\t\t<artifactId>spring-boot-starter-data-jpa</artifactId>\n" +
                "\t\t</dependency>\n" +
                "\t\t<dependency>\n" +
                "\t\t\t<groupId>com.h2database</groupId>\n" +
                "\t\t\t<artifactId>h2</artifactId>\n" +
                "\t\t\t<scope>runtime</scope>\n" +
                "\t\t</dependency>\n" +
                "\t\t<dependency>\n" +
                "\t\t\t<groupId>info.picocli</groupId>\n" +
                "\t\t\t<artifactId>picocli</artifactId>\n" +
                "\t\t\t<version>4.7.6</version>\n" +
                "\t\t</dependency>\n" +
                "\t</dependencies>\n\n" +
                "</project>\n";
    }
}
