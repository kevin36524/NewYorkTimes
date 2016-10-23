
package com.example.patelkev.newyorktimes.Models;

import java.util.ArrayList;
import java.util.List;

public class Response {

    private List<Doc> docs = new ArrayList<Doc>();

    public List<Doc> getDocs() {
        return docs;
    }

    public void setDocs(List<Doc> docs) {
        this.docs = docs;
    }

}
