package io.qameta.allure.espresso;

import android.content.Context;
import androidx.test.InstrumentationRegistry;

import java.lang.ref.WeakReference;

/**
 * Keeps context for API 19 or below.
 */
final class ContextHolder {

    private static WeakReference<Context> targetAppContext;

    private ContextHolder() {
    }

    public static Context getTargetAppContext() {
        Context context = targetAppContext == null ? null : targetAppContext.get();
        if (context == null) {
            context = InstrumentationRegistry.getTargetContext();
        }
        return context;
    }

    public static void setTargetAppContext(Context context) {
        if (targetAppContext != null) {
            targetAppContext.clear();
        }
        targetAppContext = new WeakReference<>(context);
    }
}