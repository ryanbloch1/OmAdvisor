package org.xms.f.remoteconfig;




public class ExtensionRemoteConfigServerException extends org.xms.f.remoteconfig.ExtensionRemoteConfigException {
    private boolean wrapper = true;
    
    
    
    public ExtensionRemoteConfigServerException(org.xms.g.utils.XBox param0) {
        super(param0);
        wrapper = true;
    }
    
    public ExtensionRemoteConfigServerException(int param0, java.lang.String param1) {
        super(((org.xms.g.utils.XBox) null));
        if (org.xms.g.utils.GlobalEnvSetting.isHms()) {
            
            this.setHInstance(new HImpl(param1, param0));
        } else {
            this.setGInstance(new GImpl(param0, param1));
        }
        wrapper = false;
    }
    
    public ExtensionRemoteConfigServerException(int param0, java.lang.String param1, java.lang.Throwable param2) {
        super(((org.xms.g.utils.XBox) null));
        if (org.xms.g.utils.GlobalEnvSetting.isHms()) {
            
            this.setHInstance(new HImpl(param1 + param2.getMessage(), param0));
        } else {
            this.setGInstance(new GImpl(param0, param1, param2));
        }
        wrapper = false;
    }
    
    public int getHttpStatusCode() {
        if (wrapper) {
            if (org.xms.g.utils.GlobalEnvSetting.isHms()) {
                org.xms.g.utils.XmsLog.d("XMSRouter", "((com.huawei.agconnect.remoteconfig.AGCConfigException) this.getHInstance()).getCode()");
                return ((com.huawei.agconnect.remoteconfig.AGCConfigException) this.getHInstance()).getCode();
            } else {
                org.xms.g.utils.XmsLog.d("XMSRouter", "((com.google.firebase.remoteconfig.FirebaseRemoteConfigServerException) this.getGInstance()).getHttpStatusCode()");
                return ((com.google.firebase.remoteconfig.FirebaseRemoteConfigServerException) this.getGInstance()).getHttpStatusCode();
            }
        } else {
            if (org.xms.g.utils.GlobalEnvSetting.isHms()) {
                org.xms.g.utils.XmsLog.d("XMSRouter", "((HImpl) ((com.huawei.agconnect.remoteconfig.AGCConfigException) this.getHInstance())).getCodeCallSuper()");
                return ((HImpl) ((com.huawei.agconnect.remoteconfig.AGCConfigException) this.getHInstance())).getCodeCallSuper();
            } else {
                org.xms.g.utils.XmsLog.d("XMSRouter", "((GImpl) ((com.google.firebase.remoteconfig.FirebaseRemoteConfigServerException) this.getGInstance())).getHttpStatusCodeCallSuper()");
                return ((GImpl) ((com.google.firebase.remoteconfig.FirebaseRemoteConfigServerException) this.getGInstance())).getHttpStatusCodeCallSuper();
            }
        }
    }
    
    public static org.xms.f.remoteconfig.ExtensionRemoteConfigServerException dynamicCast(java.lang.Object param0) {
        return ((org.xms.f.remoteconfig.ExtensionRemoteConfigServerException) param0);
    }
    
    public static boolean isInstance(java.lang.Object param0) {
        if (!(param0 instanceof org.xms.g.utils.XGettable)) {
            return false;
        }
        if (org.xms.g.utils.GlobalEnvSetting.isHms()) {
            return ((org.xms.g.utils.XGettable) param0).getHInstance() instanceof com.huawei.agconnect.remoteconfig.AGCConfigException;
        } else {
            return ((org.xms.g.utils.XGettable) param0).getGInstance() instanceof com.google.firebase.remoteconfig.FirebaseRemoteConfigServerException;
        }
    }
    
    private class GImpl extends com.google.firebase.remoteconfig.FirebaseRemoteConfigServerException {
        
        public int getHttpStatusCode() {
            return org.xms.f.remoteconfig.ExtensionRemoteConfigServerException.this.getHttpStatusCode();
        }
        
        public int getHttpStatusCodeCallSuper() {
            return super.getHttpStatusCode();
        }
        
        public GImpl(int param0, java.lang.String param1) {
            super(param0, param1);
        }
        
        public GImpl(int param0, java.lang.String param1, java.lang.Throwable param2) {
            super(param0, param1, param2);
        }
    }
    
    private class HImpl extends com.huawei.agconnect.remoteconfig.AGCConfigException {
        
        
        
        public int getCode() {
            return org.xms.f.remoteconfig.ExtensionRemoteConfigServerException.this.getHttpStatusCode();
        }
        
        public int getCodeCallSuper() {
            return super.getCode();
        }
        
        public HImpl(java.lang.String param0, int param1) {
            super(param0, param1);
        }
    }
}