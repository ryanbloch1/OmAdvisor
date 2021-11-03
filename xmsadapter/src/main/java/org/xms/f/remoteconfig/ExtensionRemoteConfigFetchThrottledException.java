package org.xms.f.remoteconfig;




public class ExtensionRemoteConfigFetchThrottledException extends org.xms.f.remoteconfig.ExtensionRemoteConfigFetchException {
    private boolean wrapper = true;
    
    
    
    public ExtensionRemoteConfigFetchThrottledException(org.xms.g.utils.XBox param0) {
        super(param0);
        wrapper = true;
    }
    
    public ExtensionRemoteConfigFetchThrottledException(long param0) {
        super(((org.xms.g.utils.XBox) null));
        if (org.xms.g.utils.GlobalEnvSetting.isHms()) {
            
            this.setHInstance(new com.huawei.agconnect.remoteconfig.AGCConfigException(null,
                    com.huawei.agconnect.remoteconfig.AGCConfigException.FETCH_THROTTLED, param0));
        } else {
            this.setGInstance(new GImpl(param0));
        }
        wrapper = false;
    }
    
    public long getThrottleEndTimeMillis() {
        if (wrapper) {
            if (org.xms.g.utils.GlobalEnvSetting.isHms()) {
                
                org.xms.g.utils.XmsLog.d("XMSRouter", "org.xms.f.remoteconfig.ExtensionRemoteConfigFetchThrottledException.getThrottleEndTimeMillis");
                return 0;
            } else {
                org.xms.g.utils.XmsLog.d("XMSRouter", "((com.google.firebase.remoteconfig.FirebaseRemoteConfigFetchThrottledException) this.getGInstance()).getThrottleEndTimeMillis()");
                return ((com.google.firebase.remoteconfig.FirebaseRemoteConfigFetchThrottledException) this.getGInstance()).getThrottleEndTimeMillis();
            }
        } else {
            if (org.xms.g.utils.GlobalEnvSetting.isHms()) {
                
                org.xms.g.utils.XmsLog.d("XMSRouter", "((com.huawei.agconnect.remoteconfig.AGCConfigException) this.getHInstance()).getThrottleEndTimeMillis();");
                return ((com.huawei.agconnect.remoteconfig.AGCConfigException) this.getHInstance()).getThrottleEndTimeMillis();
            } else {
                org.xms.g.utils.XmsLog.d("XMSRouter", "((GImpl) ((com.google.firebase.remoteconfig.FirebaseRemoteConfigFetchThrottledException) this.getGInstance())).getThrottleEndTimeMillisCallSuper()");
                return ((GImpl) ((com.google.firebase.remoteconfig.FirebaseRemoteConfigFetchThrottledException) this.getGInstance())).getThrottleEndTimeMillisCallSuper();
            }
        }
    }
    
    public static org.xms.f.remoteconfig.ExtensionRemoteConfigFetchThrottledException dynamicCast(java.lang.Object param0) {
        return ((org.xms.f.remoteconfig.ExtensionRemoteConfigFetchThrottledException) param0);
    }
    
    public static boolean isInstance(java.lang.Object param0) {
        if (!(param0 instanceof org.xms.g.utils.XGettable)) {
            return false;
        }
        if (org.xms.g.utils.GlobalEnvSetting.isHms()) {
            
            throw new java.lang.RuntimeException("HMS does not support this API.");
        } else {
            return ((org.xms.g.utils.XGettable) param0).getGInstance() instanceof com.google.firebase.remoteconfig.FirebaseRemoteConfigFetchThrottledException;
        }
    }
    
    private class GImpl extends com.google.firebase.remoteconfig.FirebaseRemoteConfigFetchThrottledException {
        
        public long getThrottleEndTimeMillis() {
            return org.xms.f.remoteconfig.ExtensionRemoteConfigFetchThrottledException.this.getThrottleEndTimeMillis();
        }
        
        public long getThrottleEndTimeMillisCallSuper() {
            return super.getThrottleEndTimeMillis();
        }
        
        public GImpl(long param0) {
            super(param0);
        }
    }
}