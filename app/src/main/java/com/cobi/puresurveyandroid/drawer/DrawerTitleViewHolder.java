package com.cobi.puresurveyandroid.drawer;

import com.cobi.puresurveyandroid.databinding.DrawerTitleItemBinding;

public class DrawerTitleViewHolder extends DrawerItemViewHolder<DrawerTitleItemBinding> {

    public DrawerTitleViewHolder(DrawerTitleItemBinding mBinding) {
        super(mBinding);
    }

    @Override
    public void bind(DrawerItem item) {
        mBinding.setMenuItem(item);
    }
}
