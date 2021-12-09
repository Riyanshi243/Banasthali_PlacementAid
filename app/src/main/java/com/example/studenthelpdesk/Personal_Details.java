package com.example.studenthelpdesk;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.regex.*;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
//underline create one
public class Personal_Details extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{
    private EditText fullname,fathersname,mothersname,aadhar,phoneno,pan,address;
    private String fname,fathern,mothern,gender1,dob1,aadar1,phno,pan1,add;
    private Button btnsubmit;
    TextView tvDate;
    Button btPickDate;
    Data data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_details);
        fullname=(EditText) findViewById(R.id.fullname);
        fathersname=(EditText) findViewById(R.id.fathersname);
        mothersname=(EditText) findViewById(R.id.mothersname);
        //gender= (EditText) findViewById(R.id.gender);
        //dob=(EditText) findViewById(R.id.dob);
        phoneno=(EditText) findViewById(R.id.phoneno);
        pan=(EditText) findViewById(R.id.pan);
        aadhar=(EditText) findViewById(R.id.aadhar);
        address=(EditText) findViewById(R.id.address);
        btnsubmit= (Button) findViewById(R.id.nxt);
        address=(EditText) findViewById(R.id.address);
        tvDate = findViewById(R.id.tvDate);
        btPickDate = findViewById(R.id.btPickDate);
        btPickDate.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                com.example.studenthelpdesk.DatePicker mDatePickerDialogFragment;
                mDatePickerDialogFragment = new com.example.studenthelpdesk.DatePicker();
                mDatePickerDialogFragment.show(getSupportFragmentManager(), "DATE PICK");
            }
        });
        show();

    }

    public String onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        if (checked==false)
            ((RadioButton) view).setError("Select Gender");
        switch(view.getId()) {
            case R.id.male: if(checked)
                             gender1="Male";
                             break;
            case R.id.female:if(checked)
                                gender1="Female";
                             break;
            case R.id.not: if(checked)
                               gender1="Others";
                           break;
        }

        return gender1;
    }
    public void show()
    {
        data=SignIn.data;
        if(data==null)
            return;
        fullname.setText(data.getName());
        fathersname.setText(data.getFname());
        mothersname.setText(data.getMname());
        String gender11 = data.getGender();
        if(gender11!=null)
        switch (gender11)
        {
            case "Female":
                RadioButton r1=findViewById(R.id.female);
                r1.setChecked(true);
                break;
            case "Male":
                RadioButton r2=findViewById(R.id.male);
                r2.setChecked(true);
                break;
            case "Others":
                RadioButton r3=findViewById(R.id.not);
                r3.setChecked(true);
                break;
            default:

        }
        tvDate.setText(data.getDob());
        aadhar.setText(data.getAadhar());
        phoneno.setText(data.getPno());
        pan.setText(data.getPan());
        address.setText(data.getAddress());
        address.setText(data.getAddress());
    }
    public void save()
    {
        data=SignIn.data;
        fname=fullname.getText().toString();
        data.setName(fname);
        fathern=fathersname.getText().toString();
        data.setFname(fathern);
        mothern=mothersname.getText().toString();
        data.setMname(mothern);
        RadioGroup rg=findViewById(R.id.rg);
       int i= rg.getCheckedRadioButtonId();
        switch(i) {
            case R.id.male:
                gender1="Male";
                data.setGender(gender1);
                break;
            case R.id.female:
                gender1="Female";
                data.setGender(gender1);
                break;
            case R.id.not:
                gender1="Others";
                data.setGender(gender1);
                break;
        }
        //Toast.makeText(Personal_Details.this,gender1,Toast.LENGTH_SHORT).show();

        dob1=tvDate.getText().toString();
        data.setDob(dob1);
        aadar1=aadhar.getText().toString();
        data.setAadhar(aadar1);
        phno=phoneno.getText().toString();
        data.setPno(phno);
        pan1=pan.getText().toString();
        data.setPan(pan1);
        add=address.getText().toString();
        data.setAddress(add);

    }
    public void savedata(View V)
    {
        btnsubmit.setEnabled(false);
        ProgressBar pbar =findViewById(R.id.progressBar7);
        pbar.setVisibility(View.VISIBLE);
        save();
           //check the constraits
           if(fullname.getText().toString().length()==0)
           {
               fullname.setError("ENTER VALID NAME");
               pbar.setVisibility(View.INVISIBLE);
               btnsubmit.setEnabled(true);
               return;
           }

           if(fathersname.getText().toString().trim().length()==0)
           {
               fathersname.setError("ENTER FATHERS NAME");
               pbar.setVisibility(View.INVISIBLE);
               btnsubmit.setEnabled(true);
               return;
           }
            if(mothersname.getText().toString().trim().length()==0)
           {
               mothersname.setError("ENTER MOTHERS NAME");
               pbar.setVisibility(View.INVISIBLE);
               btnsubmit.setEnabled(true);
               return;
           }
            if(address.getText().toString().trim().length()==0)
             {
                 mothersname.setError("ENTER ADDRESS NAME");
                 pbar.setVisibility(View.INVISIBLE);
                 btnsubmit.setEnabled(true);
                 return;
             }
              if(phoneno.getText().toString().length()<10|| phoneno.getText().toString().trim().length()>10)
              {
                  phoneno.setError("INVALID NUMBER");
                  pbar.setVisibility(View.INVISIBLE);
                  btnsubmit.setEnabled(true);
                  return;
              }
              else if(phoneno.getText().toString().length()==0)
              {
                phoneno.setError("ENTER NUMBER");
                  pbar.setVisibility(View.INVISIBLE);
                  btnsubmit.setEnabled(true);
                  return;
              }
               if(aadhar.getText().toString().length()<12||aadhar.getText().toString().trim().length()>12)
              {
                  aadhar.setError("INVALID AADHAR");
                  pbar.setVisibility(View.INVISIBLE);
                  btnsubmit.setEnabled(true);
                  return;

              }
           if((pan.getText().toString().length()!=0)&& (pan.getText().toString().length()<10 || pan.getText().toString().trim().length()>10))
           {
              pan.setError("ENTER VALID PAN");
               pbar.setVisibility(View.INVISIBLE);
               btnsubmit.setEnabled(true);
               return;
           }
               Intent i = new Intent(Personal_Details.this, Academic_data.class);
               startActivity(i);
               finish();


    }

    @Override
    protected void onResume() {
        super.onResume();
        show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //alert builder to are you sure you will lose ur data
        //intent to signin
        Intent intent = new Intent(Personal_Details.this,SignIn.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onDateSet(android.widget.DatePicker view, int year, int month, int dayOfMonth) {
        Calendar mCalendar = Calendar.getInstance();
        mCalendar.set(Calendar.YEAR, year);
        mCalendar.set(Calendar.MONTH, month);
        mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String selectedDate = DateFormat.getDateInstance(DateFormat.FULL).format(mCalendar.getTime());
        tvDate.setText(selectedDate);
        SignIn.data.setDob(selectedDate);
    }

    @Override
   protected void onRestart() {
        super.onRestart();
        show();
    }
}