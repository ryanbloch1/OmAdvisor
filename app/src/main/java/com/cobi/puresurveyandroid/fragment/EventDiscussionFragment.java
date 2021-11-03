package com.cobi.puresurveyandroid.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import pl.droidsonroids.gif.GifImageView;

import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.cobi.puresurveyandroid.R;
import com.cobi.puresurveyandroid.adapter.CommentAdapter;
import com.cobi.puresurveyandroid.adapter.MessageAdapter;
import com.cobi.puresurveyandroid.databinding.AddMessageBinding;
import com.cobi.puresurveyandroid.databinding.FragmentEventDiscussionBinding;
import com.cobi.puresurveyandroid.databinding.TextInputReplyBinding;
import com.cobi.puresurveyandroid.model.ErrorResponse;
import com.cobi.puresurveyandroid.model.EventMessage;
import com.cobi.puresurveyandroid.model.LikeRequest;
import com.cobi.puresurveyandroid.model.PostMessageRequest;
import com.cobi.puresurveyandroid.util.BitmapHelper;
import com.cobi.puresurveyandroid.util.PreferencesHelper;
import com.cobi.puresurveyandroid.webservice.controller.OMEventsApiController;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.ACCESSIBILITY_SERVICE;

public class EventDiscussionFragment extends BaseFragment implements View.OnClickListener, MessageAdapter.OnImageSelected {

    private FragmentEventDiscussionBinding mBinding;
    private MessageAdapter mAdapter;
    private String encodedImage;
    private GifImageView progress;
    private AddMessageBinding alertDialogBinding;

    public static final int REPLY_LOAD_IMAGE = 2;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_event_discussion, container, false);

        alertDialogBinding = DataBindingUtil.inflate(inflater, R.layout.add_message, container, false);

        progress = getActivity().findViewById(R.id.progress_event_main);


        OMEventsApiController.getMessages(getContext(), PreferencesHelper.getEventId(getContext()));

        mBinding.send.setOnClickListener(this);
        mBinding.photoUpload.setOnClickListener(this);

        return mBinding.getRoot();
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

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.send:

                if (mBinding.imageView.getVisibility() == View.VISIBLE) {
                    mBinding.imageView.setVisibility(View.GONE);
                }

                progress.setVisibility(View.VISIBLE);

                sendMessage(null, mBinding.messageField.getText().toString(), mBinding.anonCheck.isChecked());

                mBinding.messageField.setText("");

                mBinding.send.setOnClickListener(null);

                mBinding.anonCheck.setChecked(false);

                hideKeyboard();

                break;

            case R.id.photo_upload:
                openFileChooser(RESULT_LOAD_IMAGE);
                break;
        }
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            try {
                mBinding.imageView.setVisibility(View.VISIBLE);

                Glide.with(getContext()).load(data.getData()).listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        mBinding.progress.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        mBinding.progress.setVisibility(View.GONE);
                        return false;
                    }
                }).apply(new RequestOptions().override(600, 200)).into(mBinding.imageView);

                encodedImage = encodeImageToBase64(data.getData());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (requestCode == REPLY_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            try {

                alertDialogBinding.imageViewForDialog.setVisibility(View.VISIBLE);

                Glide.with(getContext()).load(data.getData()).apply(new RequestOptions().override(600, 200)).into(alertDialogBinding.imageViewForDialog);

                encodedImage = encodeImageToBase64(data.getData());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    private boolean isEditTextEmpty(EditText etText) {
        return etText.getText().toString().trim().length() == 0;
    }

    public void onReplyHandler(final EventMessage eventMessage, Boolean Like) {

        if (!Like) {
            // user is replying... show reply dialog

            encodedImage = null;

            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("Reply");

            if (alertDialogBinding.getRoot().getParent() != null) {
                ((ViewGroup) alertDialogBinding.getRoot().getParent()).removeView(alertDialogBinding.getRoot());
            }

            alertDialogBinding.imageViewForDialog.setVisibility(View.GONE);

            alertDialogBinding.messageField.setText("");

            alertDialogBinding.anonCheck.setChecked(false);

            builder.setView(alertDialogBinding.getRoot());

            alertDialogBinding.photoUpload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    try {
                        openFileChooser(REPLY_LOAD_IMAGE); //need to investigate...
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            // Set up the buttons
            builder.setPositiveButton("send", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    //do a check to see if there is an image or text in post before sending

                    if (!isEditTextEmpty(alertDialogBinding.messageField) || alertDialogBinding.imageViewForDialog.getVisibility() == View.VISIBLE) {

                        progress.setVisibility(View.VISIBLE);
                        dialog.dismiss();

                        sendMessage(eventMessage.getId(), alertDialogBinding.messageField.getText().toString(), alertDialogBinding.anonCheck.isChecked());
                    } else {
                        Toast.makeText(getContext(), "*Please add a message or attach an image to post", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
            AlertDialog dialog = builder.show();
            Window window = dialog.getWindow();
            lp.copyFrom(window.getAttributes());
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            window.setAttributes(lp);
        } else {
            //user is liking
            OMEventsApiController.postLike(getContext(), new LikeRequest(PreferencesHelper.getCommonName(getContext()), eventMessage.getId()));
        }
    }

    private void sendMessage(String postId, String messageText, boolean isAnon) {

        PostMessageRequest postMessageRequest = new PostMessageRequest(PreferencesHelper.getEventId(getContext()), PreferencesHelper.getCommonName(getContext()), postId, PreferencesHelper.getClientName(getContext()), messageText, encodedImage != null ? encodedImage : null, isAnon);

        OMEventsApiController.postMessage(getContext(), postMessageRequest);

        encodedImage = null;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onPostMessageResponse(String postId) {

        OMEventsApiController.getMessages(getContext(), PreferencesHelper.getEventId(getContext()));

        mBinding.send.setOnClickListener(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessagesResponse(List<EventMessage> result) {

        if (!result.isEmpty()) {
            if (result.get(0) instanceof EventMessage) {

                mAdapter = new MessageAdapter(result, getContext(), new MessageAdapter.OnReply() {
                    @Override
                    public void onReply(EventMessage message, Boolean Like) {
                        onReplyHandler(message, Like);
                    }
                }, new CommentAdapter.OnLike() {
                    @Override
                    public void OnLike(EventMessage eventMessage) {
                        OMEventsApiController.postLike(getContext(), new LikeRequest(PreferencesHelper.getCommonName(getContext()), eventMessage.getId()));
                    }
                }, this);
                mBinding.messagesList.setLayoutManager(new LinearLayoutManager(getContext()));
                mBinding.messagesList.setHasFixedSize(true);
                mBinding.messagesList.setAdapter(mAdapter);
            }
        }

        progress.setVisibility(View.GONE);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onErrorResponse(ErrorResponse errorResponse) {

        progress.setVisibility(View.GONE);

        mBinding.send.setOnClickListener(this);

        if (errorResponse.getRequest().equals(OMEventsApiController.CUSTOMER_POST_MESSAGE)) {
            Toast.makeText(getContext(), "Sorry your message wasn't sent. Please try again", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onImageSelected(String imagePath) {

        final Dialog dialog = new Dialog(getActivity());

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        LayoutInflater inflater = getLayoutInflater();
        View newView = inflater.inflate(R.layout.image_layout, null);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(newView);

        ImageView iv = newView.findViewById(R.id.message_full_image);

        final ImageView ci = newView.findViewById(R.id.close);

        ci.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        Glide.with(getContext()).load(imagePath).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                ci.setVisibility(View.VISIBLE);

                return false;
            }
        }).into(iv);

        dialog.getWindow().setAttributes(lp);
        dialog.show();
    }

}