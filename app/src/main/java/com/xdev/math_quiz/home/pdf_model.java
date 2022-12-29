package com.xdev.math_quiz.home;

public class pdf_model {

    String filename;

    String url;

    pdf_model() {

    }

    public pdf_model(String filename, String url) {
        this.filename = filename;
        this.url = url;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}