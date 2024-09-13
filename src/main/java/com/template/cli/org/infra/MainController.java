package com.template.cli.org.infra;

import com.template.cli.org.application.controller.CreateController;
import com.template.cli.org.application.creategitignore.CreateGitIgnore;
import com.template.cli.org.application.createk8s.CreateK8s;
import com.template.cli.org.application.createpom.CreatePom;
import com.template.cli.org.application.directorystructure.DirectoryStructure;
import com.template.cli.org.application.createmain.CreateMain;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import picocli.CommandLine;

import java.util.Objects;

@Service
public class MainController implements CommandLineRunner {

    private final DirectoryStructure directoryStructure;
    private final CreateMain createMain;
    private final CreateGitIgnore createGitIgnore;
    private final CreatePom createPom;
    private final CreateK8s createK8s;
    private final CreateController createController;

    public MainController(
            final DirectoryStructure directoryStructure,
            final CreateMain createMain,
            final CreateGitIgnore createGitIgnore,
            final CreatePom createPom,
            final CreateK8s createK8s,
            final CreateController createController
    ) {
        this.directoryStructure = Objects.requireNonNull(directoryStructure);
        this.createMain = Objects.requireNonNull(createMain);
        this.createGitIgnore = Objects.requireNonNull(createGitIgnore);
        this.createPom = Objects.requireNonNull(createPom);
        this.createK8s = Objects.requireNonNull(createK8s);
        this.createController = Objects.requireNonNull(createController);
    }

    @Override
    public void run(String... args) throws Exception {
        final CommandLine directoryStructureCommandLine = new CommandLine(directoryStructure);
        final CommandLine createMainCommandLine = new CommandLine(createMain);
        final CommandLine createGitIgnoreCommandLine = new CommandLine(createGitIgnore);
        final CommandLine createPomCommandLine = new CommandLine(createPom);
        final CommandLine createK8sCommandLine = new CommandLine(createK8s);
        final CommandLine createControllerCommandLine = new CommandLine(createController);
        directoryStructureCommandLine.execute(args);
        createMainCommandLine.execute(args);
        createGitIgnoreCommandLine.execute(args);
        createPomCommandLine.execute(args);
        createK8sCommandLine.execute(args);
        createControllerCommandLine.execute(args);
    }
}
