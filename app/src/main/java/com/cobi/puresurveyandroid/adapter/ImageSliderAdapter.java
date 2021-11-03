package com.cobi.puresurveyandroid.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.cobi.puresurveyandroid.R;
import com.cobi.puresurveyandroid.model.Medium;
import com.cobi.puresurveyandroid.util.ResourceHelper;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.List;

public class ImageSliderAdapter extends SliderViewAdapter<ImageSliderAdapter.SliderAdapterVH> {

    private Context context;
    private List<Medium> media;

    public ImageSliderAdapter(Context context, List<Medium> media) {
        this.context = context;
        this.media = media;
    }

    @Override
    public SliderAdapterVH onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_slider_layout_item, null);
        return new SliderAdapterVH(inflate);
    }

    @Override
    public void onBindViewHolder(SliderAdapterVH viewHolder, int position) {

        final String url = media.get(position).getUrl();

        String extension = url.substring(url.lastIndexOf("."));

        if (!extension.equals(".jpg") && !extension.equals(".png") && !extension.equals(".jpeg")) {
            if (extension.equals(".pdf")) {
                Glide.with(viewHolder.itemView).load(ResourceHelper.getDrawable(R.drawable.pdf_icon)).into(viewHolder.imageViewBackground);
            } else if (extension.equals(".mp4")) {
                Glide.with(viewHolder.itemView).load(ResourceHelper.getDrawable(R.drawable.video)).into(viewHolder.imageViewBackground);
            } else if (extension.equals(".mp3")) {
                Glide.with(viewHolder.itemView).load(ResourceHelper.getDrawable(R.drawable.mp3)).into(viewHolder.imageViewBackground);
            } else if (extension.equals(".pptx")) {
                Glide.with(viewHolder.itemView).load(ResourceHelper.getDrawable(R.drawable.ppt_icon)).into(viewHolder.imageViewBackground);
            }

            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    try {

                        context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } else {
            Glide.with(viewHolder.itemView).load(media.get(position).getUrl()).into(viewHolder.imageViewBackground);
        }
    }

    @Override
    public int getCount() {
        if (media != null) {
            return media.size();
        } else {
            return 0;
        }
    }

    class SliderAdapterVH extends SliderViewAdapter.ViewHolder {

        View itemView;
        ImageView imageViewBackground;

        public SliderAdapterVH(View itemView) {
            super(itemView);
            imageViewBackground = itemView.findViewById(R.id.iv_auto_image_slider);
            this.itemView = itemView;
        }
    }
}