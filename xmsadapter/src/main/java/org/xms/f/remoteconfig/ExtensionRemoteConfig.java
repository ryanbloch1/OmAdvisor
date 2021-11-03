package org.xms.f.remoteconfig;


import android.content.Context;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.util.Log;
import com.huawei.agconnect.AGConnectInstance;
import org.xmlpull.v1.XmlPullParserException;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class ExtensionRemoteConfig extends org.xms.g.utils.XObject {
    
    public interface XExtensionRemoteConfig {
        int SOURCE_REMOTE = 2;
        int SOURCE_STATIC = 0;
        int SOURCE_DEFAULT = 1;
        int LAST_FETCH_FAILURE = 1;
        int LAST_FETCH_NO_FETCH_YET = 0;
        int LAST_FETCH_SUCCESS = -1;
        int LAST_FETCH_THROTTLED = 2;
    }
    private boolean developerModeEnabled = false;
    public boolean getdeveloperModeEnabled() {
        return this.developerModeEnabled;
    }
    public void setDeveloperMode(boolean developerModeEnabled) {
        this.developerModeEnabled = developerModeEnabled;
    }
    public Map<java.lang.String, java.lang.Object> readXml2Map(int resId) {
        Map<java.lang.String, java.lang.Object> defaultsMap = new HashMap<>();
        Context context = AGConnectInstance.getInstance().getContext();
        try {
            Resources resources = context.getResources();
            if (resources == null) {
                return defaultsMap;
            }
            XmlResourceParser xmlParser = resources.getXml(resId);
            String curTag = null;
            String key = null;
            String value = null;
            int eventType = xmlParser.getEventType();
            while (eventType != XmlResourceParser.END_DOCUMENT) {
                if (eventType == XmlResourceParser.START_TAG) {
                    curTag = xmlParser.getName();
                } else if (eventType == XmlResourceParser.END_TAG) {
                    if (xmlParser.getName().equals("entry")) {
                        if (key != null && value != null) {
                            defaultsMap.put(key, value);
                        }
                        key = null;
                        value = null;
                    }
                    curTag = null;
                } else if (eventType == XmlResourceParser.TEXT) {
                    if (curTag != null) {
                        if ("key".equals(curTag)) {
                            key = xmlParser.getText();
                        } else if ("value".equals(curTag)) {
                            value = xmlParser.getText();
                        }
                    }
                }
                eventType = xmlParser.next();
            }
        } catch (XmlPullParserException e) {
            Log.e("readXml2Map", "parse default values xml failed", e);
        } catch (IOException e) {
            Log.e("readXml2Map", "parse default values xml failed", e);
        }
        return defaultsMap;
    }
    
    public ExtensionRemoteConfig(org.xms.g.utils.XBox param0) {
        super(param0);
    }
    
    public static boolean getDEFAULT_VALUE_FOR_BOOLEAN() {
        if (org.xms.g.utils.GlobalEnvSetting.isHms()) {
            org.xms.g.utils.XmsLog.d("XMSRouter", "com.huawei.agconnect.remoteconfig.AGConnectConfig.DEFAULT.BOOLEAN_VALUE");
            return com.huawei.agconnect.remoteconfig.AGConnectConfig.DEFAULT.BOOLEAN_VALUE;
        } else {
            org.xms.g.utils.XmsLog.d("XMSRouter", "com.google.firebase.remoteconfig.FirebaseRemoteConfig.DEFAULT_VALUE_FOR_BOOLEAN");
            return com.google.firebase.remoteconfig.FirebaseRemoteConfig.DEFAULT_VALUE_FOR_BOOLEAN;
        }
    }
    
    public static double getDEFAULT_VALUE_FOR_DOUBLE() {
        if (org.xms.g.utils.GlobalEnvSetting.isHms()) {
            org.xms.g.utils.XmsLog.d("XMSRouter", "com.huawei.agconnect.remoteconfig.AGConnectConfig.DEFAULT.DOUBLE_VALUE");
            return com.huawei.agconnect.remoteconfig.AGConnectConfig.DEFAULT.DOUBLE_VALUE;
        } else {
            org.xms.g.utils.XmsLog.d("XMSRouter", "com.google.firebase.remoteconfig.FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE");
            return com.google.firebase.remoteconfig.FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE;
        }
    }
    
    public static long getDEFAULT_VALUE_FOR_LONG() {
        if (org.xms.g.utils.GlobalEnvSetting.isHms()) {
            org.xms.g.utils.XmsLog.d("XMSRouter", "com.huawei.agconnect.remoteconfig.AGConnectConfig.DEFAULT.LONG_VALUE");
            return com.huawei.agconnect.remoteconfig.AGConnectConfig.DEFAULT.LONG_VALUE;
        } else {
            org.xms.g.utils.XmsLog.d("XMSRouter", "com.google.firebase.remoteconfig.FirebaseRemoteConfig.DEFAULT_VALUE_FOR_LONG");
            return com.google.firebase.remoteconfig.FirebaseRemoteConfig.DEFAULT_VALUE_FOR_LONG;
        }
    }
    
    public static java.lang.String getDEFAULT_VALUE_FOR_STRING() {
        if (org.xms.g.utils.GlobalEnvSetting.isHms()) {
            org.xms.g.utils.XmsLog.d("XMSRouter", "com.huawei.agconnect.remoteconfig.AGConnectConfig.DEFAULT.STRING_VALUE");
            return com.huawei.agconnect.remoteconfig.AGConnectConfig.DEFAULT.STRING_VALUE;
        } else {
            org.xms.g.utils.XmsLog.d("XMSRouter", "com.google.firebase.remoteconfig.FirebaseRemoteConfig.DEFAULT_VALUE_FOR_STRING");
            return com.google.firebase.remoteconfig.FirebaseRemoteConfig.DEFAULT_VALUE_FOR_STRING;
        }
    }
    
    public static int getLAST_FETCH_STATUS_FAILURE() {
        if (org.xms.g.utils.GlobalEnvSetting.isHms()) {
            
            org.xms.g.utils.XmsLog.d("XMSRouter",
                "org.xms.f.remoteconfig.ExtensionRemoteConfig.getLAST_FETCH_STATUS_FAILURE()");
            return XExtensionRemoteConfig.LAST_FETCH_FAILURE;
        } else {
            org.xms.g.utils.XmsLog.d("XMSRouter", "com.google.firebase.remoteconfig.FirebaseRemoteConfig.LAST_FETCH_STATUS_FAILURE");
            return com.google.firebase.remoteconfig.FirebaseRemoteConfig.LAST_FETCH_STATUS_FAILURE;
        }
    }
    
    public static int getLAST_FETCH_STATUS_NO_FETCH_YET() {
        if (org.xms.g.utils.GlobalEnvSetting.isHms()) {
            
            org.xms.g.utils.XmsLog.d("XMSRouter",
                "org.xms.f.remoteconfig.ExtensionRemoteConfig.getLAST_FETCH_STATUS_NO_FETCH_YET()");
            return XExtensionRemoteConfig.LAST_FETCH_NO_FETCH_YET;
        } else {
            org.xms.g.utils.XmsLog.d("XMSRouter", "com.google.firebase.remoteconfig.FirebaseRemoteConfig.LAST_FETCH_STATUS_NO_FETCH_YET");
            return com.google.firebase.remoteconfig.FirebaseRemoteConfig.LAST_FETCH_STATUS_NO_FETCH_YET;
        }
    }
    
    public static int getLAST_FETCH_STATUS_SUCCESS() {
        if (org.xms.g.utils.GlobalEnvSetting.isHms()) {
            
            org.xms.g.utils.XmsLog.d("XMSRouter",
                "org.xms.f.remoteconfig.ExtensionRemoteConfig.getLAST_FETCH_STATUS_SUCCESS()");
            return XExtensionRemoteConfig.LAST_FETCH_SUCCESS;
        } else {
            org.xms.g.utils.XmsLog.d("XMSRouter", "com.google.firebase.remoteconfig.FirebaseRemoteConfig.LAST_FETCH_STATUS_SUCCESS");
            return com.google.firebase.remoteconfig.FirebaseRemoteConfig.LAST_FETCH_STATUS_SUCCESS;
        }
    }
    
    public static int getLAST_FETCH_STATUS_THROTTLED() {
        if (org.xms.g.utils.GlobalEnvSetting.isHms()) {
            
            org.xms.g.utils.XmsLog.d("XMSRouter",
                "org.xms.f.remoteconfig.ExtensionRemoteConfig.getLAST_FETCH_STATUS_THROTTLED()");
            return XExtensionRemoteConfig.LAST_FETCH_THROTTLED;
        } else {
            org.xms.g.utils.XmsLog.d("XMSRouter", "com.google.firebase.remoteconfig.FirebaseRemoteConfig.LAST_FETCH_STATUS_THROTTLED");
            return com.google.firebase.remoteconfig.FirebaseRemoteConfig.LAST_FETCH_STATUS_THROTTLED;
        }
    }
    
    public static int getVALUE_SOURCE_DEFAULT() {
        if (org.xms.g.utils.GlobalEnvSetting.isHms()) {
            
            org.xms.g.utils.XmsLog.d("XMSRouter",
                "org.xms.f.remoteconfig.ExtensionRemoteConfig.getVALUE_SOURCE_DEFAULT()");
            return XExtensionRemoteConfig.SOURCE_DEFAULT;
        } else {
            org.xms.g.utils.XmsLog.d("XMSRouter", "com.google.firebase.remoteconfig.FirebaseRemoteConfig.VALUE_SOURCE_DEFAULT");
            return com.google.firebase.remoteconfig.FirebaseRemoteConfig.VALUE_SOURCE_DEFAULT;
        }
    }
    
    public static int getVALUE_SOURCE_REMOTE() {
        if (org.xms.g.utils.GlobalEnvSetting.isHms()) {
            
            org.xms.g.utils.XmsLog.d("XMSRouter",
                "org.xms.f.remoteconfig.ExtensionRemoteConfig.getVALUE_SOURCE_REMOTE()");
            return XExtensionRemoteConfig.SOURCE_REMOTE;
        } else {
            org.xms.g.utils.XmsLog.d("XMSRouter", "com.google.firebase.remoteconfig.FirebaseRemoteConfig.VALUE_SOURCE_REMOTE");
            return com.google.firebase.remoteconfig.FirebaseRemoteConfig.VALUE_SOURCE_REMOTE;
        }
    }
    
    public static int getVALUE_SOURCE_STATIC() {
        if (org.xms.g.utils.GlobalEnvSetting.isHms()) {
            
            org.xms.g.utils.XmsLog.d("XMSRouter",
                "org.xms.f.remoteconfig.ExtensionRemoteConfig.getVALUE_SOURCE_STATIC()");
            return XExtensionRemoteConfig.SOURCE_STATIC;
        } else {
            org.xms.g.utils.XmsLog.d("XMSRouter", "com.google.firebase.remoteconfig.FirebaseRemoteConfig.VALUE_SOURCE_STATIC");
            return com.google.firebase.remoteconfig.FirebaseRemoteConfig.VALUE_SOURCE_STATIC;
        }
    }
    
    public static byte[] getDEFAULT_VALUE_FOR_BYTE_ARRAY() {
        if (org.xms.g.utils.GlobalEnvSetting.isHms()) {
            org.xms.g.utils.XmsLog.d("XMSRouter", "com.huawei.agconnect.remoteconfig.AGConnectConfig.DEFAULT.BYTE_ARRAY_VALUE");
            return com.huawei.agconnect.remoteconfig.AGConnectConfig.DEFAULT.BYTE_ARRAY_VALUE;
        } else {
            org.xms.g.utils.XmsLog.d("XMSRouter", "com.google.firebase.remoteconfig.FirebaseRemoteConfig.DEFAULT_VALUE_FOR_BYTE_ARRAY");
            return com.google.firebase.remoteconfig.FirebaseRemoteConfig.DEFAULT_VALUE_FOR_BYTE_ARRAY;
        }
    }
    
    public org.xms.g.tasks.Task<java.lang.Boolean> activate() {
        throw new java.lang.RuntimeException("Not Supported");
    }
    
    public boolean activateFetched() {
        throw new java.lang.RuntimeException("Not Supported");
    }
    
    public org.xms.g.tasks.Task<org.xms.f.remoteconfig.ExtensionRemoteConfigInfo> ensureInitialized() {
        throw new java.lang.RuntimeException("Not Supported");
    }
    
    public org.xms.g.tasks.Task<java.lang.Void> fetch(long param0) {
        throw new java.lang.RuntimeException("Not Supported");
    }
    
    public org.xms.g.tasks.Task<java.lang.Void> fetch() {
        throw new java.lang.RuntimeException("Not Supported");
    }
    
    public org.xms.g.tasks.Task<java.lang.Boolean> fetchAndActivate() {
        throw new java.lang.RuntimeException("Not Supported");
    }
    
    public java.util.Map<java.lang.String, org.xms.f.remoteconfig.ExtensionRemoteConfigValue> getAll() {
        throw new java.lang.RuntimeException("Not Supported");
    }
    
    public boolean getBoolean(java.lang.String param0) {
        if (org.xms.g.utils.GlobalEnvSetting.isHms()) {
            org.xms.g.utils.XmsLog.d("XMSRouter", "((com.huawei.agconnect.remoteconfig.AGConnectConfig) this.getHInstance()).getValueAsBoolean(param0)");
            return ((com.huawei.agconnect.remoteconfig.AGConnectConfig) this.getHInstance()).getValueAsBoolean(param0);
        } else {
            org.xms.g.utils.XmsLog.d("XMSRouter", "((com.google.firebase.remoteconfig.FirebaseRemoteConfig) this.getGInstance()).getBoolean(param0)");
            return ((com.google.firebase.remoteconfig.FirebaseRemoteConfig) this.getGInstance()).getBoolean(param0);
        }
    }
    
    public byte[] getByteArray(java.lang.String param0) {
        if (org.xms.g.utils.GlobalEnvSetting.isHms()) {
            org.xms.g.utils.XmsLog.d("XMSRouter", "((com.huawei.agconnect.remoteconfig.AGConnectConfig) this.getHInstance()).getValueAsByteArray(param0)");
            return ((com.huawei.agconnect.remoteconfig.AGConnectConfig) this.getHInstance()).getValueAsByteArray(param0);
        } else {
            org.xms.g.utils.XmsLog.d("XMSRouter", "((com.google.firebase.remoteconfig.FirebaseRemoteConfig) this.getGInstance()).getByteArray(param0)");
            return ((com.google.firebase.remoteconfig.FirebaseRemoteConfig) this.getGInstance()).getByteArray(param0);
        }
    }
    
    public double getDouble(java.lang.String param0) {
        if (org.xms.g.utils.GlobalEnvSetting.isHms()) {
            org.xms.g.utils.XmsLog.d("XMSRouter", "((com.huawei.agconnect.remoteconfig.AGConnectConfig) this.getHInstance()).getValueAsDouble(param0)");
            return ((com.huawei.agconnect.remoteconfig.AGConnectConfig) this.getHInstance()).getValueAsDouble(param0);
        } else {
            org.xms.g.utils.XmsLog.d("XMSRouter", "((com.google.firebase.remoteconfig.FirebaseRemoteConfig) this.getGInstance()).getDouble(param0)");
            return ((com.google.firebase.remoteconfig.FirebaseRemoteConfig) this.getGInstance()).getDouble(param0);
        }
    }
    
    public org.xms.f.remoteconfig.ExtensionRemoteConfigInfo getInfo() {
        if (org.xms.g.utils.GlobalEnvSetting.isHms()) {
            
            org.xms.g.utils.XmsLog.d("XMSRouter", "org.xms.f.remoteconfig.ExtensionRemoteConfig.getInfo");
            return new org.xms.f.remoteconfig.ExtensionRemoteConfigInfo.XImpl(
                new org.xms.g.utils.XBox(null, null)).setDeveloperMode(this.getdeveloperModeEnabled());
        } else {
            org.xms.g.utils.XmsLog.d("XMSRouter", "((com.google.firebase.remoteconfig.FirebaseRemoteConfig) this.getGInstance()).getInfo()");
            com.google.firebase.remoteconfig.FirebaseRemoteConfigInfo gReturn = ((com.google.firebase.remoteconfig.FirebaseRemoteConfig) this.getGInstance()).getInfo();
            return ((gReturn) == null ? null : (new org.xms.f.remoteconfig.ExtensionRemoteConfigInfo.XImpl(new org.xms.g.utils.XBox(gReturn, null))));
        }
    }
    
    public static org.xms.f.remoteconfig.ExtensionRemoteConfig getInstance() {
        if (org.xms.g.utils.GlobalEnvSetting.isHms()) {
            org.xms.g.utils.XmsLog.d("XMSRouter", "com.huawei.agconnect.remoteconfig.AGConnectConfig.getInstance()");
            com.huawei.agconnect.remoteconfig.AGConnectConfig hReturn = com.huawei.agconnect.remoteconfig.AGConnectConfig.getInstance();
            return ((hReturn) == null ? null : (new org.xms.f.remoteconfig.ExtensionRemoteConfig(new org.xms.g.utils.XBox(null, hReturn))));
        } else {
            org.xms.g.utils.XmsLog.d("XMSRouter", "com.google.firebase.remoteconfig.FirebaseRemoteConfig.getInstance()");
            com.google.firebase.remoteconfig.FirebaseRemoteConfig gReturn = com.google.firebase.remoteconfig.FirebaseRemoteConfig.getInstance();
            return ((gReturn) == null ? null : (new org.xms.f.remoteconfig.ExtensionRemoteConfig(new org.xms.g.utils.XBox(gReturn, null))));
        }
    }
    
    public static org.xms.f.remoteconfig.ExtensionRemoteConfig getInstance(org.xms.f.ExtensionApp param0) {
        if (org.xms.g.utils.GlobalEnvSetting.isHms()) {
            
            org.xms.g.utils.XmsLog.d("XMSRouter", "com.huawei.agconnect.remoteconfig.AGConnectConfig.getInstance()");
            com.huawei.agconnect.remoteconfig.AGConnectConfig hReturn =
                com.huawei.agconnect.remoteconfig.AGConnectConfig.getInstance();
            return ((hReturn) == null ? null :
                (new org.xms.f.remoteconfig.ExtensionRemoteConfig(new org.xms.g.utils.XBox(null, hReturn))));
        } else {
            org.xms.g.utils.XmsLog.d("XMSRouter", "com.google.firebase.remoteconfig.FirebaseRemoteConfig.getInstance(((com.google.firebase.FirebaseApp) ((param0) == null ? null : (param0.getGInstance()))))");
            com.google.firebase.remoteconfig.FirebaseRemoteConfig gReturn = com.google.firebase.remoteconfig.FirebaseRemoteConfig.getInstance(((com.google.firebase.FirebaseApp) ((param0) == null ? null : (param0.getGInstance()))));
            return ((gReturn) == null ? null : (new org.xms.f.remoteconfig.ExtensionRemoteConfig(new org.xms.g.utils.XBox(gReturn, null))));
        }
    }
    
    public java.util.Set<java.lang.String> getKeysByPrefix(java.lang.String param0) {
        if (org.xms.g.utils.GlobalEnvSetting.isHms()) {
            
            org.xms.g.utils.XmsLog.d("XMSRouter",
                "((com.huawei.agconnect.remoteconfig.AGConnectConfig) this.getHInstance()).getMergedAll()");
            java.util.Set<String> keySet =
                ((com.huawei.agconnect.remoteconfig.AGConnectConfig) this.getHInstance()).getMergedAll().keySet();
            java.util.Set<String> hReturn = new HashSet<>();
            for (String key : keySet) {
                if (key.startsWith(param0)) {
                    hReturn.add(key);
                }
            }
            return hReturn;
        } else {
            org.xms.g.utils.XmsLog.d("XMSRouter", "((com.google.firebase.remoteconfig.FirebaseRemoteConfig) this.getGInstance()).getKeysByPrefix(param0)");
            return ((com.google.firebase.remoteconfig.FirebaseRemoteConfig) this.getGInstance()).getKeysByPrefix(param0);
        }
    }
    
    public long getLong(java.lang.String param0) {
        if (org.xms.g.utils.GlobalEnvSetting.isHms()) {
            org.xms.g.utils.XmsLog.d("XMSRouter", "((com.huawei.agconnect.remoteconfig.AGConnectConfig) this.getHInstance()).getValueAsLong(param0)");
            return ((com.huawei.agconnect.remoteconfig.AGConnectConfig) this.getHInstance()).getValueAsLong(param0);
        } else {
            org.xms.g.utils.XmsLog.d("XMSRouter", "((com.google.firebase.remoteconfig.FirebaseRemoteConfig) this.getGInstance()).getLong(param0)");
            return ((com.google.firebase.remoteconfig.FirebaseRemoteConfig) this.getGInstance()).getLong(param0);
        }
    }
    
    public java.lang.String getString(java.lang.String param0) {
        if (org.xms.g.utils.GlobalEnvSetting.isHms()) {
            org.xms.g.utils.XmsLog.d("XMSRouter", "((com.huawei.agconnect.remoteconfig.AGConnectConfig) this.getHInstance()).getValueAsString(param0)");
            return ((com.huawei.agconnect.remoteconfig.AGConnectConfig) this.getHInstance()).getValueAsString(param0);
        } else {
            org.xms.g.utils.XmsLog.d("XMSRouter", "((com.google.firebase.remoteconfig.FirebaseRemoteConfig) this.getGInstance()).getString(param0)");
            return ((com.google.firebase.remoteconfig.FirebaseRemoteConfig) this.getGInstance()).getString(param0);
        }
    }
    
    public org.xms.f.remoteconfig.ExtensionRemoteConfigValue getValue(java.lang.String param0) {
        if (org.xms.g.utils.GlobalEnvSetting.isHms()) {
            
            org.xms.g.utils.XmsLog.d("XMSRouter",
                "((com.huawei.agconnect.remoteconfig.AGConnectConfig) this.getHInstance()).getMergedAll().get(param0)");
            java.lang.Object hReturn =
                ((com.huawei.agconnect.remoteconfig.AGConnectConfig) this.getHInstance()).getMergedAll().get(param0);
            return ((hReturn) == null ? null :
                (new org.xms.f.remoteconfig.ExtensionRemoteConfigValue.XImpl(new org.xms.g.utils.XBox(null, hReturn))));
        } else {
            org.xms.g.utils.XmsLog.d("XMSRouter", "((com.google.firebase.remoteconfig.FirebaseRemoteConfig) this.getGInstance()).getValue(param0)");
            com.google.firebase.remoteconfig.FirebaseRemoteConfigValue gReturn = ((com.google.firebase.remoteconfig.FirebaseRemoteConfig) this.getGInstance()).getValue(param0);
            return ((gReturn) == null ? null : (new org.xms.f.remoteconfig.ExtensionRemoteConfigValue.XImpl(new org.xms.g.utils.XBox(gReturn, null))));
        }
    }
    
    public org.xms.g.tasks.Task<java.lang.Void> reset() {
        throw new java.lang.RuntimeException("Not Supported");
    }
    
    public void setConfigSettings(org.xms.f.remoteconfig.ExtensionRemoteConfigSettings param0) {
        if (org.xms.g.utils.GlobalEnvSetting.isHms()) {
            
            org.xms.g.utils.XmsLog.d("XMSRouter", "org.xms.f.remoteconfig.ExtensionRemoteConfig.setConfigSettings");
            this.setDeveloperMode(param0.getdeveloperModeEnabled());
            ((com.huawei.agconnect.remoteconfig.AGConnectConfig) this.getHInstance()).setDeveloperMode(
                param0.getdeveloperModeEnabled());
        } else {
            org.xms.g.utils.XmsLog.d("XMSRouter", "((com.google.firebase.remoteconfig.FirebaseRemoteConfig) this.getGInstance()).setConfigSettings(((com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings) ((param0) == null ? null : (param0.getGInstance()))))");
            ((com.google.firebase.remoteconfig.FirebaseRemoteConfig) this.getGInstance()).setConfigSettings(((com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings) ((param0) == null ? null : (param0.getGInstance()))));
        }
    }
    
    public org.xms.g.tasks.Task<java.lang.Void> setConfigSettingsAsync(org.xms.f.remoteconfig.ExtensionRemoteConfigSettings param0) {
        throw new java.lang.RuntimeException("Not Supported");
    }
    
    public void setDefaults(int param0) {
        if (org.xms.g.utils.GlobalEnvSetting.isHms()) {
            
            org.xms.g.utils.XmsLog.d("XMSRouter",
                "((com.huawei.agconnect.remoteconfig.AGConnectConfig) this.getHInstance()).applyDefault(map)");
            java.util.Map<java.lang.String, java.lang.Object> map = this.readXml2Map(param0);
            ((com.huawei.agconnect.remoteconfig.AGConnectConfig) this.getHInstance()).applyDefault(map);
        } else {
            org.xms.g.utils.XmsLog.d("XMSRouter", "((com.google.firebase.remoteconfig.FirebaseRemoteConfig) this.getGInstance()).setDefaults(param0)");
            ((com.google.firebase.remoteconfig.FirebaseRemoteConfig) this.getGInstance()).setDefaults(param0);
        }
    }
    
    public void setDefaults(java.util.Map<java.lang.String, java.lang.Object> param0) {
        if (org.xms.g.utils.GlobalEnvSetting.isHms()) {
            org.xms.g.utils.XmsLog.d("XMSRouter", "((com.huawei.agconnect.remoteconfig.AGConnectConfig) this.getHInstance()).applyDefault(param0)");
            ((com.huawei.agconnect.remoteconfig.AGConnectConfig) this.getHInstance()).applyDefault(param0);
        } else {
            org.xms.g.utils.XmsLog.d("XMSRouter", "((com.google.firebase.remoteconfig.FirebaseRemoteConfig) this.getGInstance()).setDefaults(param0)");
            ((com.google.firebase.remoteconfig.FirebaseRemoteConfig) this.getGInstance()).setDefaults(param0);
        }
    }
    
    public org.xms.g.tasks.Task<java.lang.Void> setDefaultsAsync(int param0) {
        throw new java.lang.RuntimeException("Not Supported");
    }
    
    public org.xms.g.tasks.Task<java.lang.Void> setDefaultsAsync(java.util.Map<java.lang.String, java.lang.Object> param0) {
        throw new java.lang.RuntimeException("Not Supported");
    }
    
    public static org.xms.f.remoteconfig.ExtensionRemoteConfig dynamicCast(java.lang.Object param0) {
        return ((org.xms.f.remoteconfig.ExtensionRemoteConfig) param0);
    }
    
    public static boolean isInstance(java.lang.Object param0) {
        if (!(param0 instanceof org.xms.g.utils.XGettable)) {
            return false;
        }
        if (org.xms.g.utils.GlobalEnvSetting.isHms()) {
            return ((org.xms.g.utils.XGettable) param0).getHInstance() instanceof com.huawei.agconnect.remoteconfig.AGConnectConfig;
        } else {
            return ((org.xms.g.utils.XGettable) param0).getGInstance() instanceof com.google.firebase.remoteconfig.FirebaseRemoteConfig;
        }
    }
}