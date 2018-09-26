package com.isuru.ebi.util;

public class ResourceURIs {
    public static final String BASE_URL = "http://www.ebi.ac.uk/biosamples/samples";
    private static final String NO_OF_RECORDS_URL = "http://www.ebi.ac.uk/biosamples/samples?page=0&size=1";

    public String getUriForNoOfRecords() {
        return NO_OF_RECORDS_URL;
    }
}
