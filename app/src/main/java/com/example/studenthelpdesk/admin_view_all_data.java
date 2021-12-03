package com.example.studenthelpdesk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class admin_view_all_data extends AppCompatActivity {
    TextView srno,address,email,name,gender,phoneno,DOB,rollno,enrollment,course,branch,sem,fname,mname,adhar,pan,ten,twelve,CGPA;
    TableLayout tl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view_all_data);
        tl=findViewById(R.id.tl);
        CollectionReference doc = FirebaseFirestore.getInstance().collection("AllowedUser");
        final int[] c = {1};
        final ArrayList<Data> student=new ArrayList<>();
        doc.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                List<DocumentSnapshot> t = queryDocumentSnapshots.getDocuments();
                for(int i=0;i<t.size();i++)
                {

                    Data s = new Data();
                    Map<String, Object> m = t.get(i).getData();
                    if(m.containsKey("Email")==false)
                    {
                        continue;
                    }
                    else if(validEmail((String)m.get("Email"))==true)
                    {
                        if((Boolean)m.get("Admin")==false&&(Boolean) m.get("New")==false)
                        {
                            doc.document((String)m.get("Email")).collection("Change").document("change").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {

                                    Map<String, Object> m1 = documentSnapshot.getData();
                                    s.setAadhar((String)m1.get("Aadhar"));
                                    s.setDob((String)m1.get("DOB"));
                                    s.setGender((String)m1.get("Gender"));
                                    s.setPan((String)m1.get("PAN"));
                                    s.setPno((String)m1.get("PhoneNumber"));
                                    s.setSemester((String)m1.get("Semester"));
                                    s.setAddress((String)m1.get("Address"));
                                    DocumentReference doc2 = doc.document((String) m.get("Email")).collection("Permanent").document("perm");
                                    doc2.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                        @Override
                                        public void onSuccess(DocumentSnapshot documentSnapshot) {

                                            Map<String, Object> m1 = documentSnapshot.getData();
                                            s.setBranch((String)m1.get("Branch"));
                                            s.setCgpa((String)m1.get("CGPA"));
                                            s.setCourse((String)m1.get("Course"));
                                            s.setEmail((String)m1.get("Email"));
                                            s.setEno((String)m1.get("Enrollment"));
                                            s.setFname((String)m1.get("Father Name"));
                                            s.setMname((String)m1.get("Mother Name"));
                                            s.setName((String)m1.get("Name"));
                                            s.setRno((String)m1.get("Roll Number"));
                                            s.setTen((String)m1.get("Tenth"));
                                            s.setTwel((String)m1.get("Twelth"));
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
                                            address=(TextView)v.findViewById(R.id.address);

                                            adhar.setText(s.getAadhar());
                                            DOB.setText(s.getDob());
                                            gender.setText(s.getGender());
                                            pan.setText(s.getPan());
                                            phoneno.setText(s.getPno());
                                            sem.setText(s.getSemester());
                                            address.setText(s.getAddress());
                                            branch.setText(s.getBranch());
                                            CGPA.setText(s.getCgpa());
                                            course.setText(s.getCourse());
                                            email.setText(s.getEmail());
                                            enrollment.setText(s.getEno());
                                            fname.setText(s.getFname());
                                            mname.setText(s.getMname());
                                            name.setText(s.getName());
                                            rollno.setText(s.getRno());
                                            ten.setText(s.getTen());
                                            twelve.setText(s.getTwel());
                                            srno.setText(c[0]+++"");

                                            tl.addView(v);
                                            if(c[0]==t.size())
                                                Toast.makeText(admin_view_all_data.this,"Data loaded",Toast.LENGTH_SHORT).show();
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

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_logout:
                AlertDialog.Builder ab = new AlertDialog.Builder(this);
                ab.setTitle("LOGOUT");
                ab.setMessage("Are you sure?");
                ab.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                        firebaseAuth.signOut();
                        Intent intent = new Intent(admin_view_all_data.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }).setNegativeButton("NO ", new DialogInterface.OnClickListener() {
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

}