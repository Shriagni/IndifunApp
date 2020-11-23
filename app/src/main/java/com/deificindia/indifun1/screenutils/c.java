package com.deificindia.indifun1.screenutils;

import kotlin.coroutines.CoroutineContext;

public interface c<T> {
    CoroutineContext getContext();

    void resumeWith(Object obj);
}
