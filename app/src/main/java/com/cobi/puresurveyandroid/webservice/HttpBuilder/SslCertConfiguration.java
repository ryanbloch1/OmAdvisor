package com.cobi.puresurveyandroid.webservice.HttpBuilder;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;

import com.cobi.puresurveyandroid.BuildConfig;
import com.cobi.puresurveyandroid.R;

import java.io.File;
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
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

import okhttp3.Cache;
import okhttp3.OkHttpClient;

public class SslCertConfiguration {
    private static final SslCertConfiguration ourInstance = new SslCertConfiguration();

    public static SslCertConfiguration getInstance() {
        return ourInstance;
    }

    private SslCertConfiguration()
    {

    }
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static OkHttpClient getSSLConfig(Context context) throws CertificateException, IOException,
            KeyStoreException, NoSuchAlgorithmException, KeyManagementException {

        try
        {
            final OkHttpClient client;

            File httpCacheDirectory = new File(context.getCacheDir(), "responses");
            int cacheSize = 10 * 1024 * 1024; // 10 MiB
            Cache cache = new Cache(httpCacheDirectory, cacheSize);

            OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
            builder.readTimeout(10, TimeUnit.SECONDS);
            builder.connectTimeout(20, TimeUnit.SECONDS);
            builder.writeTimeout(5, TimeUnit.MINUTES);
            builder.cache(cache);

            if (BuildConfig.IS_STAGING) {
                // Load CAs from an InputStream
                // (could be from a resource or ByteArrayInputStream or ...)
                CertificateFactory cf = CertificateFactory.getInstance("X.509");
                // get InputStream for the certificate
                InputStream caInput = context.getResources().openRawResource(R.raw.old_mutual_issuing_ca_001);
                Certificate ca;
                ca = cf.generateCertificate(caInput);
                System.out.println("ca=" + ((X509Certificate) ca).getSubjectDN());
                // Create a KeyStore containing our trusted CAs

                String keyStoreType = KeyStore.getDefaultType();
                KeyStore keyStore = KeyStore.getInstance(keyStoreType);
                keyStore.load(null, null);
                keyStore.setCertificateEntry("ca", ca);
                // Create a TrustManager that trusts the CAs in our KeyStore

                String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
                TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
                tmf.init(keyStore);
                // Create an SSLContext that uses our TrustManager

                SSLContext sslcontext = SSLContext.getInstance("TLS");
                sslcontext.init(null, tmf.getTrustManagers(), null);

                client = builder.sslSocketFactory(sslcontext.getSocketFactory()).build();
                caInput.close();
            } else {
                client = builder.build();
            }
            return client;
        }
        catch (Exception e)
        {
            return null;
        }
    }
}
