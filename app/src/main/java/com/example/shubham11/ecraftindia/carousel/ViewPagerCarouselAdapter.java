package com.example.shubham11.ecraftindia.carousel;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by shubhamjuneja11 on 7/6/17.
 */

public class ViewPagerCarouselAdapter extends FragmentStatePagerAdapter {
    private String[] imageResourceIds;

    public ViewPagerCarouselAdapter(FragmentManager fm, String[] imageResourceIds) {
        super(fm);
        this.imageResourceIds = imageResourceIds;
    }

    @Override
    public Fragment getItem(int position) {
        Bundle bundle = new Bundle();
        bundle.putString(ViewPagerCarouselFragment.IMAGE_RESOURCE_ID, imageResourceIds[position]);
        ViewPagerCarouselFragment frag = new ViewPagerCarouselFragment();
        frag.setArguments(bundle);

        return frag;
    }

    @Override
    public int getCount() {
        return (imageResourceIds == null) ? 0: imageResourceIds.length;
    }

}