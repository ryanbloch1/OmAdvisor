package org.xms.f.dynamiclinks;

public final class DynamicLink extends org.xms.g.utils.XObject {
    
    public DynamicLink(org.xms.g.utils.XBox param0) {
        super(param0);
    }
    
    public final android.net.Uri getUri() {
        if (org.xms.g.utils.GlobalEnvSetting.isHms()) {
            org.xms.g.utils.XmsLog.d("XMSRouter", "((com.huawei.agconnect.applinking.AppLinking) this.getHInstance()).getUri()");
            return ((com.huawei.agconnect.applinking.AppLinking) this.getHInstance()).getUri();
        } else {
            org.xms.g.utils.XmsLog.d("XMSRouter", "((com.google.firebase.dynamiclinks.DynamicLink) this.getGInstance()).getUri()");
            return ((com.google.firebase.dynamiclinks.DynamicLink) this.getGInstance()).getUri();
        }
    }
    
    public static org.xms.f.dynamiclinks.DynamicLink dynamicCast(java.lang.Object param0) {
        return ((org.xms.f.dynamiclinks.DynamicLink) param0);
    }
    
    public static boolean isInstance(java.lang.Object param0) {
        if (!(param0 instanceof org.xms.g.utils.XGettable)) {
            return false;
        }
        if (org.xms.g.utils.GlobalEnvSetting.isHms()) {
            return ((org.xms.g.utils.XGettable) param0).getHInstance() instanceof com.huawei.agconnect.applinking.AppLinking;
        } else {
            return ((org.xms.g.utils.XGettable) param0).getGInstance() instanceof com.google.firebase.dynamiclinks.DynamicLink;
        }
    }
    
    public static final class AndroidParameters extends org.xms.g.utils.XObject {
        
        public AndroidParameters(org.xms.g.utils.XBox param0) {
            super(param0);
        }
        
        public static org.xms.f.dynamiclinks.DynamicLink.AndroidParameters dynamicCast(java.lang.Object param0) {
            return ((org.xms.f.dynamiclinks.DynamicLink.AndroidParameters) param0);
        }
        
        public static boolean isInstance(java.lang.Object param0) {
            if (!(param0 instanceof org.xms.g.utils.XGettable)) {
                return false;
            }
            if (org.xms.g.utils.GlobalEnvSetting.isHms()) {
                return ((org.xms.g.utils.XGettable) param0).getHInstance() instanceof com.huawei.agconnect.applinking.AppLinking.AndroidLinkInfo;
            } else {
                return ((org.xms.g.utils.XGettable) param0).getGInstance() instanceof com.google.firebase.dynamiclinks.DynamicLink.AndroidParameters;
            }
        }
        
        public static final class Builder extends org.xms.g.utils.XObject {
            
            
            
            public Builder(org.xms.g.utils.XBox param0) {
                super(param0);
            }
            
            public Builder() {
                super(((org.xms.g.utils.XBox) null));
                if (org.xms.g.utils.GlobalEnvSetting.isHms()) {
                    this.setHInstance(new com.huawei.agconnect.applinking.AppLinking.AndroidLinkInfo.Builder());
                } else {
                    this.setGInstance(new com.google.firebase.dynamiclinks.DynamicLink.AndroidParameters.Builder());
                }
            }
            
            public Builder(java.lang.String param0) {
                super(((org.xms.g.utils.XBox) null));
                if (org.xms.g.utils.GlobalEnvSetting.isHms()) {
                    this.setHInstance(new com.huawei.agconnect.applinking.AppLinking.AndroidLinkInfo.Builder(param0));
                } else {
                    this.setGInstance(new com.google.firebase.dynamiclinks.DynamicLink.AndroidParameters.Builder(param0));
                }
            }
            
            public final org.xms.f.dynamiclinks.DynamicLink.AndroidParameters build() {
                if (org.xms.g.utils.GlobalEnvSetting.isHms()) {
                    org.xms.g.utils.XmsLog.d("XMSRouter", "((com.huawei.agconnect.applinking.AppLinking.AndroidLinkInfo.Builder) this.getHInstance()).build()");
                    com.huawei.agconnect.applinking.AppLinking.AndroidLinkInfo hReturn = ((com.huawei.agconnect.applinking.AppLinking.AndroidLinkInfo.Builder) this.getHInstance()).build();
                    return ((hReturn) == null ? null : (new org.xms.f.dynamiclinks.DynamicLink.AndroidParameters(new org.xms.g.utils.XBox(null, hReturn))));
                } else {
                    org.xms.g.utils.XmsLog.d("XMSRouter", "((com.google.firebase.dynamiclinks.DynamicLink.AndroidParameters.Builder) this.getGInstance()).build()");
                    com.google.firebase.dynamiclinks.DynamicLink.AndroidParameters gReturn = ((com.google.firebase.dynamiclinks.DynamicLink.AndroidParameters.Builder) this.getGInstance()).build();
                    return ((gReturn) == null ? null : (new org.xms.f.dynamiclinks.DynamicLink.AndroidParameters(new org.xms.g.utils.XBox(gReturn, null))));
                }
            }
            
            public android.net.Uri getFallbackUrl() {
                throw new java.lang.RuntimeException("Not Supported");
            }
            
            public int getMinimumVersion() {
                throw new java.lang.RuntimeException("Not Supported");
            }
            
            public final org.xms.f.dynamiclinks.DynamicLink.AndroidParameters.Builder setFallbackUrl(android.net.Uri param0) {
                if (org.xms.g.utils.GlobalEnvSetting.isHms()) {
                    
                    org.xms.g.utils.XmsLog.d("XMSRouter", "org.xms.f.dynamiclinks.DynamicLink.AndroidParameters.Builder.setFallbackUrl(android.net.Uri)");
                    com.huawei.agconnect.applinking.AppLinking.AndroidLinkInfo.Builder hReturn = ((com.huawei.agconnect.applinking.AppLinking.AndroidLinkInfo.Builder) this.getHInstance()).setFallbackUrl(param0.toString());
                    return ((hReturn) == null ? null : (new org.xms.f.dynamiclinks.DynamicLink.AndroidParameters.Builder(new org.xms.g.utils.XBox(null, hReturn))));
                } else {
                    org.xms.g.utils.XmsLog.d("XMSRouter", "((com.google.firebase.dynamiclinks.DynamicLink.AndroidParameters.Builder) this.getGInstance()).setFallbackUrl(param0)");
                    com.google.firebase.dynamiclinks.DynamicLink.AndroidParameters.Builder gReturn = ((com.google.firebase.dynamiclinks.DynamicLink.AndroidParameters.Builder) this.getGInstance()).setFallbackUrl(param0);
                    return ((gReturn) == null ? null : (new org.xms.f.dynamiclinks.DynamicLink.AndroidParameters.Builder(new org.xms.g.utils.XBox(gReturn, null))));
                }
            }
            
            public final org.xms.f.dynamiclinks.DynamicLink.AndroidParameters.Builder setMinimumVersion(int param0) {
                if (org.xms.g.utils.GlobalEnvSetting.isHms()) {
                    org.xms.g.utils.XmsLog.d("XMSRouter", "((com.huawei.agconnect.applinking.AppLinking.AndroidLinkInfo.Builder) this.getHInstance()).setMinimumVersion(param0)");
                    com.huawei.agconnect.applinking.AppLinking.AndroidLinkInfo.Builder hReturn = ((com.huawei.agconnect.applinking.AppLinking.AndroidLinkInfo.Builder) this.getHInstance()).setMinimumVersion(param0);
                    return ((hReturn) == null ? null : (new org.xms.f.dynamiclinks.DynamicLink.AndroidParameters.Builder(new org.xms.g.utils.XBox(null, hReturn))));
                } else {
                    org.xms.g.utils.XmsLog.d("XMSRouter", "((com.google.firebase.dynamiclinks.DynamicLink.AndroidParameters.Builder) this.getGInstance()).setMinimumVersion(param0)");
                    com.google.firebase.dynamiclinks.DynamicLink.AndroidParameters.Builder gReturn = ((com.google.firebase.dynamiclinks.DynamicLink.AndroidParameters.Builder) this.getGInstance()).setMinimumVersion(param0);
                    return ((gReturn) == null ? null : (new org.xms.f.dynamiclinks.DynamicLink.AndroidParameters.Builder(new org.xms.g.utils.XBox(gReturn, null))));
                }
            }
            
            public static org.xms.f.dynamiclinks.DynamicLink.AndroidParameters.Builder dynamicCast(java.lang.Object param0) {
                return ((org.xms.f.dynamiclinks.DynamicLink.AndroidParameters.Builder) param0);
            }
            
            public static boolean isInstance(java.lang.Object param0) {
                if (!(param0 instanceof org.xms.g.utils.XGettable)) {
                    return false;
                }
                if (org.xms.g.utils.GlobalEnvSetting.isHms()) {
                    return ((org.xms.g.utils.XGettable) param0).getHInstance() instanceof com.huawei.agconnect.applinking.AppLinking.AndroidLinkInfo.Builder;
                } else {
                    return ((org.xms.g.utils.XGettable) param0).getGInstance() instanceof com.google.firebase.dynamiclinks.DynamicLink.AndroidParameters.Builder;
                }
            }
        }
    }
    
    public static final class Builder extends org.xms.g.utils.XObject {
        
        
        
        public Builder(org.xms.g.utils.XBox param0) {
            super(param0);
        }
        
        public final org.xms.f.dynamiclinks.DynamicLink buildDynamicLink() {
            if (org.xms.g.utils.GlobalEnvSetting.isHms()) {
                org.xms.g.utils.XmsLog.d("XMSRouter", "((com.huawei.agconnect.applinking.AppLinking.Builder) this.getHInstance()).buildAppLinking()");
                com.huawei.agconnect.applinking.AppLinking hReturn = ((com.huawei.agconnect.applinking.AppLinking.Builder) this.getHInstance()).buildAppLinking();
                return ((hReturn) == null ? null : (new org.xms.f.dynamiclinks.DynamicLink(new org.xms.g.utils.XBox(null, hReturn))));
            } else {
                org.xms.g.utils.XmsLog.d("XMSRouter", "((com.google.firebase.dynamiclinks.DynamicLink.Builder) this.getGInstance()).buildDynamicLink()");
                com.google.firebase.dynamiclinks.DynamicLink gReturn = ((com.google.firebase.dynamiclinks.DynamicLink.Builder) this.getGInstance()).buildDynamicLink();
                return ((gReturn) == null ? null : (new org.xms.f.dynamiclinks.DynamicLink(new org.xms.g.utils.XBox(gReturn, null))));
            }
        }
        
        public final org.xms.g.tasks.Task<org.xms.f.dynamiclinks.ShortDynamicLink> buildShortDynamicLink() {
            if (org.xms.g.utils.GlobalEnvSetting.isHms()) {
                org.xms.g.utils.XmsLog.d("XMSRouter", "((com.huawei.agconnect.applinking.AppLinking.Builder) this.getHInstance()).buildShortAppLinking()");
                com.huawei.hmf.tasks.Task hReturn = ((com.huawei.agconnect.applinking.AppLinking.Builder) this.getHInstance()).buildShortAppLinking();
                return ((hReturn) == null ? null : (new org.xms.g.tasks.Task.XImpl(new org.xms.g.utils.XBox(null, hReturn))));
            } else {
                org.xms.g.utils.XmsLog.d("XMSRouter", "((com.google.firebase.dynamiclinks.DynamicLink.Builder) this.getGInstance()).buildShortDynamicLink()");
                com.google.android.gms.tasks.Task gReturn = ((com.google.firebase.dynamiclinks.DynamicLink.Builder) this.getGInstance()).buildShortDynamicLink();
                return ((gReturn) == null ? null : (new org.xms.g.tasks.Task.XImpl(new org.xms.g.utils.XBox(gReturn, null))));
            }
        }
        
        public final org.xms.g.tasks.Task<org.xms.f.dynamiclinks.ShortDynamicLink> buildShortDynamicLink(int param0) {
            if (org.xms.g.utils.GlobalEnvSetting.isHms()) {
                
                org.xms.g.utils.XmsLog.d("XMSRouter", "((com.huawei.agconnect.applinking.AppLinking.Builder) this.getHInstance()).buildShortAppLinking(int)");
                com.huawei.hmf.tasks.Task hReturn = ((com.huawei.agconnect.applinking.AppLinking.Builder) this.getHInstance())
                        .buildShortAppLinking(param0 == 1 ? com.huawei.agconnect.applinking.ShortAppLinking.LENGTH.LONG : com.huawei.agconnect.applinking.ShortAppLinking.LENGTH.SHORT);
                return ((hReturn) == null ? null : (new org.xms.g.tasks.Task.XImpl(new org.xms.g.utils.XBox(null, hReturn))));
            } else {
                org.xms.g.utils.XmsLog.d("XMSRouter", "((com.google.firebase.dynamiclinks.DynamicLink.Builder) this.getGInstance()).buildShortDynamicLink(param0)");
                com.google.android.gms.tasks.Task gReturn = ((com.google.firebase.dynamiclinks.DynamicLink.Builder) this.getGInstance()).buildShortDynamicLink(param0);
                return ((gReturn) == null ? null : (new org.xms.g.tasks.Task.XImpl(new org.xms.g.utils.XBox(gReturn, null))));
            }
        }
        
        public java.lang.String getDomainUriPrefix() {
            throw new java.lang.RuntimeException("Not Supported");
        }
        
        public android.net.Uri getLink() {
            throw new java.lang.RuntimeException("Not Supported");
        }
        
        public android.net.Uri getLongLink() {
            throw new java.lang.RuntimeException("Not Supported");
        }
        
        public final org.xms.f.dynamiclinks.DynamicLink.Builder setAndroidParameters(org.xms.f.dynamiclinks.DynamicLink.AndroidParameters param0) {
            if (org.xms.g.utils.GlobalEnvSetting.isHms()) {
                org.xms.g.utils.XmsLog.d("XMSRouter", "((com.huawei.agconnect.applinking.AppLinking.Builder) this.getHInstance()).setAndroidLinkInfo(((com.huawei.agconnect.applinking.AppLinking.AndroidLinkInfo) ((param0) == null ? null : (param0.getHInstance()))))");
                com.huawei.agconnect.applinking.AppLinking.Builder hReturn = ((com.huawei.agconnect.applinking.AppLinking.Builder) this.getHInstance()).setAndroidLinkInfo(((com.huawei.agconnect.applinking.AppLinking.AndroidLinkInfo) ((param0) == null ? null : (param0.getHInstance()))));
                return ((hReturn) == null ? null : (new org.xms.f.dynamiclinks.DynamicLink.Builder(new org.xms.g.utils.XBox(null, hReturn))));
            } else {
                org.xms.g.utils.XmsLog.d("XMSRouter", "((com.google.firebase.dynamiclinks.DynamicLink.Builder) this.getGInstance()).setAndroidParameters(((com.google.firebase.dynamiclinks.DynamicLink.AndroidParameters) ((param0) == null ? null : (param0.getGInstance()))))");
                com.google.firebase.dynamiclinks.DynamicLink.Builder gReturn = ((com.google.firebase.dynamiclinks.DynamicLink.Builder) this.getGInstance()).setAndroidParameters(((com.google.firebase.dynamiclinks.DynamicLink.AndroidParameters) ((param0) == null ? null : (param0.getGInstance()))));
                return ((gReturn) == null ? null : (new org.xms.f.dynamiclinks.DynamicLink.Builder(new org.xms.g.utils.XBox(gReturn, null))));
            }
        }
        
        public final org.xms.f.dynamiclinks.DynamicLink.Builder setDomainUriPrefix(java.lang.String param0) {
            if (org.xms.g.utils.GlobalEnvSetting.isHms()) {
                org.xms.g.utils.XmsLog.d("XMSRouter", "((com.huawei.agconnect.applinking.AppLinking.Builder) this.getHInstance()).setUriPrefix(param0)");
                com.huawei.agconnect.applinking.AppLinking.Builder hReturn = ((com.huawei.agconnect.applinking.AppLinking.Builder) this.getHInstance()).setUriPrefix(param0);
                return ((hReturn) == null ? null : (new org.xms.f.dynamiclinks.DynamicLink.Builder(new org.xms.g.utils.XBox(null, hReturn))));
            } else {
                org.xms.g.utils.XmsLog.d("XMSRouter", "((com.google.firebase.dynamiclinks.DynamicLink.Builder) this.getGInstance()).setDomainUriPrefix(param0)");
                com.google.firebase.dynamiclinks.DynamicLink.Builder gReturn = ((com.google.firebase.dynamiclinks.DynamicLink.Builder) this.getGInstance()).setDomainUriPrefix(param0);
                return ((gReturn) == null ? null : (new org.xms.f.dynamiclinks.DynamicLink.Builder(new org.xms.g.utils.XBox(gReturn, null))));
            }
        }
        
        public final org.xms.f.dynamiclinks.DynamicLink.Builder setDynamicLinkDomain(java.lang.String param0) {
            if (org.xms.g.utils.GlobalEnvSetting.isHms()) {
                org.xms.g.utils.XmsLog.d("XMSRouter", "((com.huawei.agconnect.applinking.AppLinking.Builder) this.getHInstance()).setUriPrefix(param0)");
                com.huawei.agconnect.applinking.AppLinking.Builder hReturn = ((com.huawei.agconnect.applinking.AppLinking.Builder) this.getHInstance()).setUriPrefix(param0);
                return ((hReturn) == null ? null : (new org.xms.f.dynamiclinks.DynamicLink.Builder(new org.xms.g.utils.XBox(null, hReturn))));
            } else {
                org.xms.g.utils.XmsLog.d("XMSRouter", "((com.google.firebase.dynamiclinks.DynamicLink.Builder) this.getGInstance()).setDynamicLinkDomain(param0)");
                com.google.firebase.dynamiclinks.DynamicLink.Builder gReturn = ((com.google.firebase.dynamiclinks.DynamicLink.Builder) this.getGInstance()).setDynamicLinkDomain(param0);
                return ((gReturn) == null ? null : (new org.xms.f.dynamiclinks.DynamicLink.Builder(new org.xms.g.utils.XBox(gReturn, null))));
            }
        }
        
        public final org.xms.f.dynamiclinks.DynamicLink.Builder setGoogleAnalyticsParameters(org.xms.f.dynamiclinks.DynamicLink.ExtensionAnalyticsParameters param0) {
            if (org.xms.g.utils.GlobalEnvSetting.isHms()) {
                org.xms.g.utils.XmsLog.d("XMSRouter", "((com.huawei.agconnect.applinking.AppLinking.Builder) this.getHInstance()).setCampaignInfo(((com.huawei.agconnect.applinking.AppLinking.CampaignInfo) ((param0) == null ? null : (param0.getHInstance()))))");
                com.huawei.agconnect.applinking.AppLinking.Builder hReturn = ((com.huawei.agconnect.applinking.AppLinking.Builder) this.getHInstance()).setCampaignInfo(((com.huawei.agconnect.applinking.AppLinking.CampaignInfo) ((param0) == null ? null : (param0.getHInstance()))));
                return ((hReturn) == null ? null : (new org.xms.f.dynamiclinks.DynamicLink.Builder(new org.xms.g.utils.XBox(null, hReturn))));
            } else {
                org.xms.g.utils.XmsLog.d("XMSRouter", "((com.google.firebase.dynamiclinks.DynamicLink.Builder) this.getGInstance()).setGoogleAnalyticsParameters(((com.google.firebase.dynamiclinks.DynamicLink.GoogleAnalyticsParameters) ((param0) == null ? null : (param0.getGInstance()))))");
                com.google.firebase.dynamiclinks.DynamicLink.Builder gReturn = ((com.google.firebase.dynamiclinks.DynamicLink.Builder) this.getGInstance()).setGoogleAnalyticsParameters(((com.google.firebase.dynamiclinks.DynamicLink.GoogleAnalyticsParameters) ((param0) == null ? null : (param0.getGInstance()))));
                return ((gReturn) == null ? null : (new org.xms.f.dynamiclinks.DynamicLink.Builder(new org.xms.g.utils.XBox(gReturn, null))));
            }
        }
        
        public final org.xms.f.dynamiclinks.DynamicLink.Builder setIosParameters(org.xms.f.dynamiclinks.DynamicLink.IosParameters param0) {
            if (org.xms.g.utils.GlobalEnvSetting.isHms()) {
                
                org.xms.g.utils.XmsLog.d("XMSRouter", "org.xms.f.dynamiclinks.DynamicLink.Builder.setIosParameters()");
                return this;
            } else {
                org.xms.g.utils.XmsLog.d("XMSRouter", "((com.google.firebase.dynamiclinks.DynamicLink.Builder) this.getGInstance()).setIosParameters(((com.google.firebase.dynamiclinks.DynamicLink.IosParameters) ((param0) == null ? null : (param0.getGInstance()))))");
                com.google.firebase.dynamiclinks.DynamicLink.Builder gReturn = ((com.google.firebase.dynamiclinks.DynamicLink.Builder) this.getGInstance()).setIosParameters(((com.google.firebase.dynamiclinks.DynamicLink.IosParameters) ((param0) == null ? null : (param0.getGInstance()))));
                return ((gReturn) == null ? null : (new org.xms.f.dynamiclinks.DynamicLink.Builder(new org.xms.g.utils.XBox(gReturn, null))));
            }
        }
        
        public final org.xms.f.dynamiclinks.DynamicLink.Builder setItunesConnectAnalyticsParameters(org.xms.f.dynamiclinks.DynamicLink.ItunesConnectAnalyticsParameters param0) {
            if (org.xms.g.utils.GlobalEnvSetting.isHms()) {
                
                org.xms.g.utils.XmsLog.d("XMSRouter", "org.xms.f.dynamiclinks.DynamicLink.Builder.setItunesConnectAnalyticsParameters()");
                return this;
            } else {
                org.xms.g.utils.XmsLog.d("XMSRouter", "((com.google.firebase.dynamiclinks.DynamicLink.Builder) this.getGInstance()).setItunesConnectAnalyticsParameters(((com.google.firebase.dynamiclinks.DynamicLink.ItunesConnectAnalyticsParameters) ((param0) == null ? null : (param0.getGInstance()))))");
                com.google.firebase.dynamiclinks.DynamicLink.Builder gReturn = ((com.google.firebase.dynamiclinks.DynamicLink.Builder) this.getGInstance()).setItunesConnectAnalyticsParameters(((com.google.firebase.dynamiclinks.DynamicLink.ItunesConnectAnalyticsParameters) ((param0) == null ? null : (param0.getGInstance()))));
                return ((gReturn) == null ? null : (new org.xms.f.dynamiclinks.DynamicLink.Builder(new org.xms.g.utils.XBox(gReturn, null))));
            }
        }
        
        public final org.xms.f.dynamiclinks.DynamicLink.Builder setLink(android.net.Uri param0) {
            if (org.xms.g.utils.GlobalEnvSetting.isHms()) {
                org.xms.g.utils.XmsLog.d("XMSRouter", "((com.huawei.agconnect.applinking.AppLinking.Builder) this.getHInstance()).setDeepLink(param0)");
                com.huawei.agconnect.applinking.AppLinking.Builder hReturn = ((com.huawei.agconnect.applinking.AppLinking.Builder) this.getHInstance()).setDeepLink(param0);
                return ((hReturn) == null ? null : (new org.xms.f.dynamiclinks.DynamicLink.Builder(new org.xms.g.utils.XBox(null, hReturn))));
            } else {
                org.xms.g.utils.XmsLog.d("XMSRouter", "((com.google.firebase.dynamiclinks.DynamicLink.Builder) this.getGInstance()).setLink(param0)");
                com.google.firebase.dynamiclinks.DynamicLink.Builder gReturn = ((com.google.firebase.dynamiclinks.DynamicLink.Builder) this.getGInstance()).setLink(param0);
                return ((gReturn) == null ? null : (new org.xms.f.dynamiclinks.DynamicLink.Builder(new org.xms.g.utils.XBox(gReturn, null))));
            }
        }
        
        public final org.xms.f.dynamiclinks.DynamicLink.Builder setLongLink(android.net.Uri param0) {
            if (org.xms.g.utils.GlobalEnvSetting.isHms()) {
                org.xms.g.utils.XmsLog.d("XMSRouter", "((com.huawei.agconnect.applinking.AppLinking.Builder) this.getHInstance()).setLongLink(param0)");
                com.huawei.agconnect.applinking.AppLinking.Builder hReturn = ((com.huawei.agconnect.applinking.AppLinking.Builder) this.getHInstance()).setLongLink(param0);
                return ((hReturn) == null ? null : (new org.xms.f.dynamiclinks.DynamicLink.Builder(new org.xms.g.utils.XBox(null, hReturn))));
            } else {
                org.xms.g.utils.XmsLog.d("XMSRouter", "((com.google.firebase.dynamiclinks.DynamicLink.Builder) this.getGInstance()).setLongLink(param0)");
                com.google.firebase.dynamiclinks.DynamicLink.Builder gReturn = ((com.google.firebase.dynamiclinks.DynamicLink.Builder) this.getGInstance()).setLongLink(param0);
                return ((gReturn) == null ? null : (new org.xms.f.dynamiclinks.DynamicLink.Builder(new org.xms.g.utils.XBox(gReturn, null))));
            }
        }
        
        public final org.xms.f.dynamiclinks.DynamicLink.Builder setNavigationInfoParameters(org.xms.f.dynamiclinks.DynamicLink.NavigationInfoParameters param0) {
            if (org.xms.g.utils.GlobalEnvSetting.isHms()) {
                
                org.xms.g.utils.XmsLog.d("XMSRouter", "org.xms.f.dynamiclinks.DynamicLink.Builder.setNavigationInfoParameters()");
                return this;
            } else {
                org.xms.g.utils.XmsLog.d("XMSRouter", "((com.google.firebase.dynamiclinks.DynamicLink.Builder) this.getGInstance()).setNavigationInfoParameters(((com.google.firebase.dynamiclinks.DynamicLink.NavigationInfoParameters) ((param0) == null ? null : (param0.getGInstance()))))");
                com.google.firebase.dynamiclinks.DynamicLink.Builder gReturn = ((com.google.firebase.dynamiclinks.DynamicLink.Builder) this.getGInstance()).setNavigationInfoParameters(((com.google.firebase.dynamiclinks.DynamicLink.NavigationInfoParameters) ((param0) == null ? null : (param0.getGInstance()))));
                return ((gReturn) == null ? null : (new org.xms.f.dynamiclinks.DynamicLink.Builder(new org.xms.g.utils.XBox(gReturn, null))));
            }
        }
        
        public final org.xms.f.dynamiclinks.DynamicLink.Builder setSocialMetaTagParameters(org.xms.f.dynamiclinks.DynamicLink.SocialMetaTagParameters param0) {
            if (org.xms.g.utils.GlobalEnvSetting.isHms()) {
                org.xms.g.utils.XmsLog.d("XMSRouter", "((com.huawei.agconnect.applinking.AppLinking.Builder) this.getHInstance()).setSocialCardInfo(((com.huawei.agconnect.applinking.AppLinking.SocialCardInfo) ((param0) == null ? null : (param0.getHInstance()))))");
                com.huawei.agconnect.applinking.AppLinking.Builder hReturn = ((com.huawei.agconnect.applinking.AppLinking.Builder) this.getHInstance()).setSocialCardInfo(((com.huawei.agconnect.applinking.AppLinking.SocialCardInfo) ((param0) == null ? null : (param0.getHInstance()))));
                return ((hReturn) == null ? null : (new org.xms.f.dynamiclinks.DynamicLink.Builder(new org.xms.g.utils.XBox(null, hReturn))));
            } else {
                org.xms.g.utils.XmsLog.d("XMSRouter", "((com.google.firebase.dynamiclinks.DynamicLink.Builder) this.getGInstance()).setSocialMetaTagParameters(((com.google.firebase.dynamiclinks.DynamicLink.SocialMetaTagParameters) ((param0) == null ? null : (param0.getGInstance()))))");
                com.google.firebase.dynamiclinks.DynamicLink.Builder gReturn = ((com.google.firebase.dynamiclinks.DynamicLink.Builder) this.getGInstance()).setSocialMetaTagParameters(((com.google.firebase.dynamiclinks.DynamicLink.SocialMetaTagParameters) ((param0) == null ? null : (param0.getGInstance()))));
                return ((gReturn) == null ? null : (new org.xms.f.dynamiclinks.DynamicLink.Builder(new org.xms.g.utils.XBox(gReturn, null))));
            }
        }
        
        public static org.xms.f.dynamiclinks.DynamicLink.Builder dynamicCast(java.lang.Object param0) {
            return ((org.xms.f.dynamiclinks.DynamicLink.Builder) param0);
        }
        
        public static boolean isInstance(java.lang.Object param0) {
            if (!(param0 instanceof org.xms.g.utils.XGettable)) {
                return false;
            }
            if (org.xms.g.utils.GlobalEnvSetting.isHms()) {
                return ((org.xms.g.utils.XGettable) param0).getHInstance() instanceof com.huawei.agconnect.applinking.AppLinking.Builder;
            } else {
                return ((org.xms.g.utils.XGettable) param0).getGInstance() instanceof com.google.firebase.dynamiclinks.DynamicLink.Builder;
            }
        }
    }
    
    public static final class ExtensionAnalyticsParameters extends org.xms.g.utils.XObject {
        
        public ExtensionAnalyticsParameters(org.xms.g.utils.XBox param0) {
            super(param0);
        }
        
        public static org.xms.f.dynamiclinks.DynamicLink.ExtensionAnalyticsParameters dynamicCast(java.lang.Object param0) {
            return ((org.xms.f.dynamiclinks.DynamicLink.ExtensionAnalyticsParameters) param0);
        }
        
        public static boolean isInstance(java.lang.Object param0) {
            if (!(param0 instanceof org.xms.g.utils.XGettable)) {
                return false;
            }
            if (org.xms.g.utils.GlobalEnvSetting.isHms()) {
                return ((org.xms.g.utils.XGettable) param0).getHInstance() instanceof com.huawei.agconnect.applinking.AppLinking.CampaignInfo;
            } else {
                return ((org.xms.g.utils.XGettable) param0).getGInstance() instanceof com.google.firebase.dynamiclinks.DynamicLink.GoogleAnalyticsParameters;
            }
        }
        
        public static final class Builder extends org.xms.g.utils.XObject {
            
            
            
            public Builder(org.xms.g.utils.XBox param0) {
                super(param0);
            }
            
            public Builder() {
                super(((org.xms.g.utils.XBox) null));
                if (org.xms.g.utils.GlobalEnvSetting.isHms()) {
                    this.setHInstance(new com.huawei.agconnect.applinking.AppLinking.CampaignInfo.Builder());
                } else {
                    this.setGInstance(new com.google.firebase.dynamiclinks.DynamicLink.GoogleAnalyticsParameters.Builder());
                }
            }
            
            public Builder(java.lang.String param0, java.lang.String param1, java.lang.String param2) {
                super(((org.xms.g.utils.XBox) null));
                if (org.xms.g.utils.GlobalEnvSetting.isHms()) {
                    
                    this.setHInstance(new com.huawei.agconnect.applinking.AppLinking.CampaignInfo.Builder()
                            .setSource(param0).setMedium(param1).setName(param2));
                } else {
                    this.setGInstance(new com.google.firebase.dynamiclinks.DynamicLink.GoogleAnalyticsParameters.Builder(param0, param1, param2));
                }
            }
            
            public final org.xms.f.dynamiclinks.DynamicLink.ExtensionAnalyticsParameters build() {
                if (org.xms.g.utils.GlobalEnvSetting.isHms()) {
                    org.xms.g.utils.XmsLog.d("XMSRouter", "((com.huawei.agconnect.applinking.AppLinking.CampaignInfo.Builder) this.getHInstance()).build()");
                    com.huawei.agconnect.applinking.AppLinking.CampaignInfo hReturn = ((com.huawei.agconnect.applinking.AppLinking.CampaignInfo.Builder) this.getHInstance()).build();
                    return ((hReturn) == null ? null : (new org.xms.f.dynamiclinks.DynamicLink.ExtensionAnalyticsParameters(new org.xms.g.utils.XBox(null, hReturn))));
                } else {
                    org.xms.g.utils.XmsLog.d("XMSRouter", "((com.google.firebase.dynamiclinks.DynamicLink.GoogleAnalyticsParameters.Builder) this.getGInstance()).build()");
                    com.google.firebase.dynamiclinks.DynamicLink.GoogleAnalyticsParameters gReturn = ((com.google.firebase.dynamiclinks.DynamicLink.GoogleAnalyticsParameters.Builder) this.getGInstance()).build();
                    return ((gReturn) == null ? null : (new org.xms.f.dynamiclinks.DynamicLink.ExtensionAnalyticsParameters(new org.xms.g.utils.XBox(gReturn, null))));
                }
            }
            
            public java.lang.String getCampaign() {
                throw new java.lang.RuntimeException("Not Supported");
            }
            
            public java.lang.String getContent() {
                throw new java.lang.RuntimeException("Not Supported");
            }
            
            public java.lang.String getMedium() {
                throw new java.lang.RuntimeException("Not Supported");
            }
            
            public java.lang.String getSource() {
                throw new java.lang.RuntimeException("Not Supported");
            }
            
            public java.lang.String getTerm() {
                throw new java.lang.RuntimeException("Not Supported");
            }
            
            public final org.xms.f.dynamiclinks.DynamicLink.ExtensionAnalyticsParameters.Builder setCampaign(java.lang.String param0) {
                if (org.xms.g.utils.GlobalEnvSetting.isHms()) {
                    org.xms.g.utils.XmsLog.d("XMSRouter", "((com.huawei.agconnect.applinking.AppLinking.CampaignInfo.Builder) this.getHInstance()).setName(param0)");
                    com.huawei.agconnect.applinking.AppLinking.CampaignInfo.Builder hReturn = ((com.huawei.agconnect.applinking.AppLinking.CampaignInfo.Builder) this.getHInstance()).setName(param0);
                    return ((hReturn) == null ? null : (new org.xms.f.dynamiclinks.DynamicLink.ExtensionAnalyticsParameters.Builder(new org.xms.g.utils.XBox(null, hReturn))));
                } else {
                    org.xms.g.utils.XmsLog.d("XMSRouter", "((com.google.firebase.dynamiclinks.DynamicLink.GoogleAnalyticsParameters.Builder) this.getGInstance()).setCampaign(param0)");
                    com.google.firebase.dynamiclinks.DynamicLink.GoogleAnalyticsParameters.Builder gReturn = ((com.google.firebase.dynamiclinks.DynamicLink.GoogleAnalyticsParameters.Builder) this.getGInstance()).setCampaign(param0);
                    return ((gReturn) == null ? null : (new org.xms.f.dynamiclinks.DynamicLink.ExtensionAnalyticsParameters.Builder(new org.xms.g.utils.XBox(gReturn, null))));
                }
            }
            
            public final org.xms.f.dynamiclinks.DynamicLink.ExtensionAnalyticsParameters.Builder setContent(java.lang.String param0) {
                if (org.xms.g.utils.GlobalEnvSetting.isHms()) {
                    
                    org.xms.g.utils.XmsLog.d("XMSRouter", "org.xms.f.dynamiclinks.DynamicLink.ExtensionAnalyticsParameters.Builder.setContent");
                    return this;
                } else {
                    org.xms.g.utils.XmsLog.d("XMSRouter", "((com.google.firebase.dynamiclinks.DynamicLink.GoogleAnalyticsParameters.Builder) this.getGInstance()).setContent(param0)");
                    com.google.firebase.dynamiclinks.DynamicLink.GoogleAnalyticsParameters.Builder gReturn = ((com.google.firebase.dynamiclinks.DynamicLink.GoogleAnalyticsParameters.Builder) this.getGInstance()).setContent(param0);
                    return ((gReturn) == null ? null : (new org.xms.f.dynamiclinks.DynamicLink.ExtensionAnalyticsParameters.Builder(new org.xms.g.utils.XBox(gReturn, null))));
                }
            }
            
            public final org.xms.f.dynamiclinks.DynamicLink.ExtensionAnalyticsParameters.Builder setMedium(java.lang.String param0) {
                if (org.xms.g.utils.GlobalEnvSetting.isHms()) {
                    org.xms.g.utils.XmsLog.d("XMSRouter", "((com.huawei.agconnect.applinking.AppLinking.CampaignInfo.Builder) this.getHInstance()).setMedium(param0)");
                    com.huawei.agconnect.applinking.AppLinking.CampaignInfo.Builder hReturn = ((com.huawei.agconnect.applinking.AppLinking.CampaignInfo.Builder) this.getHInstance()).setMedium(param0);
                    return ((hReturn) == null ? null : (new org.xms.f.dynamiclinks.DynamicLink.ExtensionAnalyticsParameters.Builder(new org.xms.g.utils.XBox(null, hReturn))));
                } else {
                    org.xms.g.utils.XmsLog.d("XMSRouter", "((com.google.firebase.dynamiclinks.DynamicLink.GoogleAnalyticsParameters.Builder) this.getGInstance()).setMedium(param0)");
                    com.google.firebase.dynamiclinks.DynamicLink.GoogleAnalyticsParameters.Builder gReturn = ((com.google.firebase.dynamiclinks.DynamicLink.GoogleAnalyticsParameters.Builder) this.getGInstance()).setMedium(param0);
                    return ((gReturn) == null ? null : (new org.xms.f.dynamiclinks.DynamicLink.ExtensionAnalyticsParameters.Builder(new org.xms.g.utils.XBox(gReturn, null))));
                }
            }
            
            public final org.xms.f.dynamiclinks.DynamicLink.ExtensionAnalyticsParameters.Builder setSource(java.lang.String param0) {
                if (org.xms.g.utils.GlobalEnvSetting.isHms()) {
                    org.xms.g.utils.XmsLog.d("XMSRouter", "((com.huawei.agconnect.applinking.AppLinking.CampaignInfo.Builder) this.getHInstance()).setSource(param0)");
                    com.huawei.agconnect.applinking.AppLinking.CampaignInfo.Builder hReturn = ((com.huawei.agconnect.applinking.AppLinking.CampaignInfo.Builder) this.getHInstance()).setSource(param0);
                    return ((hReturn) == null ? null : (new org.xms.f.dynamiclinks.DynamicLink.ExtensionAnalyticsParameters.Builder(new org.xms.g.utils.XBox(null, hReturn))));
                } else {
                    org.xms.g.utils.XmsLog.d("XMSRouter", "((com.google.firebase.dynamiclinks.DynamicLink.GoogleAnalyticsParameters.Builder) this.getGInstance()).setSource(param0)");
                    com.google.firebase.dynamiclinks.DynamicLink.GoogleAnalyticsParameters.Builder gReturn = ((com.google.firebase.dynamiclinks.DynamicLink.GoogleAnalyticsParameters.Builder) this.getGInstance()).setSource(param0);
                    return ((gReturn) == null ? null : (new org.xms.f.dynamiclinks.DynamicLink.ExtensionAnalyticsParameters.Builder(new org.xms.g.utils.XBox(gReturn, null))));
                }
            }
            
            public final org.xms.f.dynamiclinks.DynamicLink.ExtensionAnalyticsParameters.Builder setTerm(java.lang.String param0) {
                if (org.xms.g.utils.GlobalEnvSetting.isHms()) {
                    
                    org.xms.g.utils.XmsLog.d("XMSRouter", "org.xms.f.dynamiclinks.DynamicLink.ExtensionAnalyticsParameters.Builder.setTerm");
                    return this;
                } else {
                    org.xms.g.utils.XmsLog.d("XMSRouter", "((com.google.firebase.dynamiclinks.DynamicLink.GoogleAnalyticsParameters.Builder) this.getGInstance()).setTerm(param0)");
                    com.google.firebase.dynamiclinks.DynamicLink.GoogleAnalyticsParameters.Builder gReturn = ((com.google.firebase.dynamiclinks.DynamicLink.GoogleAnalyticsParameters.Builder) this.getGInstance()).setTerm(param0);
                    return ((gReturn) == null ? null : (new org.xms.f.dynamiclinks.DynamicLink.ExtensionAnalyticsParameters.Builder(new org.xms.g.utils.XBox(gReturn, null))));
                }
            }
            
            public static org.xms.f.dynamiclinks.DynamicLink.ExtensionAnalyticsParameters.Builder dynamicCast(java.lang.Object param0) {
                return ((org.xms.f.dynamiclinks.DynamicLink.ExtensionAnalyticsParameters.Builder) param0);
            }
            
            public static boolean isInstance(java.lang.Object param0) {
                if (!(param0 instanceof org.xms.g.utils.XGettable)) {
                    return false;
                }
                if (org.xms.g.utils.GlobalEnvSetting.isHms()) {
                    return ((org.xms.g.utils.XGettable) param0).getHInstance() instanceof com.huawei.agconnect.applinking.AppLinking.CampaignInfo.Builder;
                } else {
                    return ((org.xms.g.utils.XGettable) param0).getGInstance() instanceof com.google.firebase.dynamiclinks.DynamicLink.GoogleAnalyticsParameters.Builder;
                }
            }
        }
    }
    
    public static final class IosParameters extends org.xms.g.utils.XObject {
        
        
        
        public IosParameters(org.xms.g.utils.XBox param0) {
            super(param0);
        }
        
        public static org.xms.f.dynamiclinks.DynamicLink.IosParameters dynamicCast(java.lang.Object param0) {
            return ((org.xms.f.dynamiclinks.DynamicLink.IosParameters) param0);
        }
        
        public static boolean isInstance(java.lang.Object param0) {
            if (!(param0 instanceof org.xms.g.utils.XGettable)) {
                return false;
            }
            if (org.xms.g.utils.GlobalEnvSetting.isHms()) {
                
                throw new java.lang.RuntimeException("AppGallery-connect does not support this API.");
            } else {
                return ((org.xms.g.utils.XGettable) param0).getGInstance() instanceof com.google.firebase.dynamiclinks.DynamicLink.IosParameters;
            }
        }
        
        public static final class Builder extends org.xms.g.utils.XObject {
            
            
            
            public Builder(org.xms.g.utils.XBox param0) {
                super(param0);
            }
            
            public Builder(java.lang.String param0) {
                super(((org.xms.g.utils.XBox) null));
                if (org.xms.g.utils.GlobalEnvSetting.isHms()) {
                    
                    this.setHInstance(null);
                } else {
                    this.setGInstance(new com.google.firebase.dynamiclinks.DynamicLink.IosParameters.Builder(param0));
                }
            }
            
            public final org.xms.f.dynamiclinks.DynamicLink.IosParameters build() {
                if (org.xms.g.utils.GlobalEnvSetting.isHms()) {
                    
                    org.xms.g.utils.XmsLog.d("XMSRouter", "org.xms.f.dynamiclinks.DynamicLink.IosParameters.Builder.build()");
                    return new org.xms.f.dynamiclinks.DynamicLink.IosParameters(new org.xms.g.utils.XBox(null, null));
                } else {
                    org.xms.g.utils.XmsLog.d("XMSRouter", "((com.google.firebase.dynamiclinks.DynamicLink.IosParameters.Builder) this.getGInstance()).build()");
                    com.google.firebase.dynamiclinks.DynamicLink.IosParameters gReturn = ((com.google.firebase.dynamiclinks.DynamicLink.IosParameters.Builder) this.getGInstance()).build();
                    return ((gReturn) == null ? null : (new org.xms.f.dynamiclinks.DynamicLink.IosParameters(new org.xms.g.utils.XBox(gReturn, null))));
                }
            }
            
            public java.lang.String getAppStoreId() {
                if (org.xms.g.utils.GlobalEnvSetting.isHms()) {
                    
                    org.xms.g.utils.XmsLog.d("XMSRouter", "org.xms.f.dynamiclinks.DynamicLink.IosParameters.Builder.getAppStoreId()");
                    return "";
                } else {
                    org.xms.g.utils.XmsLog.d("XMSRouter", "((com.google.firebase.dynamiclinks.DynamicLink.IosParameters.Builder) this.getGInstance()).getAppStoreId()");
                    return ((com.google.firebase.dynamiclinks.DynamicLink.IosParameters.Builder) this.getGInstance()).getAppStoreId();
                }
            }
            
            public java.lang.String getCustomScheme() {
                if (org.xms.g.utils.GlobalEnvSetting.isHms()) {
                    
                    org.xms.g.utils.XmsLog.d("XMSRouter", "org.xms.f.dynamiclinks.DynamicLink.IosParameters.Builder.getCustomScheme()");
                    return "";
                } else {
                    org.xms.g.utils.XmsLog.d("XMSRouter", "((com.google.firebase.dynamiclinks.DynamicLink.IosParameters.Builder) this.getGInstance()).getCustomScheme()");
                    return ((com.google.firebase.dynamiclinks.DynamicLink.IosParameters.Builder) this.getGInstance()).getCustomScheme();
                }
            }
            
            public java.lang.String getIpadBundleId() {
                if (org.xms.g.utils.GlobalEnvSetting.isHms()) {
                    
                    org.xms.g.utils.XmsLog.d("XMSRouter", "org.xms.f.dynamiclinks.DynamicLink.IosParameters.Builder.getIpadBundleId()");
                    return "";
                } else {
                    org.xms.g.utils.XmsLog.d("XMSRouter", "((com.google.firebase.dynamiclinks.DynamicLink.IosParameters.Builder) this.getGInstance()).getIpadBundleId()");
                    return ((com.google.firebase.dynamiclinks.DynamicLink.IosParameters.Builder) this.getGInstance()).getIpadBundleId();
                }
            }
            
            public android.net.Uri getIpadFallbackUrl() {
                if (org.xms.g.utils.GlobalEnvSetting.isHms()) {
                    
                    org.xms.g.utils.XmsLog.d("XMSRouter", "org.xms.f.dynamiclinks.DynamicLink.IosParameters.Builder.getIpadFallbackUrl()");
                    return android.net.Uri.parse("");
                } else {
                    org.xms.g.utils.XmsLog.d("XMSRouter", "((com.google.firebase.dynamiclinks.DynamicLink.IosParameters.Builder) this.getGInstance()).getIpadFallbackUrl()");
                    return ((com.google.firebase.dynamiclinks.DynamicLink.IosParameters.Builder) this.getGInstance()).getIpadFallbackUrl();
                }
            }
            
            public java.lang.String getMinimumVersion() {
                if (org.xms.g.utils.GlobalEnvSetting.isHms()) {
                    
                    org.xms.g.utils.XmsLog.d("XMSRouter", "org.xms.f.dynamiclinks.DynamicLink.IosParameters.Builder.getMinimumVersion()");
                    return "17";
                } else {
                    org.xms.g.utils.XmsLog.d("XMSRouter", "((com.google.firebase.dynamiclinks.DynamicLink.IosParameters.Builder) this.getGInstance()).getMinimumVersion()");
                    return ((com.google.firebase.dynamiclinks.DynamicLink.IosParameters.Builder) this.getGInstance()).getMinimumVersion();
                }
            }
            
            public final org.xms.f.dynamiclinks.DynamicLink.IosParameters.Builder setAppStoreId(java.lang.String param0) {
                if (org.xms.g.utils.GlobalEnvSetting.isHms()) {
                    
                    org.xms.g.utils.XmsLog.d("XMSRouter", "org.xms.f.dynamiclinks.DynamicLink.IosParameters.Builder.setAppStoreId()");
                    return this;
                } else {
                    org.xms.g.utils.XmsLog.d("XMSRouter", "((com.google.firebase.dynamiclinks.DynamicLink.IosParameters.Builder) this.getGInstance()).setAppStoreId(param0)");
                    com.google.firebase.dynamiclinks.DynamicLink.IosParameters.Builder gReturn = ((com.google.firebase.dynamiclinks.DynamicLink.IosParameters.Builder) this.getGInstance()).setAppStoreId(param0);
                    return ((gReturn) == null ? null : (new org.xms.f.dynamiclinks.DynamicLink.IosParameters.Builder(new org.xms.g.utils.XBox(gReturn, null))));
                }
            }
            
            public final org.xms.f.dynamiclinks.DynamicLink.IosParameters.Builder setCustomScheme(java.lang.String param0) {
                if (org.xms.g.utils.GlobalEnvSetting.isHms()) {
                    
                    org.xms.g.utils.XmsLog.d("XMSRouter", "org.xms.f.dynamiclinks.DynamicLink.IosParameters.Builder.setCustomScheme()");
                    return this;
                } else {
                    org.xms.g.utils.XmsLog.d("XMSRouter", "((com.google.firebase.dynamiclinks.DynamicLink.IosParameters.Builder) this.getGInstance()).setCustomScheme(param0)");
                    com.google.firebase.dynamiclinks.DynamicLink.IosParameters.Builder gReturn = ((com.google.firebase.dynamiclinks.DynamicLink.IosParameters.Builder) this.getGInstance()).setCustomScheme(param0);
                    return ((gReturn) == null ? null : (new org.xms.f.dynamiclinks.DynamicLink.IosParameters.Builder(new org.xms.g.utils.XBox(gReturn, null))));
                }
            }
            
            public final org.xms.f.dynamiclinks.DynamicLink.IosParameters.Builder setFallbackUrl(android.net.Uri param0) {
                if (org.xms.g.utils.GlobalEnvSetting.isHms()) {
                    
                    org.xms.g.utils.XmsLog.d("XMSRouter", "org.xms.f.dynamiclinks.DynamicLink.IosParameters.Builder.setFallbackUrl()");
                    return this;
                } else {
                    org.xms.g.utils.XmsLog.d("XMSRouter", "((com.google.firebase.dynamiclinks.DynamicLink.IosParameters.Builder) this.getGInstance()).setFallbackUrl(param0)");
                    com.google.firebase.dynamiclinks.DynamicLink.IosParameters.Builder gReturn = ((com.google.firebase.dynamiclinks.DynamicLink.IosParameters.Builder) this.getGInstance()).setFallbackUrl(param0);
                    return ((gReturn) == null ? null : (new org.xms.f.dynamiclinks.DynamicLink.IosParameters.Builder(new org.xms.g.utils.XBox(gReturn, null))));
                }
            }
            
            public final org.xms.f.dynamiclinks.DynamicLink.IosParameters.Builder setIpadBundleId(java.lang.String param0) {
                if (org.xms.g.utils.GlobalEnvSetting.isHms()) {
                    
                    org.xms.g.utils.XmsLog.d("XMSRouter", "org.xms.f.dynamiclinks.DynamicLink.IosParameters.Builder.setIpadBundleId()");
                    return this;
                } else {
                    org.xms.g.utils.XmsLog.d("XMSRouter", "((com.google.firebase.dynamiclinks.DynamicLink.IosParameters.Builder) this.getGInstance()).setIpadBundleId(param0)");
                    com.google.firebase.dynamiclinks.DynamicLink.IosParameters.Builder gReturn = ((com.google.firebase.dynamiclinks.DynamicLink.IosParameters.Builder) this.getGInstance()).setIpadBundleId(param0);
                    return ((gReturn) == null ? null : (new org.xms.f.dynamiclinks.DynamicLink.IosParameters.Builder(new org.xms.g.utils.XBox(gReturn, null))));
                }
            }
            
            public final org.xms.f.dynamiclinks.DynamicLink.IosParameters.Builder setIpadFallbackUrl(android.net.Uri param0) {
                if (org.xms.g.utils.GlobalEnvSetting.isHms()) {
                    
                    org.xms.g.utils.XmsLog.d("XMSRouter", "org.xms.f.dynamiclinks.DynamicLink.IosParameters.Builder.setIpadFallbackUrl()");
                    return this;
                } else {
                    org.xms.g.utils.XmsLog.d("XMSRouter", "((com.google.firebase.dynamiclinks.DynamicLink.IosParameters.Builder) this.getGInstance()).setIpadFallbackUrl(param0)");
                    com.google.firebase.dynamiclinks.DynamicLink.IosParameters.Builder gReturn = ((com.google.firebase.dynamiclinks.DynamicLink.IosParameters.Builder) this.getGInstance()).setIpadFallbackUrl(param0);
                    return ((gReturn) == null ? null : (new org.xms.f.dynamiclinks.DynamicLink.IosParameters.Builder(new org.xms.g.utils.XBox(gReturn, null))));
                }
            }
            
            public final org.xms.f.dynamiclinks.DynamicLink.IosParameters.Builder setMinimumVersion(java.lang.String param0) {
                if (org.xms.g.utils.GlobalEnvSetting.isHms()) {
                    
                    org.xms.g.utils.XmsLog.d("XMSRouter", "org.xms.f.dynamiclinks.DynamicLink.IosParameters.Builder.setMinimumVersion()");
                    return this;
                } else {
                    org.xms.g.utils.XmsLog.d("XMSRouter", "((com.google.firebase.dynamiclinks.DynamicLink.IosParameters.Builder) this.getGInstance()).setMinimumVersion(param0)");
                    com.google.firebase.dynamiclinks.DynamicLink.IosParameters.Builder gReturn = ((com.google.firebase.dynamiclinks.DynamicLink.IosParameters.Builder) this.getGInstance()).setMinimumVersion(param0);
                    return ((gReturn) == null ? null : (new org.xms.f.dynamiclinks.DynamicLink.IosParameters.Builder(new org.xms.g.utils.XBox(gReturn, null))));
                }
            }
            
            public static org.xms.f.dynamiclinks.DynamicLink.IosParameters.Builder dynamicCast(java.lang.Object param0) {
                return ((org.xms.f.dynamiclinks.DynamicLink.IosParameters.Builder) param0);
            }
            
            public static boolean isInstance(java.lang.Object param0) {
                if (!(param0 instanceof org.xms.g.utils.XGettable)) {
                    return false;
                }
                if (org.xms.g.utils.GlobalEnvSetting.isHms()) {
                    
                    throw new java.lang.RuntimeException("AppGallery-connect does not support this API.");
                } else {
                    return ((org.xms.g.utils.XGettable) param0).getGInstance() instanceof com.google.firebase.dynamiclinks.DynamicLink.IosParameters.Builder;
                }
            }
        }
    }
    
    public static final class ItunesConnectAnalyticsParameters extends org.xms.g.utils.XObject {
        
        
        
        public ItunesConnectAnalyticsParameters(org.xms.g.utils.XBox param0) {
            super(param0);
        }
        
        public static org.xms.f.dynamiclinks.DynamicLink.ItunesConnectAnalyticsParameters dynamicCast(java.lang.Object param0) {
            return ((org.xms.f.dynamiclinks.DynamicLink.ItunesConnectAnalyticsParameters) param0);
        }
        
        public static boolean isInstance(java.lang.Object param0) {
            if (!(param0 instanceof org.xms.g.utils.XGettable)) {
                return false;
            }
            if (org.xms.g.utils.GlobalEnvSetting.isHms()) {
                
                throw new java.lang.RuntimeException("AppGallery-connect does not support this API.");
            } else {
                return ((org.xms.g.utils.XGettable) param0).getGInstance() instanceof com.google.firebase.dynamiclinks.DynamicLink.ItunesConnectAnalyticsParameters;
            }
        }
        
        public static final class Builder extends org.xms.g.utils.XObject {
            
            
            
            public Builder(org.xms.g.utils.XBox param0) {
                super(param0);
            }
            
            public Builder() {
                super(((org.xms.g.utils.XBox) null));
                if (org.xms.g.utils.GlobalEnvSetting.isHms()) {
                    
                    this.setHInstance(null);
                } else {
                    this.setGInstance(new com.google.firebase.dynamiclinks.DynamicLink.ItunesConnectAnalyticsParameters.Builder());
                }
            }
            
            public final org.xms.f.dynamiclinks.DynamicLink.ItunesConnectAnalyticsParameters build() {
                if (org.xms.g.utils.GlobalEnvSetting.isHms()) {
                    
                    org.xms.g.utils.XmsLog.d("XMSRouter", "org.xms.f.dynamiclinks.DynamicLink.ItunesConnectAnalyticsParameters.Builder.build()");
                    return new org.xms.f.dynamiclinks.DynamicLink.ItunesConnectAnalyticsParameters(new org.xms.g.utils.XBox(null, null));
                } else {
                    org.xms.g.utils.XmsLog.d("XMSRouter", "((com.google.firebase.dynamiclinks.DynamicLink.ItunesConnectAnalyticsParameters.Builder) this.getGInstance()).build()");
                    com.google.firebase.dynamiclinks.DynamicLink.ItunesConnectAnalyticsParameters gReturn = ((com.google.firebase.dynamiclinks.DynamicLink.ItunesConnectAnalyticsParameters.Builder) this.getGInstance()).build();
                    return ((gReturn) == null ? null : (new org.xms.f.dynamiclinks.DynamicLink.ItunesConnectAnalyticsParameters(new org.xms.g.utils.XBox(gReturn, null))));
                }
            }
            
            public java.lang.String getAffiliateToken() {
                if (org.xms.g.utils.GlobalEnvSetting.isHms()) {
                    
                    org.xms.g.utils.XmsLog.d("XMSRouter", "org.xms.f.dynamiclinks.DynamicLink.ItunesConnectAnalyticsParameters.Builder.getAffiliateToken()");
                    return "";
                } else {
                    org.xms.g.utils.XmsLog.d("XMSRouter", "((com.google.firebase.dynamiclinks.DynamicLink.ItunesConnectAnalyticsParameters.Builder) this.getGInstance()).getAffiliateToken()");
                    return ((com.google.firebase.dynamiclinks.DynamicLink.ItunesConnectAnalyticsParameters.Builder) this.getGInstance()).getAffiliateToken();
                }
            }
            
            public java.lang.String getCampaignToken() {
                if (org.xms.g.utils.GlobalEnvSetting.isHms()) {
                    
                    org.xms.g.utils.XmsLog.d("XMSRouter", "org.xms.f.dynamiclinks.DynamicLink.ItunesConnectAnalyticsParameters.Builder.getCampaignToken()");
                    return "";
                } else {
                    org.xms.g.utils.XmsLog.d("XMSRouter", "((com.google.firebase.dynamiclinks.DynamicLink.ItunesConnectAnalyticsParameters.Builder) this.getGInstance()).getCampaignToken()");
                    return ((com.google.firebase.dynamiclinks.DynamicLink.ItunesConnectAnalyticsParameters.Builder) this.getGInstance()).getCampaignToken();
                }
            }
            
            public java.lang.String getProviderToken() {
                if (org.xms.g.utils.GlobalEnvSetting.isHms()) {
                    
                    org.xms.g.utils.XmsLog.d("XMSRouter", "org.xms.f.dynamiclinks.DynamicLink.ItunesConnectAnalyticsParameters.Builder.getProviderToken()");
                    return "";
                } else {
                    org.xms.g.utils.XmsLog.d("XMSRouter", "((com.google.firebase.dynamiclinks.DynamicLink.ItunesConnectAnalyticsParameters.Builder) this.getGInstance()).getProviderToken()");
                    return ((com.google.firebase.dynamiclinks.DynamicLink.ItunesConnectAnalyticsParameters.Builder) this.getGInstance()).getProviderToken();
                }
            }
            
            public final org.xms.f.dynamiclinks.DynamicLink.ItunesConnectAnalyticsParameters.Builder setAffiliateToken(java.lang.String param0) {
                if (org.xms.g.utils.GlobalEnvSetting.isHms()) {
                    
                    org.xms.g.utils.XmsLog.d("XMSRouter", "org.xms.f.dynamiclinks.DynamicLink.ItunesConnectAnalyticsParameters.Builder.setAffiliateToken()");
                    return this;
                } else {
                    org.xms.g.utils.XmsLog.d("XMSRouter", "((com.google.firebase.dynamiclinks.DynamicLink.ItunesConnectAnalyticsParameters.Builder) this.getGInstance()).setAffiliateToken(param0)");
                    com.google.firebase.dynamiclinks.DynamicLink.ItunesConnectAnalyticsParameters.Builder gReturn = ((com.google.firebase.dynamiclinks.DynamicLink.ItunesConnectAnalyticsParameters.Builder) this.getGInstance()).setAffiliateToken(param0);
                    return ((gReturn) == null ? null : (new org.xms.f.dynamiclinks.DynamicLink.ItunesConnectAnalyticsParameters.Builder(new org.xms.g.utils.XBox(gReturn, null))));
                }
            }
            
            public final org.xms.f.dynamiclinks.DynamicLink.ItunesConnectAnalyticsParameters.Builder setCampaignToken(java.lang.String param0) {
                if (org.xms.g.utils.GlobalEnvSetting.isHms()) {
                    
                    org.xms.g.utils.XmsLog.d("XMSRouter", "org.xms.f.dynamiclinks.DynamicLink.ItunesConnectAnalyticsParameters.Builder.setCampaignToken()");
                    return this;
                } else {
                    org.xms.g.utils.XmsLog.d("XMSRouter", "((com.google.firebase.dynamiclinks.DynamicLink.ItunesConnectAnalyticsParameters.Builder) this.getGInstance()).setCampaignToken(param0)");
                    com.google.firebase.dynamiclinks.DynamicLink.ItunesConnectAnalyticsParameters.Builder gReturn = ((com.google.firebase.dynamiclinks.DynamicLink.ItunesConnectAnalyticsParameters.Builder) this.getGInstance()).setCampaignToken(param0);
                    return ((gReturn) == null ? null : (new org.xms.f.dynamiclinks.DynamicLink.ItunesConnectAnalyticsParameters.Builder(new org.xms.g.utils.XBox(gReturn, null))));
                }
            }
            
            public final org.xms.f.dynamiclinks.DynamicLink.ItunesConnectAnalyticsParameters.Builder setProviderToken(java.lang.String param0) {
                if (org.xms.g.utils.GlobalEnvSetting.isHms()) {
                    
                    org.xms.g.utils.XmsLog.d("XMSRouter", "org.xms.f.dynamiclinks.DynamicLink.ItunesConnectAnalyticsParameters.Builder.setProviderToken()");
                    return this;
                } else {
                    org.xms.g.utils.XmsLog.d("XMSRouter", "((com.google.firebase.dynamiclinks.DynamicLink.ItunesConnectAnalyticsParameters.Builder) this.getGInstance()).setProviderToken(param0)");
                    com.google.firebase.dynamiclinks.DynamicLink.ItunesConnectAnalyticsParameters.Builder gReturn = ((com.google.firebase.dynamiclinks.DynamicLink.ItunesConnectAnalyticsParameters.Builder) this.getGInstance()).setProviderToken(param0);
                    return ((gReturn) == null ? null : (new org.xms.f.dynamiclinks.DynamicLink.ItunesConnectAnalyticsParameters.Builder(new org.xms.g.utils.XBox(gReturn, null))));
                }
            }
            
            public static org.xms.f.dynamiclinks.DynamicLink.ItunesConnectAnalyticsParameters.Builder dynamicCast(java.lang.Object param0) {
                return ((org.xms.f.dynamiclinks.DynamicLink.ItunesConnectAnalyticsParameters.Builder) param0);
            }
            
            public static boolean isInstance(java.lang.Object param0) {
                if (!(param0 instanceof org.xms.g.utils.XGettable)) {
                    return false;
                }
                if (org.xms.g.utils.GlobalEnvSetting.isHms()) {
                    
                    throw new java.lang.RuntimeException("AppGallery-connect does not support this API.");
                } else {
                    return ((org.xms.g.utils.XGettable) param0).getGInstance() instanceof com.google.firebase.dynamiclinks.DynamicLink.ItunesConnectAnalyticsParameters.Builder;
                }
            }
        }
    }
    
    public static final class NavigationInfoParameters extends org.xms.g.utils.XObject {
        
        
        
        public NavigationInfoParameters(org.xms.g.utils.XBox param0) {
            super(param0);
        }
        
        public static org.xms.f.dynamiclinks.DynamicLink.NavigationInfoParameters dynamicCast(java.lang.Object param0) {
            return ((org.xms.f.dynamiclinks.DynamicLink.NavigationInfoParameters) param0);
        }
        
        public static boolean isInstance(java.lang.Object param0) {
            if (!(param0 instanceof org.xms.g.utils.XGettable)) {
                return false;
            }
            if (org.xms.g.utils.GlobalEnvSetting.isHms()) {
                
                throw new java.lang.RuntimeException("AppGallery-connect does not support this API.");
            } else {
                return ((org.xms.g.utils.XGettable) param0).getGInstance() instanceof com.google.firebase.dynamiclinks.DynamicLink.NavigationInfoParameters;
            }
        }
        
        public static final class Builder extends org.xms.g.utils.XObject {
            
            
            
            public Builder(org.xms.g.utils.XBox param0) {
                super(param0);
            }
            
            public Builder() {
                super(((org.xms.g.utils.XBox) null));
                if (org.xms.g.utils.GlobalEnvSetting.isHms()) {
                    
                    this.setHInstance(null);
                } else {
                    this.setGInstance(new com.google.firebase.dynamiclinks.DynamicLink.NavigationInfoParameters.Builder());
                }
            }
            
            public final org.xms.f.dynamiclinks.DynamicLink.NavigationInfoParameters build() {
                if (org.xms.g.utils.GlobalEnvSetting.isHms()) {
                    
                    org.xms.g.utils.XmsLog.d("XMSRouter", "org.xms.f.dynamiclinks.DynamicLink.NavigationInfoParameters.Builder.build()");
                    return new org.xms.f.dynamiclinks.DynamicLink.NavigationInfoParameters(new org.xms.g.utils.XBox(null, null));
                } else {
                    org.xms.g.utils.XmsLog.d("XMSRouter", "((com.google.firebase.dynamiclinks.DynamicLink.NavigationInfoParameters.Builder) this.getGInstance()).build()");
                    com.google.firebase.dynamiclinks.DynamicLink.NavigationInfoParameters gReturn = ((com.google.firebase.dynamiclinks.DynamicLink.NavigationInfoParameters.Builder) this.getGInstance()).build();
                    return ((gReturn) == null ? null : (new org.xms.f.dynamiclinks.DynamicLink.NavigationInfoParameters(new org.xms.g.utils.XBox(gReturn, null))));
                }
            }
            
            public boolean getForcedRedirectEnabled() {
                if (org.xms.g.utils.GlobalEnvSetting.isHms()) {
                    
                    org.xms.g.utils.XmsLog.d("XMSRouter", "org.xms.f.dynamiclinks.DynamicLink.NavigationInfoParameters.Builder.getForcedRedirectEnabled()");
                    return false;
                } else {
                    org.xms.g.utils.XmsLog.d("XMSRouter", "((com.google.firebase.dynamiclinks.DynamicLink.NavigationInfoParameters.Builder) this.getGInstance()).getForcedRedirectEnabled()");
                    return ((com.google.firebase.dynamiclinks.DynamicLink.NavigationInfoParameters.Builder) this.getGInstance()).getForcedRedirectEnabled();
                }
            }
            
            public final org.xms.f.dynamiclinks.DynamicLink.NavigationInfoParameters.Builder setForcedRedirectEnabled(boolean param0) {
                if (org.xms.g.utils.GlobalEnvSetting.isHms()) {
                    
                    org.xms.g.utils.XmsLog.d("XMSRouter", "org.xms.f.dynamiclinks.DynamicLink.NavigationInfoParameters.Builder.setForcedRedirectEnabled()");
                    return this;
                } else {
                    org.xms.g.utils.XmsLog.d("XMSRouter", "((com.google.firebase.dynamiclinks.DynamicLink.NavigationInfoParameters.Builder) this.getGInstance()).setForcedRedirectEnabled(param0)");
                    com.google.firebase.dynamiclinks.DynamicLink.NavigationInfoParameters.Builder gReturn = ((com.google.firebase.dynamiclinks.DynamicLink.NavigationInfoParameters.Builder) this.getGInstance()).setForcedRedirectEnabled(param0);
                    return ((gReturn) == null ? null : (new org.xms.f.dynamiclinks.DynamicLink.NavigationInfoParameters.Builder(new org.xms.g.utils.XBox(gReturn, null))));
                }
            }
            
            public static org.xms.f.dynamiclinks.DynamicLink.NavigationInfoParameters.Builder dynamicCast(java.lang.Object param0) {
                return ((org.xms.f.dynamiclinks.DynamicLink.NavigationInfoParameters.Builder) param0);
            }
            
            public static boolean isInstance(java.lang.Object param0) {
                if (!(param0 instanceof org.xms.g.utils.XGettable)) {
                    return false;
                }
                if (org.xms.g.utils.GlobalEnvSetting.isHms()) {
                    
                    throw new java.lang.RuntimeException("AppGallery-connect does not support this API.");
                } else {
                    return ((org.xms.g.utils.XGettable) param0).getGInstance() instanceof com.google.firebase.dynamiclinks.DynamicLink.NavigationInfoParameters.Builder;
                }
            }
        }
    }
    
    public static final class SocialMetaTagParameters extends org.xms.g.utils.XObject {
        
        public SocialMetaTagParameters(org.xms.g.utils.XBox param0) {
            super(param0);
        }
        
        public static org.xms.f.dynamiclinks.DynamicLink.SocialMetaTagParameters dynamicCast(java.lang.Object param0) {
            return ((org.xms.f.dynamiclinks.DynamicLink.SocialMetaTagParameters) param0);
        }
        
        public static boolean isInstance(java.lang.Object param0) {
            if (!(param0 instanceof org.xms.g.utils.XGettable)) {
                return false;
            }
            if (org.xms.g.utils.GlobalEnvSetting.isHms()) {
                return ((org.xms.g.utils.XGettable) param0).getHInstance() instanceof com.huawei.agconnect.applinking.AppLinking.SocialCardInfo;
            } else {
                return ((org.xms.g.utils.XGettable) param0).getGInstance() instanceof com.google.firebase.dynamiclinks.DynamicLink.SocialMetaTagParameters;
            }
        }
        
        public static final class Builder extends org.xms.g.utils.XObject {
            
            
            
            public Builder(org.xms.g.utils.XBox param0) {
                super(param0);
            }
            
            public Builder() {
                super(((org.xms.g.utils.XBox) null));
                if (org.xms.g.utils.GlobalEnvSetting.isHms()) {
                    this.setHInstance(new com.huawei.agconnect.applinking.AppLinking.SocialCardInfo.Builder());
                } else {
                    this.setGInstance(new com.google.firebase.dynamiclinks.DynamicLink.SocialMetaTagParameters.Builder());
                }
            }
            
            public final org.xms.f.dynamiclinks.DynamicLink.SocialMetaTagParameters build() {
                if (org.xms.g.utils.GlobalEnvSetting.isHms()) {
                    org.xms.g.utils.XmsLog.d("XMSRouter", "((com.huawei.agconnect.applinking.AppLinking.SocialCardInfo.Builder) this.getHInstance()).build()");
                    com.huawei.agconnect.applinking.AppLinking.SocialCardInfo hReturn = ((com.huawei.agconnect.applinking.AppLinking.SocialCardInfo.Builder) this.getHInstance()).build();
                    return ((hReturn) == null ? null : (new org.xms.f.dynamiclinks.DynamicLink.SocialMetaTagParameters(new org.xms.g.utils.XBox(null, hReturn))));
                } else {
                    org.xms.g.utils.XmsLog.d("XMSRouter", "((com.google.firebase.dynamiclinks.DynamicLink.SocialMetaTagParameters.Builder) this.getGInstance()).build()");
                    com.google.firebase.dynamiclinks.DynamicLink.SocialMetaTagParameters gReturn = ((com.google.firebase.dynamiclinks.DynamicLink.SocialMetaTagParameters.Builder) this.getGInstance()).build();
                    return ((gReturn) == null ? null : (new org.xms.f.dynamiclinks.DynamicLink.SocialMetaTagParameters(new org.xms.g.utils.XBox(gReturn, null))));
                }
            }
            
            public java.lang.String getDescription() {
                throw new java.lang.RuntimeException("Not Supported");
            }
            
            public android.net.Uri getImageUrl() {
                throw new java.lang.RuntimeException("Not Supported");
            }
            
            public java.lang.String getTitle() {
                throw new java.lang.RuntimeException("Not Supported");
            }
            
            public final org.xms.f.dynamiclinks.DynamicLink.SocialMetaTagParameters.Builder setDescription(java.lang.String param0) {
                if (org.xms.g.utils.GlobalEnvSetting.isHms()) {
                    org.xms.g.utils.XmsLog.d("XMSRouter", "((com.huawei.agconnect.applinking.AppLinking.SocialCardInfo.Builder) this.getHInstance()).setDescription(param0)");
                    com.huawei.agconnect.applinking.AppLinking.SocialCardInfo.Builder hReturn = ((com.huawei.agconnect.applinking.AppLinking.SocialCardInfo.Builder) this.getHInstance()).setDescription(param0);
                    return ((hReturn) == null ? null : (new org.xms.f.dynamiclinks.DynamicLink.SocialMetaTagParameters.Builder(new org.xms.g.utils.XBox(null, hReturn))));
                } else {
                    org.xms.g.utils.XmsLog.d("XMSRouter", "((com.google.firebase.dynamiclinks.DynamicLink.SocialMetaTagParameters.Builder) this.getGInstance()).setDescription(param0)");
                    com.google.firebase.dynamiclinks.DynamicLink.SocialMetaTagParameters.Builder gReturn = ((com.google.firebase.dynamiclinks.DynamicLink.SocialMetaTagParameters.Builder) this.getGInstance()).setDescription(param0);
                    return ((gReturn) == null ? null : (new org.xms.f.dynamiclinks.DynamicLink.SocialMetaTagParameters.Builder(new org.xms.g.utils.XBox(gReturn, null))));
                }
            }
            
            public final org.xms.f.dynamiclinks.DynamicLink.SocialMetaTagParameters.Builder setImageUrl(android.net.Uri param0) {
                if (org.xms.g.utils.GlobalEnvSetting.isHms()) {
                    
                    org.xms.g.utils.XmsLog.d("XMSRouter", "((com.huawei.agconnect.applinking.AppLinking.SocialCardInfo.Builder) this.getHInstance()).setImageUrl(param0.toString())");
                    com.huawei.agconnect.applinking.AppLinking.SocialCardInfo.Builder hReturn = ((com.huawei.agconnect.applinking.AppLinking.SocialCardInfo.Builder) this.getHInstance()).setImageUrl(param0.toString());
                    return ((hReturn) == null ? null : (new org.xms.f.dynamiclinks.DynamicLink.SocialMetaTagParameters.Builder(new org.xms.g.utils.XBox(null, hReturn))));
                } else {
                    org.xms.g.utils.XmsLog.d("XMSRouter", "((com.google.firebase.dynamiclinks.DynamicLink.SocialMetaTagParameters.Builder) this.getGInstance()).setImageUrl(param0)");
                    com.google.firebase.dynamiclinks.DynamicLink.SocialMetaTagParameters.Builder gReturn = ((com.google.firebase.dynamiclinks.DynamicLink.SocialMetaTagParameters.Builder) this.getGInstance()).setImageUrl(param0);
                    return ((gReturn) == null ? null : (new org.xms.f.dynamiclinks.DynamicLink.SocialMetaTagParameters.Builder(new org.xms.g.utils.XBox(gReturn, null))));
                }
            }
            
            public final org.xms.f.dynamiclinks.DynamicLink.SocialMetaTagParameters.Builder setTitle(java.lang.String param0) {
                if (org.xms.g.utils.GlobalEnvSetting.isHms()) {
                    org.xms.g.utils.XmsLog.d("XMSRouter", "((com.huawei.agconnect.applinking.AppLinking.SocialCardInfo.Builder) this.getHInstance()).setTitle(param0)");
                    com.huawei.agconnect.applinking.AppLinking.SocialCardInfo.Builder hReturn = ((com.huawei.agconnect.applinking.AppLinking.SocialCardInfo.Builder) this.getHInstance()).setTitle(param0);
                    return ((hReturn) == null ? null : (new org.xms.f.dynamiclinks.DynamicLink.SocialMetaTagParameters.Builder(new org.xms.g.utils.XBox(null, hReturn))));
                } else {
                    org.xms.g.utils.XmsLog.d("XMSRouter", "((com.google.firebase.dynamiclinks.DynamicLink.SocialMetaTagParameters.Builder) this.getGInstance()).setTitle(param0)");
                    com.google.firebase.dynamiclinks.DynamicLink.SocialMetaTagParameters.Builder gReturn = ((com.google.firebase.dynamiclinks.DynamicLink.SocialMetaTagParameters.Builder) this.getGInstance()).setTitle(param0);
                    return ((gReturn) == null ? null : (new org.xms.f.dynamiclinks.DynamicLink.SocialMetaTagParameters.Builder(new org.xms.g.utils.XBox(gReturn, null))));
                }
            }
            
            public static org.xms.f.dynamiclinks.DynamicLink.SocialMetaTagParameters.Builder dynamicCast(java.lang.Object param0) {
                return ((org.xms.f.dynamiclinks.DynamicLink.SocialMetaTagParameters.Builder) param0);
            }
            
            public static boolean isInstance(java.lang.Object param0) {
                if (!(param0 instanceof org.xms.g.utils.XGettable)) {
                    return false;
                }
                if (org.xms.g.utils.GlobalEnvSetting.isHms()) {
                    return ((org.xms.g.utils.XGettable) param0).getHInstance() instanceof com.huawei.agconnect.applinking.AppLinking.SocialCardInfo.Builder;
                } else {
                    return ((org.xms.g.utils.XGettable) param0).getGInstance() instanceof com.google.firebase.dynamiclinks.DynamicLink.SocialMetaTagParameters.Builder;
                }
            }
        }
    }
}