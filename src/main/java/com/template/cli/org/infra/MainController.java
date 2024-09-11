package com.template.cli.org.infra;

import com.template.cli.org.application.directorystructure.DirectoryStructure;
import com.template.cli.org.application.src.CreateMain;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import picocli.CommandLine;

import java.util.Objects;

@Service
public class MainController implements CommandLineRunner {

    private final DirectoryStructure directoryStructure;

    private final CreateMain createMain;

    public MainController(final DirectoryStructure directoryStructure, final CreateMain createMain) {
        this.directoryStructure = Objects.requireNonNull(directoryStructure);
        this.createMain = Objects.requireNonNull(createMain);
    }

    @Override
    public void run(String... args) throws Exception {
        final CommandLine directoryStructureCommandLine = new CommandLine(directoryStructure);
        final CommandLine createMainCommandLine = new CommandLine(createMain);
        directoryStructureCommandLine.execute(args);
        createMainCommandLine.execute(args);
    }
}
