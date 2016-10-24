
package com.example.patelkev.newyorktimes.Models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Doc implements Serializable{

    private String webUrl;
    private String snippet;
    private List<Multimedium> multimedia = new ArrayList<Multimedium>();
    private Headline headline;
    private String sectionName;
    private Object subsectionName;

    public String getWebUrl() {
        return webUrl;
    }

    public String getSnippet() {
        return snippet;
    }

    public List<Multimedium> getMultimedia() {
        return multimedia;
    }

    public Headline getHeadline() {
        return headline;
    }

    public String getHeadlineString() {
        return headline.getMain();
    }

    public String getImageUrl() {
        if (multimedia.size() > 0) {
            return multimedia.get(0).getUrl();
        } else {
            return null;
        }
    }

    public String getSectionName() {
        return sectionName;
    }

    public Object getSubsectionName() {
        return subsectionName;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    public void setSnippet(String snippet) {
        this.snippet = snippet;
    }

    public void setMultimedia(List<Multimedium> multimedia) {
        this.multimedia = multimedia;
    }

    public void setHeadline(Headline headline) {
        this.headline = headline;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public void setSubsectionName(Object subsectionName) {
        this.subsectionName = subsectionName;
    }

}
