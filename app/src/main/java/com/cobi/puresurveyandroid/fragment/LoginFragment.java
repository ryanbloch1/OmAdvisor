package com.cobi.puresurveyandroid.fragment;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.KeyguardManager;
import android.content.DialogInterface;
import android.content.Intent;

import androidx.biometric.BiometricPrompt;
import androidx.databinding.DataBindingUtil;

import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.os.Handler;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cobi.puresurveyandroid.R;
import com.cobi.puresurveyandroid.activity.BaseActivity;
import com.cobi.puresurveyandroid.activity.MatrixSelectionActivity;
import com.cobi.puresurveyandroid.activity.NewPasswordActivity;
import com.cobi.puresurveyandroid.activity.ResetCredentialActivity;
import com.cobi.puresurveyandroid.activity.SalesActivity;
import com.cobi.puresurveyandroid.activity.SalesCodesActivity;
import com.cobi.puresurveyandroid.databinding.FragmentLoginBinding;
import com.cobi.puresurveyandroid.model.AnalyticsRequests.ActionRequest;
import com.cobi.puresurveyandroid.model.AnalyticsRequests.ActionTypes;
import com.cobi.puresurveyandroid.model.ChannelRole;
import com.cobi.puresurveyandroid.model.ErrorResponse;
import com.cobi.puresurveyandroid.model.IMEDDetails;
import com.cobi.puresurveyandroid.model.ImedTokenResponse;
import com.cobi.puresurveyandroid.model.PartyPerson;
import com.cobi.puresurveyandroid.model.PartyRegistration;
import com.cobi.puresurveyandroid.model.PingDigitalIdResponse;
import com.cobi.puresurveyandroid.model.RefreshTokenResponse;
import com.cobi.puresurveyandroid.model.ValidateResponse;
import com.cobi.puresurveyandroid.util.AlertDialogHelper;
import com.cobi.puresurveyandroid.util.BiometricUtils;
import com.cobi.puresurveyandroid.util.PreferencesHelper;
import com.cobi.puresurveyandroid.webservice.ApiResponse.DigitalIdResponse;
import com.cobi.puresurveyandroid.webservice.ApiResponse.TokenResponse;
import com.cobi.puresurveyandroid.webservice.controller.AnalyticsController;
import com.cobi.puresurveyandroid.webservice.controller.CSIApiController;
import com.cobi.puresurveyandroid.webservice.controller.ImedAuthenticationController;
import com.cobi.puresurveyandroid.webservice.controller.PingAuthenticationController;
import com.cobi.puresurveyandroid.webservice.controller.SnapappAuthenticationApiController;
import org.xms.f.analytics.ExtensionAnalytics;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.List;
import java.util.concurrent.Executor;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

import static com.cobi.puresurveyandroid.PureSurveyApplication.IMED_API_KEY;
import static com.cobi.puresurveyandroid.PureSurveyApplication.IMED_APP_ID;
import static com.cobi.puresurveyandroid.activity.BaseActivity.CRED_KEY;
import static com.cobi.puresurveyandroid.util.PreferencesHelper.USING_BIOMETRIC;
import static com.cobi.puresurveyandroid.util.PreferencesHelper.getAccessToken;
import static javax.crypto.Cipher.DECRYPT_MODE;
import static javax.crypto.Cipher.ENCRYPT_MODE;

public class LoginFragment extends BaseFragment {

    private String userName;
    private String password;

    private Cipher cipher;
    private KeyStore keyStore;

    private FragmentLoginBinding mBinding;

    private FingerprintManager fingerprintManager;
    private KeyguardManager keyguardManager;

    private static final int REQUESTCODE_FINGERPRINT_ENROLLMENT = 0;

    private Handler handler = new Handler();

    private Executor executor = new Executor() {
        @Override
        public void execute(Runnable command) {
            handler.post(command);
        }
    };

    private String KEY_NAME = "AndroidKey";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false);

        if (PreferencesHelper.isUsingBiometric(getContext())) {

            Handler handler = new Handler();
            handler.post(new Runnable() {
                @Override
                public void run() {
                    showBiometricPrompt();
                }
            });
        }

        mBinding.forgotUsername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getContext(), ResetCredentialActivity.class);
                i.putExtra(CRED_KEY, "username");

                getActivity().startActivity(i);
            }
        });

        mBinding.forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getContext(), ResetCredentialActivity.class);
                i.putExtra(CRED_KEY, "password");

                getActivity().startActivity(i);
            }
        });

        mBinding.loginButton.requestFocus();

        mBinding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboard();
                validate();
            }
        });

        return mBinding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        registerEventBus();
    }

    private void showBiometricPrompt() {
        BiometricPrompt.PromptInfo promptInfo = new BiometricPrompt.PromptInfo.Builder().setTitle("Biometric login for my app").setSubtitle("Log in using your biometric credential").setNegativeButtonText("Cancel").build();

        BiometricPrompt biometricPrompt = new BiometricPrompt(getActivity(), executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                //                Toast.makeText(getContext(), "Authentication error: " + errString, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                //                BiometricPrompt.CryptoObject authenticatedCryptoObject = result.getCryptoObject();
                // User has verified the signature, cipher, or message
                // authentication code (MAC) associated with the crypto object,
                // so you can use it in your app's crypto-driven workflows.

                loginWithFingerprint();
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                Toast.makeText(getContext(), "Authentication failed", Toast.LENGTH_SHORT).show();
            }
        });

        // Displays the "log in" prompt.
        biometricPrompt.authenticate(promptInfo);
    }

    public String decrypt(String encryptedToken) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, IOException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, KeyStoreException, CertificateException, UnrecoverableKeyException {

        KeyStore keyStore = KeyStore.getInstance("AndroidKeyStore");
        keyStore.load(null);

        SecretKey secretKey = (SecretKey) keyStore.getKey(KEY_NAME, null);

        Cipher cipher = Cipher.getInstance(KeyProperties.KEY_ALGORITHM_AES + "/" + KeyProperties.BLOCK_MODE_CBC + "/" + KeyProperties.ENCRYPTION_PADDING_PKCS7);

        byte[] encryptionIv = Base64.decode(PreferencesHelper.getEncryptionIv(getContext()), Base64.DEFAULT);

        cipher.init(DECRYPT_MODE, secretKey, new IvParameterSpec(encryptionIv));

        byte[] stringBytes = Base64.decode(encryptedToken, android.util.Base64.DEFAULT);
        byte[] decryptedBytes = cipher.doFinal(stringBytes);
        return new String(decryptedBytes, "UTF-8");
    }

    private void loginWithFingerprint() {

        try {

            String base64Encrypted = PreferencesHelper.getEncryptedToken(getContext());

            String refreshToken = decrypt(base64Encrypted);

            PingAuthenticationController.refreshToken(getContext(), refreshToken);

            mBinding.progress.setVisibility(View.VISIBLE);
            mBinding.loginButton.setEnabled(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    private SecretKey generateKey() {

        try {

            KeyStore keyStore = KeyStore.getInstance("AndroidKeyStore");
            KeyGenerator keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore");

            keyStore.load(null);
            keyGenerator.init(new KeyGenParameterSpec.Builder(KEY_NAME, KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT).setBlockModes(KeyProperties.BLOCK_MODE_CBC).setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7).build());
            return keyGenerator.generateKey();
        } catch (KeyStoreException | IOException | CertificateException | NoSuchAlgorithmException | InvalidAlgorithmParameterException | NoSuchProviderException e) {

            e.printStackTrace();
            return null;
        }
    }

    private void validate() {
        try {
            userName = mBinding.userNameEditText.getText().toString();
            password = mBinding.passwordEditText.getText().toString();
            mBinding.loginBox.setEnabled(true);

            if (!TextUtils.isEmpty(userName) && !TextUtils.isEmpty(password)) {
                PingAuthenticationController.getToken(getContext(), userName, password);

                mBinding.progress.setVisibility(View.VISIBLE);
                mBinding.loginButton.setEnabled(false);
            } else if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(password)) {
                if (TextUtils.isEmpty(userName)) {
                    mBinding.userNameEditText.setError(getString(R.string.empty_username));
                }
                if (TextUtils.isEmpty(password)) {
                    mBinding.passwordEditText.setError(getString(R.string.empty_password));
                }
            }
        } catch (Exception ex) {
            Log.e("RETROFIT : ", ex.toString());
        }
    }

    public void showErrorMessage(String message) {
        mBinding.progress.setVisibility(View.GONE);
        mBinding.loginButton.setEnabled(true);
        mBinding.passwordEditText.setError(message);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSuccessResponse(final TokenResponse result) {
        if (result instanceof TokenResponse) {
            if (!TextUtils.isEmpty(result.getAccessToken())) {

                if (PreferencesHelper.getBooleanPreference(getContext(), USING_BIOMETRIC)) { //biometrics is already enabled when manually logging in.
                    String refreshToken = result.getRefreshToken();

                    storeEncryptedRefreshToken(refreshToken);

                    PingAuthenticationController.getDigitalID(getContext(), result.getAccessToken());
                } else if (!PreferencesHelper.getBooleanPreference(getContext(), USING_BIOMETRIC) && !PreferencesHelper.gethasCancelledBiometric(getContext())) { //user isnt yet using biometrics and hasn't cancelled biometrics

                    //check here if user has hardware + biometrics enabled

                    if (BiometricUtils.isHardwareSupported(getContext())) {

                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                        builder.setMessage("Would you like to authenticate with biometrics in future?");
                        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {

                                //maybe do check here to see if the fingerprint is available and if not give user option of adding a fingerprint, allow them to add one and continue....

                                if (!BiometricUtils.isFingerprintAvailable(getContext())) {

                                    dialog.dismiss();

                                    showAlert("You do not have any biometrics enrolled on your device. Please ensure that you enroll a biometric on your device before setting up Biometric Authentication", result.getAccessToken());
                                } else {
                                    PreferencesHelper.setIsUsingBiometric(getContext(), true);

                                    String refreshToken = result.getRefreshToken();

                                    storeEncryptedRefreshToken(refreshToken);

                                    PingAuthenticationController.getDigitalID(getContext(), result.getAccessToken());

                                    dialog.dismiss();
                                }
                            }
                        });

                        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                PreferencesHelper.setIsUsingBiometric(getContext(), false);

                                PreferencesHelper.sethasCancelledBiometric(getContext(), true);

                                PingAuthenticationController.getDigitalID(getContext(), result.getAccessToken());
                            }
                        });

                        AlertDialog dialog = builder.create();
                        dialog.show();
                    } else {
                        PingAuthenticationController.getDigitalID(getContext(), result.getAccessToken());
                    }
                } else {
                    PingAuthenticationController.getDigitalID(getContext(), result.getAccessToken());
                }
            } else {
//                showErrorMessage("Could not get access token");
            }
        }
    }

    private void showAlert(String message, final String accessToken) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        builder.setMessage(message);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {

                //maybe do check here to see if the fingerprint is available and if not give user option of adding a fingerprint, allow them to add one and continue....

                PingAuthenticationController.getDigitalID(getContext(), accessToken);

                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSuccessResponse(RefreshTokenResponse result) {

        if (result instanceof RefreshTokenResponse) {

            //encrypt new refresh token

            storeEncryptedRefreshToken(result.getTokenResponse().getRefreshToken());

            PingAuthenticationController.getDigitalID(getContext(), result.getTokenResponse().getAccessToken());
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSuccessResponse(PingDigitalIdResponse result) {


        if (result.getObpasswordchangeflag()!=null && result.getObpasswordchangeflag().equals("true")) {

            goToDestinationActivity(NewPasswordActivity.class);
        } else {
            if (!TextUtils.isEmpty(result.getDigitalID())) {
                CSIApiController.getIntermediaryDetails(getContext(), PreferencesHelper.getAccessToken(getContext()), PreferencesHelper.getCommonName(getContext()));
            }
        }
    }



    public void encrypt(String refreshToken, SecretKey secretKey) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException, NoSuchPaddingException, NoSuchAlgorithmException {

        Cipher cipher = Cipher.getInstance(KeyProperties.KEY_ALGORITHM_AES + "/" + KeyProperties.BLOCK_MODE_CBC + "/" + KeyProperties.ENCRYPTION_PADDING_PKCS7);

        cipher.init(ENCRYPT_MODE, secretKey);

        byte[] encryptionIv = cipher.getIV();

        PreferencesHelper.setEncryptionIv(getContext(), Base64.encodeToString(encryptionIv, Base64.DEFAULT));

        byte[] cipherText = cipher.doFinal(refreshToken.getBytes("UTF-8"));

        String data = Base64.encodeToString(cipherText, android.util.Base64.DEFAULT);

        PreferencesHelper.setEncryptedToken(getContext(), data);
    }

    private void storeEncryptedRefreshToken(String refreshToken) {

        try {

            SecretKey secretKey = generateKey();

            encrypt(refreshToken, secretKey);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        unregisterEventBus();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onErrorResponse(ErrorResponse errorResponse) {
        mBinding.progress.setVisibility(View.GONE);
        mBinding.loginButton.setEnabled(true);

        if (errorResponse.getRequest().equals(CSIApiController.CUSTOMER_REFRESH_TOKEN)) {
            AlertDialogHelper.showAlertDialog("Please login again with your credentials", getContext());

        }
        if (errorResponse.getRequest().equals(SnapappAuthenticationApiController.VALIDATE)) {
            goToDestinationActivity(SalesActivity.class);

            PreferencesHelper.setSalesCode(getContext(), null);


        }
        if (errorResponse.getRequest().equals(CSIApiController.CUSTOMER_PARTY_DETAILS)) {
            goToDestinationActivity(SalesActivity.class);

            PreferencesHelper.setSalesCode(getContext(), null);


        }
        if (errorResponse.getRequest().equals(CSIApiController.CUSTOMER_INTERMEDIARY_DETAILS)) {
            CSIApiController.getPartyDetails(getContext(), getAccessToken(getContext()));
        }
        else {
            showErrorMessage(errorResponse.getMessage());
        }
    }

    @Subscribe(sticky = false, threadMode = ThreadMode.MAIN)
    public void onSuccessResponse(IMEDDetails response) {
        mBinding.progress.setVisibility(View.GONE);
        mBinding.loginButton.setEnabled(true);

        AnalyticsController.PostAction(getContext(), new ActionRequest(PreferencesHelper.getDeviceId(getContext()), ActionTypes.Login.getNumVal(), ""));

        mFirebaseAnalytics.setUserId(PreferencesHelper.getCommonName(getContext()));  //digitalId should be set if user gets successful imeddetails response

        Bundle bundle = new Bundle();
        bundle.putString(ExtensionAnalytics.Param.getMETHOD(), "login");
        mFirebaseAnalytics.logEvent(ExtensionAnalytics.Event.getLOGIN(), bundle);

        navigateToActivity(response);
    }


    public void navigateToActivity(IMEDDetails userDetails) {
        try {
            if (userDetails.getIntermediaryDetails() != null && !userDetails.getIntermediaryDetails().isEmpty()) {

                List<ChannelRole> channels = userDetails.getIntermediaryDetails().get(0).getPerson().get(0).getChannelRole();

                if (channels != null && !channels.isEmpty()) {

                    if (channels.size() > 1) { //user has multple sakles codes
                        ((BaseActivity) getActivity()).gotoDestinationActivity(SalesCodesActivity.class);
                        return;
                    } else {

                        ImedAuthenticationController.register(getContext(), PreferencesHelper.getSalesCode(getContext()), PreferencesHelper.getCommonName(getContext()), PreferencesHelper.getClientEmail(getContext()), PreferencesHelper.getChannel(getContext()), PreferencesHelper.getRegion(getContext()), PreferencesHelper.getTeam(getContext()), PreferencesHelper.getRole(getContext()), PreferencesHelper.getSegment(getContext()), PreferencesHelper.getFirstName(getContext()), PreferencesHelper.getLastName(getContext()), PreferencesHelper.getImedToken(getContext()), PreferencesHelper.getArea(getContext()));

                        if (PreferencesHelper.getSegment(getContext()) != null && PreferencesHelper.getSegment(getContext()).equals("MFC-ZA")) {       //do check here for Retail mass....snap ap

                            SnapappAuthenticationApiController.validate(getContext(), PreferencesHelper.getSalesCode(getContext()));


                        } else {
                            goToDestinationActivity(SalesActivity.class);
                        }

//                        String salesCode = channels.get(0).getExternalReference();
//
//                        PreferencesHelper.setSalesCode(getContext(), salesCode);

                        return;
                    }
                } else {


                    //check party api


                    CSIApiController.getPartyDetails(getContext(), PreferencesHelper.getAccessToken(getContext()));


                }
            } else {
                CSIApiController.getPartyDetails(getContext(), PreferencesHelper.getAccessToken(getContext()));

            }
        } catch (Exception e) {
            e.printStackTrace();
//            CSIApiController.getPartyDetails(getContext(), PreferencesHelper.getAccessToken(getContext()));

        }
    }


    @Subscribe(sticky = false, threadMode = ThreadMode.MAIN)
    public void onSuccessResponse(ValidateResponse response) {
        if (response instanceof ValidateResponse) {
            goToDestinationActivity(MatrixSelectionActivity.class);
        }
    }


    @Subscribe(sticky = false, threadMode = ThreadMode.MAIN)
    public void onSuccessResponse(PartyPerson response) {


        if (response instanceof PartyPerson) {


            String salesCode = "";
            for(PartyRegistration partyRegistration : response.getPerson().get(0).getPartyRegistration() ){

                 if(partyRegistration.getIsRegisteredBySoftwareSystem().equals("MAGNIFY")){
                     salesCode = partyRegistration.getExternalReference();





                     break;
                 }

            }




            PreferencesHelper.setSalesCode(getContext(), salesCode);

            ImedAuthenticationController.register(getContext(), salesCode, PreferencesHelper.getCommonName(getContext()), PreferencesHelper.getClientEmail(getContext()), PreferencesHelper.getChannel(getContext()), PreferencesHelper.getRegion(getContext()), PreferencesHelper.getTeam(getContext()), PreferencesHelper.getRole(getContext()), "MFC-ZA", PreferencesHelper.getFirstName(getContext()), PreferencesHelper.getLastName(getContext()), PreferencesHelper.getImedToken(getContext()), PreferencesHelper.getArea(getContext()));


            SnapappAuthenticationApiController.validate(getContext(), salesCode);


        }

    }


}
