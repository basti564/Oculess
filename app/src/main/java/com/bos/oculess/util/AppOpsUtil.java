package com.bos.oculess.util;

// CREDIT: https://github.com/apsun/NekoSMS/blob/cde4074f4f839e088f251500d8a1d19dee7a1895/app/src/main/java/com/crossbowffs/nekosms/utils/AppOpsUtils.java
// Used from NekoSMS by apsun under GPLv3

import android.app.AppOpsManager;
import android.content.Context;

import java.lang.reflect.Method;

public final class AppOpsUtil {
    public static final int OP_WRITE_SMS = 15;

    private static final Method sCheckOpMethod;
    private static final Method sSetModeMethod;

    static {
        Class<AppOpsManager> cls = AppOpsManager.class;
        sCheckOpMethod = ReflectionUtils.getDeclaredMethod(cls, "checkOpNoThrow", int.class, int.class, String.class);
        sSetModeMethod = ReflectionUtils.getDeclaredMethod(cls, "setMode", int.class, int.class, String.class, int.class);
    }

    private AppOpsUtil() { }

    private static AppOpsManager getAppOpsManager(Context context) {
        return (AppOpsManager)context.getSystemService(Context.APP_OPS_SERVICE);
    }

    public static boolean checkOp(Context context, int opCode, int uid, String packageName) {
        AppOpsManager appOpsManager = getAppOpsManager(context);
        int result = (Integer)ReflectionUtils.invoke(sCheckOpMethod, appOpsManager, opCode, uid, packageName);
        return result == AppOpsManager.MODE_ALLOWED;
    }

    public static void allowOp(Context context, int opCode, int uid, String packageName) {
        AppOpsManager appOpsManager = getAppOpsManager(context);
        ReflectionUtils.invoke(sSetModeMethod, appOpsManager, opCode, uid, packageName, AppOpsManager.MODE_ALLOWED);
    }
}