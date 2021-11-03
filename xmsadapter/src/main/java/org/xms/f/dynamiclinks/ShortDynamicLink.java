package org.xms.f.dynamiclinks;




public interface ShortDynamicLink extends org.xms.g.utils.XInterface {
    
    
    
    public android.net.Uri getPreviewLink();
    
    public android.net.Uri getShortLink();
    
    public java.util.List<? extends org.xms.f.dynamiclinks.ShortDynamicLink.Warning> getWarnings();
    
    default java.lang.Object getZInstanceShortDynamicLink() {
        if (org.xms.g.utils.GlobalEnvSetting.isHms()) {
            return getHInstanceShortDynamicLink();
        } else {
            return getGInstanceShortDynamicLink();
        }
    }
    
    default com.google.firebase.dynamiclinks.ShortDynamicLink getGInstanceShortDynamicLink() {
        if (this instanceof org.xms.g.utils.XGettable) {
            return ((com.google.firebase.dynamiclinks.ShortDynamicLink) ((org.xms.g.utils.XGettable) this).getGInstance());
        }
        return new com.google.firebase.dynamiclinks.ShortDynamicLink() {
            
            public android.net.Uri getPreviewLink() {
                return org.xms.f.dynamiclinks.ShortDynamicLink.this.getPreviewLink();
            }
            
            public android.net.Uri getShortLink() {
                return org.xms.f.dynamiclinks.ShortDynamicLink.this.getShortLink();
            }
            
            public java.util.List<? extends com.google.firebase.dynamiclinks.ShortDynamicLink.Warning> getWarnings() {
                throw new java.lang.RuntimeException("Not Supported");
            }
        };
    }
    
    default com.huawei.agconnect.applinking.ShortAppLinking getHInstanceShortDynamicLink() {
        if (this instanceof org.xms.g.utils.XGettable) {
            return ((com.huawei.agconnect.applinking.ShortAppLinking) ((org.xms.g.utils.XGettable) this).getHInstance());
        }
        return new com.huawei.agconnect.applinking.ShortAppLinking(null, null);
    }
    
    public static org.xms.f.dynamiclinks.ShortDynamicLink dynamicCast(java.lang.Object param0) {
        return ((org.xms.f.dynamiclinks.ShortDynamicLink) param0);
    }
    
    public static boolean isInstance(java.lang.Object param0) {
        if (!(param0 instanceof org.xms.g.utils.XInterface)) {
            return false;
        }
        if (param0 instanceof org.xms.g.utils.XGettable) {
            if (org.xms.g.utils.GlobalEnvSetting.isHms()) {
                
                throw new java.lang.RuntimeException("AppGallery-connect does not support this API.");
            } else {
                return ((org.xms.g.utils.XGettable) param0).getGInstance() instanceof com.google.firebase.dynamiclinks.ShortDynamicLink;
            }
        }
        return param0 instanceof org.xms.f.dynamiclinks.ShortDynamicLink;
    }
    
    public static class XImpl extends org.xms.g.utils.XObject implements org.xms.f.dynamiclinks.ShortDynamicLink {
        
        
        
        public XImpl(org.xms.g.utils.XBox param0) {
            super(param0);
        }
        
        public android.net.Uri getPreviewLink() {
            if (org.xms.g.utils.GlobalEnvSetting.isHms()) {
                
                org.xms.g.utils.XmsLog.d("XMSRouter", "((com.huawei.agconnect.applinking.ShortAppLinking) this.getHInstance()).getTestUrl()");
                return ((com.huawei.agconnect.applinking.ShortAppLinking) this.getHInstance()).getTestUrl();
            } else {
                org.xms.g.utils.XmsLog.d("XMSRouter", "((com.google.firebase.dynamiclinks.ShortDynamicLink) this.getGInstance()).getPreviewLink()");
                return ((com.google.firebase.dynamiclinks.ShortDynamicLink) this.getGInstance()).getPreviewLink();
            }
        }
        
        public android.net.Uri getShortLink() {
            if (org.xms.g.utils.GlobalEnvSetting.isHms()) {
                
                org.xms.g.utils.XmsLog.d("XMSRouter", "((com.huawei.agconnect.applinking.ShortAppLinking) this.getHInstance()).getShortUrl()");
                return ((com.huawei.agconnect.applinking.ShortAppLinking) this.getHInstance()).getShortUrl();
            } else {
                org.xms.g.utils.XmsLog.d("XMSRouter", "((com.google.firebase.dynamiclinks.ShortDynamicLink) this.getGInstance()).getShortLink()");
                return ((com.google.firebase.dynamiclinks.ShortDynamicLink) this.getGInstance()).getShortLink();
            }
        }
        
        public java.util.List<? extends org.xms.f.dynamiclinks.ShortDynamicLink.Warning> getWarnings() {
            throw new java.lang.RuntimeException("Not Supported");
        }
    }
    
    public static interface Suffix extends org.xms.g.utils.XInterface, java.lang.annotation.Annotation {
        
        interface XSuffix {
            int SHORT = 2;
            int UNGUESSABLE = 1;
        }
        
        public static int getSHORT() {
            if (org.xms.g.utils.GlobalEnvSetting.isHms()) {
                
                return XSuffix.SHORT;
            } else {
                org.xms.g.utils.XmsLog.d("XMSRouter", "com.google.firebase.dynamiclinks.ShortDynamicLink.Suffix.SHORT");
                return com.google.firebase.dynamiclinks.ShortDynamicLink.Suffix.SHORT;
            }
        }
        
        public static int getUNGUESSABLE() {
            if (org.xms.g.utils.GlobalEnvSetting.isHms()) {
                
                return XSuffix.UNGUESSABLE;
            } else {
                org.xms.g.utils.XmsLog.d("XMSRouter", "com.google.firebase.dynamiclinks.ShortDynamicLink.Suffix.UNGUESSABLE");
                return com.google.firebase.dynamiclinks.ShortDynamicLink.Suffix.UNGUESSABLE;
            }
        }
        
        default java.lang.Object getZInstanceSuffix() {
            if (org.xms.g.utils.GlobalEnvSetting.isHms()) {
                return getHInstanceSuffix();
            } else {
                return getGInstanceSuffix();
            }
        }
        
        default com.google.firebase.dynamiclinks.ShortDynamicLink.Suffix getGInstanceSuffix() {
            if (this instanceof org.xms.g.utils.XGettable) {
                return ((com.google.firebase.dynamiclinks.ShortDynamicLink.Suffix) ((org.xms.g.utils.XGettable) this).getGInstance());
            }
            return new com.google.firebase.dynamiclinks.ShortDynamicLink.Suffix() {
                
                public boolean equals(java.lang.Object param0) {
                    throw new java.lang.RuntimeException("Not Supported");
                }
                
                public int hashCode() {
                    throw new java.lang.RuntimeException("Not Supported");
                }
                
                public java.lang.String toString() {
                    throw new java.lang.RuntimeException("Not Supported");
                }
                
                public java.lang.Class<? extends java.lang.annotation.Annotation> annotationType() {
                    throw new java.lang.RuntimeException("Not Supported");
                }
            };
        }
        
        default java.lang.Object getHInstanceSuffix() {
            if (this instanceof org.xms.g.utils.XGettable) {
                return ((java.lang.Object) ((org.xms.g.utils.XGettable) this).getHInstance());
            }
            return new java.lang.Object();
        }
        
        public static org.xms.f.dynamiclinks.ShortDynamicLink.Suffix dynamicCast(java.lang.Object param0) {
            return ((org.xms.f.dynamiclinks.ShortDynamicLink.Suffix) param0);
        }
        
        public static boolean isInstance(java.lang.Object param0) {
            if (!(param0 instanceof org.xms.g.utils.XInterface)) {
                return false;
            }
            if (param0 instanceof org.xms.g.utils.XGettable) {
                if (org.xms.g.utils.GlobalEnvSetting.isHms()) {
                    
                    throw new java.lang.RuntimeException("AppGallery-connect does not support this API.");
                } else {
                    return ((org.xms.g.utils.XGettable) param0).getGInstance() instanceof com.google.firebase.dynamiclinks.ShortDynamicLink.Suffix;
                }
            }
            return param0 instanceof org.xms.f.dynamiclinks.ShortDynamicLink.Suffix;
        }
        
        public static class XImpl extends org.xms.g.utils.XObject implements org.xms.f.dynamiclinks.ShortDynamicLink.Suffix {
            
            public XImpl(org.xms.g.utils.XBox param0) {
                super(param0);
            }
            
            public boolean equals(java.lang.Object param0) {
                throw new java.lang.RuntimeException("Not Supported");
            }
            
            public int hashCode() {
                throw new java.lang.RuntimeException("Not Supported");
            }
            
            public java.lang.String toString() {
                throw new java.lang.RuntimeException("Not Supported");
            }
            
            public java.lang.Class<? extends java.lang.annotation.Annotation> annotationType() {
                throw new java.lang.RuntimeException("Not Supported");
            }
        }
    }
    
    public static interface Warning extends org.xms.g.utils.XInterface {
        
        public java.lang.String getCode();
        
        public java.lang.String getMessage();
        
        default java.lang.Object getZInstanceWarning() {
            throw new java.lang.RuntimeException("Not Supported");
        }
        
        default com.google.firebase.dynamiclinks.ShortDynamicLink.Warning getGInstanceWarning() {
            throw new java.lang.RuntimeException("Not Supported");
        }
        
        default java.lang.Object getHInstanceWarning() {
            throw new java.lang.RuntimeException("Not Supported");
        }
        
        public static org.xms.f.dynamiclinks.ShortDynamicLink.Warning dynamicCast(java.lang.Object param0) {
            throw new java.lang.RuntimeException("Not Supported");
        }
        
        public static boolean isInstance(java.lang.Object param0) {
            throw new java.lang.RuntimeException("Not Supported");
        }
        
        public static class XImpl extends org.xms.g.utils.XObject implements org.xms.f.dynamiclinks.ShortDynamicLink.Warning {
            
            public XImpl(org.xms.g.utils.XBox param0) {
                super(param0);
            }
            
            public java.lang.String getCode() {
                throw new java.lang.RuntimeException("Not Supported");
            }
            
            public java.lang.String getMessage() {
                throw new java.lang.RuntimeException("Not Supported");
            }
        }
    }
}