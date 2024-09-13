package com.template.cli.org.application.createk8s;

import org.springframework.stereotype.Service;
import picocli.CommandLine;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@CommandLine.Command(name = "create-k8s", description = "Create K8s")
public class CreateK8s implements Runnable {
    @CommandLine.Option(names = {"-s","--service"}, description = "Service Name")
    private String serviceName;

    @Override
    public void run() {
        final String classTemplateDev = createTemplateK8sDev();
        final String classTemplateQA = createTemplateK8sQa();
        final String k8sTemplateDev = String.format(classTemplateDev, serviceName, serviceName, serviceName);
        final String k8sTemplateQa = String.format(classTemplateQA, serviceName, serviceName, serviceName);
        final String baseDir = "./";
        final var nameRepository = String.format("bff-%s-service",serviceName);
        final Path basePackage = Paths.get(baseDir, nameRepository);
        try {
            Files.writeString(basePackage.resolve("k8s/k8s-service-dev.yaml"), k8sTemplateDev);
            Files.writeString(basePackage.resolve("k8s/k8s-service-qa.yaml"), k8sTemplateQa);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String createTemplateK8sDev() {
        return "apiVersion: v1\n" +
                "kind: Service\n" +
                "metadata:\n" +
                "  name: bff-%s-service\n" +
                "spec:\n" +
                "  selector:\n" +
                "    app: bff-%s-service\n" +
                "  type: ClusterIP\n" +
                "  ports:\n" +
                "    - name: bff-%s-service\n" +
                "      port: 80\n" +
                "      targetPort: 7000\n" +
                "      protocol: TCP";
    }

    private String createTemplateK8sQa() {
        return "apiVersion: v1\n" +
                "kind: Service\n" +
                "metadata:\n" +
                "  name: bff-%s-service\n" +
                "spec:\n" +
                "  selector:\n" +
                "    app: bff-%s-service\n" +
                "  type: ClusterIP\n" +
                "  ports:\n" +
                "    - name: bff-%s-service\n" +
                "      port: 80\n" +
                "      targetPort: 7000\n" +
                "      protocol: TCP";
    }
}
