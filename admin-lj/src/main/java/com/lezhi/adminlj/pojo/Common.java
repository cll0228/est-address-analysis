package com.lezhi.adminlj.pojo;

/**
 * Created by Cuill on 2017/2/15.
 */
public class Common {
    private String key;
    private String text;
    private String urldata;
    private String value;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUrldata() {
        return urldata;
    }

    public void setUrldata(String urldata) {
        this.urldata = urldata;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Common(String key, String text, String urldata, String value) {
        this.key = key;
        this.text = text;
        this.urldata = urldata;
        this.value = value;
    }

    public Common() {

    }
}
