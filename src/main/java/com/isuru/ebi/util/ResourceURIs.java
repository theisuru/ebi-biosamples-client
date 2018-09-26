package com.isuru.ebi.util;

/*
* Construct URI for specific resources
* */
public class ResourceURIs {
    private static final String BASE_URL = "http://www.ebi.ac.uk/biosamples/samples/";
    private static final String NO_OF_RECORDS_URL = "http://www.ebi.ac.uk/biosamples/samples?page=0&size=1";

    public String getUriForNoOfRecords() {
        return NO_OF_RECORDS_URL;
    }

    public String getUriForAccession(String accession) {
        return BASE_URL + accession;
    }

    public String getUriForSearchAttributeValue(String attribute, String value) {
        return BASE_URL + "?filter=attr:" + attribute + ":" + value;
    }

    public String getUriForSearchAttributeValue(String attribute, String value, long page, int size) {
        return BASE_URL + "?filter=attr:" + attribute + ":" + value + "&page="+ page +"&size=" + size;
    }
}
