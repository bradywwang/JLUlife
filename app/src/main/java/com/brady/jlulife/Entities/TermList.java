package com.brady.jlulife.Entities;

/**
 * Created by brady on 15-11-8.
 */
public class TermList {
    private String termName;
    private String startDate;
    private String termSeq;
    private String examDate;
    private String termType;
    private String activeStage;
    private String year;
    private String vacationDate;
    private String weeks;
    private String termId;
    private String egrade;

    public String getTermName() {
        return termName;
    }

    public void setTermName(String termName) {
        this.termName = termName;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getTermSeq() {
        return termSeq;
    }

    public void setTermSeq(String termSeq) {
        this.termSeq = termSeq;
    }

    public String getExamDate() {
        return examDate;
    }

    public void setExamDate(String examDate) {
        this.examDate = examDate;
    }

    public String getTermType() {
        return termType;
    }

    public void setTermType(String termType) {
        this.termType = termType;
    }

    public String getActiveStage() {
        return activeStage;
    }

    public void setActiveStage(String activeStage) {
        this.activeStage = activeStage;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getVacationDate() {
        return vacationDate;
    }

    public void setVacationDate(String vacationDate) {
        this.vacationDate = vacationDate;
    }

    public String getWeeks() {
        return weeks;
    }

    public void setWeeks(String weeks) {
        this.weeks = weeks;
    }

    public String getTermId() {
        return termId;
    }

    public void setTermId(String termId) {
        this.termId = termId;
    }

    public String getEgrade() {
        return egrade;
    }

    public void setEgrade(String egrade) {
        this.egrade = egrade;
    }

    @Override
    public String toString() {
        return termName;
    }
}
