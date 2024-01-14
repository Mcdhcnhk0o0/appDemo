package com.example.buildsrc;

import com.android.build.api.transform.QualifiedContent;
import com.android.build.api.transform.Transform;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

import java.util.Set;

public class AsmTransform implements Plugin<Project> {

    @Override
    public void apply(Project target) {
//        target.getExtensions().getByType(AppExtension)
//        appExtension.registerTransform(new SingleClickHunterTransform(project), Collections.EMPTY_LIST);
    }

    @SuppressWarnings("deprecation")
    static class t extends Transform {

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

}