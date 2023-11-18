package com.example.router;

import com.example.router.annotation.Router;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.util.Collections;
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
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;


@AutoService(Processor.class)
@SupportedSourceVersion(SourceVersion.RELEASE_7)
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
        return Collections.singleton(Router.class.getCanonicalName());
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
            String originalPackageName = null;
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
