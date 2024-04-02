# cli

## Prerequisites
1. Access to K8s cluster and permissions to list config maps
2. Java and GraalVM installed
    ```shell
    sdk install java 21.0.2-tem
    sdk install java 21.0.2-graalce
    ```

## Local
```shell
# java
sdk use java 21.0.2-tem && \
  mvn clean install && \
  java -jar target/k8s-cli-native-example-*.jar get-pods

# native
sdk use java 21.0.2-graalce && \
  mvn native:compile -Pnative && \
  sdk use java 21.0.2-tem && \
  ./target/k8s-cli-native-example get-pods
```