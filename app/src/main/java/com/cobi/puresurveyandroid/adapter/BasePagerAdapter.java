/*
 *
 * BasePagerAdapter.java
 * cybertrac-android
 *
 * Copyright (c) 2017 <Cybertrac>. All rights reserved.
 */

package com.cobi.puresurveyandroid.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

public class BasePagerAdapter extends FragmentStatePagerAdapter {
    private ArrayList<Fragment> mFragments;

    public BasePagerAdapter(ArrayList<Fragment> fragments, FragmentManager fragmentManager) {
        super(fragmentManager);
        mFragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    public void clear() {
        mFragments.clear();
    }

    public void addAllData(ArrayList<Fragment> fragments) {
        mFragments.addAll(fragments);
    }

    public void addFragments(Fragment fragment) {
        mFragments.add(fragment);
    }
}