package com.deificindia.indifun1.screenutils;

import kotlin.UninitializedPropertyAccessException;

import static com.elvishew.xlog.XLog.e;

public class j {

    public static void l(String str) {
        UninitializedPropertyAccessException uninitializedPropertyAccessException = new UninitializedPropertyAccessException(str);
        e(uninitializedPropertyAccessException);
        throw uninitializedPropertyAccessException;
    }

    public static void m(String str) {
        l("lateinit property " + str + " has not been initialized");
        throw null;
    }
}
