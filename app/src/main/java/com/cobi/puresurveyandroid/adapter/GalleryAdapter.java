package com.cobi.puresurveyandroid.adapter;

import android.content.Context;

import androidx.databinding.DataBindingUtil;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.cobi.puresurveyandroid.R;
import com.cobi.puresurveyandroid.databinding.GalleryCellBinding;
import com.cobi.puresurveyandroid.model.Medium;
import com.cobi.puresurveyandroid.util.ResourceHelper;

import java.util.List;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.GalleryViewHolder> {

    private List<Medium> media;
    Context context;
    private LayoutInflater inflater;
    private ImageSelected listener;

    public GalleryAdapter(List<Medium> media, Context context, ImageSelected listener) {
        this.media = media;
        this.context = context;
        this.listener = listener;
    }

    public interface ImageSelected {
        void ImageSelected(View v, int position, String file);
    }

    public void clearItems() {
        media.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public GalleryAdapter.GalleryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater inflater = LayoutInflater.from(this.context);

        GalleryCellBinding binding = DataBindingUtil.inflate(inflater, R.layout.gallery_cell, viewGroup, false);

        return new GalleryAdapter.GalleryViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull GalleryAdapter.GalleryViewHolder imageViewHolder, int position) {

        imageViewHolder.bind(media.get(position));
    }

    @Override
    public int getItemCount() {
        if (media != null) {
            return media.size();
        } else {
            return 0;
        }
    }

    public class GalleryViewHolder extends RecyclerView.ViewHolder {

        private final GalleryCellBinding binding;

        public GalleryViewHolder(GalleryCellBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(final Medium image) {

            binding.executePendingBindings();

            binding.cellImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.ImageSelected(view, getAdapterPosition(), image.getUrl());
                }
            });

            String url = image.getUrl();

            String extension = url.substring(url.lastIndexOf("."));

            if (extension.equals(".pdf")) {
                Glide.with(context).load(ResourceHelper.getDrawable(R.drawable.pdf_icon)).apply(new RequestOptions().override(300, 180)).into(this.binding.cellImage);
            } else if (extension.equals(".mp4")) {
                Glide.with(context).load(ResourceHelper.getDrawable(R.drawable.video)).apply(new RequestOptions().override(300, 180)).into(this.binding.cellImage);
            }
            else if(extension.equals(".mp3")){
                Glide.with(context).load(ResourceHelper.getDrawable(R.drawable.mp3)).apply(new RequestOptions().override(300, 180)).into(this.binding.cellImage);
            }
            else if (extension.equals(".pptx")) {
                Glide.with(context).load(ResourceHelper.getDrawable(R.drawable.ppt_icon)).apply(new RequestOptions().override(300, 180)).into(this.binding.cellImage);
            } else {
                Glide.with(context).load(image.getUrl()).apply(new RequestOptions().override(300, 180)).into(this.binding.cellImage);
            }
        }
    }
}