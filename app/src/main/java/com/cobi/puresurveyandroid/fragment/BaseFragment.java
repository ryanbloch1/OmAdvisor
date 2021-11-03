package com.cobi.puresurveyandroid.fragment;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.core.app.ShareCompat;

import io.michaelrocks.libphonenumber.android.NumberParseException;
import io.michaelrocks.libphonenumber.android.PhoneNumberUtil;
import io.michaelrocks.libphonenumber.android.Phonenumber;

import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.text.TextUtils;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.cobi.puresurveyandroid.PureSurveyApplication;
import com.cobi.puresurveyandroid.R;
import com.cobi.puresurveyandroid.activity.BaseActivity;
import com.cobi.puresurveyandroid.model.AnalyticsRequests.ActionRequest;
import com.cobi.puresurveyandroid.model.AnalyticsRequests.ActionTypes;
import com.cobi.puresurveyandroid.model.AnalyticsRequests.PageTypes;
import com.cobi.puresurveyandroid.model.AnalyticsRequests.RatingRequestAnalytics;
import com.cobi.puresurveyandroid.model.CSIContactDetails;
import com.cobi.puresurveyandroid.model.ErrorResponse;
import com.cobi.puresurveyandroid.model.MissedPremium;
import com.cobi.puresurveyandroid.util.AlertDialogHelper;
import com.cobi.puresurveyandroid.util.BitmapHelper;
import com.cobi.puresurveyandroid.util.FragmentUtility;
import com.cobi.puresurveyandroid.util.PreferencesHelper;
import com.cobi.puresurveyandroid.webservice.controller.AnalyticsController;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.xms.f.analytics.ExtensionAnalytics;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.net.Uri.withAppendedPath;

public abstract class BaseFragment extends Fragment {

    private static final String TAG = BaseFragment.class.getSimpleName();
    public FragmentUtility fragmentUtility = new FragmentUtility();
    public static final int RESULT_LOAD_IMAGE = 1;
    public final int PICK_IMAGE_CAMERA = 1, PICK_IMAGE_GALLERY = 2;

    public final Calendar myCalendar = Calendar.getInstance();

    int ratingScore;

    String comment;

    int pageType;

    protected ExtensionAnalytics mFirebaseAnalytics;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mFirebaseAnalytics = ExtensionAnalytics.getInstance(getContext());
    }


    @Override
    public void onResume() {
        super.onResume();

        registerEventBus();
    }

    @Override
    public void onPause() {
        super.onPause();

        unregisterEventBus();
    }

    protected void registerEventBus() {

        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
            Log.e("test", "register " + getClass().toString());
        }
    }

    protected void unregisterEventBus() {
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
            Log.e("test", "unregister " + getClass().toString());
        }
    }


    public void updateDateLabel(TextView dateError, TextView date, TextView timeError, TextView time) {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        dateError.setVisibility(View.GONE);
        date.setText(sdf.format(myCalendar.getTime()));
        showTimePicker(timeError, time);
    }

    public void showTimePicker(final TextView timeError, final TextView time) {
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);

        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                timeError.setVisibility(View.GONE);
                time.setText(String.format("%02d:%02d", selectedHour, selectedMinute) + ":00");
            }
        }, hour, minute, true);//Yes 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();
    }


    public boolean validCellPhone(String phone) {
        if (!Pattern.matches("[a-zA-Z]+", phone)) {
            return phone.length() == 10;
        }
        return false;
    }

    public void showSpinnerError(Spinner spinner) {
        ((TextView) spinner.getSelectedView()).setError("Please choose option");

        spinner.requestFocus();

        TextView errorText = (TextView) spinner.getSelectedView();
        errorText.setError("");
        errorText.setTextColor(Color.RED);//just to highlight that this is an error
        errorText.setText("Please select an option");
    }


    public void clearBackStack() {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        for (int i = 0; i < fm.getBackStackEntryCount(); ++i) {
            fm.popBackStack();
        }
    }

    public void hideKeyboard() {
        try {
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
            if ((getActivity().getCurrentFocus() != null && getActivity().getCurrentFocus().getWindowToken() != null)) {
                ((InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    String currentPhotoPath;

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */);

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getContext().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, PICK_IMAGE_CAMERA);
        }
    }

    public String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = getContext().getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                cursor.close();
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }

    // Select image from camera and gallery
    public void selectImage() {
        try {
            PackageManager pm = getContext().getPackageManager();
            int hasPerm = pm.checkPermission(Manifest.permission.CAMERA, getContext().getPackageName());
            if (hasPerm == PackageManager.PERMISSION_GRANTED) {
                final CharSequence[] options = {"Take Photo", "Choose From Gallery", "Cancel"};
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Select Option");
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if (options[item].equals("Take Photo")) {
                            dialog.dismiss();
                            dispatchTakePictureIntent();
                        } else if (options[item].equals("Choose From Gallery")) {
                            dialog.dismiss();
                            Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                            startActivityForResult(pickPhoto, PICK_IMAGE_GALLERY);
                        } else if (options[item].equals("Cancel")) {
                            dialog.dismiss();
                        }
                    }
                });
                builder.show();
            } else {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, 0);
            }
        } catch (Exception e) {
            Toast.makeText(getContext(), "Camera Permission error", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    public static boolean isValidEmail(final String email) {
        return Pattern.compile("^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@" + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?" + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\." + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?" + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|" + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$").matcher(email).matches();
    }

    private String generateJsonStringForContactAction(ActionTypes actionType, String contact) {

        String jsonString = "";

        try {
            if (actionType == ActionTypes.MissedPremiumsContactEmail || actionType == ActionTypes.MissedPremiumsContactSms || actionType == ActionTypes.MissedPremiumsContactPhone) {

                jsonString = String.valueOf(new JSONObject().put("fullName", PreferencesHelper.getCustomerFullName(getContext())).put("contact", contact).put("contractNo.", PreferencesHelper.gettMissedPContractNo(getContext())));
            } else if (actionType == ActionTypes.HelpPagePhone || actionType == ActionTypes.HelpPageEmail || actionType == ActionTypes.TAndCEmailContacted) {
                jsonString = "";
            } else {

                jsonString = String.valueOf(new JSONObject().put("fullName", PreferencesHelper.getCustomerFullName(getContext())).put("contact", contact).put("gCSId", PreferencesHelper.getGcsId(getContext())));
            }
        } catch (Exception e) {

        }
        return jsonString;
    }

    public String generateHotLeadsJson(String leadId, String salesCode) {

        String jsonString = "";

        try {
            jsonString = String.valueOf(new JSONObject().put("leadId", leadId).put("salesCode", salesCode));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return jsonString;
    }


    public void openWhatsappContact(String number, final boolean m) {
        Uri uri = Uri.parse("smsto:" + number);
        Intent i = new Intent(Intent.ACTION_SENDTO, uri);

        if (m) {
            i.putExtra(Intent.EXTRA_TEXT, "Amazing people deserve to have an amazing birthday.  May your birthday be out of this world!");

        }
        i.setPackage("com.whatsapp");
        startActivity(i);

//        PackageManager pm = getContext().getPackageManager();
//        try {
//            Uri uri = Uri.parse("smsto:" + number);
//            Intent waIntent = new Intent(Intent.ACTION_SEND, uri);
//            waIntent.setType("text/plain");
//
//
//            PackageInfo info = pm.getPackageInfo("com.whatsapp", PackageManager.GET_META_DATA);
//            //Check if package exists or not. If not then code
//            //in catch block will be called
//            waIntent.setPackage("com.whatsapp");
//            if (m) {
//                waIntent.putExtra(Intent.EXTRA_TEXT, "Amazing people deserve to have an amazing birthday.  May your birthday be out of this world!");
//            }
//
//            startActivity(waIntent);
//        } catch (PackageManager.NameNotFoundException e) {
//            Toast.makeText(getContext(), "WhatsApp not Installed", Toast.LENGTH_SHORT).show();
//        }
    }

    public void openWhatsappConversation(String mobile, String message) {


        String countryCode = getContext().getResources().getConfiguration().locale.getCountry();


        PhoneNumberUtil phoneUtil = PhoneNumberUtil.createInstance(getContext());
        try {
            Phonenumber.PhoneNumber phoneNumber = phoneUtil.parse(mobile, countryCode);

            String formattedNumber = phoneUtil.format(phoneNumber, PhoneNumberUtil.PhoneNumberFormat.E164);

            Uri uri = Uri.parse("https://api.whatsapp.com/send?phone=" + formattedNumber + "&text=" + message);

            Intent sendIntent = new Intent(Intent.ACTION_VIEW, uri);

            getContext().startActivity(sendIntent);

        } catch (NumberParseException e) {
            System.err.println("NumberParseException was thrown: " + e.toString());
        }

    }

    public void sendSms(final String mobile, final boolean m, ActionTypes actionType) {

        if (!TextUtils.isEmpty(mobile) && getActivity() != null) {
            Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + mobile));
            if (m) {
                intent.putExtra("sms_body", "Amazing people deserve to have an amazing birthday.  May your birthday be out of this world!");
            }
            if (intent.resolveActivity(getActivity().getPackageManager()) != null) {

                if (actionType != null) {
                    AnalyticsController.PostAction(getContext(), new ActionRequest(PreferencesHelper.getDeviceId(getContext()), actionType.getNumVal(), generateJsonStringForContactAction(actionType, mobile)));

                }

                startActivity(intent);
            }
        }
    }

    public void sendEmail(String emailAddress, boolean m, ActionTypes actionType) {
        if (!TextUtils.isEmpty(emailAddress) && getActivity() != null) {
            Intent shareIntent = ShareCompat.IntentBuilder.from(getActivity()).setType("text/email").addEmailTo(emailAddress).getIntent();
            if (shareIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                if (m) {
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Happy Birthday");
                    shareIntent.putExtra(Intent.EXTRA_TEXT, "Amazing people deserve to have an amazing birthday.  May your birthday be out of this world!");
                }

                if(actionType != null){
                    AnalyticsController.PostAction(getContext(), new ActionRequest(PreferencesHelper.getDeviceId(getContext()), actionType.getNumVal(), generateJsonStringForContactAction(actionType, emailAddress)));

                }


                startActivity(shareIntent);
            }
        }
    }

    public void makePhoneCall(final String phoneCall, ActionTypes actionType) {
        if (!TextUtils.isEmpty(phoneCall) && getActivity() != null) {
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneCall));
            if (intent.resolveActivity(getActivity().getPackageManager()) != null) {


                if (actionType != null) {
                    AnalyticsController.PostAction(getContext(), new ActionRequest(PreferencesHelper.getDeviceId(getContext()), actionType.getNumVal(), generateJsonStringForContactAction(actionType, phoneCall)));
                }

                startActivity(intent);
            }
        }
    }

    protected void showNestedFragment(Fragment fragment) {
        if (getActivity() != null) {
            ((BaseActivity) getActivity()).showFragment(R.id.fragment_container, fragment);
        }
    }

    protected void showNestedFragmentCustom(Fragment fragment, Boolean isAddedToStack) {
        if (getActivity() != null) {
            ((BaseActivity) getActivity()).showFragmentTest(fragment, isAddedToStack);
        }
    }

    protected void addFragment(Fragment fragment, int contentFrameId) {
        if (getActivity() != null) {
            ((BaseActivity) getActivity()).addFragment(fragment, contentFrameId);
        }
    }

    protected void replaceFragmentNestedInContainer(Fragment replacingFragment, int contentFrameId) {
        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.slide_in_from_right, R.anim.slide_out_from_left).replace(contentFrameId, replacingFragment).commit();
    }

    public void toggleVis(View view) {
        if (view != null) {
            if (view.getVisibility() == View.VISIBLE) {
                view.setVisibility(View.GONE);
            } else {
                view.setVisibility(View.VISIBLE);
            }

        }

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Activity activity = getActivity();
        if (activity instanceof BaseActivity) {
            ((BaseActivity) getActivity()).decideWhetherToShowBackButton(this);
        }
    }

    public void reloadActivity() {
        Activity activity = getActivity();
        activity.finish();
        activity.startActivity(activity.getIntent());
    }

    protected void goToDestinationActivity(Class clazz) {
        ((BaseActivity) getActivity()).gotoDestinationActivity(clazz);
    }

    public String encodeImageBitmap(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        String encImage = Base64.encodeToString(b, Base64.DEFAULT);

        return encImage;
    }

    public String encodeImageToBase64(Uri imageUri) throws IOException {

        final InputStream imageStream = getContext().getContentResolver().openInputStream(imageUri);
        final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
        String encodedImage = encodeImageBitmap(selectedImage);

        return encodedImage;
    }

    public void openFileChooser(int requestCode) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");

        startActivityForResult(Intent.createChooser(intent, "Select Picture"), requestCode);
    }

    public boolean hasWhatsapp(String contactID) {
        String rowContactId = null;

        boolean hasWhatsApp = false;


        String[] projection = new String[]{ContactsContract.RawContacts._ID};
        String selection = ContactsContract.Data.CONTACT_ID + " = ? AND account_type IN (?)";
        String[] selectionArgs = new String[]{contactID, "com.whatsapp"};
        Cursor cursor = getContext().getContentResolver().query(ContactsContract.RawContacts.CONTENT_URI, projection, selection, selectionArgs, null);
        if (cursor != null) {
            hasWhatsApp = cursor.moveToNext();
            if (hasWhatsApp) {
                rowContactId = cursor.getString(0);
            }
            cursor.close();
        }
        return hasWhatsApp;
    }

    public static int getContactIDFromNumber(String contactNumber, Context context) {
        contactNumber = Uri.encode(contactNumber);
        int phoneContactID = new Random().nextInt();
        Cursor contactLookupCursor = context.getContentResolver().query(withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, contactNumber), new String[]{ContactsContract.PhoneLookup.DISPLAY_NAME, ContactsContract.PhoneLookup._ID}, null, null, null);
        while (contactLookupCursor.moveToNext()) {
            phoneContactID = contactLookupCursor.getInt(contactLookupCursor.getColumnIndexOrThrow(ContactsContract.PhoneLookup._ID));
        }
        contactLookupCursor.close();

        return phoneContactID;
    }

    public String getContactName(final String phoneNumber, Context context) {
        Uri uri = withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(phoneNumber));

        String[] projection = new String[]{ContactsContract.PhoneLookup.DISPLAY_NAME};

        String contactName = "";
        Cursor cursor = context.getContentResolver().query(uri, projection, null, null, null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                contactName = cursor.getString(0);
            }
            cursor.close();
        }

        return contactName;
    }

    public int getContactIdForWhatsAppCall(String name, Context context) {

        Cursor cursor = getContext().getContentResolver().query(ContactsContract.Data.CONTENT_URI, new String[]{ContactsContract.Data._ID}, ContactsContract.Data.DISPLAY_NAME + "=? and " + ContactsContract.Data.MIMETYPE + "=?", new String[]{name, "vnd.android.cursor.item/vnd.com.whatsapp.voip.call"}, ContactsContract.Contacts.DISPLAY_NAME);

        if (cursor.getCount() > 0) {
            cursor.moveToNext();
            int phoneContactID = cursor.getInt(cursor.getColumnIndex(ContactsContract.Data._ID));
            return phoneContactID;
        } else {
            return 0;
        }
    }

    public static void addToContacts(final Context context, final Object person) {

        Intent intent = new Intent(Intent.ACTION_INSERT);
        intent.setType(ContactsContract.Contacts.CONTENT_TYPE);

        if (person instanceof CSIContactDetails) {
            intent.putExtra(ContactsContract.Intents.Insert.NAME, ((CSIContactDetails) person).getFullName());
            intent.putExtra(ContactsContract.Intents.Insert.PHONE, ((CSIContactDetails) person).getWorkMobileNumber());
            intent.putExtra(ContactsContract.Intents.Insert.EMAIL, ((CSIContactDetails) person).getWorkEmailAddress());
        }

        if (person instanceof MissedPremium) {
            intent.putExtra(ContactsContract.Intents.Insert.NAME, ((MissedPremium) person).getContractingParty());
            intent.putExtra(ContactsContract.Intents.Insert.PHONE, ((MissedPremium) person).getCellNumber());
            intent.putExtra(ContactsContract.Intents.Insert.EMAIL, ((MissedPremium) person).getEmail());
        }

        context.startActivity(intent);
    }

    public static void showMessageDialogForAddToContacts(final Context context, final Object person) {
        try {

            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage("Would you like to add this person to your contacts?");
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {

                    addToContacts(context, person);

                    dialog.cancel();
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {

                    dialog.cancel();
                }
            });

            AlertDialog dialog = builder.create();
            dialog.show();
        } catch (final Exception e) {
            Log.d("alert ::", "showAlertDialog(): Failed.", e);
        }
    }

    public static boolean contactExists(Context context, String number) {
        /// number is the phone number
        Uri lookupUri = withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(number));
        String[] mPhoneNumberProjection = {ContactsContract.PhoneLookup._ID, ContactsContract.PhoneLookup.NUMBER, ContactsContract.PhoneLookup.DISPLAY_NAME};
        Cursor cur = context.getContentResolver().query(lookupUri, mPhoneNumberProjection, null, null, null);
        try {
            if (cur.moveToFirst()) {
                return true;
            }
        } finally {
            if (cur != null) {
                cur.close();
            }
        }
        return false;
    }

    public void callWhatsapp(final String phoneCall) {
        //        if (!TextUtils.isEmpty(phoneCall) && getActivity() != null) {
        //            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneCall));
        //            if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
        //                startActivity(intent);
        //            }
        //        }

        //        hasWhatsapp(String.valueOf(getContactIDFromNumber(phoneCall, getContext())));

        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_CONTACTS}, 0);
        } else {

            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);

            String name = getContactName(phoneCall, getContext());
            int voiceCall = getContactIdForWhatsAppCall(name, getContext());
            if (voiceCall != 0) {
                intent.setDataAndType(Uri.parse("content://com.android.contacts/data/" + voiceCall), "vnd.android.cursor.item/vnd.com.whatsapp.voip.call");
                intent.setPackage("com.whatsapp");
                startActivity(intent);
            } else {
                Toast.makeText(getContext(), "Sorry this number is not one of your whatsapp contacts", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void ShowRatingStarDialog(final Context mContext, final int PageType, String pageTitle, final boolean enhancements) {

        final Dialog dialog = new Dialog(mContext, R.style.DialogTheme);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        View mView = inflater.inflate(R.layout.star_rating_dialog, null);

        ImageView close = mView.findViewById(R.id.close);
        TextView submit = mView.findViewById(R.id.submit);
        final EditText editText = mView.findViewById(R.id.rating_comment);
        final RatingBar stars = mView.findViewById(R.id.ratingBar);
        final LinearLayout feedback = mView.findViewById(R.id.feedback_section);
        final TextView topTitle = mView.findViewById(R.id.topText);
        final TextView subTitle = mView.findViewById(R.id.subTitle);
        final TextView error = mView.findViewById(R.id.error);
        final TextView comment_title = mView.findViewById(R.id.comment_title);
        final TextView comment_error = mView.findViewById(R.id.commentError);


        if (enhancements) {
            stars.setVisibility(View.GONE);

            topTitle.setVisibility(View.GONE);

            comment_title.setVisibility(View.GONE);

            subTitle.setText("Please let us know what future enhancements you would like to see in the app.");

        } else {
            subTitle.setText("Please take a moment to rate the functionality of the " + pageTitle);
        }


        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        stars.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                if (error.getVisibility() == View.VISIBLE) {
                    error.setVisibility(View.GONE);

                }
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!enhancements) {
                    if (stars.getRating() > 0) {

                        //set global star variable

                        ratingScore = (int) stars.getRating();

                        comment = editText.getText().toString();

                        pageType = PageType;

                        AnalyticsController.PostRating(mContext, new RatingRequestAnalytics(PreferencesHelper.getCommonName(mContext), (int) stars.getRating(), editText.getText().toString(), PageType, ""));

                        feedback.setVisibility(View.GONE);

                        topTitle.setText("Rating sent.");
                        subTitle.setText("Thank you for rating this page. Your feedback is\n" + " extremely valuable to us.");
                    } else {
                        error.setVisibility(View.VISIBLE);
                    }
                } else {

                    if (editText.getText().toString().equals("")) {
                        comment_error.setVisibility(View.VISIBLE);
                    } else {
                        AnalyticsController.PostRating(mContext, new RatingRequestAnalytics(PreferencesHelper.getCommonName(mContext), 0, editText.getText().toString(), PageType, ""));




                        ratingScore = 0;

                        comment = editText.getText().toString();

                        pageType = PageType;

                        feedback.setVisibility(View.GONE);

                        topTitle.setText("Feedback sent");
                        subTitle.setText("Thank you for rating this page. Your feedback is\n" + " extremely valuable to us.");
                    }


                }


            }
        });


        dialog.setCancelable(false);

        dialog.getWindow().getDecorView().setBackground(new ColorDrawable(Color.TRANSPARENT));

        dialog.setContentView(mView);
        dialog.show();

    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onErrorResponse(ErrorResponse errorResponse) {


        if (errorResponse.getRequest().equals(AnalyticsController.POST_RATING)) {

            Toast.makeText(getContext(), errorResponse.getMessage(), Toast.LENGTH_LONG).show();

            AnalyticsController.PostRating(getContext(), new RatingRequestAnalytics(PreferencesHelper.getCommonName(getContext()), ratingScore, comment, pageType, ""));

        }

    }



}
