package com.example.studenthelpdesk;

public class Data {
    private String uname,name,email,fname,mname,dob,aadhar,Pan,address,Pno,course,branch,semester,rno,eno;
    private float cgpa,ten,twel;
    public void setUname(String s)
    {
        uname=s;
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
    public void ToDatabase()
    {
        
    }
}
