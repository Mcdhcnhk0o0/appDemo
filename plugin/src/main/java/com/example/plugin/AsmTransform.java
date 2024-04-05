package com.example.plugin;

import com.android.build.api.transform.QualifiedContent;
import com.android.build.api.transform.Transform;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

import java.util.Set;


@SuppressWarnings("deprecation")
public class AsmTransform extends Transform {


    @Override
    public String getName() {
        return null;
    }

    @Override
    public Set<QualifiedContent.ContentType> getInputTypes() {
        return null;
    }

    @Override
    public Set<? super QualifiedContent.Scope> getScopes() {
        return null;
    }

    @Override
    public boolean isIncremental() {
        return false;
    }
}