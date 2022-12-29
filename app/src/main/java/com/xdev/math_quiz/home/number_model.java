package com.xdev.math_quiz.home;


public class number_model {
    String email;
    String fno;
    String level;
    String subscription;
    String uname;

    number_model() {

    }

    public number_model(String email, String fno, String level, String subscription, String uname) {
        this.email = email;
        this.fno = fno;
        this.level = level;
        this.subscription = subscription;
        this.uname = uname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFno() {
        return fno;
    }

    public void setFno(String fno) {
        this.fno = fno;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getSubscription() {
        return subscription;
    }

    public void setSubscription(String subscription) {
        this.subscription = subscription;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }
}