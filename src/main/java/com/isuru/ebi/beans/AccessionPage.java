package com.isuru.ebi.beans;

import com.isuru.ebi.api.Page;

public class AccessionPage {
    private String accessions;
    private Page page;

    public String getAccessions() {
        return accessions;
    }

    public void setAccessions(String accessions) {
        this.accessions = accessions;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }
}
