package com.example.studenthelpdesk;

import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Data {
    private String uname,name,email,fname,mname,dob,aadhar,Pan,address,Pno,course,branch,semester,rno,eno,gender;
    private float cgpa,ten,twel;

    private DocumentReference documentReference;

    public String getUname() {
        return uname;
    }

    public float getCgpa() {
        return cgpa;
    }

    public float getTen() {
        return ten;
    }

    public float getTwel() {
        return twel;
    }

    public String getAadhar() {
        return aadhar;
    }

    public String getAddress() {
        return address;
    }

    public String getBranch() {
        return branch;
    }

    public String getCourse() {
        return course;
    }

    public String getDob() {
        return dob;
    }

    public String getEmail() {
        return email;
    }

    public String getEno() {
        return eno;
    }

    public String getFname() {
        return fname;
    }

    public String getGender() {
        return gender;
    }

    public String getMname() {
        return mname;
    }

    public String getName() {
        return name;
    }

    public String getPan() {
        return Pan;
    }

    public String getPno() {
        return Pno;
    }

    public String getRno() {
        return rno;
    }

    public String getSemester() {
        return semester;
    }
    

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
    public String FromDatabase(String email1)
    {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String f[]={null};
        documentReference = db.collection("AllowedUser").document(email1).collection("Change").document("change");
        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Map<String, Object> doc = documentSnapshot.getData();
                address= (String) doc.get("Address");
                Pno=(String)doc.get("PhoneNumber");
                aadhar=(String)doc.get("Aadhar");
                Pan= (String) doc.get("PAN");
                dob= (String) doc.get("DOB");
                gender= (String) doc.get("Gender");
                semester= (String) doc.get("Semester");
                DocumentReference documentReference1 = db.collection("AllowedUser").document(email).collection("Permanent").document("perm");
                documentReference1.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        Map<String, Object> doc = documentSnapshot.getData();
                        cgpa= (float) doc.get("CGPA");
                        ten= (float) doc.get("Tenth");
                        twel= (float) doc.get("Twelth");
                        course= (String) doc.get("Course");
                        name= (String) doc.get("Name");
                        mname= (String) doc.get("Mother Name");
                        fname= (String) doc.get("Father Name");
                        email= (String) doc.get("Email");
                        rno=(String) doc.get("Roll Number");
                        eno=(String)doc.get("Enrollment");
                        branch= (String) doc.get("Branch");
                        f[0]=null;
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        f[0]= e.getMessage();
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                f[0]= e.getMessage();    
            }
        });
        return f[0];
    }
    public int ToDatabase()
    {
        Map<String,Object> m=new HashMap<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        documentReference = db.collection("AllowedUser").document(email).collection("Change").document("change");
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
                DocumentReference documentReference1 = db.collection("AllowedUser").document(email).collection("Permanent").document("perm");
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
