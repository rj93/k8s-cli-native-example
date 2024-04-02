
package com.rj93.cli.commands;

import io.kubernetes.client.extended.kubectl.Kubectl;
import io.kubernetes.client.extended.kubectl.exception.KubectlException;
import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.openapi.models.V1Pod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.command.annotation.Command;
import org.springframework.shell.command.annotation.Option;

import java.util.List;

@Command
public class GetPodsCommand {

    private static final Logger log = LoggerFactory.getLogger(GetPodsCommand.class);

    @Autowired
    private ApiClient apiClient;

    @Command(command = "get-pods")
    public void getConfigMaps(@Option(defaultValue = "default") String namespace) throws KubectlException {
        log.info("Getting pods");
        List<V1Pod> pods = Kubectl.get(V1Pod.class).apiClient(apiClient).namespace(namespace).execute();
        log.info("Got {} pods", pods.size());

        pods.forEach(pod -> {
            String name = pod.getMetadata().getName();
            log.info("pod: {}", name);
        });
    }
}
