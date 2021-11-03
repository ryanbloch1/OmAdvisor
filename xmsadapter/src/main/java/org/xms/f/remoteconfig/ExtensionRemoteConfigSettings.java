package org.xms.f.remoteconfig;




public class ExtensionRemoteConfigSettings extends org.xms.g.utils.XObject {
    
    private boolean developerModeEnabled = false;
    public boolean getdeveloperModeEnabled() {
        return this.developerModeEnabled;
    }
    public org.xms.f.remoteconfig.ExtensionRemoteConfigSettings setDeveloperMode(boolean developerModeEnabled) {
        this.developerModeEnabled = developerModeEnabled;
        return this;
    }
    
    public ExtensionRemoteConfigSettings(org.xms.g.utils.XBox param0) {
        super(param0);
    }
    
    public long getFetchTimeoutInSeconds() {
        if (org.xms.g.utils.GlobalEnvSetting.isHms()) {
            
            org.xms.g.utils.XmsLog.d("XMSRouter", "org.xms.f.remoteconfig.ExtensionRemoteConfigSettings.getFetchTimeoutInSeconds");
            return 0;
        } else {
            org.xms.g.utils.XmsLog.d("XMSRouter", "((com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings) this.getGInstance()).getFetchTimeoutInSeconds()");
            return ((com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings) this.getGInstance()).getFetchTimeoutInSeconds();
        }
    }
    
    public long getMinimumFetchIntervalInSeconds() {
        if (org.xms.g.utils.GlobalEnvSetting.isHms()) {
            
            org.xms.g.utils.XmsLog.d("XMSRouter", "org.xms.f.remoteconfig.ExtensionRemoteConfigSettings.getMinimumFetchIntervalInSeconds");
            return 0;
        } else {
            org.xms.g.utils.XmsLog.d("XMSRouter", "((com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings) this.getGInstance()).getMinimumFetchIntervalInSeconds()");
            return ((com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings) this.getGInstance()).getMinimumFetchIntervalInSeconds();
        }
    }
    
    public boolean isDeveloperModeEnabled() {
        if (org.xms.g.utils.GlobalEnvSetting.isHms()) {
            
            return this.developerModeEnabled;
        } else {
            org.xms.g.utils.XmsLog.d("XMSRouter", "((com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings) this.getGInstance()).isDeveloperModeEnabled()");
            return ((com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings) this.getGInstance()).isDeveloperModeEnabled();
        }
    }
    
    public org.xms.f.remoteconfig.ExtensionRemoteConfigSettings.Builder toBuilder() {
        if (org.xms.g.utils.GlobalEnvSetting.isHms()) {
            
            org.xms.g.utils.XmsLog.d("XMSRouter", "org.xms.f.remoteconfig.ExtensionRemoteConfigSettings.toBuilder");
            return new org.xms.f.remoteconfig.ExtensionRemoteConfigSettings.Builder(new org.xms.g.utils.XBox(null, null)).setDeveloperMode(this.getdeveloperModeEnabled());
        } else {
            org.xms.g.utils.XmsLog.d("XMSRouter", "((com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings) this.getGInstance()).toBuilder()");
            com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings.Builder gReturn = ((com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings) this.getGInstance()).toBuilder();
            return ((gReturn) == null ? null : (new org.xms.f.remoteconfig.ExtensionRemoteConfigSettings.Builder(new org.xms.g.utils.XBox(gReturn, null))));
        }
    }
    
    public static org.xms.f.remoteconfig.ExtensionRemoteConfigSettings dynamicCast(java.lang.Object param0) {
        return ((org.xms.f.remoteconfig.ExtensionRemoteConfigSettings) param0);
    }
    
    public static boolean isInstance(java.lang.Object param0) {
        if (!(param0 instanceof org.xms.g.utils.XGettable)) {
            return false;
        }
        if (org.xms.g.utils.GlobalEnvSetting.isHms()) {
            
            throw new java.lang.RuntimeException("HMS does not support this API.");
        } else {
            return ((org.xms.g.utils.XGettable) param0).getGInstance() instanceof com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
        }
    }
    
    public static class Builder extends org.xms.g.utils.XObject {
        
        private boolean developerModeEnabled = false;
        public boolean getdeveloperModeEnabled() {
            return this.developerModeEnabled;
        }
        public org.xms.f.remoteconfig.ExtensionRemoteConfigSettings.Builder setDeveloperMode(boolean developerModeEnabled) {
            this.developerModeEnabled = developerModeEnabled;
            return this;
        }
        
        public Builder(org.xms.g.utils.XBox param0) {
            super(param0);
        }
        
        public Builder() {
            super(((org.xms.g.utils.XBox) null));
            if (org.xms.g.utils.GlobalEnvSetting.isHms()) {
                
                this.setHInstance(null);
            } else {
                this.setGInstance(new com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings.Builder());
            }
        }
        
        public org.xms.f.remoteconfig.ExtensionRemoteConfigSettings build() {
            if (org.xms.g.utils.GlobalEnvSetting.isHms()) {
                
                org.xms.g.utils.XmsLog.d("XMSRouter", "org.xms.f.remoteconfig.ExtensionRemoteConfigSettings.Builder.build");
                return new org.xms.f.remoteconfig.ExtensionRemoteConfigSettings(new org.xms.g.utils.XBox(null, null)).setDeveloperMode(this.getdeveloperModeEnabled());
            } else {
                org.xms.g.utils.XmsLog.d("XMSRouter", "((com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings.Builder) this.getGInstance()).build()");
                com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings gReturn = ((com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings.Builder) this.getGInstance()).build();
                return ((gReturn) == null ? null : (new org.xms.f.remoteconfig.ExtensionRemoteConfigSettings(new org.xms.g.utils.XBox(gReturn, null))));
            }
        }
        
        public long getFetchTimeoutInSeconds() {
            if (org.xms.g.utils.GlobalEnvSetting.isHms()) {
                
                org.xms.g.utils.XmsLog.d("XMSRouter", "org.xms.f.remoteconfig.ExtensionRemoteConfigSettings.Builder.getFetchTimeoutInSeconds");
                return 0;
            } else {
                org.xms.g.utils.XmsLog.d("XMSRouter", "((com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings.Builder) this.getGInstance()).getFetchTimeoutInSeconds()");
                return ((com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings.Builder) this.getGInstance()).getFetchTimeoutInSeconds();
            }
        }
        
        public long getMinimumFetchIntervalInSeconds() {
            if (org.xms.g.utils.GlobalEnvSetting.isHms()) {
                
                org.xms.g.utils.XmsLog.d("XMSRouter", "org.xms.f.remoteconfig.ExtensionRemoteConfigSettings.Builder.getMinimumFetchIntervalInSeconds");
                return 0;
            } else {
                org.xms.g.utils.XmsLog.d("XMSRouter", "((com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings.Builder) this.getGInstance()).getMinimumFetchIntervalInSeconds()");
                return ((com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings.Builder) this.getGInstance()).getMinimumFetchIntervalInSeconds();
            }
        }
        
        public org.xms.f.remoteconfig.ExtensionRemoteConfigSettings.Builder setDeveloperModeEnabled(boolean param0) {
            if (org.xms.g.utils.GlobalEnvSetting.isHms()) {
                
                org.xms.g.utils.XmsLog.d("XMSRouter", "org.xms.f.remoteconfig.ExtensionRemoteConfigSettings.Builder.setDeveloperModeEnabled");
                return this.setDeveloperMode(param0);
            } else {
                org.xms.g.utils.XmsLog.d("XMSRouter", "((com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings.Builder) this.getGInstance()).setDeveloperModeEnabled(param0)");
                com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings.Builder gReturn = ((com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings.Builder) this.getGInstance()).setDeveloperModeEnabled(param0);
                return ((gReturn) == null ? null : (new org.xms.f.remoteconfig.ExtensionRemoteConfigSettings.Builder(new org.xms.g.utils.XBox(gReturn, null))));
            }
        }
        
        public org.xms.f.remoteconfig.ExtensionRemoteConfigSettings.Builder setFetchTimeoutInSeconds(long param0) throws java.lang.IllegalArgumentException {
            if (org.xms.g.utils.GlobalEnvSetting.isHms()) {
                
                org.xms.g.utils.XmsLog.d("XMSRouter", "org.xms.f.remoteconfig.ExtensionRemoteConfigSettings.Builder.setFetchTimeoutInSeconds");
                return this;
            } else {
                org.xms.g.utils.XmsLog.d("XMSRouter", "((com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings.Builder) this.getGInstance()).setFetchTimeoutInSeconds(param0)");
                com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings.Builder gReturn = ((com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings.Builder) this.getGInstance()).setFetchTimeoutInSeconds(param0);
                return ((gReturn) == null ? null : (new org.xms.f.remoteconfig.ExtensionRemoteConfigSettings.Builder(new org.xms.g.utils.XBox(gReturn, null))));
            }
        }
        
        public org.xms.f.remoteconfig.ExtensionRemoteConfigSettings.Builder setMinimumFetchIntervalInSeconds(long param0) {
            if (org.xms.g.utils.GlobalEnvSetting.isHms()) {
                
                org.xms.g.utils.XmsLog.d("XMSRouter", "org.xms.f.remoteconfig.ExtensionRemoteConfigSettings.Builder.setMinimumFetchIntervalInSeconds");
                return this;
            } else {
                org.xms.g.utils.XmsLog.d("XMSRouter", "((com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings.Builder) this.getGInstance()).setMinimumFetchIntervalInSeconds(param0)");
                com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings.Builder gReturn = ((com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings.Builder) this.getGInstance()).setMinimumFetchIntervalInSeconds(param0);
                return ((gReturn) == null ? null : (new org.xms.f.remoteconfig.ExtensionRemoteConfigSettings.Builder(new org.xms.g.utils.XBox(gReturn, null))));
            }
        }
        
        public static org.xms.f.remoteconfig.ExtensionRemoteConfigSettings.Builder dynamicCast(java.lang.Object param0) {
            return ((org.xms.f.remoteconfig.ExtensionRemoteConfigSettings.Builder) param0);
        }
        
        public static boolean isInstance(java.lang.Object param0) {
            if (!(param0 instanceof org.xms.g.utils.XGettable)) {
                return false;
            }
            if (org.xms.g.utils.GlobalEnvSetting.isHms()) {
                
                throw new java.lang.RuntimeException("HMS does not support this API.");
            } else {
                return ((org.xms.g.utils.XGettable) param0).getGInstance() instanceof com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings.Builder;
            }
        }
    }
}