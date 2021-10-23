package com.example.studenthelpdesk;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class PagerAdapter extends FragmentPagerAdapter {

    private int noOfTabs;
    public PagerAdapter(FragmentManager fm,int noOfTabs)
    {
        super(fm);
        this.noOfTabs=noOfTabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0:return new frag_PersonalDetails();
            case 1: return new frag_AcademicDetails();
            case 2: return new frag_Resume();
            default:return null;
        }

    }

    @Override
    public int getCount() {
        return noOfTabs;
    }
}
