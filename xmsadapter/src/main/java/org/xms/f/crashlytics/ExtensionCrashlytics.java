package org.xms.f.crashlytics;

public class ExtensionCrashlytics extends org.xms.g.utils.XObject {
    
    public ExtensionCrashlytics(org.xms.g.utils.XBox param0) {
        super(param0);
    }
    
    public org.xms.g.tasks.Task<java.lang.Boolean> checkForUnsentReports() {
        throw new java.lang.RuntimeException("Not Supported");
    }
    
    public void deleteUnsentReports() {
        throw new java.lang.RuntimeException("Not Supported");
    }
    
    public boolean didCrashOnPreviousExecution() {
        throw new java.lang.RuntimeException("Not Supported");
    }
    
    public static org.xms.f.crashlytics.ExtensionCrashlytics getInstance() {
        if (org.xms.g.utils.GlobalEnvSetting.isHms()) {
            org.xms.g.utils.XmsLog.d("XMSRouter", "com.huawei.agconnect.crash.AGConnectCrash.getInstance()");
            com.huawei.agconnect.crash.AGConnectCrash hReturn = com.huawei.agconnect.crash.AGConnectCrash.getInstance();
            return ((hReturn) == null ? null : (new org.xms.f.crashlytics.ExtensionCrashlytics(new org.xms.g.utils.XBox(null, hReturn))));
        } else {
            org.xms.g.utils.XmsLog.d("XMSRouter", "com.google.firebase.crashlytics.FirebaseCrashlytics.getInstance()");
            com.google.firebase.crashlytics.FirebaseCrashlytics gReturn = com.google.firebase.crashlytics.FirebaseCrashlytics.getInstance();
            return ((gReturn) == null ? null : (new org.xms.f.crashlytics.ExtensionCrashlytics(new org.xms.g.utils.XBox(gReturn, null))));
        }
    }
    
    public void log(java.lang.String param0) {
        if (org.xms.g.utils.GlobalEnvSetting.isHms()) {
            org.xms.g.utils.XmsLog.d("XMSRouter", "((com.huawei.agconnect.crash.AGConnectCrash) this.getHInstance()).log(param0)");
            ((com.huawei.agconnect.crash.AGConnectCrash) this.getHInstance()).log(param0);
        } else {
            org.xms.g.utils.XmsLog.d("XMSRouter", "((com.google.firebase.crashlytics.FirebaseCrashlytics) this.getGInstance()).log(param0)");
            ((com.google.firebase.crashlytics.FirebaseCrashlytics) this.getGInstance()).log(param0);
        }
    }
    
    public void recordException(java.lang.Throwable param0) {
        throw new java.lang.RuntimeException("Not Supported");
    }
    
    public void sendUnsentReports() {
        throw new java.lang.RuntimeException("Not Supported");
    }
    
    public void setCrashlyticsCollectionEnabled(boolean param0) {
        if (org.xms.g.utils.GlobalEnvSetting.isHms()) {
            org.xms.g.utils.XmsLog.d("XMSRouter", "((com.huawei.agconnect.crash.AGConnectCrash) this.getHInstance()).enableCrashCollection(param0)");
            ((com.huawei.agconnect.crash.AGConnectCrash) this.getHInstance()).enableCrashCollection(param0);
        } else {
            org.xms.g.utils.XmsLog.d("XMSRouter", "((com.google.firebase.crashlytics.FirebaseCrashlytics) this.getGInstance()).setCrashlyticsCollectionEnabled(param0)");
            ((com.google.firebase.crashlytics.FirebaseCrashlytics) this.getGInstance()).setCrashlyticsCollectionEnabled(param0);
        }
    }
    
    public void setCustomKey(java.lang.String param0, boolean param1) {
        if (org.xms.g.utils.GlobalEnvSetting.isHms()) {
            org.xms.g.utils.XmsLog.d("XMSRouter", "((com.huawei.agconnect.crash.AGConnectCrash) this.getHInstance()).setCustomKey(param0, param1)");
            ((com.huawei.agconnect.crash.AGConnectCrash) this.getHInstance()).setCustomKey(param0, param1);
        } else {
            org.xms.g.utils.XmsLog.d("XMSRouter", "((com.google.firebase.crashlytics.FirebaseCrashlytics) this.getGInstance()).setCustomKey(param0, param1)");
            ((com.google.firebase.crashlytics.FirebaseCrashlytics) this.getGInstance()).setCustomKey(param0, param1);
        }
    }
    
    public void setCustomKey(java.lang.String param0, double param1) {
        if (org.xms.g.utils.GlobalEnvSetting.isHms()) {
            org.xms.g.utils.XmsLog.d("XMSRouter", "((com.huawei.agconnect.crash.AGConnectCrash) this.getHInstance()).setCustomKey(param0, param1)");
            ((com.huawei.agconnect.crash.AGConnectCrash) this.getHInstance()).setCustomKey(param0, param1);
        } else {
            org.xms.g.utils.XmsLog.d("XMSRouter", "((com.google.firebase.crashlytics.FirebaseCrashlytics) this.getGInstance()).setCustomKey(param0, param1)");
            ((com.google.firebase.crashlytics.FirebaseCrashlytics) this.getGInstance()).setCustomKey(param0, param1);
        }
    }
    
    public void setCustomKey(java.lang.String param0, float param1) {
        if (org.xms.g.utils.GlobalEnvSetting.isHms()) {
            org.xms.g.utils.XmsLog.d("XMSRouter", "((com.huawei.agconnect.crash.AGConnectCrash) this.getHInstance()).setCustomKey(param0, param1)");
            ((com.huawei.agconnect.crash.AGConnectCrash) this.getHInstance()).setCustomKey(param0, param1);
        } else {
            org.xms.g.utils.XmsLog.d("XMSRouter", "((com.google.firebase.crashlytics.FirebaseCrashlytics) this.getGInstance()).setCustomKey(param0, param1)");
            ((com.google.firebase.crashlytics.FirebaseCrashlytics) this.getGInstance()).setCustomKey(param0, param1);
        }
    }
    
    public void setCustomKey(java.lang.String param0, int param1) {
        if (org.xms.g.utils.GlobalEnvSetting.isHms()) {
            org.xms.g.utils.XmsLog.d("XMSRouter", "((com.huawei.agconnect.crash.AGConnectCrash) this.getHInstance()).setCustomKey(param0, param1)");
            ((com.huawei.agconnect.crash.AGConnectCrash) this.getHInstance()).setCustomKey(param0, param1);
        } else {
            org.xms.g.utils.XmsLog.d("XMSRouter", "((com.google.firebase.crashlytics.FirebaseCrashlytics) this.getGInstance()).setCustomKey(param0, param1)");
            ((com.google.firebase.crashlytics.FirebaseCrashlytics) this.getGInstance()).setCustomKey(param0, param1);
        }
    }
    
    public void setCustomKey(java.lang.String param0, long param1) {
        if (org.xms.g.utils.GlobalEnvSetting.isHms()) {
            org.xms.g.utils.XmsLog.d("XMSRouter", "((com.huawei.agconnect.crash.AGConnectCrash) this.getHInstance()).setCustomKey(param0, param1)");
            ((com.huawei.agconnect.crash.AGConnectCrash) this.getHInstance()).setCustomKey(param0, param1);
        } else {
            org.xms.g.utils.XmsLog.d("XMSRouter", "((com.google.firebase.crashlytics.FirebaseCrashlytics) this.getGInstance()).setCustomKey(param0, param1)");
            ((com.google.firebase.crashlytics.FirebaseCrashlytics) this.getGInstance()).setCustomKey(param0, param1);
        }
    }
    
    public void setCustomKey(java.lang.String param0, java.lang.String param1) {
        if (org.xms.g.utils.GlobalEnvSetting.isHms()) {
            org.xms.g.utils.XmsLog.d("XMSRouter", "((com.huawei.agconnect.crash.AGConnectCrash) this.getHInstance()).setCustomKey(param0, param1)");
            ((com.huawei.agconnect.crash.AGConnectCrash) this.getHInstance()).setCustomKey(param0, param1);
        } else {
            org.xms.g.utils.XmsLog.d("XMSRouter", "((com.google.firebase.crashlytics.FirebaseCrashlytics) this.getGInstance()).setCustomKey(param0, param1)");
            ((com.google.firebase.crashlytics.FirebaseCrashlytics) this.getGInstance()).setCustomKey(param0, param1);
        }
    }
    
    public void setUserId(java.lang.String param0) {
        if (org.xms.g.utils.GlobalEnvSetting.isHms()) {
            org.xms.g.utils.XmsLog.d("XMSRouter", "((com.huawei.agconnect.crash.AGConnectCrash) this.getHInstance()).setUserId(param0)");
            ((com.huawei.agconnect.crash.AGConnectCrash) this.getHInstance()).setUserId(param0);
        } else {
            org.xms.g.utils.XmsLog.d("XMSRouter", "((com.google.firebase.crashlytics.FirebaseCrashlytics) this.getGInstance()).setUserId(param0)");
            ((com.google.firebase.crashlytics.FirebaseCrashlytics) this.getGInstance()).setUserId(param0);
        }
    }
    
    public static org.xms.f.crashlytics.ExtensionCrashlytics dynamicCast(java.lang.Object param0) {
        return ((org.xms.f.crashlytics.ExtensionCrashlytics) param0);
    }
    
    public static boolean isInstance(java.lang.Object param0) {
        if (!(param0 instanceof org.xms.g.utils.XGettable)) {
            return false;
        }
        if (org.xms.g.utils.GlobalEnvSetting.isHms()) {
            return ((org.xms.g.utils.XGettable) param0).getHInstance() instanceof com.huawei.agconnect.crash.AGConnectCrash;
        } else {
            return ((org.xms.g.utils.XGettable) param0).getGInstance() instanceof com.google.firebase.crashlytics.FirebaseCrashlytics;
        }
    }
}