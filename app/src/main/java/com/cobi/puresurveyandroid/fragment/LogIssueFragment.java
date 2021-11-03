package com.cobi.puresurveyandroid.fragment;


import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.cobi.puresurveyandroid.R;
import com.cobi.puresurveyandroid.databinding.FragmentLogOmIssueBinding;
import com.cobi.puresurveyandroid.fragment.BaseFragment;
import com.cobi.puresurveyandroid.model.AnalyticsRequests.ActionRequest;
import com.cobi.puresurveyandroid.model.AnalyticsRequests.ActionTypes;
import com.cobi.puresurveyandroid.model.OMIssueLog;
import com.cobi.puresurveyandroid.model.UserDetailsLogIssue;
import com.cobi.puresurveyandroid.util.DateHelper;
import com.cobi.puresurveyandroid.util.PreferencesHelper;
import com.cobi.puresurveyandroid.util.ResourceHelper;
import com.cobi.puresurveyandroid.webservice.controller.AnalyticsController;
import com.cobi.puresurveyandroid.webservice.controller.OmIssueController;

import java.io.File;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import io.github.douglasjunior.androidSimpleTooltip.SimpleTooltip;

public class LogIssueFragment extends BaseFragment {

    FragmentLogOmIssueBinding mBinding;

    final List<String> whatEnvironment = new ArrayList<String>();

    List<String> internetAccess = new ArrayList<String>();

    List<String> howIssueOccurred = new ArrayList<String>();

    List<String> additionalInfo = new ArrayList<String>();


    private String encodedImage = null;

    private File destination = null;
    private InputStream inputStreamImg;
    private String imgPath = null;

    Bitmap bitmap;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        AnalyticsController.PostAction(getContext(), new ActionRequest(PreferencesHelper.getDeviceId(getContext()), ActionTypes.LogIssueAccessed.getNumVal(), ""));

        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_log_om_issue, container, false);

        mBinding.setData(new UserDetailsLogIssue(PreferencesHelper.getFullName(getContext()), PreferencesHelper.getClientEmail(getContext()), PreferencesHelper.getMobileNo(getContext())));

        mBinding.commentsTooltip.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {
                new SimpleTooltip.Builder(getContext())
                        .anchorView(mBinding.commentsTooltip)
                        .text("Please add any relevant information about the error.")
                        .gravity(Gravity.END)
                        .backgroundColor(ResourceHelper.getColour(R.color.light_grey))
                         .arrowColor(ResourceHelper.getColour(R.color.light_grey))
                        .transparentOverlay(false)
                        .build()
                        .show();
            }
        });

        mBinding.imageTooltip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new SimpleTooltip.Builder(getContext())
                        .anchorView(mBinding.imageTooltip)
                        .text("Attaching an image of the error will help provide a speedier resolution")
                        .gravity(Gravity.END)
                        .backgroundColor(ResourceHelper.getColour(R.color.light_grey))
                         .arrowColor(ResourceHelper.getColour(R.color.light_grey))
                        .build()
                        .show();
            }
        });

        mBinding.dateTooltip.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {
                new SimpleTooltip.Builder(getContext())
                        .anchorView(mBinding.dateTooltip)
                        .text("If you are unsure of the date and time the incident occurred, please give an approximate time")
                        .gravity(Gravity.END)
                        .backgroundColor(ResourceHelper.getColour(R.color.light_grey))
                        .arrowColor(ResourceHelper.getColour(R.color.light_grey))
                        .transparentOverlay(false)
                        .build()
                        .show();
            }
        });



        initAdapters();

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateDateLabel(mBinding.dateError, mBinding.dateIssueOccured, mBinding.timeError, mBinding.time);
            }
        };

        mBinding.dateIssueOccured.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(getContext(), date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        mBinding.time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePicker(mBinding.timeError, mBinding.time);
            }
        });

        mBinding.browsePhotos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });


        return mBinding.getRoot();
    }


    private ArrayAdapter<String> createAdditionalInfoAdapter(int[] array) {

        List<String> updatedInfo = new ArrayList<>();

        for (int j = 0; j < array.length; j++) {
            updatedInfo.add(additionalInfo.get(array[j]));
        }

        ArrayAdapter<String> newAdapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_item_layout, updatedInfo) {

            @Override
            public int getCount() {
                return super.getCount() - 1;
            }// you dont display last item. It is used as hint.
        };

        //
        newAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);

        return newAdapter;
    }

    private void initAdapters() {

        whatEnvironment.add("Production");
        whatEnvironment.add("Training");
        whatEnvironment.add(getText(R.string.choose_spinner_option).toString());

        internetAccess.add("Internal (Old Mutual Network)");
        internetAccess.add("External (3G/4G, Wi-Fi, Branch Networks)");
        internetAccess.add(getText(R.string.choose_spinner_option).toString());

        howIssueOccurred.add("Log In");
        howIssueOccurred.add("Solution Recommendation");
        howIssueOccurred.add("Quote");
        howIssueOccurred.add("Application");
        howIssueOccurred.add("Policy Servicing");
        howIssueOccurred.add(getText(R.string.choose_spinner_option).toString());

        additionalInfo.add("Unable to access Adviser Web Dashboard");
        additionalInfo.add("Unable to upload document");
        additionalInfo.add("USSD: unable to send / receive SMS");
        additionalInfo.add("Management Review");
        additionalInfo.add("Terms Change");
        additionalInfo.add("Other");
        additionalInfo.add(getText(R.string.choose_spinner_option).toString());

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapterEnvironment = new ArrayAdapter<String>(getContext(), R.layout.spinner_item_layout, whatEnvironment) {

            @Override
            public int getCount() {
                return super.getCount() - 1; // you dont display last item. It is used as hint.
            }
        };

        ArrayAdapter<String> dataAdapterInternet = new ArrayAdapter<String>(getContext(), R.layout.spinner_item_layout, internetAccess) {

            @Override
            public int getCount() {
                return super.getCount() - 1; // you dont display last item. It is used as hint.
            }
        };
        ArrayAdapter<String> dataAdapterhowIssue = new ArrayAdapter<String>(getContext(), R.layout.spinner_item_layout, howIssueOccurred) {

            @Override
            public int getCount() {
                return super.getCount() - 1; // you dont display last item. It is used as hint.
            }
        };
        ArrayAdapter<String> dataAdapteradditionalInfo = new ArrayAdapter<String>(getContext(), R.layout.spinner_item_layout, additionalInfo) {

            @Override
            public int getCount() {
                return super.getCount() - 1; // you dont display last item. It is used as hint.
            }
        };

        dataAdapterEnvironment.setDropDownViewResource(R.layout.spinner_dropdown_item);

        dataAdapterInternet.setDropDownViewResource(R.layout.spinner_dropdown_item);

        dataAdapterhowIssue.setDropDownViewResource(R.layout.spinner_dropdown_item);

        dataAdapteradditionalInfo.setDropDownViewResource(R.layout.spinner_dropdown_item);

        mBinding.environment.setAdapter(dataAdapterEnvironment);
        mBinding.environment.setSelection(dataAdapterEnvironment.getCount());

        mBinding.internetAccess.setAdapter(dataAdapterInternet);
        mBinding.internetAccess.setSelection(dataAdapterInternet.getCount());

        mBinding.processIssueOccurred.setAdapter(dataAdapterhowIssue);
        mBinding.processIssueOccurred.setSelection(dataAdapterhowIssue.getCount());

        mBinding.additionalInfo.setAdapter(dataAdapteradditionalInfo);
        mBinding.additionalInfo.setSelection(dataAdapteradditionalInfo.getCount());

        mBinding.processIssueOccurred.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position == 0) {
                    int[] a = {0, 6};

                    setUpAdditionalInfoSpinner(a);
                } else if (position == 1 || position == 3 || position == 4) {
                    int[] a = {1, 2, 3, 4, 5, 6};

                    setUpAdditionalInfoSpinner(a);
                } else if (position == 2) {
                    int[] a = {1, 5, 6};
                    setUpAdditionalInfoSpinner(a);
                } else {
                    int[] a = {0, 1, 2, 3, 4, 5, 6};

                    setUpAdditionalInfoSpinner(a);
                }

                if (position == 1 || position == 2 || position == 3 || position == 4) {
                    mBinding.processArea.setVisibility(View.VISIBLE);
                } else {
                    mBinding.processArea.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mBinding.removeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBinding.imageSection.setVisibility(View.GONE);
                encodedImage = null;
                mBinding.imageFileName.setText("No image selected");
            }
        });

        mBinding.submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                validate();
            }
        });

        mBinding.additionalInfo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();
                if (selectedItem.equals("Other")) {

                    mBinding.otherTextBox.setVisibility(View.VISIBLE);

                    // do your stuff
                } else {
                    mBinding.otherTextBox.setVisibility(View.GONE);
                }
            } // to close the onItemSelectedom

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setUpAdditionalInfoSpinner(int[] a) {
        ArrayAdapter<String> arrayAdapter = createAdditionalInfoAdapter(a);

        mBinding.additionalInfo.setAdapter(arrayAdapter);

        mBinding.additionalInfo.setSelection(arrayAdapter.getCount());
    }

    private void postIncident() {
        String salesCode = PreferencesHelper.getSalesCode(getContext());
        String name = PreferencesHelper.getFullName(getContext());
        String email = mBinding.email.getText().toString();
        String contactNo = mBinding.contact.getText().toString();
        String environment = mBinding.environment.getSelectedItem().toString();
        String internet = mBinding.internetAccess.getSelectedItem().toString();
        String howIssue = mBinding.processIssueOccurred.getSelectedItem().toString();
        String dateissueOccured = mBinding.dateIssueOccured.getText().toString() + "T" + mBinding.time.getText().toString();
        String additionalInfo = mBinding.additionalInfo.getSelectedItem().toString();
        String comments = mBinding.commentBox.getText().toString();
        String additionalinfoComment = mBinding.additionalInfoComment.getText().toString();
        String processDescription = mBinding.processDescription.getText().toString();

        OMIssueLog object = new OMIssueLog(salesCode, name, email, contactNo, environment, dateissueOccured, internet, howIssue, processDescription, additionalInfo, additionalinfoComment, encodedImage, comments);

        OmIssueController.postIncident(getContext(), object);

        mBinding.thanks.setVisibility(View.VISIBLE);
    }


    private void validate() {
        try {
            String email = mBinding.email.getText().toString();
            String contact = mBinding.contact.getText().toString();
            String date = mBinding.dateIssueOccured.getText().toString();
            String time = mBinding.time.getText().toString();
            String environment = mBinding.environment.getSelectedItem().toString();
            String internet = mBinding.internetAccess.getSelectedItem().toString();
            String howIssue = mBinding.processIssueOccurred.getSelectedItem().toString();
            String dateissueOccured = mBinding.dateIssueOccured.getText().toString();
            String timeIssueOccured = mBinding.time.getText().toString();
            String additionalInfo = mBinding.additionalInfo.getSelectedItem().toString();
            String comments = mBinding.commentBox.getText().toString();
            String additionalinfoComment = mBinding.additionalInfoComment.getText().toString();
            String processDescription = mBinding.processDescription.getText().toString();

            if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(contact) && !environment.equals(getText(R.string.choose_spinner_option)) && !internet.equals(getText(R.string.choose_spinner_option)) && !howIssue.equals(getText(R.string.choose_spinner_option)) && !additionalInfo.equals(getText(R.string.choose_spinner_option)) && !dateissueOccured.equals(getText(R.string.date_placeholder)) && !timeIssueOccured.equals(getText(R.string.time_placeholder))) {

                if (!isValidEmail(email) || mBinding.processArea.getVisibility() == View.VISIBLE || !validCellPhone(contact)) {

                    if (!isValidEmail(email)) {
                        mBinding.email.setError("Please enter a valid email address");
                        mBinding.email.requestFocus();
                    }

                    if (!validCellPhone(contact)) {
                        mBinding.contact.setError("Please enter a valid 10 digit contact number");
                        mBinding.contact.requestFocus();
                    }

                    if (mBinding.processDescription.getVisibility() == View.VISIBLE) {
                        if (TextUtils.isEmpty(mBinding.processDescription.getText())) {
                            mBinding.processDescription.setError("Please include description of issue");
                            mBinding.processDescription.requestFocus();
                        } else if (isValidEmail(email) && validCellPhone(contact)) {
                            postIncident();
                        }
                    }
                } else {

                    if (additionalInfo.equals("Other")) {
                        if (TextUtils.isEmpty(additionalinfoComment)) {
                            mBinding.additionalInfoComment.setError("Please specify");
                            mBinding.additionalInfoComment.requestFocus();
                        } else {
                            postIncident();
                        }
                    } else {
                        postIncident();
                    }
                }
            } else {
                if (TextUtils.isEmpty(email)) {
                    mBinding.email.setError("Email required");
                    mBinding.email.requestFocus();
                } else {
                    if (!isValidEmail(email)) {
                        mBinding.email.setError("Please enter valid email address");
                        mBinding.email.requestFocus();
                    }
                }

                if (mBinding.processArea.getVisibility() == View.VISIBLE) {
                    if (TextUtils.isEmpty(mBinding.processDescription.getText())) {
                        mBinding.processDescription.setError("Please include description of issue");
                        mBinding.processDescription.requestFocus();
                    }
                }

                if (TextUtils.isEmpty(contact)) {
                    mBinding.contact.setError("Contact number required");
                    mBinding.contact.requestFocus();
                } else {
                    if (!validCellPhone(contact)) {
                        mBinding.contact.setError("Please enter a valid 10 digit contact number");
                        mBinding.contact.requestFocus();
                    }
                }

                if (environment.equals(getText(R.string.choose_spinner_option))) {
                    showSpinnerError(mBinding.environment);
                }
                if (howIssue.equals(getText(R.string.choose_spinner_option))) {
                    showSpinnerError(mBinding.processIssueOccurred);
                }
                if (internet.equals(getText(R.string.choose_spinner_option))) {
                    showSpinnerError(mBinding.internetAccess);
                }
                if (additionalInfo.equals(getText(R.string.choose_spinner_option))) {
                    showSpinnerError(mBinding.additionalInfo);
                }

                if (date.equals(getText(R.string.date_placeholder))) {
                    mBinding.dateError.setVisibility(View.VISIBLE);
                    mBinding.dateIssueOccured.requestFocus();
                }

                if (time.equals(getText(R.string.time_placeholder))) {
                    mBinding.timeError.setVisibility(View.VISIBLE);
                    mBinding.time.requestFocus();
                }

                if (environment.equals("Other")) {
                    if (TextUtils.isEmpty(additionalinfoComment)) {
                        mBinding.additionalInfoComment.setError("Please specify");
                        mBinding.additionalInfoComment.requestFocus();
                    }
                }
            }
        } catch (Exception ex) {
            Log.e("RETROFIT : ", ex.toString());
        }
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        try {
            if (data.getData() != null) {
                mBinding.imageFileName.setText(getFileName(data.getData()));
            }
        } catch (Exception e) {

        }

        if (requestCode == PICK_IMAGE_CAMERA) {
            try {
                try {
                    Bundle extras = data.getExtras();
                    Bitmap imageBitmap = (Bitmap) extras.get("data");

                    mBinding.imageSection.setVisibility(View.VISIBLE);
                    mBinding.imageView.setImageBitmap(imageBitmap);

                    mBinding.noImage.setVisibility(View.GONE);

                    encodedImage = encodeImageBitmap(imageBitmap);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (requestCode == PICK_IMAGE_GALLERY) {
            Uri selectedImage = data.getData();

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), selectedImage);

                mBinding.imageSection.setVisibility(View.VISIBLE);
                mBinding.imageView.setImageBitmap(bitmap);

                mBinding.noImage.setVisibility(View.GONE);

                encodedImage = encodeImageToBase64(data.getData());

                String s;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
