package com.example.studenthelpdesk;

import com.google.firebase.Timestamp;

import java.util.Date;

public class RequestData implements Comparable<RequestData>{
    String header,reason,Applieddate,ReviewedDate,reviewReason,email;
    Timestamp applied;

    public Timestamp getApplied() {
        return applied;
    }

    public void setApplied(Timestamp applied) {
        this.applied = applied;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    long status,id,delforme;
    RequestData()
    {
        header="";
        reason="";
        Applieddate="";
        ReviewedDate="";
        reviewReason="";
        status=-1;
        id=0;
        delforme=0;
    }
    public void setId(int id) {
        this.id = id;
    }

    public void setDelforme(long delforme) {
        this.delforme = delforme;
    }

    public long getDelforme() {
        return delforme;
    }

    public long getId() {
        return id;
    }

    public String getApplieddate() {
        return Applieddate;
    }

    public void setApplieddate(String applieddate) {
        Applieddate = applieddate;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public void setStatus(long status) {
        this.status = status;
    }

    public void setReviewedDate(String reviewedDate) {
        ReviewedDate = reviewedDate;
    }

    public void setReviewReason(String reviewReason) {
        this.reviewReason = reviewReason;
    }

    public String getHeader() {
        return header;
    }

    public String getReason() {
        return reason;
    }

    public String getReviewedDate() {
        return ReviewedDate;
    }

    public String getReviewReason() {
        return reviewReason;
    }

    public long getStatus() {
        return status;
    }
    public int compareTo(RequestData o) {
        return o.applied.compareTo(this.applied);
    }
}
