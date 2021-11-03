package org.xms.f.remoteconfig;




public interface ExtensionRemoteConfigInfo extends org.xms.g.utils.XInterface {
    
    
    
    public org.xms.f.remoteconfig.ExtensionRemoteConfigSettings getConfigSettings();
    
    public long getFetchTimeMillis();
    
    public int getLastFetchStatus();
    
    default java.lang.Object getZInstanceExtensionRemoteConfigInfo() {
        if (org.xms.g.utils.GlobalEnvSetting.isHms()) {
            return getHInstanceExtensionRemoteConfigInfo();
        } else {
            return getGInstanceExtensionRemoteConfigInfo();
        }
    }
    
    default com.google.firebase.remoteconfig.FirebaseRemoteConfigInfo getGInstanceExtensionRemoteConfigInfo() {
        if (this instanceof org.xms.g.utils.XGettable) {
            return ((com.google.firebase.remoteconfig.FirebaseRemoteConfigInfo) ((org.xms.g.utils.XGettable) this).getGInstance());
        }
        return new com.google.firebase.remoteconfig.FirebaseRemoteConfigInfo() {
            
            public com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings getConfigSettings() {
                org.xms.f.remoteconfig.ExtensionRemoteConfigSettings xResult = org.xms.f.remoteconfig.ExtensionRemoteConfigInfo.this.getConfigSettings();
                return ((com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings) ((xResult) == null ? null : (xResult.getGInstance())));
            }
            
            public long getFetchTimeMillis() {
                return org.xms.f.remoteconfig.ExtensionRemoteConfigInfo.this.getFetchTimeMillis();
            }
            
            public int getLastFetchStatus() {
                return org.xms.f.remoteconfig.ExtensionRemoteConfigInfo.this.getLastFetchStatus();
            }
        };
    }
    
    default java.lang.Object getHInstanceExtensionRemoteConfigInfo() {
        if (this instanceof org.xms.g.utils.XGettable) {
            return ((java.lang.Object) ((org.xms.g.utils.XGettable) this).getHInstance());
        }
        return new java.lang.Object();
    }
    
    public static org.xms.f.remoteconfig.ExtensionRemoteConfigInfo dynamicCast(java.lang.Object param0) {
        return ((org.xms.f.remoteconfig.ExtensionRemoteConfigInfo) param0);
    }
    
    public static boolean isInstance(java.lang.Object param0) {
        if (!(param0 instanceof org.xms.g.utils.XInterface)) {
            return false;
        }
        if (param0 instanceof org.xms.g.utils.XGettable) {
            if (org.xms.g.utils.GlobalEnvSetting.isHms()) {
                
                throw new java.lang.RuntimeException("HMS does not support this API.");
            } else {
                return ((org.xms.g.utils.XGettable) param0).getGInstance() instanceof com.google.firebase.remoteconfig.FirebaseRemoteConfigInfo;
            }
        }
        return param0 instanceof org.xms.f.remoteconfig.ExtensionRemoteConfigInfo;
    }
    
    public static class XImpl extends org.xms.g.utils.XObject implements org.xms.f.remoteconfig.ExtensionRemoteConfigInfo {
        
        private boolean developerModeEnabled = false;
        public boolean getdeveloperModeEnabled() {
            return this.developerModeEnabled;
        }
        public org.xms.f.remoteconfig.ExtensionRemoteConfigInfo.XImpl setDeveloperMode(boolean developerModeEnabled) {
            this.developerModeEnabled = developerModeEnabled;
            return this;
        }
        
        public XImpl(org.xms.g.utils.XBox param0) {
            super(param0);
        }
        
        public org.xms.f.remoteconfig.ExtensionRemoteConfigSettings getConfigSettings() {
            if (org.xms.g.utils.GlobalEnvSetting.isHms()) {
                
                org.xms.g.utils.XmsLog.d("XMSRouter", "org.xms.f.remoteconfig.ExtensionRemoteConfigInfo.XImpl.getConfigSettings");
                return new org.xms.f.remoteconfig.ExtensionRemoteConfigSettings(new org.xms.g.utils.XBox(null, null)).setDeveloperMode(this.getdeveloperModeEnabled());
            } else {
                org.xms.g.utils.XmsLog.d("XMSRouter", "((com.google.firebase.remoteconfig.FirebaseRemoteConfigInfo) this.getGInstance()).getConfigSettings()");
                com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings gReturn = ((com.google.firebase.remoteconfig.FirebaseRemoteConfigInfo) this.getGInstance()).getConfigSettings();
                return ((gReturn) == null ? null : (new org.xms.f.remoteconfig.ExtensionRemoteConfigSettings(new org.xms.g.utils.XBox(gReturn, null))));
            }
        }
        
        public long getFetchTimeMillis() {
            if (org.xms.g.utils.GlobalEnvSetting.isHms()) {
                
                org.xms.g.utils.XmsLog.d("XMSRouter", "org.xms.f.remoteconfig.ExtensionRemoteConfigInfo.XImpl.getFetchTimeMillis");
                return 0;
            } else {
                org.xms.g.utils.XmsLog.d("XMSRouter", "((com.google.firebase.remoteconfig.FirebaseRemoteConfigInfo) this.getGInstance()).getFetchTimeMillis()");
                return ((com.google.firebase.remoteconfig.FirebaseRemoteConfigInfo) this.getGInstance()).getFetchTimeMillis();
            }
        }
        
        public int getLastFetchStatus() {
            if (org.xms.g.utils.GlobalEnvSetting.isHms()) {
                
                org.xms.g.utils.XmsLog.d("XMSRouter", "org.xms.f.remoteconfig.ExtensionRemoteConfigInfo.XImpl.getLastFetchStatus");
                return 0;
            } else {
                org.xms.g.utils.XmsLog.d("XMSRouter", "((com.google.firebase.remoteconfig.FirebaseRemoteConfigInfo) this.getGInstance()).getLastFetchStatus()");
                return ((com.google.firebase.remoteconfig.FirebaseRemoteConfigInfo) this.getGInstance()).getLastFetchStatus();
            }
        }
    }
}