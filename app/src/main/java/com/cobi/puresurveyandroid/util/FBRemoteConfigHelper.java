package com.cobi.puresurveyandroid.util;

import com.cobi.puresurveyandroid.R;
import org.xms.f.remoteconfig.ExtensionRemoteConfig;

public class FBRemoteConfigHelper {

    //sales_commission_target_minimum
    public static String getSCTargetMinimum() {
        return ExtensionRemoteConfig.getInstance().getString("sales_commission_target_minimum");
    }

    //sales_commission_target_maximum
    public static String getSCTargetMaximum() {
        return ExtensionRemoteConfig.getInstance().getString("sales_commission_target_maximum");
    }

    //feature_deep_link_activation
    public static boolean getDeepLinkActivated() {
        return ExtensionRemoteConfig.getInstance().getBoolean("feature_deep_link_activation");
    }

    //terms_and_conditions_url
    public static String getTermsAndConditionsUrl() {
        return ExtensionRemoteConfig.getInstance().getString("terms_and_conditions_url");
    }

    public static String getClientIdQa() {
        return ExtensionRemoteConfig.getInstance().getString("client_id_qa");
    }

    public static String getClientSecretQa() {
        return ExtensionRemoteConfig.getInstance().getString("client_secret_qa");
    }

    public static String getClientSecret() {
        return ExtensionRemoteConfig.getInstance().getString(ResourceHelper.getString(R.string.client_secret));
    }

    public static String getClientId() {
        return ExtensionRemoteConfig.getInstance().getString(ResourceHelper.getString(R.string.client_id));
    }
}
