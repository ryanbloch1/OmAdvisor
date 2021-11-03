package com.cobi.puresurveyandroid.drawer;

import android.app.Activity;
import android.content.Context;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.cobi.puresurveyandroid.R;
import com.cobi.puresurveyandroid.databinding.DrawerButtonItemBinding;
import com.cobi.puresurveyandroid.databinding.DrawerTitleItemBinding;

import java.util.List;

public class CustomDrawerAdapter extends ArrayAdapter<DrawerItem> {

    private Context context;
    private List<DrawerItem> drawerItemList;

    public CustomDrawerAdapter(Context context, int layoutResourceID, List<DrawerItem> listItems) {
        super(context, layoutResourceID, listItems);
        this.context = context;
        this.drawerItemList = listItems;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        DrawerItemViewHolder viewHolder = null;
        View view = convertView;
        DrawerItem drawerItem = this.drawerItemList.get(position);

        if (view == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            ViewDataBinding binding = null;

            switch (drawerItem.type) {
                case TITLE:
                    binding = DataBindingUtil.inflate(inflater, R.layout.drawer_title_item, parent, false);
                    viewHolder = new DrawerTitleViewHolder(((DrawerTitleItemBinding) binding));
                    break;
                case BUTTON:
                    binding = DataBindingUtil.inflate(inflater, R.layout.drawer_button_item, parent, false);
                    viewHolder = new DrawerButtonViewHolder(((DrawerButtonItemBinding) binding));
                    break;
            }

            view = binding.getRoot();
            view.setTag(viewHolder);
        } else {
            viewHolder = (DrawerItemViewHolder) view.getTag();
        }

        viewHolder.bind(drawerItem);

        return view;
    }
}
