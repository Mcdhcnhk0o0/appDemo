package com.example.plugin;

import com.android.build.gradle.AppExtension;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

public class ASMPlugin implements Plugin<Project> {

    @Override
    @SuppressWarnings("deprecation")
    public void apply(Project target) {
        AppExtension appExtension = target.getExtensions().getByType(AppExtension.class);
        appExtension.registerTransform(new AsmTransform());
    }
}
