package com.template.cli.org.application.controller;

import org.springframework.stereotype.Service;
import picocli.CommandLine;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@CommandLine.Command(name = "create-controller", description = "Create Controller")
public class CreateController implements Runnable {
    @CommandLine.Option(names = {"-s","--service"}, description = "Service Name")
    private String serviceName;

    @Override
    public void run() {
        final String firstName = serviceName.split("-")[0];
        final String lastName = serviceName.split("-")[1];
//        final String newControllerName = capitalize(firstName) + capitalize(lastName) + "Controller";
//        final String newControllerImplName = capitalize(firstName) + capitalize(lastName) + "ControllerImpl";
//        final String controllerTemplate = createTemplateController();
//        final String controllerImplTemplate = createTemplateControllerImpl();
//        final var controllerClassContent = String.format(
//                controllerTemplate,
//                firstName,
//                lastName,
//                capitalize(firstName),
//                capitalize(lastName),
//                capitalize(firstName),
//                capitalize(lastName),
//                newControllerName,
//                newControllerImplName,
//                newControllerName,
//                newControllerName);
////        final var controllerImplClassContent = String.format(controllerImplTemplate,firstName,lastName,newServiceName,newServiceName);
        final String baseDir = "./";
        final var nameRepository = String.format("bff-%s-service",serviceName);
        final Path basePackage = Paths.get(baseDir, nameRepository);
        try {
            Files.writeString(basePackage.resolve(
                    String.format(
                            "src/main/java/br/com/%s/%s/controller/%s.java",
                            firstName,
                            lastName,
                            joinNamePascalCase(firstName, lastName, "Controller")
                    )),
                    createController(firstName, lastName));
            Files.writeString(basePackage.resolve(
                            String.format(
                                    "src/main/java/br/com/%s/%s/controller/%s.java",
                                    firstName,
                                    lastName,
                                    joinNamePascalCase(firstName, lastName, "ControllerImpl")
                            )),
                    createControllerImpl(firstName, lastName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String joinNameCameCase(final String valueOne, final String valueTwo, final String suffix) {
        return valueOne + capitalize(valueTwo) + suffix;
    }

    private String joinNamePascalCase(final String valueOne, final String valueTwo, final String suffix) {
        return capitalize(valueOne) + capitalize(valueTwo) + suffix;
    }

    private String createController(final String firstName, final String lastName) {
        final String newControllerNamePascalCase = joinNamePascalCase(firstName, lastName, "Controller");
        final String newControllerImplNamePascalCase = joinNamePascalCase(firstName, lastName, "ControllerImpl");
        final String controllerTemplate = createTemplateController();
        return String.format(
                controllerTemplate,
                firstName,
                lastName,
                capitalize(firstName),
                capitalize(lastName),
                capitalize(firstName),
                capitalize(lastName),
                newControllerNamePascalCase,
                newControllerImplNamePascalCase,
                newControllerNamePascalCase,
                newControllerNamePascalCase);
    }

    private String createControllerImpl(final String firstName, final String lastName) {
        final String newControllerNamePascalCase = joinNamePascalCase(firstName, lastName, "Controller");
        final String newControllerImplNamePascalCase = joinNamePascalCase(firstName, lastName, "ControllerImpl");
        final String serviceNamePascalCase = joinNamePascalCase(firstName, lastName, "Service");
        final String serviceNameCameCase = joinNameCameCase(firstName, lastName, "Service");
        final String controllerImplTemplate = createTemplateControllerImpl();
        return String.format(
                controllerImplTemplate,
                firstName,
                lastName,
                firstName,
                lastName,
                firstName,
                lastName,
                serviceNamePascalCase,
                newControllerImplNamePascalCase,
                newControllerNamePascalCase,
                serviceNamePascalCase,
                serviceNameCameCase,
                newControllerImplNamePascalCase,
                serviceNamePascalCase,
                serviceNameCameCase,
                serviceNameCameCase,
                serviceNameCameCase,
                serviceNameCameCase);
    }

    private String capitalize(final String input) {
        return input.substring(0, 1).toUpperCase() + input.substring(1);
    }

    private String createTemplateController() {
        return "package br.com.meubay.%s.%s.controller;\n\n" +
                "import io.swagger.v3.oas.annotations.Operation;\n" +
                "import io.swagger.v3.oas.annotations.media.Content;\n" +
                "import io.swagger.v3.oas.annotations.media.Schema;\n" +
                "import io.swagger.v3.oas.annotations.tags.Tag;\n" +
                "import io.swagger.v3.oas.annotations.tags.Tags;\n" +
                "import org.springframework.http.ResponseEntity;\n" +
                "import org.springframework.web.bind.annotation.*;\n" +
                "import reactor.core.publisher.Mono;\n\n" +
                "import statis org.springframework.http.MediaType.APPLICATION_JSON_VALUE;\n\n" +
                "@Tags(@Tag(name = \"%s %s\", " +
                "description = '%s %s Rest Controller'))\n" +
                "public sealed interface %s permits %s {\n\n" +
                "@Operation(method = \"POST\", summary = \" POST %s.class\", oprationId = \"execute\", description = \"POST %s.class\"\n" +
                "@PostMapping(value = \"/test\")\n" +
                "@ApiResponse(\n" +
                "\t\tresponseCode = \"200\"\n" +
                "\t\tdescription = \"\"\n" +
                "\t\tcontent = {@Content(\n" +
                "\t\t\t\tmediaType = APPLICATION_JSON_VALUE,\n" +
                "\t\t\t\tschema = @Schema(implementation = TesteResponse.class))\n" +
                "\t\t})\n" +
                "Mono<ResponseEntity<TesteResponse>> execute(\n" +
                "\t\t@RequestBody() Object request,\n" +
                "\t\t@RequestAttribute(value = \"Headers\", required = false) RequestHeaders headers\n" +
                ");";
    }

    private String createTemplateControllerImpl() {
        return "package br.com.meubay.%s.%s.controller;\n\n" +
                "import br.com.meubay.%s.%s.dto.TestRequest;\n" +
                "import br.com.meubay.%s.%s.service.%s" +
                "import lombok.extern.slf4j.Slf4j;\n" +
                "import org.springframework.http.ResponseEntity;\n" +
                "import org.springframework.stereotype.Component;\n" +
                "import org.springframework.web.bind.annotation.RequestMapping;\n" +
                "import org.springframework.web.bind.annotation.RestController;\n" +
                "import reactor.core.publisher.Mono;\n\n" +
                "import static org.springframework.http.HttpStatus.OK;\n\n" +
                "@Component\n" +
                "@Slf4j\n" +
                "@RestController\n" +
                "@RequestMapping\n" +
                "public no-sealed class %s implements %s {\n" +
                "\tprivate final %s %s\n\n" +
                "\tpublic %s(%s %s) {\n" +
                "\t\tthis.%s = %s;\n" +
                "\t}\n\n" +
                "\t@Override\n" +
                "\tpublic Mono<ResponseEntity<TestResponse>> execute(RequestBody request) {\n" +
                "\t\treturn %s.execute();\n" +
                "\t}\n" +
                "}\n";
    }
}
