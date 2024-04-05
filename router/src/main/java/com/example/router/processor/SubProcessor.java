package com.example.router.processor;

import java.util.Set;

import javax.annotation.processing.Filer;
import javax.annotation.processing.RoundEnvironment;

public interface SubProcessor {

    void onProcess(RoundEnvironment roundEnvironment);

    void onProcessFinished(Filer filer);

    Set<String> supportedAnnotations();

}
