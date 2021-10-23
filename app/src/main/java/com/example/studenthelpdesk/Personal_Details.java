package com.example.studenthelpdesk;

import androidx.appcompat.app.AppCompatActivity;
import java.util.regex.*;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

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

           String MobilePattern = "[0-9]{10}";
           if(phoneno.getText().toString().matches(MobilePattern)) {
            phoneno.setError("INVALID NUMBER");//aise
            return ;

            } else if(!phoneno.getText().toString().matches(MobilePattern)) {

               Toast.makeText(getApplicationContext(), "Please enter valid 10 digit phone number", Toast.LENGTH_SHORT).show();
           }


               //move to next page
               Intent i = new Intent(Personal_Details.this, Academic_data.class);
               startActivity(i);


    }

    public boolean isValidAadharNumber(String a)
    {
        String regex
                = "^[2-9]{1}[0-9]{3}\\s[0-9]{4}\\s[0-9]{4}$";

        Pattern p = Pattern.compile(regex);
        if (a == null) {
            aadhar.setError("INVALID");
        }
        Matcher m = p.matcher(a);
        //condition
        return m.matches();
       // return true;
    }
}