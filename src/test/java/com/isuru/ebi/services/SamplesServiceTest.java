package com.isuru.ebi.services;

import com.isuru.ebi.services.SamplesService;
import org.junit.Assert;
import org.junit.Test;

/*
* Here we will not mock external service API.
* In a real scenario, we will use mock API in unit tests and call to actual API for integration tests
* */
public class SamplesServiceTest {
    @Test
    public void testGetNoOfSamples() {
        SamplesService samplesService = new SamplesService();
        long samples = samplesService.getNoOfSamples();

        Assert.assertTrue(samples != -1);
    }
}
