package com.example.appdemo.flutter;

import androidx.annotation.NonNull;
import io.flutter.embedding.android.FlutterFragment;

public class FlutterRootFragment extends FlutterFragment {

    public static FlutterFragment.CachedEngineFragmentBuilder withCachedEngine(@NonNull String cachedEngineId) {
        return FlutterFragment.withCachedEngine(cachedEngineId);
    }

}
