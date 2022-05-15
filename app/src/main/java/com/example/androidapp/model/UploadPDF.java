package com.example.androidapp.model;

public class UploadPDF {
    public String name, url;

    public UploadPDF() {
    }

    public UploadPDF(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
