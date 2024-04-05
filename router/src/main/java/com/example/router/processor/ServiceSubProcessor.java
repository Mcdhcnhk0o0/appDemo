package com.example.router.processor;

import com.example.router.Config;
import com.example.router.ServiceManager;
import com.example.router.annotation.Service;
import com.example.router.annotation.ServiceMethod;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.Filer;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.tools.Diagnostic;

public class ServiceSubProcessor implements SubProcessor {

    private List<FieldSpec> fieldSpecList;

    private MethodSpec.Builder methodBuilder;

    @Override
    public void onProcess(RoundEnvironment roundEnvironment) {
        initialize();
        // add service
        for (Element element: roundEnvironment.getElementsAnnotatedWith(Service.class)) {
            String serviceName = element.getAnnotation(Service.class).name();
            String className = element.toString();
            methodBuilder.addStatement("$T.getInstance().register($S, $S)", ServiceManager.class, serviceName, className);
            FieldSpec.Builder fieldBuilder = FieldSpec.builder(String.class, serviceName)
                    .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                    .initializer("$S", className);
            fieldSpecList.add(fieldBuilder.build());
        }
        // add method
        for (Element element: roundEnvironment.getElementsAnnotatedWith(ServiceMethod.class)) {
            ExecutableElement executableElement = (ExecutableElement) element;
            String methodName = executableElement.getSimpleName().toString();
            String className = executableElement.getEnclosingElement().toString();
            methodBuilder.addStatement("$T.getInstance().addMethod($S, $S)", ServiceManager.class, className, methodName);
        }
    }

    @Override
    public void onProcessFinished(Filer filer) {

    }

    @Override
    public Set<String> supportedAnnotations() {
        return Set.of(Service.class.getCanonicalName(), ServiceMethod.class.getCanonicalName());
    }

    private void initialize() {
        fieldSpecList = new ArrayList<>();
        methodBuilder = MethodSpec.methodBuilder(Config.INITIALIZE_METHOD)
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(void.class);
    }

}
