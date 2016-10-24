
package com.example.patelkev.newyorktimes.Models;

import java.io.Serializable;

public class Headline implements Serializable {

    private String main;
    private String contentKicker;
    private String kicker;

    public String getMain() {
        return main;
    }

    public String getContentKicker() {
        return contentKicker;
    }

    public String getKicker() {
        return kicker;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public void setContentKicker(String contentKicker) {
        this.contentKicker = contentKicker;
    }

    public void setKicker(String kicker) {
        this.kicker = kicker;
    }
}
