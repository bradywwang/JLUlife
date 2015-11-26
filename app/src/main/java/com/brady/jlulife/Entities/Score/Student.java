package com.brady.jlulife.Entities.Score;

/**
 * Created by brady on 15-11-19.
 */
public class Student {
    private String studId;
    private String name;
    private AdminClass adminClass;
    private String admissionYear;
    private String studStatus;
    private String studNo;
    private String egrade;

    public String getStudId() {
        return studId;
    }

    public void setStudId(String studId) {
        this.studId = studId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AdminClass getAdminClass() {
        return adminClass;
    }

    public void setAdminClass(AdminClass adminClass) {
        this.adminClass = adminClass;
    }

    public String getAdmissionYear() {
        return admissionYear;
    }

    public void setAdmissionYear(String admissionYear) {
        this.admissionYear = admissionYear;
    }

    public String getStudStatus() {
        return studStatus;
    }

    public void setStudStatus(String studStatus) {
        this.studStatus = studStatus;
    }

    public String getStudNo() {
        return studNo;
    }

    public void setStudNo(String studNo) {
        this.studNo = studNo;
    }

    public String getEgrade() {
        return egrade;
    }

    public void setEgrade(String egrade) {
        this.egrade = egrade;
    }
}
