package org.xms.f.remoteconfig;




public interface ExtensionRemoteConfigValue extends org.xms.g.utils.XInterface {
    
    
    
    public boolean asBoolean() throws java.lang.IllegalArgumentException;
    
    public byte[] asByteArray();
    
    public double asDouble() throws java.lang.IllegalArgumentException;
    
    public long asLong() throws java.lang.IllegalArgumentException;
    
    public java.lang.String asString();
    
    public int getSource();
    
    default java.lang.Object getZInstanceExtensionRemoteConfigValue() {
        if (org.xms.g.utils.GlobalEnvSetting.isHms()) {
            return getHInstanceExtensionRemoteConfigValue();
        } else {
            return getGInstanceExtensionRemoteConfigValue();
        }
    }
    
    default com.google.firebase.remoteconfig.FirebaseRemoteConfigValue getGInstanceExtensionRemoteConfigValue() {
        if (this instanceof org.xms.g.utils.XGettable) {
            return ((com.google.firebase.remoteconfig.FirebaseRemoteConfigValue) ((org.xms.g.utils.XGettable) this).getGInstance());
        }
        return new com.google.firebase.remoteconfig.FirebaseRemoteConfigValue() {
            
            public boolean asBoolean() throws java.lang.IllegalArgumentException {
                return org.xms.f.remoteconfig.ExtensionRemoteConfigValue.this.asBoolean();
            }
            
            public byte[] asByteArray() {
                return org.xms.f.remoteconfig.ExtensionRemoteConfigValue.this.asByteArray();
            }
            
            public double asDouble() throws java.lang.IllegalArgumentException {
                return org.xms.f.remoteconfig.ExtensionRemoteConfigValue.this.asDouble();
            }
            
            public long asLong() throws java.lang.IllegalArgumentException {
                return org.xms.f.remoteconfig.ExtensionRemoteConfigValue.this.asLong();
            }
            
            public java.lang.String asString() {
                return org.xms.f.remoteconfig.ExtensionRemoteConfigValue.this.asString();
            }
            
            public int getSource() {
                throw new java.lang.RuntimeException("Not Supported");
            }
        };
    }
    
    default java.lang.Object getHInstanceExtensionRemoteConfigValue() {
        if (this instanceof org.xms.g.utils.XGettable) {
            return ((java.lang.Object) ((org.xms.g.utils.XGettable) this).getHInstance());
        }
        return new java.lang.Object();
    }
    
    public static org.xms.f.remoteconfig.ExtensionRemoteConfigValue dynamicCast(java.lang.Object param0) {
        return ((org.xms.f.remoteconfig.ExtensionRemoteConfigValue) param0);
    }
    
    public static boolean isInstance(java.lang.Object param0) {
        if (!(param0 instanceof org.xms.g.utils.XInterface)) {
            return false;
        }
        if (param0 instanceof org.xms.g.utils.XGettable) {
            if (org.xms.g.utils.GlobalEnvSetting.isHms()) {
                
                throw new java.lang.RuntimeException("HMS does not support this API.");
            } else {
                return ((org.xms.g.utils.XGettable) param0).getGInstance() instanceof com.google.firebase.remoteconfig.FirebaseRemoteConfigValue;
            }
        }
        return param0 instanceof org.xms.f.remoteconfig.ExtensionRemoteConfigValue;
    }
    
    public static class XImpl extends org.xms.g.utils.XObject implements org.xms.f.remoteconfig.ExtensionRemoteConfigValue {
        
        
        
        public XImpl(org.xms.g.utils.XBox param0) {
            super(param0);
        }
        
        public boolean asBoolean() throws java.lang.IllegalArgumentException {
            if (org.xms.g.utils.GlobalEnvSetting.isHms()) {
                
                org.xms.g.utils.XmsLog.d("XMSRouter", "Boolean.parseBoolean(this.getHInstance().toString())");
                return Boolean.parseBoolean(this.getHInstance().toString());
            } else {
                org.xms.g.utils.XmsLog.d("XMSRouter", "((com.google.firebase.remoteconfig.FirebaseRemoteConfigValue) this.getGInstance()).asBoolean()");
                return ((com.google.firebase.remoteconfig.FirebaseRemoteConfigValue) this.getGInstance()).asBoolean();
            }
        }
        
        public byte[] asByteArray() {
            if (org.xms.g.utils.GlobalEnvSetting.isHms()) {
                
                org.xms.g.utils.XmsLog.d("XMSRouter", "new byte[]{Byte.parseByte(this.getHInstance().toString())}");
                return new byte[]{Byte.parseByte(this.getHInstance().toString())};
            } else {
                org.xms.g.utils.XmsLog.d("XMSRouter", "((com.google.firebase.remoteconfig.FirebaseRemoteConfigValue) this.getGInstance()).asByteArray()");
                return ((com.google.firebase.remoteconfig.FirebaseRemoteConfigValue) this.getGInstance()).asByteArray();
            }
        }
        
        public double asDouble() throws java.lang.IllegalArgumentException {
            if (org.xms.g.utils.GlobalEnvSetting.isHms()) {
                
                org.xms.g.utils.XmsLog.d("XMSRouter", "Double.parseDouble(this.getHInstance().toString())");
                return Double.parseDouble(this.getHInstance().toString());
            } else {
                org.xms.g.utils.XmsLog.d("XMSRouter", "((com.google.firebase.remoteconfig.FirebaseRemoteConfigValue) this.getGInstance()).asDouble()");
                return ((com.google.firebase.remoteconfig.FirebaseRemoteConfigValue) this.getGInstance()).asDouble();
            }
        }
        
        public long asLong() throws java.lang.IllegalArgumentException {
            if (org.xms.g.utils.GlobalEnvSetting.isHms()) {
                
                org.xms.g.utils.XmsLog.d("XMSRouter", "Long.parseLong(this.getHInstance().toString())");
                return Long.parseLong(this.getHInstance().toString());
            } else {
                org.xms.g.utils.XmsLog.d("XMSRouter", "((com.google.firebase.remoteconfig.FirebaseRemoteConfigValue) this.getGInstance()).asLong()");
                return ((com.google.firebase.remoteconfig.FirebaseRemoteConfigValue) this.getGInstance()).asLong();
            }
        }
        
        public java.lang.String asString() {
            if (org.xms.g.utils.GlobalEnvSetting.isHms()) {
                
                org.xms.g.utils.XmsLog.d("XMSRouter", "this.getHInstance().toString()");
                return this.getHInstance().toString();
            } else {
                org.xms.g.utils.XmsLog.d("XMSRouter", "((com.google.firebase.remoteconfig.FirebaseRemoteConfigValue) this.getGInstance()).asString()");
                return ((com.google.firebase.remoteconfig.FirebaseRemoteConfigValue) this.getGInstance()).asString();
            }
        }
        
        public int getSource() {
            throw new java.lang.RuntimeException("Not Supported");
        }
    }
}