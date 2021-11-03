package org.xms.f.remoteconfig;




public class ExtensionRemoteConfigClientException extends org.xms.f.remoteconfig.ExtensionRemoteConfigException {
    private boolean wrapper = true;
    
    
    
    public ExtensionRemoteConfigClientException(org.xms.g.utils.XBox param0) {
        super(param0);
        wrapper = true;
    }
    
    public ExtensionRemoteConfigClientException(java.lang.String param0) {
        super(((org.xms.g.utils.XBox) null));
        if (org.xms.g.utils.GlobalEnvSetting.isHms()) {
            
            this.setHInstance(new com.huawei.agconnect.remoteconfig.AGCConfigException(param0,
                    com.huawei.agconnect.remoteconfig.AGCConfigException.UNKNOWN_ERROR));
        } else {
            this.setGInstance(new GImpl(param0));
        }
        wrapper = false;
    }
    
    public ExtensionRemoteConfigClientException(java.lang.String param0, java.lang.Throwable param1) {
        super(((org.xms.g.utils.XBox) null));
        if (org.xms.g.utils.GlobalEnvSetting.isHms()) {
            
            this.setHInstance(new com.huawei.agconnect.remoteconfig.AGCConfigException(param0 + param1.getMessage(),
                    com.huawei.agconnect.remoteconfig.AGCConfigException.UNKNOWN_ERROR));
        } else {
            this.setGInstance(new GImpl(param0, param1));
        }
        wrapper = false;
    }
    
    public static org.xms.f.remoteconfig.ExtensionRemoteConfigClientException dynamicCast(java.lang.Object param0) {
        return ((org.xms.f.remoteconfig.ExtensionRemoteConfigClientException) param0);
    }
    
    public static boolean isInstance(java.lang.Object param0) {
        if (!(param0 instanceof org.xms.g.utils.XGettable)) {
            return false;
        }
        if (org.xms.g.utils.GlobalEnvSetting.isHms()) {
            
            throw new java.lang.RuntimeException("HMS does not support this API.");
        } else {
            return ((org.xms.g.utils.XGettable) param0).getGInstance() instanceof com.google.firebase.remoteconfig.FirebaseRemoteConfigClientException;
        }
    }
    
    private class GImpl extends com.google.firebase.remoteconfig.FirebaseRemoteConfigClientException {
        
        public GImpl(java.lang.String param0) {
            super(param0);
        }
        
        public GImpl(java.lang.String param0, java.lang.Throwable param1) {
            super(param0, param1);
        }
    }
}