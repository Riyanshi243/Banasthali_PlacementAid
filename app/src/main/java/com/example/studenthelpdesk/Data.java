package com.example.studenthelpdesk;

import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Data {
    private String uname,name,email,fname,mname,dob,aadhar,Pan,address,Pno,course,branch,semester,rno,eno,gender;
    private float cgpa,ten,twel;

    private DocumentReference documentReference;
    public void setUname(String s)
    {
        uname=s;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setName(String s)
    {
        name=s;
    }

    public void setAadhar(String aadhar) {
        this.aadhar = aadhar;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setTwel(float twel) {
        this.twel = twel;
    }

    public void setTen(float ten) {
        this.ten = ten;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public void setRno(String rno) {
        this.rno = rno;
    }

    public void setPno(String pno) {
        Pno = pno;
    }

    public void setPan(String pan) {
        Pan = pan;
    }

    public void setMname(String mname) {
        this.mname = mname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public void setEno(String eno) {
        this.eno = eno;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public void setCgpa(float cgpa) {
        this.cgpa = cgpa;
    }
    public int ToDatabase()
    {
        Map<String,Object> m=new HashMap<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        documentReference = db.collection("AllowedUser").document(uname).collection("Change").document("change");
        m.put("Address",address);
        m.put("PhoneNumber",Pno);
        m.put("Aadhar",aadhar);
        m.put("PAN",Pan);
        m.put("DOB",dob);
        m.put("Gender",gender);
        m.put("Semester",semester);
        int flag[]={0};
        documentReference.set(m).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                m.clear();
                m.put("CGPA",cgpa);
                m.put("Tenth",ten);
                m.put("Twelth",twel);
                m.put("Course",course);
                m.put("Name",name);
                m.put("Mother Name",mname);
                m.put("Father Name",fname);
                m.put("Email",email);
                m.put("Roll Number",rno);
                m.put("Enrollment",eno);
                m.put("Branch",branch);
                DocumentReference documentReference1 = db.collection("AllowedUser").document(uname).collection("Permanent").document("perm");
                documentReference1.set(m).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        flag[0]=1;
                    }
                });
            }
        });
        return  flag[0];

    }

}
