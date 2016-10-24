package com.example.patelkev.newyorktimes.Models;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by patelkev on 10/23/16.
 */

public class FilterModel implements Serializable {
    Boolean isOldest;
    Boolean isNewest;
    Boolean isArts;
    Boolean isSports;
    Boolean isForeign;
    Date beginDate;

    public FilterModel () {
        isOldest = false;
        isNewest = false;
        isArts = false;
        isSports = false;
        isForeign = false;
        beginDate = new Date();
    }

    public Boolean getOldest() {
        return isOldest;
    }

    public void setOldest(Boolean oldest) {
        isOldest = oldest;
    }

    public Boolean getNewest() {
        return isNewest;
    }

    public void setNewest(Boolean newest) {
        isNewest = newest;
    }

    public Boolean getArts() {
        return isArts;
    }

    public void setArts(Boolean arts) {
        isArts = arts;
    }

    public Boolean getSports() {
        return isSports;
    }

    public void setSports(Boolean sports) {
        isSports = sports;
    }

    public Boolean getForeign() {
        return isForeign;
    }

    public void setForeign(Boolean foreign) {
        isForeign = foreign;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public String getFilterString() {
        if (isSports == null && isArts == null && isForeign == null) {
            return null;
        }

        if(!isSports && !isArts && !isForeign) {
            return null;
        }
        String args = null;

        if(isSports) {
            args = "%22Sports%22";
        }
        if (isArts) {
            if (args != null) {
                args += "%20%22Arts%22";
            } else {
                args = "%22Arts%22";
            }
        }
        if (isForeign) {
            if (args != null) {
                args += "%20%22Foreign%22";
            } else {
                args = "%22Foreign%22";
            }
        }

        String retString = String.format("news_desk:(%s)",args);
        return retString;

    }
}
