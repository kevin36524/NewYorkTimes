
package com.example.patelkev.newyorktimes.Models;

import java.io.Serializable;

public class Multimedium implements Serializable {

    private Integer width;
    private String url;
    private Integer height;
    private String subtype;
    private String type;

    public Integer getWidth() {
        return width;
    }

    public String getUrl() {
        if (url != null) {
            return "http://nytimes.com/" + url;
        } else {
            return null;
        }
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
