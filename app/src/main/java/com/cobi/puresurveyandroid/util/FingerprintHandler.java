package com.cobi.puresurveyandroid.util;

import android.content.Context;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.CancellationSignal;

import androidx.annotation.RequiresApi;

@RequiresApi(api = Build.VERSION_CODES.M)
public class FingerprintHandler extends FingerprintManager.AuthenticationCallback {


    private Context context;


    public FingerprintHandler(Context context){
        this.context = context;
    }


    @RequiresApi(api = Build.VERSION_CODES.P)
    public void startAuth(FingerprintManager fingerprintManager, FingerprintManager.CryptoObject cryptoObject){

        CancellationSignal cancellationSignal = new CancellationSignal();


        fingerprintManager.authenticate(cryptoObject,cancellationSignal,0,this, null);



    }

    @Override
    public void onAuthenticationError(int errorCode, CharSequence errString) {
      //show toast error
    }

    @Override
    public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {

//

    }

    @Override
    public void onAuthenticationHelp(int helpCode, CharSequence helpString) {

    }

    @Override
    public void onAuthenticationFailed() {
        //show toast
    }
}
