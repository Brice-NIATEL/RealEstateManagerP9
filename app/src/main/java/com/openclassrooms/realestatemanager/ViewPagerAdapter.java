package com.openclassrooms.realestatemanager;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    public static final int NUMBER_FRAGMENT = 2;

    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 1:
                return new RVListRealEstateFragment();
            case 0:
            default:
                return new MapFragment();
        }
    }

    @Override
    public int getCount() {
        return NUMBER_FRAGMENT;
    }
}