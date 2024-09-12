package com.template.cli.org.application.creategitignore;

import org.springframework.stereotype.Service;
import picocli.CommandLine;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@CommandLine.Command(name = "create-git-ignore", description = "Create gitignore")
public class CreateGitIgnore implements Runnable {
    @CommandLine.Option(names = {"-s","--service"}, description = "Service Name")
    private String serviceName;

    @Override
    public void run() {
        final String template = createTemplateGitIgnoreFactory();
        final String baseDir = "./";
        final var nameRepository = String.format("bff-%s-service",serviceName);
        final Path basePackage = Paths.get(baseDir, nameRepository);
        try {
            Files.writeString(basePackage.resolve("./.gitignore"), template);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String createTemplateGitIgnoreFactory() {
        return "target/\n" +
                "!.mvn/wrapper/maven-wrapper.jar\n" +
                "!**/src/main/**/target/\n" +
                "!**/src/test/**/target/\n\n" +
                "### IntelliJ IDEA ###\n" +
                ".idea/modules.xml\n" +
                ".idea/jarRepositories.xml\n" +
                ".idea/compiler.xml\n" +
                ".idea/libraries/\n" +
                "*.iws\n" +
                "*.iml\n" +
                "*.ipr\n\n" +
                "### Eclipse ###\n" +
                ".apt_generated\n" +
                ".classpath\n" +
                ".factorypath\n" +
                ".project\n" +
                ".settings\n" +
                ".springBeans\n" +
                ".sts4-cache\n\n" +
                "### NetBeans ###\n" +
                "/nbproject/private/\n" +
                "/nbbuild/\n" +
                "/dist/\n" +
                "/nbdist/\n" +
                "/.nb-gradle/\n" +
                "build/\n" +
                "!**/src/main/**/build/\n" +
                "!**/src/test/**/build/\n\n" +
                "### VS Code ###\n" +
                ".vscode/\n\n" +
                "### Mac OS ###\n" +
                ".DS_Store";
    }
}
