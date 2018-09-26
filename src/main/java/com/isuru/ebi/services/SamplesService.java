package com.isuru.ebi.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.isuru.ebi.api.ResponseOk;
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
                ResponseOk response = objectMapper.readerFor(ResponseOk.class).readValue(httpResponse.getEntity().getContent());
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
            logger.severe("Failed to parse message: " + e.getMessage());
        }

        return noOfSamples;
    }


}
