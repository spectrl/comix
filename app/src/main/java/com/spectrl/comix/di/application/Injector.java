package com.spectrl.comix.di.application;

import android.content.Context;

public final class Injector {
    private static final String INJECTOR_SERVICE = "com.spectrl.comix.injector";

    @SuppressWarnings("ResourceType") // Explicitly doing a custom service
    public static ApplicationComponent obtain(Context context) {
        return (ApplicationComponent) context.getSystemService(INJECTOR_SERVICE);
    }

    public static boolean matchesService(String name) {
        return INJECTOR_SERVICE.equals(name);
    }

    private Injector() {
        throw new AssertionError("No instances.");
    }
}
