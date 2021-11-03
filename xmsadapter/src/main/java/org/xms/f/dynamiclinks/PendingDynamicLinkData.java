package org.xms.f.dynamiclinks;


import android.content.Intent;
import android.content.pm.PackageManager;

public class PendingDynamicLinkData extends org.xms.g.utils.XObject {
    
    
    
    public PendingDynamicLinkData(org.xms.g.utils.XBox param0) {
        super(param0);
    }
    
    protected PendingDynamicLinkData(java.lang.String param0, int param1, long param2, android.net.Uri param3) {
        super(((org.xms.g.utils.XBox) null));
    }
    
    public long getClickTimestamp() {
        if (org.xms.g.utils.GlobalEnvSetting.isHms()) {
            org.xms.g.utils.XmsLog.d("XMSRouter", "((com.huawei.agconnect.applinking.ResolvedLinkData) this.getHInstance()).getClickTimestamp()");
            return ((com.huawei.agconnect.applinking.ResolvedLinkData) this.getHInstance()).getClickTimestamp();
        } else {
            org.xms.g.utils.XmsLog.d("XMSRouter", "((com.google.firebase.dynamiclinks.PendingDynamicLinkData) this.getGInstance()).getClickTimestamp()");
            return ((com.google.firebase.dynamiclinks.PendingDynamicLinkData) this.getGInstance()).getClickTimestamp();
        }
    }
    
    public android.net.Uri getLink() {
        if (org.xms.g.utils.GlobalEnvSetting.isHms()) {
            org.xms.g.utils.XmsLog.d("XMSRouter", "((com.huawei.agconnect.applinking.ResolvedLinkData) this.getHInstance()).getDeepLink()");
            return ((com.huawei.agconnect.applinking.ResolvedLinkData) this.getHInstance()).getDeepLink();
        } else {
            org.xms.g.utils.XmsLog.d("XMSRouter", "((com.google.firebase.dynamiclinks.PendingDynamicLinkData) this.getGInstance()).getLink()");
            return ((com.google.firebase.dynamiclinks.PendingDynamicLinkData) this.getGInstance()).getLink();
        }
    }
    
    public int getMinimumAppVersion() {
        if (org.xms.g.utils.GlobalEnvSetting.isHms()) {
            
            org.xms.g.utils.XmsLog.d("XMSRouter", "((com.huawei.agconnect.applinking.ResolvedLinkData) this.getHInstance()).getMinimumAppVersion()");
            return (int) ((com.huawei.agconnect.applinking.ResolvedLinkData) this.getHInstance()).getMinimumAppVersion();
        } else {
            org.xms.g.utils.XmsLog.d("XMSRouter", "((com.google.firebase.dynamiclinks.PendingDynamicLinkData) this.getGInstance()).getMinimumAppVersion()");
            return ((com.google.firebase.dynamiclinks.PendingDynamicLinkData) this.getGInstance()).getMinimumAppVersion();
        }
    }
    
    public android.content.Intent getUpdateAppIntent(android.content.Context param0) {
        if (org.xms.g.utils.GlobalEnvSetting.isHms()) {
            
            int versionCode;
            if (getMinimumAppVersion() == 0) {
                return null;
            }
            String packageName = param0.getApplicationContext().getPackageName();
            try {
                versionCode =
                    param0
                        .getPackageManager()
                        .getPackageInfo(packageName, 0)
                        .versionCode;
            } catch (PackageManager.NameNotFoundException e) {
                return null;
            }
            if (versionCode < getMinimumAppVersion()) {
                return new Intent("com.huawei.appmarket.intent.action.AppDetail")
                    .putExtra("APP_PACKAGENAME", packageName)
                    .setPackage("com.huawei.appmarket");
            }
            return null;
        } else {
            org.xms.g.utils.XmsLog.d("XMSRouter", "((com.google.firebase.dynamiclinks.PendingDynamicLinkData) this.getGInstance()).getUpdateAppIntent(param0)");
            return ((com.google.firebase.dynamiclinks.PendingDynamicLinkData) this.getGInstance()).getUpdateAppIntent(param0);
        }
    }
    
    public static org.xms.f.dynamiclinks.PendingDynamicLinkData dynamicCast(java.lang.Object param0) {
        return ((org.xms.f.dynamiclinks.PendingDynamicLinkData) param0);
    }
    
    public static boolean isInstance(java.lang.Object param0) {
        if (!(param0 instanceof org.xms.g.utils.XGettable)) {
            return false;
        }
        if (org.xms.g.utils.GlobalEnvSetting.isHms()) {
            return ((org.xms.g.utils.XGettable) param0).getHInstance() instanceof com.huawei.agconnect.applinking.ResolvedLinkData;
        } else {
            return ((org.xms.g.utils.XGettable) param0).getGInstance() instanceof com.google.firebase.dynamiclinks.PendingDynamicLinkData;
        }
    }
}