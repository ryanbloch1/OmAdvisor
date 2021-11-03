package com.cobi.puresurveyandroid.activity;

import android.net.http.SslCertificate;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebView;

import com.cobi.puresurveyandroid.R;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.Authenticator;
import okhttp3.Credentials;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;
import retrofit2.Retrofit;


public class LoginAuthActivity extends AppCompatActivity {
    public  String URL = "https://secure.dcc.oldmutual.co.za.qas/";
    public  WebView webView;
    Retrofit retrofit = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_auth);

//        if (android.os.Build.VERSION.SDK_INT > 9) {
//            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
//            StrictMode.setThreadPolicy(policy);
//        }
//       Http http = new Http();
//        try {
//            http.getToken(getApplicationContext());
//        } catch (CertificateException e) {
//            e.printStackTrace();
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        } catch (KeyStoreException e) {
//            e.printStackTrace();
//        } catch (KeyManagementException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


//        OkHttpClient client = new OkHttpClient();
//        try
//        {
////            client = new OkHttpClient.Builder()
////                    .sslSocketFactory(getSSLSocketFactory())
////                    .hostnameVerifier(getHostnameVerifier())
////                    .build();
//
//            OkHttpClient.Builder builder = new OkHttpClient.Builder();
//            builder.sslSocketFactory(getSSLSocketFactory());
//            builder.hostnameVerifier(getHostnameVerifier());
//            builder.authenticator(getBasicAuth("Markekussen", "Password@01"));
//            retrofit = new Retrofit.Builder()
//                    .baseUrl(URL)
//                    .client(builder.build())
//                    .addConverterFactory(GsonConverterFactory.create())
//                    .build();
//        }
//        catch(Exception ex)
//        {
//            Log.e("ERROR_CERT",ex.toString());
//        }







//        RequestBody requestBody =  new FormBody.Builder()
//                .add("grant_type", "authorization_code")
//                .add("client_id", "925975372698-4tijvsd5cgjis5kff2o2u068o6cjhg0u.apps.googleusercontent.com")
//                .add("client_secret", "Passsword@01")
//                .add("redirect_uri","")
//                .add("code", "4/4-GMMhmHCXhWEzkobqIHGG_EnNYYsAkukHspeYUk9E8")
//                .build();
//        final Request request = new Request.Builder()
//                .url(URL)
//                .post(requestBody)
//                .build();
//        client.newCall(request).enqueue(new Callback() {
//
//
//            @Override
//            public void onFailure(Call call, IOException e) {
//                Log.e("", e.toString());
//            }
//
//            @Override
//            public void onResponse(Call call, okhttp3.Response response) throws IOException {
//                try {
//                    JSONObject jsonObject = new JSONObject(response.body().toString());
//                    final String message = jsonObject.toString(5);
//                    Log.i("", message);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        });

//        try {
//             webView = (WebView) findViewById(R.id.webview);
//            if (webView != null) {
//                // Get cert from raw resource...
//                CertificateFactory cf = CertificateFactory.getInstance("X.509");
//                InputStream caInput = getResources().openRawResource(R.raw.old_mutual_issuing_ca_001); // stored at \app\src\main\res\raw
//                final Certificate certificate = cf.generateCertificate(caInput);
//                caInput.close();
//
//                String url = URL;
//                webView.setWebViewClient(new WebViewClient() {
//
//                    public void onPageStarted(WebView view, String url, Bitmap favicon) {
//                        // Is this the callback url?
//
//                        String fragment = "?code="; //https://secure.dcc.oldmutual.co.za.qas/oam/server/auth_cred_submit
//                        int start = url.indexOf(fragment);
//                        if (start > -1) {
//                            // Yeah, stop loading then
//
//
//                            webView.stopLoading();
//                            // And get the code parameter
//
//                            String code = url.substring(start+fragment.length(), url.length());
//                        }
//                    }
//
//                    @Override
//                    public void onPageFinished(WebView view,String url){
//
//
//                        super.onPageFinished(view, url);
//                        System.out.println(">>>>>>>>>>>>>>onPageFinished>>>>>>>>>>>>>>>>>>");
//                        view.stopLoading();
//                    }
//
//                    @Override
//                    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
//                        // Get cert from SslError
//                        SslCertificate sslCertificate = error.getCertificate();
//                        Certificate cert = getX509Certificate(sslCertificate);
//                        if (cert != null && certificate != null){
//                            try {
//                                // Reference: https://developer.android.com/reference/java/security/cert/Certificate.html#verify(java.security.PublicKey)
//                                cert.verify(certificate.getPublicKey()); // Verify here...
//                                handler.proceed();
//
//
//                            } catch (CertificateException | NoSuchAlgorithmException | InvalidKeyException | NoSuchProviderException | SignatureException e) {
//                                super.onReceivedSslError(view, handler, error);
//                                e.printStackTrace();
//                            }
//                        } else {
//                            super.onReceivedSslError(view, handler, error);
//                        }
//                    }
//                });
//
//                webView.loadUrl(url);
//
//            }
//        } catch (Exception e){
//            e.printStackTrace();
//        }
    }


    protected Authenticator getBasicAuth(final String username, final String password) {
        return new Authenticator() {
            private int mCounter = 0;

            @Override
            public Request authenticate(Route route, Response response) throws IOException {
                Log.e("OkHttp", "authenticate(Route route, Response response) | mCounter = " + mCounter);
                if (mCounter++ > 0) {
                    Log.d("OkHttp", "authenticate(Route route, Response response) | I'll return null");
                    return null;
                } else {
                    Log.e("OkHttp", "authenticate(Route route, Response response) | This is first time, I'll try to authenticate");
                    String credential = Credentials.basic(username, password);
                    return response.request().newBuilder().header("Authorization", credential).build();
                }
            }
        };
    }


        private Certificate getX509Certificate(SslCertificate sslCertificate){
        Bundle bundle = SslCertificate.saveState(sslCertificate);
        byte[] bytes = bundle.getByteArray("x509-certificate");
        if (bytes == null) {
            return null;
        } else {
            try {
                CertificateFactory certFactory = CertificateFactory.getInstance("X.509");
                return certFactory.generateCertificate(new ByteArrayInputStream(bytes));
            } catch (CertificateException e) {
                return null;
            }
        }
    }


    private HostnameVerifier getHostnameVerifier() {
        return new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true; // verify always returns true, which could cause insecure network traffic due to trusting TLS/SSL server certificates for wrong hostnames
                //HostnameVerifier hv = HttpsURLConnection.getDefaultHostnameVerifier();
                //return hv.verify("localhost", session);
            }
        };
    }

    private TrustManager[] getWrappedTrustManagers(TrustManager[] trustManagers) {
        final X509TrustManager originalTrustManager = (X509TrustManager) trustManagers[0];
        return new TrustManager[]{
                new X509TrustManager() {
                    public X509Certificate[] getAcceptedIssuers() {
                        return originalTrustManager.getAcceptedIssuers();
                    }

                    public void checkClientTrusted(X509Certificate[] certs, String authType) {
                        try {
                            if (certs != null && certs.length > 0){
                                certs[0].checkValidity();
                            } else {
                                originalTrustManager.checkClientTrusted(certs, authType);
                            }
                        } catch (CertificateException e) {
                            Log.w("checkClientTrusted", e.toString());
                        }
                    }

                    public void checkServerTrusted(X509Certificate[] certs, String authType) {
                        try {
                            if (certs != null && certs.length > 0){
                                certs[0].checkValidity();
                            } else {
                                originalTrustManager.checkServerTrusted(certs, authType);
                            }
                        } catch (CertificateException e) {
                            Log.w("checkServerTrusted", e.toString());
                        }
                    }
                }
        };
    }

    private SSLSocketFactory getSSLSocketFactory()
            throws CertificateException, KeyStoreException, IOException,
            NoSuchAlgorithmException, KeyManagementException {
        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        InputStream caInput = getResources().openRawResource(R.raw.old_mutual_issuing_ca_001); // File path: app\src\main\res\raw\your_cert.cer
        Certificate ca = cf.generateCertificate(caInput);
        caInput.close();
        KeyStore keyStore = KeyStore.getInstance("BKS");
        keyStore.load(null, null);
        keyStore.setCertificateEntry("ca", ca);
        String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
        TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
        tmf.init(keyStore);
        TrustManager[] wrappedTrustManagers = getWrappedTrustManagers(tmf.getTrustManagers());
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, wrappedTrustManagers, null);
        return sslContext.getSocketFactory();
    }



}
