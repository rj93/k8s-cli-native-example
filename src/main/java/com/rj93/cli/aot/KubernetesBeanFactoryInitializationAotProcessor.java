package com.rj93.cli.aot;

import java.util.Set;

import jakarta.annotation.Generated;
import org.jetbrains.annotations.NotNull;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;
import org.reflections.scanners.SubTypesScanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aot.hint.MemberCategory;
import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.TypeReference;
import org.springframework.beans.factory.aot.BeanFactoryInitializationAotContribution;
import org.springframework.beans.factory.aot.BeanFactoryInitializationAotProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

@SuppressWarnings("unused")
public class KubernetesBeanFactoryInitializationAotProcessor
        implements BeanFactoryInitializationAotProcessor {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(KubernetesBeanFactoryInitializationAotProcessor.class);

    private final MemberCategory[] allMemberCategories = MemberCategory.values();

    @Override
    public BeanFactoryInitializationAotContribution processAheadOfTime(
            @NotNull ConfigurableListableBeanFactory beanFactory) {
        return (generationContext, beanFactoryInitializationCode) -> {
            RuntimeHints hints = generationContext.getRuntimeHints();
            Reflections reflections = new Reflections("io.kubernetes.client.openapi.models", Scanners.SubTypes.filterResultsBy(s -> true));
            Set<Class<?>> models = reflections.getSubTypesOf(Object.class);
            LOGGER.info("Found {} models", models.size());
            for (Class<?> clazz : models) {
                LOGGER.info("registering {} for reflection", clazz);
                hints.reflection().registerType(clazz, allMemberCategories);
            }
        };
    }
}
