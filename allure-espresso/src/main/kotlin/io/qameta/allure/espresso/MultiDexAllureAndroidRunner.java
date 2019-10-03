package io.qameta.allure.espresso;

import android.os.Bundle;
import androidx.multidex.MultiDex;
import androidx.test.runner.AndroidJUnitRunner;

/**
 * Patch multi dex app for older APIs and keeps reference to
 * target context.
 *
 * @author Shackih Pavel
 */
public class MultiDexAllureAndroidRunner extends AndroidJUnitRunner {

    private static final String LISTENER_KEY = "listener";
    private static final String COMMA = ",";

    @Override
    public void onCreate(Bundle arguments) {
        MultiDex.installInstrumentation(getContext(), getTargetContext());
        ContextHolder.setTargetAppContext(getTargetContext());
        applyListener(arguments);
        super.onCreate(arguments);
    }

    private void applyListener(Bundle bundle) {
        CharSequence listener = bundle.getCharSequence(LISTENER_KEY);
        CharSequence allureListener = AllureAndroidListener.class.getName();
        if (listener == null) {
            listener = allureListener;
        } else {
            listener = listener + COMMA + allureListener;
        }
        bundle.putCharSequence(LISTENER_KEY, listener);
    }
}