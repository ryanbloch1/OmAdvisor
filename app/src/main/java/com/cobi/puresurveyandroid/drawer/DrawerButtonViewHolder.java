package com.cobi.puresurveyandroid.drawer;

import com.cobi.puresurveyandroid.databinding.DrawerButtonItemBinding;

public class DrawerButtonViewHolder extends DrawerItemViewHolder<DrawerButtonItemBinding> {

    public DrawerButtonViewHolder(DrawerButtonItemBinding mBinding) {
        super(mBinding);
    }

    @Override
    public void bind(DrawerItem item) {
        mBinding.setMenuItem(item);
    }
}
