package com.isuru.ebi.services;

import com.isuru.ebi.beans.AccessionPage;
import org.junit.Assert;
import org.junit.Test;

/*
 * Here we will not mock external service API.
 * In a real scenario, we will use mock API in unit tests and will call to actual API for integration tests
 * */
public class SamplesServiceTest {

    @Test
    public void testGetNoOfSamples() {
        SamplesService samplesService = new SamplesService();
        long samples = samplesService.getNoOfSamples();

        Assert.assertTrue(samples != -1);
    }

    @Test
    public void testGetSampleNameFromAccession() {
        SamplesService samplesService = new SamplesService();
        String sampleName = samplesService.getSampleNameFromAccession("SAMD00000001");

        Assert.assertEquals("SAMD00000001", sampleName);
    }

    @Test
    public void testGetFilteredAccessions() {
        SamplesService samplesService = new SamplesService();
        AccessionPage accessionsPage = samplesService.getFilteredAccessions("organism+part", "liver");

        Assert.assertFalse(accessionsPage.getAccessions().isEmpty());
    }

    @Test
    public void testGetFilteredAccessionsWithPageSize() {
        SamplesService samplesService = new SamplesService();
        AccessionPage accessionsPage = samplesService.getFilteredAccessions("organism+part", "liver", 10L, 20);

        Assert.assertFalse(accessionsPage.getAccessions().isEmpty());
    }
}