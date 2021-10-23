package com.example.studenthelpdesk;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

public class Student_viewData extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_view_data);

        TabLayout tabLayout=findViewById(R.id.tabs);
        TabItem tab_PD=findViewById(R.id.tab_personal_details);
        TabItem tab_AD=findViewById(R.id.tab_academic_details);
        TabItem tab_R=findViewById(R.id.tab_resume);
        ViewPager viewPager=findViewById(R.id.view_pager);

        PagerAdapter pagerAdapter=new
                PagerAdapter(getSupportFragmentManager(),tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}