package com.brady.jlulife.Entities;

/**
 * Created by wang on 2015/9/27.
 */
public class News {
    private String title;
    private String submitDepartment;
    private String submitTime;
    private String content;
    private String id;
    private String cTop;
    private String cAttach;
    private String cAttachName;

    public String getcAttachName() {
        return cAttachName;
    }

    public void setcAttachName(String cAttachName) {
        this.cAttachName = cAttachName;
    }

    public String getcAttach() {
        return cAttach;
    }

    public void setcAttach(String cAttach) {
        this.cAttach = cAttach;
    }

    public String getcTop() {
        return cTop;
    }

    public void setcTop(String cTop) {
        this.cTop = cTop;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubmitDepartment() {
        return submitDepartment;
    }

    public void setSubmitDepartment(String submitDepartment) {
        this.submitDepartment = submitDepartment;
    }

    public String getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(String submitTime) {
        this.submitTime = submitTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
