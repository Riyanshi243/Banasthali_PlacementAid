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
            case 0:
                frag_PersonalDetails f=new frag_PersonalDetails();
                return f;
            case 1:
                frag_AcademicDetails f2=new frag_AcademicDetails();
                return f2;
            case 2:
                frag_Resume f3=new frag_Resume();
                return f3;
            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return noOfTabs;
    }
}
