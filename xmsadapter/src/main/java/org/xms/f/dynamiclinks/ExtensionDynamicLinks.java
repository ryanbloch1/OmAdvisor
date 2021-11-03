package org.xms.f.dynamiclinks;


import android.app.Activity;
import org.xms.f.TaskImpl;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

public abstract class ExtensionDynamicLinks extends org.xms.g.utils.XObject {
    
    private static Activity getCurrentActivity() {
        try {
            Class activityThreadClass = Class.forName("android.app.ActivityThread");
            Object activityThread = activityThreadClass.getMethod("currentActivityThread").invoke(
                    null);
            Field activitiesField = activityThreadClass.getDeclaredField("mActivities");
            activitiesField.setAccessible(true);
            Map activities = (Map) activitiesField.get(activityThread);
            for (Object activityRecord : activities.values()) {
                Class activityRecordClass = activityRecord.getClass();
                Field pausedField = activityRecordClass.getDeclaredField("paused");
                pausedField.setAccessible(true);
                if (!pausedField.getBoolean(activityRecord)) {
                    Field activityField = activityRecordClass.getDeclaredField("activity");
                    activityField.setAccessible(true);
                    Activity activity = (Activity) activityField.get(activityRecord);
                    return activity;
                }
            }
        } catch (ClassNotFoundException e) {
            org.xms.g.utils.XmsLog.e("org.xms.f.dynamiclinks.ExtensionDynamicLinks.getCurrentActivity", "", e);
        } catch (InvocationTargetException e) {
            org.xms.g.utils.XmsLog.e("org.xms.f.dynamiclinks.ExtensionDynamicLinks.getCurrentActivity", "", e);
        } catch (NoSuchMethodException e) {
            org.xms.g.utils.XmsLog.e("org.xms.f.dynamiclinks.ExtensionDynamicLinks.getCurrentActivity", "", e);
        } catch (NoSuchFieldException e) {
            org.xms.g.utils.XmsLog.e("org.xms.f.dynamiclinks.ExtensionDynamicLinks.getCurrentActivity", "", e);
        } catch (IllegalAccessException e) {
            org.xms.g.utils.XmsLog.e("org.xms.f.dynamiclinks.ExtensionDynamicLinks.getCurrentActivity", "", e);
        }
        return null;
    }
    
    public ExtensionDynamicLinks(org.xms.g.utils.XBox param0) {
        super(param0);
    }
    
    public ExtensionDynamicLinks() {
        super(((org.xms.g.utils.XBox) null));
    }
    
    public abstract org.xms.f.dynamiclinks.DynamicLink.Builder createDynamicLink();
    
    public abstract org.xms.g.tasks.Task<org.xms.f.dynamiclinks.PendingDynamicLinkData> getDynamicLink(android.net.Uri param0);
    
    public abstract org.xms.g.tasks.Task<org.xms.f.dynamiclinks.PendingDynamicLinkData> getDynamicLink(android.content.Intent param0);
    
    public static synchronized org.xms.f.dynamiclinks.ExtensionDynamicLinks getInstance() {
        if (org.xms.g.utils.GlobalEnvSetting.isHms()) {
            org.xms.g.utils.XmsLog.d("XMSRouter", "com.huawei.agconnect.applinking.AGConnectAppLinking.getInstance()");
            com.huawei.agconnect.applinking.AGConnectAppLinking hReturn = com.huawei.agconnect.applinking.AGConnectAppLinking.getInstance();
            return ((hReturn) == null ? null : (new org.xms.f.dynamiclinks.ExtensionDynamicLinks.XImpl(new org.xms.g.utils.XBox(null, hReturn))));
        } else {
            org.xms.g.utils.XmsLog.d("XMSRouter", "com.google.firebase.dynamiclinks.FirebaseDynamicLinks.getInstance()");
            com.google.firebase.dynamiclinks.FirebaseDynamicLinks gReturn = com.google.firebase.dynamiclinks.FirebaseDynamicLinks.getInstance();
            return ((gReturn) == null ? null : (new org.xms.f.dynamiclinks.ExtensionDynamicLinks.XImpl(new org.xms.g.utils.XBox(gReturn, null))));
        }
    }
    
    public static synchronized org.xms.f.dynamiclinks.ExtensionDynamicLinks getInstance(org.xms.f.ExtensionApp param0) {
        if (org.xms.g.utils.GlobalEnvSetting.isHms()) {
            
            org.xms.g.utils.XmsLog.d("XMSRouter", "com.huawei.agconnect.applinking.AGConnectAppLinking.getInstance(org.xms.f.ExtensionApp)");
            com.huawei.agconnect.applinking.AGConnectAppLinking hReturn = com.huawei.agconnect.applinking.AGConnectAppLinking.getInstance();
            return ((hReturn) == null ? null : (new org.xms.f.dynamiclinks.ExtensionDynamicLinks.XImpl(new org.xms.g.utils.XBox(null, hReturn))));
        } else {
            org.xms.g.utils.XmsLog.d("XMSRouter", "com.google.firebase.dynamiclinks.FirebaseDynamicLinks.getInstance(((com.google.firebase.FirebaseApp) ((param0) == null ? null : (param0.getGInstance()))))");
            com.google.firebase.dynamiclinks.FirebaseDynamicLinks gReturn = com.google.firebase.dynamiclinks.FirebaseDynamicLinks.getInstance(((com.google.firebase.FirebaseApp) ((param0) == null ? null : (param0.getGInstance()))));
            return ((gReturn) == null ? null : (new org.xms.f.dynamiclinks.ExtensionDynamicLinks.XImpl(new org.xms.g.utils.XBox(gReturn, null))));
        }
    }
    
    public static org.xms.f.dynamiclinks.ExtensionDynamicLinks dynamicCast(java.lang.Object param0) {
        return ((org.xms.f.dynamiclinks.ExtensionDynamicLinks) param0);
    }
    
    public static boolean isInstance(java.lang.Object param0) {
        if (!(param0 instanceof org.xms.g.utils.XGettable)) {
            return false;
        }
        if (org.xms.g.utils.GlobalEnvSetting.isHms()) {
            return ((org.xms.g.utils.XGettable) param0).getHInstance() instanceof com.huawei.agconnect.applinking.AGConnectAppLinking;
        } else {
            return ((org.xms.g.utils.XGettable) param0).getGInstance() instanceof com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
        }
    }
    
    public static class XImpl extends org.xms.f.dynamiclinks.ExtensionDynamicLinks {
        
        
        
        public XImpl(org.xms.g.utils.XBox param0) {
            super(param0);
        }
        
        public org.xms.f.dynamiclinks.DynamicLink.Builder createDynamicLink() {
            if (org.xms.g.utils.GlobalEnvSetting.isHms()) {
                
                org.xms.g.utils.XmsLog.d("XMSRouter", "new com.huawei.agconnect.applinking.AppLinking.Builder()");
                com.huawei.agconnect.applinking.AppLinking.Builder hReturn = new com.huawei.agconnect.applinking.AppLinking.Builder();
                return ((hReturn) == null ? null : (new org.xms.f.dynamiclinks.DynamicLink.Builder(new org.xms.g.utils.XBox(null, hReturn))));
            } else {
                org.xms.g.utils.XmsLog.d("XMSRouter", "((com.google.firebase.dynamiclinks.FirebaseDynamicLinks) this.getGInstance()).createDynamicLink()");
                com.google.firebase.dynamiclinks.DynamicLink.Builder gReturn = ((com.google.firebase.dynamiclinks.FirebaseDynamicLinks) this.getGInstance()).createDynamicLink();
                return ((gReturn) == null ? null : (new org.xms.f.dynamiclinks.DynamicLink.Builder(new org.xms.g.utils.XBox(gReturn, null))));
            }
        }
        
        public org.xms.g.tasks.Task<org.xms.f.dynamiclinks.PendingDynamicLinkData> getDynamicLink(android.net.Uri param0) {
            if (org.xms.g.utils.GlobalEnvSetting.isHms()) {
                
                org.xms.g.utils.XmsLog.d("XMSRouter", "((com.huawei.agconnect.applinking.AGConnectAppLinking) this.getHInstance()).getAppLinking(currentActivity, param0)");
                Activity currentActivity = ExtensionDynamicLinks.getCurrentActivity();
                if (currentActivity != null) {
                    com.huawei.hmf.tasks.Task hReturn = ((com.huawei.agconnect.applinking.AGConnectAppLinking) this.getHInstance()).getAppLinking(currentActivity, param0);
                    return ((hReturn) == null ? null : (new org.xms.g.tasks.Task.XImpl(new org.xms.g.utils.XBox(null, hReturn))));
                }
                return (new org.xms.g.tasks.Task.XImpl(new org.xms.g.utils.XBox(null, new TaskImpl())));
            } else {
                org.xms.g.utils.XmsLog.d("XMSRouter", "((com.google.firebase.dynamiclinks.FirebaseDynamicLinks) this.getGInstance()).getDynamicLink(param0)");
                com.google.android.gms.tasks.Task gReturn = ((com.google.firebase.dynamiclinks.FirebaseDynamicLinks) this.getGInstance()).getDynamicLink(param0);
                return ((gReturn) == null ? null : (new org.xms.g.tasks.Task.XImpl(new org.xms.g.utils.XBox(gReturn, null))));
            }
        }
        
        public org.xms.g.tasks.Task<org.xms.f.dynamiclinks.PendingDynamicLinkData> getDynamicLink(android.content.Intent param0) {
            if (org.xms.g.utils.GlobalEnvSetting.isHms()) {
                
                org.xms.g.utils.XmsLog.d("XMSRouter", "((com.huawei.agconnect.applinking.AGConnectAppLinking) this.getHInstance()).getAppLinking(currentActivity, param0)");
                Activity currentActivity = ExtensionDynamicLinks.getCurrentActivity();
                if (currentActivity != null) {
                    com.huawei.hmf.tasks.Task hReturn = ((com.huawei.agconnect.applinking.AGConnectAppLinking) this.getHInstance()).getAppLinking(currentActivity, param0);
                    return ((hReturn) == null ? null : (new org.xms.g.tasks.Task.XImpl(new org.xms.g.utils.XBox(null, hReturn))));
                }
                return (new org.xms.g.tasks.Task.XImpl(new org.xms.g.utils.XBox(null, new TaskImpl())));
            } else {
                org.xms.g.utils.XmsLog.d("XMSRouter", "((com.google.firebase.dynamiclinks.FirebaseDynamicLinks) this.getGInstance()).getDynamicLink(param0)");
                com.google.android.gms.tasks.Task gReturn = ((com.google.firebase.dynamiclinks.FirebaseDynamicLinks) this.getGInstance()).getDynamicLink(param0);
                return ((gReturn) == null ? null : (new org.xms.g.tasks.Task.XImpl(new org.xms.g.utils.XBox(gReturn, null))));
            }
        }
    }
}