package com.cobi.puresurveyandroid.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.Toast;

import com.cobi.puresurveyandroid.R;
import com.cobi.puresurveyandroid.databinding.ActivityWebviewBinding;
import com.cobi.puresurveyandroid.fragment.EventFragment;
import com.cobi.puresurveyandroid.util.PreferencesHelper;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.cobi.puresurveyandroid.PureSurveyApplication.IMED_API_KEY;
import static com.cobi.puresurveyandroid.PureSurveyApplication.IMED_APP_ID;

/**
 * Created by admin on 2018/03/29.
 */

public class WebviewActivity extends BaseActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    WebView webView;
    private static final String TAG = WebviewActivity.class.getSimpleName();
    private String mCM;
    private ValueCallback<Uri> mUM;
    private ValueCallback<Uri[]> mUMA;
    private ActivityWebviewBinding mBinding;
    ImageView progressBar;
    public static String URL_EXTRA_KEY = "url_extra_key";
    private final static int FCR = 1;
    String mUrl;

    //select whether you want to upload multiple files (set 'true' for yes)
    private boolean multiple_files = false;

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_matrix:

                gotoDestinationActivity(MatrixSelectionActivity.class);

                break;
            case R.id.action_events:

                mBinding.webview.setVisibility(View.GONE);
                showFragment(R.id.fragment_container, new EventFragment());
                mBinding.fragmentContainer.setVisibility(View.VISIBLE);

                break;
            default:
                return false;
        }

        return true;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (Build.VERSION.SDK_INT >= 21) {
            Uri[] results = null;
            //checking if response is positive
            if (resultCode == RESULT_OK) {
                if (requestCode == FCR) {
                    if (null == mUMA) {
                        return;
                    }
                    if (intent == null || intent.getData() == null) {
                        if (mCM != null) {
                            results = new Uri[]{Uri.parse(mCM)};
                        }
                    } else {
                        String dataString = intent.getDataString();
                        if (dataString != null) {
                            results = new Uri[]{Uri.parse(dataString)};
                        } else {
                            if (multiple_files) {
                                if (intent.getClipData() != null) {
                                    final int numSelectedFiles = intent.getClipData().getItemCount();
                                    results = new Uri[numSelectedFiles];
                                    for (int i = 0; i < numSelectedFiles; i++) {
                                        results[i] = intent.getClipData().getItemAt(i).getUri();
                                    }
                                }
                            }
                        }
                    }
                }
            }
            mUMA.onReceiveValue(results);
            mUMA = null;
        } else {
            if (requestCode == FCR) {
                if (null == mUM) {
                    return;
                }
                Uri result = intent == null || resultCode != RESULT_OK ? null : intent.getData();
                mUM.onReceiveValue(result);
                mUM = null;
            }
        }
    }

    public boolean file_permission() {

            return true;

    }

    @Override
    protected void onResume() {
        super.onResume();


        if (!org.xms.g.utils.GlobalEnvSetting.isHms()) {
            FirebaseAnalytics instanceFA = FirebaseAnalytics.getInstance(this);
            instanceFA.setCurrentScreen(this, "Web Screen", null);
        }

    }

    @SuppressLint({"SetJavaScriptEnabled", "WrongViewCast"})
    @SuppressWarnings({"findViewById", "RedundantCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_webview);

        mBinding.bottomNavigation.setOnNavigationItemSelectedListener(this);

        initToolbarMenu();

        webView = (WebView) findViewById(R.id.webview);
        progressBar = findViewById(R.id.progress);
        assert webView != null;
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setAllowContentAccess(true);


        mUrl = getIntent().getStringExtra(URL_EXTRA_KEY);


        if (Build.VERSION.SDK_INT >= 21) {
            webSettings.setMixedContentMode(0);
            webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        } else if (Build.VERSION.SDK_INT >= 19) {
            webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        } else {
            webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
        webView.setWebViewClient(new Callback());//add your test web/page address here
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    if (request != null && !request.isForMainFrame() && request.getUrl() != null && request.getUrl().getPath() != null && request.getUrl().getPath().equals("/favicon.ico")) {
                        try {
                            return new WebResourceResponse("image/png", null, new BufferedInputStream(view.getContext().getAssets().open("empty_favicon.ico")));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                return null;
            }

            @Override
            public void onPageFinished(final WebView view, String url) {
                super.onPageFinished(view, url);
                progressBar.setVisibility(View.GONE);
                String pageTitle = view.getTitle();
                if (pageTitle.equals("about:blank")) {
                    showConnectionDialog(new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            reload(view);
                        }
                    });
                }
            }

            @Override
            public void onReceivedError(final WebView view, int errorCode, String description, String failingUrl) {
                progressBar.setVisibility(View.GONE);
                showConnectionDialog(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        reload(view);
                    }
                });
            }

            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onReceivedError(final WebView view, WebResourceRequest request, WebResourceError error) {

                progressBar.setVisibility(View.GONE);
                showConnectionDialog(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        reload(view);
                    }
                });
            }

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onReceivedHttpError(final WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {

                progressBar.setVisibility(View.GONE);
                showConnectionDialog(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        reload(view);
                    }
                });
            }
        });
        webView.setWebChromeClient(new WebChromeClient() {
            /*
             * openFileChooser is not a public Android API and has never been part of the SDK.
             */
            //handling input[type="file"] requests for android API 16+
            @SuppressLint("ObsoleteSdkInt")
            @SuppressWarnings("unused")
            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
                mUM = uploadMsg;
                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                i.addCategory(Intent.CATEGORY_OPENABLE);
                i.setType("*/*");
                if (multiple_files && Build.VERSION.SDK_INT >= 18) {
                    i.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                }
                startActivityForResult(Intent.createChooser(i, "File Chooser"), FCR);
            }

            //handling input[type="file"] requests for android API 21+
            @SuppressLint("InlinedApi")
            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {

                if (file_permission()) {
//                    String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
//
//                    //checking for storage permission to write images for upload
//                    if (ContextCompat.checkSelfPermission(WebviewActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(WebviewActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
//                        ActivityCompat.requestPermissions(WebviewActivity.this, perms, FCR);
//
//                        //checking for WRITE_EXTERNAL_STORAGE permission
//                    } else if (ContextCompat.checkSelfPermission(WebviewActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//                        ActivityCompat.requestPermissions(WebviewActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, FCR);
//
//                        //checking for CAMERA permissions
//                    } else if (ContextCompat.checkSelfPermission(WebviewActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
//                        ActivityCompat.requestPermissions(WebviewActivity.this, new String[]{Manifest.permission.CAMERA}, FCR);
//                    }
                    if (mUMA != null) {
                        mUMA.onReceiveValue(null);
                    }
                    mUMA = filePathCallback;
                    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                        File photoFile = null;
                        try {
                            photoFile = createImageFile();
                            takePictureIntent.putExtra("PhotoPath", mCM);
                        } catch (IOException ex) {
                            Log.e(TAG, "Image file creation failed", ex);
                        }
                        if (photoFile != null) {
                            mCM = "file:" + photoFile.getAbsolutePath();
                            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                        } else {
                            takePictureIntent = null;
                        }
                    }
                    Intent contentSelectionIntent = new Intent(Intent.ACTION_GET_CONTENT);
                    contentSelectionIntent.addCategory(Intent.CATEGORY_OPENABLE);
                    contentSelectionIntent.setType("*/*");
                    if (multiple_files) {
                        contentSelectionIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                    }
                    Intent[] intentArray;
                    if (takePictureIntent != null) {
                        intentArray = new Intent[]{takePictureIntent};
                    } else {
                        intentArray = new Intent[0];
                    }

                    Intent chooserIntent = new Intent(Intent.ACTION_CHOOSER);
                    chooserIntent.putExtra(Intent.EXTRA_INTENT, contentSelectionIntent);
                    chooserIntent.putExtra(Intent.EXTRA_TITLE, "File Chooser");
                    chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, intentArray);
                    startActivityForResult(chooserIntent, FCR);
                    return true;
                } else {
                    return false;
                }
            }
        });


       switch (mUrl) {
            case "connect":
                webView.loadUrl("https://"+this.getString(R.string.base_url_remotesales)+"/Admin/Connect", getAuthHeaders("37c8980d-24cf-4bcc-a565-b4279f63992f", "MU0zZF9jM050cjRsLSR1dEguNVlzVDNtOlchdGgrIUQ9NDczOTYyMjFjMjZjNDEyNWJjN2JjMDUxZTkxMTY2ODA="));
                break;
            case "fmconnect":
                webView.loadUrl("https://"+this.getString(R.string.base_url_remotesales)+"/fm/Admin/Connect", getAuthHeaders("37c8980d-24cf-4bcc-a565-b4279f63992f", "MU0zZF9jM050cjRsLSR1dEguNVlzVDNtOlchdGgrIUQ9NDczOTYyMjFjMjZjNDEyNWJjN2JjMDUxZTkxMTY2ODA="));
                break;
            case "hcwh":
                webView.loadUrl("https://"+ this.getString(R.string.base_url_imed)+"central/HowCanWeHelp/connect", getAuthHeaders(IMED_APP_ID, IMED_API_KEY));
                break;
            default:
                webView.loadUrl(mUrl);
                break;
        }
    }


    private Map<String, String> getAuthHeaders(String appId, String apiKey) {

        Map<String, String> extraHeaders = new HashMap<String, String>();

        extraHeaders.put("AppId", appId);
        extraHeaders.put("ApiKey", apiKey);
        extraHeaders.put("Authorization", "Bearer " + PreferencesHelper.getImedToken(this));
        extraHeaders.put("staffCode", PreferencesHelper.getSalesCode(this));
        extraHeaders.put("surname", PreferencesHelper.getLastName(this));
        extraHeaders.put("Content-Type", "application/json");

        return extraHeaders;
    }


    //callback reporting if error occurs
    public class Callback extends WebViewClient {
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            Toast.makeText(getApplicationContext(), "Failed loading app!", Toast.LENGTH_SHORT).show();
        }
    }

    //creating new image file here
    private File createImageFile() throws IOException {
        @SuppressLint("SimpleDateFormat") String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "img_" + timeStamp + "_";
        File storageDir = this.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        return File.createTempFile(imageFileName, ".jpg", storageDir);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    private void reload(WebView webview) {
        webview.reload();

        progressBar.setVisibility(View.VISIBLE);
    }
}
