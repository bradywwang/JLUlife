package com.brady.jlulife.Entities.Score;

/**
 * Created by brady on 15-11-19.
 */
public class Student {
    private int studId;
    private String name;
    private AdminClass adminClass;
    private int admissionYear;
    private int studStatus;
    private int studNo;
    private int egrade;

    public int getStudId() {
        return studId;
    }

    public void setStudId(int studId) {
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

    public int getAdmissionYear() {
        return admissionYear;
    }

    public void setAdmissionYear(int admissionYear) {
        this.admissionYear = admissionYear;
    }

    public int getStudStatus() {
        return studStatus;
    }

    public void setStudStatus(int studStatus) {
        this.studStatus = studStatus;
    }

    public int getStudNo() {
        return studNo;
    }

    public void setStudNo(int studNo) {
        this.studNo = studNo;
    }

    public int getEgrade() {
        return egrade;
    }

    public void setEgrade(int egrade) {
        this.egrade = egrade;
    }
}
