package com.cobi.puresurveyandroid.adapter;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.cobi.puresurveyandroid.R;
import com.cobi.puresurveyandroid.databinding.CommentLayoutBinding;
import com.cobi.puresurveyandroid.model.EventMessage;
import com.cobi.puresurveyandroid.util.DateHelper;
import com.cobi.puresurveyandroid.util.ResourceHelper;

import org.ocpsoft.prettytime.PrettyTime;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {

    private List<EventMessage> messages;
    Context context;
    private LayoutInflater inflater;
    private OnLike listener;
    private MessageAdapter.OnImageSelected imageSelected;

    public CommentAdapter(List<EventMessage> messages, Context context, OnLike listener, MessageAdapter.OnImageSelected imageSelected) {
        this.messages = messages;
        this.context = context;
        this.listener = listener;
        this.imageSelected = imageSelected;
    }

    public void clearItems() {
        messages.clear();
        notifyDataSetChanged();
    }

    public interface OnLike {
        void OnLike(EventMessage eventMessage);
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater inflater = LayoutInflater.from(this.context);

        CommentLayoutBinding mBinding = DataBindingUtil.inflate(inflater, R.layout.comment_layout, viewGroup, false);

        return new CommentViewHolder(mBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder eventViewHolder, int position) {

        eventViewHolder.bind(messages.get(position));
    }

    @Override
    public int getItemCount() {
        if (messages != null) {
            return messages.size();
        } else {
            return 0;
        }
    }

    public class CommentViewHolder extends RecyclerView.ViewHolder {

        private final CommentLayoutBinding mBinding;
        PrettyTime p = new PrettyTime();

        public CommentViewHolder(CommentLayoutBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        public void bind(final EventMessage message) {
            mBinding.setData(message);

            String wasSentText = p.format(DateHelper.ISO8601ToDate(message.getDateLogged()));

            if (wasSentText.equalsIgnoreCase("moments from now")) {
                wasSentText = wasSentText.replaceAll(wasSentText, "just now");
            }

            mBinding.wasSent.setText(wasSentText);

            if (!TextUtils.isEmpty(message.getClientName())) {
                mBinding.setInitials(getInitials(message.getClientName()));
            }

            try {
                mBinding.circleImage.getBackground().setColorFilter(Color.parseColor(message.getClientColor()), PorterDuff.Mode.SRC_ATOP);
            } catch (Exception e) {
            }

            mBinding.setLikes(message.getLikes());

            if (!TextUtils.isEmpty(message.image)) {

                mBinding.progress.setVisibility(View.VISIBLE);

                try {
                    Glide.with(context).load(message.getImage()).listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            mBinding.progress.setVisibility(View.GONE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            mBinding.progress.setVisibility(View.GONE);
                            mBinding.commentImage.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    if (imageSelected != null) {

                                        imageSelected.onImageSelected(message.getImage());
                                    }
                                }
                            });
                            return false;
                        }
                    }).into(mBinding.commentImage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            mBinding.like.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (message.getIsLiked()) {
                        mBinding.like.setBackground(ResourceHelper.getDrawable(R.drawable.not_liked));
                        message.setIsLiked(false);
                        mBinding.setLikes(mBinding.getLikes() - 1);
                    } else {
                        mBinding.like.setBackground(ResourceHelper.getDrawable(R.drawable.liked));
                        message.setIsLiked(true);
                        mBinding.setLikes(mBinding.getLikes() + 1);
                    }

                    listener.OnLike(message);
                }
            });

            mBinding.executePendingBindings();
        }
    }

    private String getInitials(String fullName) {

        Pattern p = Pattern.compile("((^| )[A-Za-z])");
        Matcher m = p.matcher(fullName);
        String initials = "";
        while (m.find()) {
            initials += m.group().trim();
        }
        return initials.toUpperCase();
    }
}