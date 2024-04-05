package com.example.router;

import com.example.router.annotation.Router;
import com.example.router.annotation.Service;
import com.example.router.annotation.ServiceMethod;
import com.example.router.processor.SubProcessor;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;


@AutoService(Processor.class)
@SupportedSourceVersion(SourceVersion.RELEASE_17)
public class RouterProcessor extends AbstractProcessor {

    private static final String TAG = RouterProcessor.class.getSimpleName();

    public static final String INIT_CLASS = "RouterInit";
    public static final String INIT_PACKAGE = "com.example";
    public static final String INIT_METHOD = "initRouter";

    private Filer filer;

    private Messager messager;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        filer = processingEnv.getFiler();
        messager = processingEnv.getMessager();
        messager.printMessage(Diagnostic.Kind.NOTE, "messager init!");
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> routerAnnotations = new HashSet<>();
        for (SubProcessor subProcessor: Config.subProcessors) {
            routerAnnotations.addAll(subProcessor.supportedAnnotations());
        }
        routerAnnotations.add(Router.class.getCanonicalName());
        routerAnnotations.add(Service.class.getCanonicalName());
        routerAnnotations.add(ServiceMethod.class.getCanonicalName());
        return routerAnnotations;
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        if (set == null || set.isEmpty()) {
            return false;
        }
        messager.printMessage(Diagnostic.Kind.NOTE, TAG + " -> process start!");
        try {
            MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder(INIT_METHOD)
                    .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                    .returns(void.class);
            List<FieldSpec> fieldSpecList = new ArrayList<>();
            String originalPackageName = null;
            for (Element element: roundEnvironment.getElementsAnnotatedWith(Service.class)) {
                String serviceName = element.getAnnotation(Service.class).name();
                String className = element.toString();
                methodBuilder.addStatement("$T.getInstance().register($S, $S)", ServiceManager.class, serviceName, className);
                FieldSpec.Builder fieldBuilder = FieldSpec.builder(String.class, serviceName)
                        .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                        .initializer("$S", className);
                fieldSpecList.add(fieldBuilder.build());

            }
            for (Element element: roundEnvironment.getElementsAnnotatedWith(ServiceMethod.class)) {

                ExecutableElement executableElement = (ExecutableElement) element;
                String methodName = executableElement.getSimpleName().toString();
                String className = executableElement.getEnclosingElement().toString();
                messager.printMessage(Diagnostic.Kind.NOTE, TAG + " -> " + className + "#" + methodName);
                methodBuilder.addStatement("$T.getInstance().addMethod($S, $S)", ServiceManager.class, className, methodName);
            }

            for (Element element: roundEnvironment.getElementsAnnotatedWith(Router.class)) {
                if (!(element instanceof TypeElement)) {
                    continue;
                }
                TypeElement typeElement = (TypeElement) element;
                String className = typeElement.getQualifiedName().toString();
                String url = typeElement.getAnnotation(Router.class).url();
                String packageName = typeElement.getAnnotation(Router.class).group();
                String description = typeElement.getAnnotation(Router.class).description();
                if (originalPackageName == null) {
                    originalPackageName = packageName;
                } else {
                    if (!originalPackageName.equals(packageName)) {
                        throw new RuntimeException(TAG + "【different groups】 in one module are not supported");
                    }
                }
                methodBuilder.addStatement("$T.getInstance().register($S, $S)", RouterManager.class, url, className);
                methodBuilder.addStatement("$T.getInstance().addDescription($S, $S)", RouterManager.class, url, description);
            }
            TypeSpec routerInit = TypeSpec.classBuilder(INIT_CLASS)
                    .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                    .addMethod(methodBuilder.build())
                    .addFields(fieldSpecList)
                    .build();
            String packageName = INIT_PACKAGE + "." + originalPackageName;
            JavaFile javaFile = JavaFile.builder(packageName, routerInit).build();
            javaFile.writeTo(filer);
            messager.printMessage(Diagnostic.Kind.NOTE, TAG + " -> process succeed!");
            return true;
        } catch (Exception e) {
            messager.printMessage(Diagnostic.Kind.NOTE, TAG + " -> errors in annotation process: " + e.getMessage());
        }
        return true;
    }

}
