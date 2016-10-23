
package com.example.patelkev.newyorktimes.Models;

public class Multimedium {

    private Integer width;
    private String url;
    private Integer height;
    private String subtype;
    private String type;

    public Integer getWidth() {
        return width;
    }

    public String getUrl() {
        return "http://nytimes.com/" + url;
    }

    public Integer getHeight() {
        return height;
    }

    public String getSubtype() {
        return subtype;
    }

    public String getType() {
        return type;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public void setSubtype(String subtype) {
        this.subtype = subtype;
    }

    public void setType(String type) {
        this.type = type;
    }
}
