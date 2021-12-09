package com.example.studenthelpdesk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.DatePicker;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;

import java.text.DateFormat;
import java.util.Calendar;

public class Student_viewData extends AppCompatActivity  implements DatePickerDialog.OnDateSetListener{
    public static String change;
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

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Student_page.data.ToDatabase();
        //intent to student page
        Intent intent = new Intent(Student_viewData.this,Student_page.class);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.main,menu);
        return super.onCreateOptionsMenu(menu);
    }//logout
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.action_logout:
                AlertDialog.Builder ab=new AlertDialog.Builder(this);
                ab.setTitle("LOGOUT");
                ab.setMessage("Are you sure?");
                ab.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //logut
                        FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
                        firebaseAuth.signOut();
                        Intent intent = new Intent(Student_viewData.this,LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }).setNegativeButton("N0", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //do nothing;
                    }
                });
                ab.create().show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    //static String date;
    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
        Calendar mCalendar = Calendar.getInstance();
        mCalendar.set(Calendar.YEAR, year);
        mCalendar.set(Calendar.MONTH, month);
        mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String selectedDate = DateFormat.getDateInstance(DateFormat.FULL).format(mCalendar.getTime());
        Student_page.data.setDob(selectedDate);
        frag_PersonalDetails.dob.setText(selectedDate);
        if(Student_page.data!=null)
            Student_page.data.ToDatabase();
    }
}