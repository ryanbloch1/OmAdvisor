package com.cobi.puresurveyandroid.adapter;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.cobi.puresurveyandroid.R;
import com.cobi.puresurveyandroid.databinding.MessageLayoutBinding;
import com.cobi.puresurveyandroid.model.EventMessage;
import com.cobi.puresurveyandroid.util.DateHelper;
import com.cobi.puresurveyandroid.util.ResourceHelper;

import org.ocpsoft.prettytime.PrettyTime;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {

    private List<EventMessage> messages;
    Context context;
    private OnReply listener;
    private OnImageSelected imageSelected;
    private CommentAdapter.OnLike onLikeListener;
    private CommentAdapter commentAdapter;
    boolean isCommentsExtended = false;

    public MessageAdapter(List<EventMessage> messages, Context context, OnReply listener, CommentAdapter.OnLike onLikeListener, OnImageSelected imageSelected) {
        this.messages = messages;
        this.context = context;
        this.listener = listener;
        this.onLikeListener = onLikeListener;
        this.imageSelected = imageSelected;
    }

    public interface OnReply {
        void onReply(EventMessage message, Boolean Like);
    }

    public interface OnImageSelected {
        void onImageSelected(String imageUrl);
    }

    public void clearItems() {
        messages.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater inflater = LayoutInflater.from(this.context);

        MessageLayoutBinding binding = DataBindingUtil.inflate(inflater, R.layout.message_layout, viewGroup, false);

        return new MessageViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder eventViewHolder, int position) {

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

    public void populateCommentList(List<EventMessage> replies, MessageLayoutBinding binding) {

        commentAdapter = new CommentAdapter(replies, context, onLikeListener, imageSelected);
        binding.commentList.setLayoutManager(new LinearLayoutManager(context));
        binding.commentList.setHasFixedSize(true);
        binding.commentList.setAdapter(commentAdapter);
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder {

        private final MessageLayoutBinding binding;
        PrettyTime p = new PrettyTime();

        public MessageViewHolder(MessageLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(final EventMessage message) {
            this.binding.setData(message);

            String wasSentText = p.format(DateHelper.ISO8601ToDate(message.getDateLogged()));

            if (wasSentText.equalsIgnoreCase("moments from now")) {
                wasSentText = wasSentText.replaceAll(wasSentText, "just now");
            }

            this.binding.wasSent.setText(wasSentText);

            binding.setLikes(message.getLikes());

            if (!TextUtils.isEmpty(message.getClientName())) {
                binding.setInitials(getInitials(message.getClientName()));
            }

            if (!TextUtils.isEmpty(message.image)) {

                binding.progress.setVisibility(View.VISIBLE);

                try {
                    Glide.with(context).load(message.getImage()).listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            binding.progress.setVisibility(View.GONE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            binding.progress.setVisibility(View.GONE);
                            binding.messageImage.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    if (imageSelected != null) {

                                        imageSelected.onImageSelected(message.getImage());
                                    }
                                }
                            });
                            return false;
                        }
                    }).apply(new RequestOptions().error(R.drawable.image_placeholder)).into(binding.messageImage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


            if (!message.getReplies().isEmpty()) {
                if (message.getReplies().size() > 2) {
                    populateCommentList(message.getReplies().subList(0, 2), this.binding);
                } else {
                    populateCommentList(message.getReplies(), this.binding);
                }
            }

            try {
                this.binding.circleImage.getBackground().setColorFilter(Color.parseColor(message.getClientColor()), PorterDuff.Mode.SRC_ATOP);
            } catch (Exception e) {
            }

            this.binding.reply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onReply(message, false);
                }
            });

            this.binding.like.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (message.getIsLiked()) {
                        binding.like.setBackground(ResourceHelper.getDrawable(R.drawable.not_liked));
                        message.setIsLiked(false);
                        binding.setLikes(binding.getLikes() - 1);
                    } else {
                        binding.like.setBackground(ResourceHelper.getDrawable(R.drawable.liked));
                        message.setIsLiked(true);
                        binding.setLikes(binding.getLikes() + 1);
                    }

                    listener.onReply(message, true);
                }
            });

            if (message.getReplies().size() > 2) {
                this.binding.viewMore.setVisibility(View.VISIBLE);
                this.binding.setCommentNumber(message.getReplies().size() - 2);
            } else {
                this.binding.viewMore.setVisibility(View.GONE);
            }

            this.binding.viewMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (!isCommentsExtended) {
                        populateCommentList(message.getReplies(), binding);
                        binding.viewMore.setVisibility(View.GONE);
                        binding.hide.setVisibility(View.VISIBLE);
                        isCommentsExtended = true;
                    }
                }
            });

            this.binding.hide.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    populateCommentList(message.getReplies().subList(0, 2), binding);
                    binding.hide.setVisibility(View.GONE);
                    binding.viewMore.setVisibility(View.VISIBLE);
                    isCommentsExtended = false;
                }
            });

            binding.executePendingBindings();
        }

        public MessageLayoutBinding getMessageBinding() {
            return binding;
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