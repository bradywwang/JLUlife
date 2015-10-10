package com.brady.jlulife.Entities;

/**
 * Created by wang on 2015/9/27.
 */
public class NewsBaseInfo {
    private String title;
    private String date;
    private String href;
    private String dep;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getDep() {
        return dep;
    }

    public void setDep(String dep) {
        this.dep = dep;
    }

    @Override
    public String toString() {
        return "NewsBaseInfo{" +
                "title='" + title + '\'' +
                ", date='" + date + '\'' +
                ", href='" + href + '\'' +
                ", dep='" + dep + '\'' +
                '}';
    }
}
