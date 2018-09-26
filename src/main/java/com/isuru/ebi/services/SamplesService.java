package com.isuru.ebi.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.isuru.ebi.api.ResponsePaged;
import com.isuru.ebi.api.ResponseSimple;
import com.isuru.ebi.api.Sample;
import com.isuru.ebi.beans.AccessionPage;
import com.isuru.ebi.util.ResourceURIs;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.util.logging.Logger;

public class SamplesService {
    private static final Logger logger = Logger.getLogger("SampleService");
    private static final String MEDIA_TYPE_APPLICATION_HAL_JSON = "application/hal+json";

    private HttpClient client = HttpClientBuilder.create().build();
    private ObjectMapper objectMapper = new ObjectMapper();
    private ResourceURIs resourceURIs = new ResourceURIs();

    public long getNoOfSamples() {
        HttpGet request = new HttpGet(resourceURIs.getUriForNoOfRecords());
        request.addHeader(HttpHeaders.ACCEPT, MEDIA_TYPE_APPLICATION_HAL_JSON);

        long noOfSamples;
        try {
            HttpResponse httpResponse = client.execute(request);
            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                ResponsePaged response = objectMapper.readerFor(ResponsePaged.class).readValue(httpResponse.getEntity().getContent());
                noOfSamples = response.getPage().getTotalElements();
            } else {
                noOfSamples = -1;
                logger.severe("Failed to retrieve number of samples, Error code: " + httpResponse.getStatusLine().getStatusCode());
            }
        } catch (ClientProtocolException e) {
            noOfSamples = -1;
            logger.severe("Failed to retrieve total number of samples: " + e.getMessage());
        } catch (IOException e) {
            noOfSamples = -1;
            logger.severe("Failed to parse response message: " + e.getMessage());
        }

        return noOfSamples;
    }

    public String getSampleNameFromAccession(String accession) {
        HttpGet request = new HttpGet(resourceURIs.getUriForAccession(accession));
        request.addHeader(HttpHeaders.ACCEPT, MEDIA_TYPE_APPLICATION_HAL_JSON);

        String sampleName = null;
        try {
            HttpResponse httpResponse = client.execute(request);
            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                ResponseSimple response = objectMapper.readerFor(ResponseSimple.class).readValue(httpResponse.getEntity().getContent());
                sampleName = response.getName();
            } else {
                logger.severe("Failed to retrieve sample, Error code: " + httpResponse.getStatusLine().getStatusCode());
            }
        } catch (ClientProtocolException e) {
            logger.severe("Failed to retrieve sample: " + e.getMessage());
        } catch (IOException e) {
            logger.severe("Failed to parse response message: " + e.getMessage());
        }

        return sampleName;
    }

    public AccessionPage getFilteredAccessions(String attribute, String value) {
        return getFilteredAccessions(resourceURIs.getUriForSearchAttributeValue(attribute, value));
    }

    public AccessionPage getFilteredAccessions(String attribute, String value, long page, int size) {
        return getFilteredAccessions(resourceURIs.getUriForSearchAttributeValue(attribute, value, page, size));
    }

    private AccessionPage getFilteredAccessions(String url) {
        HttpGet request = new HttpGet(url);
        request.addHeader(HttpHeaders.ACCEPT, MEDIA_TYPE_APPLICATION_HAL_JSON);

        AccessionPage accessionPage = new AccessionPage();
        StringBuilder accessionsBuilder = new StringBuilder();
        try {
            HttpResponse httpResponse = client.execute(request);
            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                ResponsePaged response = objectMapper.readerFor(ResponsePaged.class).readValue(httpResponse.getEntity().getContent());
                accessionPage.setPage(response.getPage());
                if (response.getEmbedded() != null) {
                    for (Sample sample : response.getEmbedded().getSamples()) {
                        accessionsBuilder.append(sample.getAccession());
                        accessionsBuilder.append(", ");
                    }
                }

                accessionPage.setAccessions(accessionsBuilder.toString());
            } else {
                logger.severe("Failed to retrieve number of samples, Error code: " + httpResponse.getStatusLine().getStatusCode());
            }
        } catch (ClientProtocolException e) {
            logger.severe("Failed to retrieve total number of samples: " + e.getMessage());
        } catch (IOException e) {
            logger.severe("Failed to parse message: " + e.getMessage());
        }

        return accessionPage;
    }

}
