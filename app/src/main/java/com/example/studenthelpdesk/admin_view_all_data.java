package com.example.studenthelpdesk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;
import java.util.Map;

public class admin_view_all_data extends AppCompatActivity {
    TextView srno,email,name,gender,phoneno,DOB,rollno,enrollment,course,branch,sem,fname,mname,adhar,pan,ten,twelve,CGPA;
    TableLayout tl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view_all_data);
        tl=findViewById(R.id.tl);
        CollectionReference doc = FirebaseFirestore.getInstance().collection("AllowedUser");

        doc.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                List<DocumentSnapshot> t = queryDocumentSnapshots.getDocuments();
                int c=1;
                for(int i=0;i<t.size();i++)
                {

                    Map<String, Object> m = t.get(i).getData();
                    if(m.containsKey("Email")==false)
                    {
                        continue;
                    }
                    else if(validEmail((String)m.get("Email"))==true)
                    {
                        if((Boolean)m.get("Admin")==false)
                        {

                            View v = LayoutInflater.from(admin_view_all_data.this).inflate(R.layout.view_data_table, null);
                            srno =(TextView) v.findViewById(R.id.srno);
                            email =(TextView) v.findViewById(R.id.email);
                            name =(TextView) v.findViewById(R.id.name);
                            gender=(TextView) v.findViewById(R.id.gender);
                            phoneno=(TextView) v.findViewById(R.id.phoneno);
                            DOB=(TextView) v.findViewById(R.id.DOB);
                            rollno=(TextView) v.findViewById(R.id.rollno);
                            enrollment=(TextView) v.findViewById(R.id.enrollment);
                            course=(TextView) v.findViewById(R.id.course);
                            branch=(TextView) v.findViewById(R.id.branch);
                            sem=(TextView) v.findViewById(R.id.sem);
                            fname=(TextView) v.findViewById(R.id.fname);
                            mname=(TextView) v.findViewById(R.id.mname);
                            adhar=(TextView) v.findViewById(R.id.adhar);
                            pan=(TextView) v.findViewById(R.id.pan);
                            ten=(TextView) v.findViewById(R.id.ten);
                            twelve=(TextView) v.findViewById(R.id.twelve);
                            CGPA=(TextView) v.findViewById(R.id.CGPA);
                            //srno.setText(c++);
                            doc.document((String)m.get("Email")).collection("Change").document("change").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {

                                    Map<String, Object> m1 = documentSnapshot.getData();
                                    adhar.setText((String)m1.get("Aadhar"));
                                    //((String)m1.get("Address"));
                                    DOB.setText((String)m1.get("DOB"));
                                    gender.setText((String)m1.get("Gender"));
                                    pan.setText((String)m1.get("PAN"));
                                    phoneno.setText((String)m1.get("PhoneNumber"));
                                    sem.setText((String)m1.get("Semester"));
                                    DocumentReference doc2 = doc.document((String) m.get("Email")).collection("Permanent").document("perm");
                                    doc2.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                        @Override
                                        public void onSuccess(DocumentSnapshot documentSnapshot) {

                                            Map<String, Object> m1 = documentSnapshot.getData();
                                            branch.setText((String)m1.get("Branch"));
                                            CGPA.setText((String)m1.get("CGPA"));
                                            course.setText((String)m1.get("Course"));
                                            email.setText((String)m1.get("Email"));
                                            enrollment.setText((String)m1.get("Enrollment"));
                                            fname.setText((String)m1.get("Father Name"));
                                            mname.setText((String)m1.get("Mother Name"));
                                            name.setText((String)m1.get("Name"));
                                            rollno.setText((String)m1.get("Roll Number"));
                                            ten.setText((String)m1.get("Tenth"));
                                            twelve.setText((String)m1.get("Twelth"));
                                            Toast.makeText(admin_view_all_data.this,Thread.currentThread().getName()+"hi "+(String)m.get("Email"),Toast.LENGTH_SHORT).show();
                                            tl.addView(v);

                                        }
                                    });
                                }
                            });

                        }
                    }

                }
            }
        });

    }
    public boolean validEmail(String email)
    {
        email=email.trim();
       String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (email.matches(emailPattern)==false)
        {
            return false;
        }
        return true;
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //intent to admin page

        Intent intent = new Intent(admin_view_all_data.this,Admin_page.class);
        startActivity(intent);
        finish();
    }

}