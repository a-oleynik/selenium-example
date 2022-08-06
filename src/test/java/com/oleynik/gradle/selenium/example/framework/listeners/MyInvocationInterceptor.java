package com.oleynik.gradle.selenium.example.framework.listeners;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.InvocationInterceptor;
import org.junit.jupiter.api.extension.ReflectiveInvocationContext;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;

import static com.oleynik.gradle.selenium.example.framework.listeners.MyTestWatcher.METHOD_PARAMETERS;
import static com.oleynik.gradle.selenium.example.framework.listeners.MyTestWatcher.NAMESPACE;

public class MyInvocationInterceptor implements InvocationInterceptor {

    @Override
    public void interceptTestTemplateMethod(final Invocation<Void> invocation,
                                            final ReflectiveInvocationContext<Method> invocationContext,
                                            final ExtensionContext extensionContext) throws Throwable {
        sendParameters2Store(invocationContext, extensionContext);
        invocation.proceed();
    }

    private void sendParameters2Store(final ReflectiveInvocationContext<Method> invocationContext,
                                      final ExtensionContext extensionContext) {
        final Parameter[] parameters = invocationContext.getExecutable().getParameters();
        List<Object> methodParameters = new ArrayList<>();
        for (int i = 0; i < parameters.length; i++) {
            final Parameter parameter = parameters[i];
            final Class<?> parameterType = parameter.getType();
            // Skip default jupiter injectables as TestInfo, TestReporter and TempDirectory
            if (parameterType.getCanonicalName().startsWith("org.junit.jupiter.api")) {
                continue;
            }
            methodParameters.add(invocationContext.getArguments().get(i));
        }
        extensionContext.getStore(NAMESPACE).put(METHOD_PARAMETERS, methodParameters);
    }
}
