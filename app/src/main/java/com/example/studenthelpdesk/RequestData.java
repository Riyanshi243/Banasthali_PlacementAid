package com.example.studenthelpdesk;

public class RequestData {
    String header,reason,Applieddate,ReviewedDate,reviewReason;
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
}
