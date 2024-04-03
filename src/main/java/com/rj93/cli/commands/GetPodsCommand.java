
package com.rj93.cli.commands;

import io.kubernetes.client.extended.kubectl.Kubectl;
import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.openapi.models.V1Pod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.command.annotation.Command;
import org.springframework.shell.command.annotation.Option;

import java.util.List;

@SuppressWarnings("unused")
@Command
public class GetPodsCommand {

    private static final Logger log = LoggerFactory.getLogger(GetPodsCommand.class);

    @Autowired
    private ApiClient apiClient;

    @Command(command = "get-pods")
    public void getPods(@Option(defaultValue = "default") String namespace) throws ApiException {
        getPodsKubectl(namespace); // doesn't work
        getPodsCoreApi(namespace); // works
    }

    private void getPodsKubectl(String namespace) {
        try {
            log.info("getPodsKubectl: Getting pods");
            List<V1Pod> pods = Kubectl.get(V1Pod.class).apiClient(apiClient).namespace(namespace).execute();
            log.info("getPodsKubectl: Got {} pods", pods.size());

            pods.forEach(pod -> log.info("getPodsKubectl: pod: {}", pod.getMetadata().getName()));
        } catch (Exception e) {
            log.error("Unable to get pods using Kubectl: {}", e.getMessage(), e);
        }
    }

    private void getPodsCoreApi(String namespace) {
        CoreV1Api coreV1Api = new CoreV1Api(apiClient);

        try {
            log.info("getPodsCoreApi: Getting pods");
            List<V1Pod> pods = coreV1Api.listNamespacedPod(namespace).execute().getItems();
            log.info("getPodsCoreApi: Got {} pods", pods.size());

            pods.forEach(pod -> log.info("getPodsCoreApi: pod: {}", pod.getMetadata().getName()));
        } catch (Exception e) {
            log.error("getPodsCoreApi: Unable to get pods using PodsCoreApi: {}", e.getMessage(), e);
        }
    }
}
