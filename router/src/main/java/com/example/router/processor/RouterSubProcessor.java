package com.example.router.processor;

import com.example.router.annotation.Router;

import java.util.Set;

import javax.annotation.processing.Filer;
import javax.annotation.processing.RoundEnvironment;

public class RouterSubProcessor implements SubProcessor {

    @Override
    public void onProcess(RoundEnvironment roundEnvironment) {

    }

    @Override
    public void onProcessFinished(Filer filer) {

    }

    @Override
    public Set<String> supportedAnnotations() {
        return Set.of(Router.class.getCanonicalName());
    }
}
