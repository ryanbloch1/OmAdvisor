package com.cobi.puresurveyandroid.drawer;

import androidx.databinding.ViewDataBinding;

public abstract class DrawerItemViewHolder<T extends ViewDataBinding> {

    protected T mBinding;

    public DrawerItemViewHolder(T mBinding) {
        this.mBinding = mBinding;
    }

    abstract void bind(DrawerItem item);
}
