package com.cobi.puresurveyandroid.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.cobi.puresurveyandroid.model.UserCustomerEvent;

import com.google.gson.Gson;
import com.securepreferences.SecurePreferences;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;
import java.util.Queue;

public class PreferencesHelper{

    public static final String TAG = PreferencesHelper.class.getSimpleName();

    private static final String PREFERENCES = "VER_1_PURE_SURVEY_PREFS";
    private static final String UUID_KEY = "VER_1_UUID_KEY";
    private static final String STAFF_NUMBER_KEY = "VER_1_STAFF_NUMBER_KEY";
    private static final String SINCE_KEY = "VER_1_SINCE_KEY";
    private static final String FULL_NAME_KEY = "VER_1_FULL_NAME_KEY";
    private static final String PENDING_MESSAGE_KEY = "VER_1_PENDING_MESSAGE_KEY";
    private static final String UNLOCKED_KEY = "VER_1_UNLOCKED_KEY";
    private static final String IMEI_KEY = "VER_1_IMEI_KEY";
    private static final String IMSI_KEY = "VER_1_IMSI_KEY";
    private static final String ONBOARDING_KEY = "VER_1_ONBOARDING_";
    private static final String COMISSIONAMOUNT_KEY = "VER_1_COMMISSIONAMOUNT";
    private static final String COMISSIONAMOUNTDEFAULTS_KEY = "VER_1_COMMISSIONAMOUNT_DEFAULTS";
    private static final String COMMON_NAME = "VER_1_COMMON_NAME";
    private static final String ACCESS_TOKEN = "VER_1_ACCESS_TOKEN";
    private static final String REFRESH_TOKEN = "VER_1_REFRESH_TOKEN";
    private static final String HAS_MULTIPLE_SALES_CODES = "VER_1_HAS_MULTIPLE_SALES_CODES";
    private static final String SALES_CODE = "VER_1_SALES_CODE";
    private static final String CAMPAIGN_ID = "VER_1_CAMPAIGN_ID";
    private static final String GCS_ID = "VER_1_GCS_ID";
    private static final String SERIALIZED_MAP = "VER_SERIALIZED_MAP";
    private static final String UPDATED_DATE = "VER_1_UPDATED_DATE";
    private static final String EVENT_ID = "VER_1_EVENT_ID";
    private static final String SEGMENT_KEY = "VER_1_SEGMENT_KEY";
    private static final String EVENT_CHECKIN = "VER_1_CHECKIN_KEY";
    private static final String IMAGE_URL = "VER_1_IMAGE_URL";
    private static final String SPEAKER_ID_KEY = "VER_1_SPEAKER_ID";
    private static final String RATING_KEY = "VER_1_RATING_KEY";
    private static final String EVENT_READ_MORE_KEY = "VER_1_RATING_KEY";
    private static final String FIRSTNAME_KEY = "VER_1_FIRSTNAME_KEY";
    private static final String LASTNAME_KEY = "VER_1_LASTNAME__KEY";
    private static final String LOGGED_IN_KEY = "VER_1_LOGGED_IN_KEY";
    private static final String AREA_KEY = "VER_1_AREA_KEY";
    private static final String PROVINCE_KEY = "VER_1_PROVINCE_KEY";
    private static final String CONTACT_NO_KEY = "VER_1_CONTACT_NO_KEY";
    private static final String MISSED_PREMIUM_CONTRACT_NO = "VER_1_MISSED_P_CONTRACT_NO_KEY";
    private static final String CUSTOMER_FULL_NAME = "VER_1_MISSED_P_CUSTOMER_FULL_NAME";
    private static final String MOBILE_NO = "VER_1_MOBILE_NO";
    private static final String DEVICE_ID = "VER_1_DEVICE_ID";
    private static final String ENCRYPTION_IV = "VER_1_ENCRYPTION_IV";
    public static final String USING_BIOMETRIC = "VER_1_BIOMETRIC";
    public static final String CANCELLED_BIOMETRIC = "VER_1_CANCELLED_BIOMETRIC";
    public static final String ENCRYPTED_TOKEN = "VER_1_ENCRYPTED_TOKEN";
    public static final String BUSINESS_UNIT = "VER_1_BUSINESS_UNIT";
    public static final String EMPLOYEE_TYPE = "VER_1_EMPLOYEE_TYPE";
    public static final String IS_HOT_LEAD = "VER_1_HOT_LEAD";
    public static final String HAS_SHOWN_OVERLAY_RE = "VER_1_HAS_SHOWN_OVERLAY_RE";
    public static final String IMED_TOKEN = "VER_1_IMED_TOKEN";
    public static final String ROLE = "VER_1_ROLE";
    public static final String REGION = "VER_1_REGION";
    public static final String TEAM = "VER_1_TEAM";
    public static final String CHANNEL = "VER_1_CHANNEL";
    public static final String STAFF_ID = "VER_1_STAFF_ID";
    public static final String NOTIFICATIONS_OVERLAY = "VER_1_NOTIFICATIONS";
    public static final String COUNTRY = "VER_1_COUNTRY";


    //temp store of client name for demo purposes
    private static final String CLIENT_NAME = "VER_1_CLIENT_NAME";

    private static final String EMAIL = "VER_1_EMAIL";

    private static SecurePreferences securePreferences;

    private static final String GOOGLE_CLOUD_MESSAGING_REGISTRATION_ID_KEY = "VER_1_GOOGLE_CLOUD_MESSAGING_REGISTRATION_ID_KEY";

    private static String getStringPreference(Context context, String key) {
        if (context == null) {
            return null;
        }

        return getSharedPreferences(context).getString(key, null);
    }

    private static void setStringPreference(Context context, String key, String value) {
        if (context == null) {
            return;
        }

        SharedPreferences sharedPreferences = getSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        if (!editor.commit()) {
            Log.e(TAG, "FAILED to store preference!");
        }
    }

    public static Date getLoggedIn(Context context) {
        String sinceString = getStringPreference(context, LOGGED_IN_KEY);
        return DateHelper.ISO8601ToDate(sinceString);
    }

    public static void setLoggedIn(Context context, Date since) {
        String sinceString = DateHelper.dateToISO8601(since);
        setStringPreference(context, LOGGED_IN_KEY, sinceString);
    }

    private static int getIntPreference(Context context, String key) {
        if (context == null) {
            return 0;
        }

        return getSharedPreferences(context).getInt(key, 0);
    }

    private static void setIntPreference(Context context, String key, int value) {
        if (context == null) {
            return;
        }

        SharedPreferences sharedPreferences = getSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        if (!editor.commit()) {
            Log.e(TAG, "FAILED to store preference!");
        }
    }

    private static long getLongPreference(Context context, String key) {
        if (context == null) {
            return 0;
        }

        return getSharedPreferences(context).getLong(key, 0);
    }

    private static void setLongPreference(Context context, String key, long value) {
        if (context == null) {
            return;
        }

        SharedPreferences sharedPreferences = getSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(key, value);
        if (!editor.commit()) {
            Log.e(TAG, "FAILED to store preference!");
        }
    }

    public static boolean getBooleanPreference(Context context, String key) {
        if (context == null) {
            return false;
        }
        return getSharedPreferences(context).getBoolean(key, false);
    }

    private static void setBooleanPreference(Context context, String key, boolean value) {
        if (context == null) {
            return;
        }

        SharedPreferences sharedPreferences = getSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        if (!editor.commit()) {
            Log.e(TAG, "FAILED to store preference!");
        }
    }

    public static void writeObjectToFile(Context context, Object object, String filename) {
        Gson gson = new Gson();
        String objStr = gson.toJson(object);
        writeToFile(context, objStr, filename);
    }

    public static Object readObjectFromFile(Context context, Class type, String filename) {
        String objString = readFromFile(context, filename);
        Object object = null;
        if (objString != null && objString.length() > 0) {
            try {
                Gson gson = new Gson();
                object = gson.fromJson(objString, type);
            } catch (Exception e) {

            }
        }
        return object;
    }

    public static <T> List<T> readListFromFile(Context context, Class<T> type, String filename) {
        String objString = readFromFile(context, filename);
        List<T> list = null;
        if (objString != null && objString.length() > 0) {
            try {
                Gson gson = new Gson();
                list = gson.fromJson(objString, getType(List.class, type));
            } catch (Exception e) {

            }
        }
        return list;
    }

    public static <T> Queue<T> readQueueFromFile(Context context, Class<T> type, String filename) {
        String objString = readFromFile(context, filename);
        Queue<T> queue = null;
        if (objString != null && objString.length() > 0) {
            try {
                Gson gson = new Gson();
                queue = gson.fromJson(objString, getType(Queue.class, type));
            } catch (Exception e) {

            }
        }
        return queue;
    }

    private static void writeToFile(Context context, String data, String filename) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(filename, Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        } catch (IOException e) {
            Log.e(TAG, "File write failed: " + e.toString());
        }
    }

    private static String readFromFile(Context context, String filename) {
        String ret = "";
        if (context == null) {
            return ret;
        }
        try {
            InputStream inputStream = context.openFileInput(filename);

            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString;
                StringBuilder stringBuilder = new StringBuilder();

                while ((receiveString = bufferedReader.readLine()) != null) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        } catch (FileNotFoundException e) {
            Log.e(TAG, "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e(TAG, "Can not read file: " + e.toString());
        } catch (Exception e) {
            Log.e(TAG, "Error reading file: " + e.toString());
        }
        return ret;
    }

    private static Type getType(final Class<?> rawClass, final Class<?> parameter) {
        return new ParameterizedType() {
            @Override
            public Type[] getActualTypeArguments() {
                return new Type[]{parameter};
            }

            @Override
            public Type getRawType() {
                return rawClass;
            }

            @Override
            public Type getOwnerType() {
                return null;
            }
        };
    }

    public static boolean isUnlocked(Context context) {
        return getBooleanPreference(context, UNLOCKED_KEY);
    }

    public static void setIMSI(Context context, String imsi) {
        setStringPreference(context, IMSI_KEY, imsi);
    }

    public static String getIMSI(Context context) {
        return getStringPreference(context, IMSI_KEY);
    }

    public static void setIMEI(Context context, String imei) {
        setStringPreference(context, IMEI_KEY, imei);
    }

    public static String getIMEI(Context context) {
        return getStringPreference(context, IMEI_KEY);
    }

    public static void setUnlocked(Context context, boolean locked) {
        setBooleanPreference(context, UNLOCKED_KEY, locked);
    }

    public static UserCustomerEvent getPendingMessage(Context context) {
        String filename = UserCustomerEvent.class.getSimpleName().toLowerCase() + ".json";
        return (UserCustomerEvent) readObjectFromFile(context, UserCustomerEvent.class, filename);
    }

    public static void setPendingMessage(Context context, UserCustomerEvent event) {
        String filename = UserCustomerEvent.class.getSimpleName().toLowerCase() + ".json";
        writeObjectToFile(context, event, filename);
    }

    public static String getFullName(Context context) {
        return getStringPreference(context, FULL_NAME_KEY);
    }

    public static void setFullName(Context context, String name) {
        setStringPreference(context, FULL_NAME_KEY, name);
    }

    public static String getCountryReg(Context context) {
        return getStringPreference(context, COUNTRY);
    }

    public static void setCountryReg(Context context, String name) {
        setStringPreference(context, COUNTRY, name);
    }

    public static String getCustomerFullName(Context context) {
        return getStringPreference(context, CUSTOMER_FULL_NAME);
    }

    public static void setCustomerFullName(Context context, String name) {
        setStringPreference(context, CUSTOMER_FULL_NAME, name);
    }

    public static String getImedToken(Context context) {
        return getStringPreference(context, IMED_TOKEN);
    }

    public static void setImedToken(Context context, String name) {
        setStringPreference(context, IMED_TOKEN, name);
    }

    public static String getStaffId(Context context) {
        return getStringPreference(context, STAFF_ID);
    }

    public static void setStaffId(Context context, String name) {
        setStringPreference(context, STAFF_ID, name);
    }

    public static String getEncryptionIv(Context context) {
        return getStringPreference(context, ENCRYPTION_IV);
    }

    public static void setEncryptionIv(Context context, String eiv) {
        setStringPreference(context, ENCRYPTION_IV, eiv);
    }

    public static Boolean isUsingBiometric(Context context) {
        return getSharedPreferences(context).getBoolean(USING_BIOMETRIC, false);
    }

    public static void setIsUsingBiometric(Context context, Boolean is) {
        setBooleanPreference(context, USING_BIOMETRIC, is);
    }

    public static Boolean getIsHotLead(Context context) {
        return getSharedPreferences(context).getBoolean(IS_HOT_LEAD, false);
    }

    public static void setIsHotLead(Context context, Boolean is) {
        setBooleanPreference(context, IS_HOT_LEAD, is);
    }

    public static Boolean gethasCancelledBiometric(Context context) {
        return getSharedPreferences(context).getBoolean(CANCELLED_BIOMETRIC, false);
    }

    public static void sethasCancelledBiometric(Context context, Boolean is) {
        setBooleanPreference(context, CANCELLED_BIOMETRIC, is);
    }


    public static String getMobileNo(Context context) {
        return getStringPreference(context, MOBILE_NO);
    }

    public static void setMobileNo(Context context, String name) {
        setStringPreference(context, MOBILE_NO, name);
    }

    public static String getArea(Context context) {
        return getStringPreference(context, AREA_KEY);
    }

    public static void setArea(Context context, String name) {
        setStringPreference(context, AREA_KEY, name);
    }

    public static String getRegion(Context context) {
        return getStringPreference(context, REGION);
    }

    public static void setRegion(Context context, String name) {
        setStringPreference(context, REGION, name);
    }


    public static String getChannel(Context context) {
        return getStringPreference(context, CHANNEL);
    }

    public static void setChannel(Context context, String name) {
        setStringPreference(context, CHANNEL, name);
    }


    public static String getRole(Context context) {
        return getStringPreference(context, ROLE);
    }

    public static void setRole(Context context, String name) {
        setStringPreference(context, ROLE, name);
    }


    public static String getTeam(Context context) {
        return getStringPreference(context, TEAM);
    }

    public static void setTeam(Context context, String name) {
        setStringPreference(context, TEAM, name);
    }


    public static String getProvince(Context context) {
        return getStringPreference(context, PROVINCE_KEY);
    }


    public static void setProvince(Context context, String name) {
        setStringPreference(context, PROVINCE_KEY, name);
    }


    public static String getDeviceId(Context context) {
        return getStringPreference(context, DEVICE_ID);
    }


    public static void setDeviceId(Context context, String name) {
        setStringPreference(context, DEVICE_ID, name);
    }

    public static void setMissedPContractNo(Context context, String name) {
        setStringPreference(context, MISSED_PREMIUM_CONTRACT_NO, name);
    }

    public static String gettMissedPContractNo(Context context) {
        return getStringPreference(context, MISSED_PREMIUM_CONTRACT_NO);
    }


    public static String getContactNo(Context context) {
        return getStringPreference(context, CONTACT_NO_KEY);
    }

    public static void setContactNo(Context context, String name) {
        setStringPreference(context, CONTACT_NO_KEY, name);
    }

    public static String getFirstName(Context context) {
        return getStringPreference(context, FIRSTNAME_KEY);
    }

    public static void setFirstName(Context context, String name) {
        setStringPreference(context, FIRSTNAME_KEY, name);
    }

    public static String getLastName(Context context) {
        return getStringPreference(context, LASTNAME_KEY);
    }

    public static void setLastName(Context context, String name) {
        setStringPreference(context, LASTNAME_KEY, name);
    }

    public static String getEncryptedToken(Context context) {
        return getStringPreference(context, ENCRYPTED_TOKEN);
    }


    public static void setEncryptedToken(Context context, String s) {
        setStringPreference(context, ENCRYPTED_TOKEN, s);
    }


    public static String getUpdatedDate(Context context) {
        return getStringPreference(context, UPDATED_DATE);
    }

    public static void setUpdatedDate(Context context, String date) {
        setStringPreference(context, UPDATED_DATE, date);
    }

    public static Date getSince(Context context) {
        String sinceString = getStringPreference(context, SINCE_KEY);
        return DateHelper.ISO8601ToDate(sinceString);
    }

    public static void setSince(Context context, Date since) {
        String sinceString = DateHelper.dateToISO8601(since);
        setStringPreference(context, SINCE_KEY, sinceString);
    }

    public static String getUUUID(Context context) {
        return getStringPreference(context, UUID_KEY);
    }

    public static void setUUID(Context context, String uuid) {
        setStringPreference(context, UUID_KEY, uuid);
    }

    public static String getSegment(Context context) {
        return getStringPreference(context, SEGMENT_KEY);
    }

    public static void setSegment(Context context, String uuid) {
        setStringPreference(context, SEGMENT_KEY, uuid);
    }

    public static String getStaffNumber(Context context) {
        return getStringPreference(context, STAFF_NUMBER_KEY);
    }

    public static void setStaffNumber(Context context, String uuid) {
        setStringPreference(context, STAFF_NUMBER_KEY, uuid);
    }

    public static String getCloudMessagingRegistrationId(Context context) {
        return getStringPreference(context, GOOGLE_CLOUD_MESSAGING_REGISTRATION_ID_KEY);
    }

    public static void setCloudMessagingRegistrationId(Context context, String regId) {
        setStringPreference(context, GOOGLE_CLOUD_MESSAGING_REGISTRATION_ID_KEY, regId);
    }

    public static Boolean hasShowOnboarding(Context context) {
        return getSharedPreferences(context).getBoolean(ONBOARDING_KEY, false);
    }

    public static void setShownOnboarding(Context context, Boolean shown) {
        setBooleanPreference(context, ONBOARDING_KEY, shown);
    }

    public static Boolean hasShownOverlayReinter(Context context) {
        return getSharedPreferences(context).getBoolean(HAS_SHOWN_OVERLAY_RE, false);
    }

    public static void setHasShownOverlayReinter(Context context, Boolean shown) {
        setBooleanPreference(context, HAS_SHOWN_OVERLAY_RE, shown);
    }


    public static Boolean hasShownOverlayNotifications(Context context) {
        return getSharedPreferences(context).getBoolean(NOTIFICATIONS_OVERLAY,false);
    }

    public static void hasShownOverlayNotifications(Context context, Boolean shown) {
        setBooleanPreference(context, NOTIFICATIONS_OVERLAY, shown);
    }



    public static Boolean getEventReadMoreFlag(Context context) {
        return getSharedPreferences(context).getBoolean(EVENT_READ_MORE_KEY, false);
    }

    public static void setEventReadMoreFlag(Context context, Boolean flag) {
        setBooleanPreference(context, EVENT_READ_MORE_KEY, flag);
    }

    private static SharedPreferences getSharedPreferences(Context context) {
        if (securePreferences == null) {
            securePreferences = new SecurePreferences(context, "UpBw0S7PLCB7WybEn8SVFlKeAKAhSxPtF0ETonxj", "preferences.xml");
        }

        return securePreferences;
    }

    //Login Flow

    public static String getCommonName(Context context) {
        return getStringPreference(context, COMMON_NAME);
    }

    public static void setCommonName(Context context, String digitalID) {
        setStringPreference(context, COMMON_NAME, digitalID);
    }

    public static String getAccessToken(Context context) {
        return getStringPreference(context, ACCESS_TOKEN);
    }

    public static void setAccessToken(Context context, String accessToken) {
        setStringPreference(context, ACCESS_TOKEN, accessToken);
    }

    public static String getRefreshoken(Context context) {
        return getStringPreference(context, REFRESH_TOKEN);
    }

    public static void setRefreshToken(Context context, String refreshToken) {
        setStringPreference(context, REFRESH_TOKEN, refreshToken);
    }

    public static void setHasMultipleSalesCodes(Context context, boolean multipleCodes) {
        setBooleanPreference(context, HAS_MULTIPLE_SALES_CODES, multipleCodes);
    }

    public static boolean hasMultipleSalesCodes(Context context) {
        return getBooleanPreference(context, HAS_MULTIPLE_SALES_CODES);
    }

    //storing  slaesCodeTargetHashMap

    // Ryan Storing sales code

    public static String getSalesCode(Context context) {
        return getStringPreference(context, SALES_CODE);
    }

    public static void setSalesCode(Context context, String salesCode) {
        setStringPreference(context, SALES_CODE, salesCode);
    }

    public static String getEmployeeType(Context context) {
        return getStringPreference(context, EMPLOYEE_TYPE);
    }

    public static void setEmployeeType(Context context, String string) {
        setStringPreference(context, EMPLOYEE_TYPE, string);
    }

    //Ryan storing gcsID

    public static String getGcsId(Context context) {
        return getStringPreference(context, GCS_ID);
    }

    public static void setGcsId(Context context, String gcsId) {
        setStringPreference(context, GCS_ID, gcsId);
    }

    //Ryan storing campaignID

    public static int getCampaignId(Context context) {
        return getIntPreference(context, CAMPAIGN_ID);
    }

    public static void setCampaignId(Context context, int setCampaignId) {
        setIntPreference(context, CAMPAIGN_ID, setCampaignId);
    }

    //Storing event_id
    public static String getEventId(Context context) {
        return getStringPreference(context, EVENT_ID);
    }

    public static void setEventId(Context context, String eventID) {
        setStringPreference(context, EVENT_ID, eventID);
    }

    //storing checkin bool

    public static void setIsCheckIn(Context context, boolean checkIn) {
        setBooleanPreference(context, EVENT_CHECKIN, checkIn);
    }

    public static boolean getIsCheckedIn(Context context) {
        return getBooleanPreference(context, EVENT_CHECKIN);
    }

    //Ryan storing HashMap

    public static String getSerializedMap(Context context) {
        return getStringPreference(context, SERIALIZED_MAP);
    }

    public static void setSerializedMap(Context context, String setSerializedMap) {
        setStringPreference(context, SERIALIZED_MAP, setSerializedMap);
    }

    //storing event image url

    public static String getEventImageUrl(Context context) {
        return getStringPreference(context, IMAGE_URL);
    }

    public static void setEventImageUr(Context context, String url) {
        setStringPreference(context, IMAGE_URL, url);
    }

    //temp store of client name

    public static String getClientName(Context context) {
        return getStringPreference(context, CLIENT_NAME);
    }

    public static void setClientName(Context context, String name) {
        setStringPreference(context, CLIENT_NAME, name);
    }


    public static String getBusinessUnit(Context context) {
        return getStringPreference(context, BUSINESS_UNIT);
    }

    public static void setBusinessUnit(Context context, String name) {
        setStringPreference(context, BUSINESS_UNIT, name);
    }

    //temp store of client email

    public static String getClientEmail(Context context) {
        return getStringPreference(context, EMAIL);
    }

    public static void setClientEmail(Context context, String email) {
        setStringPreference(context, EMAIL, email);
    }

    //setting speaker id

    public static String getSpeakerId(Context context) {
        return getStringPreference(context, SPEAKER_ID_KEY);
    }

    public static void setSpeakerId(Context context, String id) {
        setStringPreference(context, SPEAKER_ID_KEY, id);
    }

    //Willem: Store commission values

    public static int getCommissionAmount(Context context) {
        return getIntPreference(context, COMISSIONAMOUNT_KEY);
    }

    public static void setCommissionAmount(Context context, int value) {
        setIntPreference(context, COMISSIONAMOUNT_KEY, value);
    }

    public static boolean getIsDefaultValues(Context context) {
        return getBooleanPreference(context, COMISSIONAMOUNTDEFAULTS_KEY);
    }

    public static void setIsDefaultValues(Context context, boolean value) {
        setBooleanPreference(context, COMISSIONAMOUNTDEFAULTS_KEY, value);
    }

    //Checking what is being rated, speaker or event..

    public static boolean getRatingSpeaker(Context context) {
        return getBooleanPreference(context, RATING_KEY);
    }

    public static void setRatingSpeaker(Context context, boolean value) {
        setBooleanPreference(context, RATING_KEY, value);
    }
}
