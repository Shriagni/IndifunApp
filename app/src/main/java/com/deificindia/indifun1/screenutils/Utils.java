package com.deificindia.indifun1.screenutils;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import java.util.Collection;

public class Utils {
    public static boolean ensureNotNull(Object... objArr) {
        for (Object isNull : objArr) {
            if (isNull(isNull)) {
                return false;
            }
        }
        return true;
    }

    public static int getCollectionSize(Collection collection) {
        if (collection == null) {
            return 0;
        }
        return collection.size();
    }

    public static String getStringNotNull(String str) {
        return isNull(str) ? "" : str;
    }

    public static boolean isEmptyArray(Object[] objArr) {
        return isNull(objArr) || isZero(objArr.length);
    }

    public static boolean isEmptyByte(byte[] bArr) {
        return isNull(bArr) || isZero(bArr.length);
    }

    public static boolean isEmptyCollection(Collection collection) {
        return isNull(collection) || isZero(collection.size());
    }

    public static boolean isEmptyString(String str) {
        if (isNull(str)) {
            return true;
        }
        return isZero(str.trim().length());
    }

    public static boolean isEquals(Object obj, Object obj2) {
        return obj != null && obj.equals(obj2);
    }

    public static boolean isEqualsAllowNull(Object obj, Object obj2) {
        boolean z = true;
        boolean z2 = obj != null;
        boolean z3 = obj2 != null;
        if (z2 && z3) {
            return obj.equals(obj2);
        }
        if (z2 || z3) {
            z = false;
        }
        return z;
    }

   /* public static boolean isIntentExists(Context context, Intent intent) {
        return isNotEmptyCollection(context.getPackageManager().queryIntentActivities(intent, 65536));
    }*/

    public static boolean isNotEmptyCollection(Collection collection) {
        return !isEmptyCollection(collection);
    }

    public static boolean isNotEmptyString(String str) {
        return !isEmptyString(str);
    }

    public static boolean isNull(Object obj) {
        return obj == null;
    }

    public static boolean isZero(int i2) {
        return i2 == 0;
    }

    public static boolean isZeroDouble(double d) {
        return d == 0.0d;
    }

    public static boolean isZeroLong(long j2) {
        return j2 == 0;
    }

    public static boolean nonNull(Object obj) {
        return obj != null;
    }

    public static boolean isEquals(String str, String str2) {
        boolean isEmpty = TextUtils.isEmpty(str);
        boolean isEmpty2 = TextUtils.isEmpty(str2);
        if (isEmpty || isEmpty2) {
            return false;
        }
        return str.equals(str2);
    }
}
