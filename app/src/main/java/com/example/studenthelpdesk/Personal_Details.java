package com.example.studenthelpdesk;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class Personal_Details extends AppCompatActivity {
    private EditText fullname,fathersname,mothersname,gender,dob,aadhar,phoneno;
    private String fname,fathern,mothern,gender1,dob1,aadar1,phno;
    Data data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_details);
        fullname=(EditText) findViewById(R.id.fullname);
        fathersname=(EditText) findViewById(R.id.fathersname);
        mothersname=(EditText) findViewById(R.id.mothersname);
        gender=(EditText) findViewById(R.id.gender);
        dob=(EditText) findViewById(R.id.dob);
        aadhar=(EditText) findViewById(R.id.aadhar);
        phoneno=(EditText) findViewById(R.id.phoneno);


    }
    public void savedata(View V)
    {
        data=SignIn.data;
        fname=fullname.getText().toString();
        data.setName(fname);
        fathern=fathersname.getText().toString();
        data.setFname(fathern);
        mothern=mothersname.getText().toString();
        data.setMname(mothern);
        gender1=gender.getText().toString();
        data.setGender(gender1);
        dob1=dob.getText().toString();
        data.setDob(dob1);
        aadar1=aadhar.getText().toString();
        data.setAadhar(aadar1);
        phno=phoneno.getText().toString();
        data.setPno(phno);

           //check the constraits

           //if(phno != null && !phno .isEmpty()
           //love u both
           //hehehehehe
           //mil

        //move to next page
        Intent i=new Intent(Personal_Details.this,Academic_data.class);
        startActivity(i);






    }
}