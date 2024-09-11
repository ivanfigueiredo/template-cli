package com.template.cli.org.application.directorystructure;

import org.springframework.stereotype.Service;
import picocli.CommandLine.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@Command(name = "directory-structure", description = "Project Structure Generator")
public class DirectoryStructure implements Runnable {
    @Option(names = {"-s","--service"}, description = "Service Name")
    private String serviceName;

    @Override
    public void run() {
        final String baseDir = "./";
        final var nameRepository = String.format("bff-%s-service",serviceName);
        final Path basePackage = Paths.get(baseDir, nameRepository);
        final String firstName = serviceName.split("-")[0];
        final String lastName = serviceName.split("-")[1];
        try {
            Files.createDirectories(basePackage.resolve("k8s"));
            Files.createDirectories(basePackage.resolve("src"));
            final Path srcPackage = Paths.get(String.format("%s%s/src/main/java/br/com/%s/%s",baseDir,nameRepository,firstName,lastName));
            Files.createDirectories(srcPackage.resolve("configuration"));
            Files.createDirectories(srcPackage.resolve("controller"));
            Files.createDirectories(srcPackage.resolve("dto"));
            Files.createDirectories(srcPackage.resolve("exceptions"));
            Files.createDirectories(srcPackage.resolve("service"));
            Files.createDirectories(srcPackage.resolve("util"));
            final Path resourcePackage = Paths.get(String.format("%s%s/src/main/resources",baseDir,nameRepository));
            final Path testPackage = Paths.get(String.format("%s%s/src/test",baseDir,nameRepository));
            Files.createDirectories(resourcePackage.resolve("."));
            Files.createDirectories(testPackage.resolve("."));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

