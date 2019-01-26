package com.job.scc.scc;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private final List<Fragment>stFragment=new ArrayList<>();
    private final List<String> stTitles=new ArrayList<>();


    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return stFragment.get(position);
    }

    @Override
    public int getCount() {
        return stTitles.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return (CharSequence) stTitles.get(position);
    }

    public void AddFragment(Fragment fragment,String title){
        stFragment.add(fragment);
        stTitles.add(title);
    }
}
