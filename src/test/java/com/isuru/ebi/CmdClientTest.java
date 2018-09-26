package com.isuru.ebi;

import com.isuru.ebi.api.Page;
import com.isuru.ebi.beans.AccessionPage;
import com.isuru.ebi.services.SamplesService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;

@RunWith(MockitoJUnitRunner.class)
public class CmdClientTest {
    @Mock
    private SamplesService samplesService;

    @Test
    public void testListenForInputTotalRecords() {
        Mockito.when(samplesService.getNoOfSamples()).thenReturn(1L);

        String input = "1";
        InputStream in = new ByteArrayInputStream(input.getBytes());

        CmdClient cmdClient = new CmdClient();
        boolean continueProgram = cmdClient.listenForInput(new Scanner(in), samplesService);
        Assert.assertTrue(continueProgram);
    }

    @Test
    public void testListenForInputNameFromAccession() {
        String accession = "accession";
        String sampleName = "sample_name";
        Mockito.when(samplesService.getSampleNameFromAccession(accession)).thenReturn(sampleName);

        String input = 2 + "\n" + accession;
        InputStream in = new ByteArrayInputStream(input.getBytes());

        CmdClient cmdClient = new CmdClient();
        boolean continueProgram = cmdClient.listenForInput(new Scanner(in), samplesService);
        Assert.assertTrue(continueProgram);
    }

    @Test
    public void testListenForInputNameFromAccessionNotExist() {
        String accession = "accession";
        String sampleName = "sample_name";
        Mockito.when(samplesService.getSampleNameFromAccession(accession)).thenReturn(null);

        String input = 2 + "\n" + accession;
        InputStream in = new ByteArrayInputStream(input.getBytes());

        CmdClient cmdClient = new CmdClient();
        boolean continueProgram = cmdClient.listenForInput(new Scanner(in), samplesService);
        Assert.assertTrue(continueProgram);
    }

    @Test
    public void testListenForInputAttributeQuery() {
        Page page = new Page();
        page.setTotalElements(3);
        AccessionPage accessionPage = new AccessionPage();
        accessionPage.setPage(page);
        accessionPage.setAccessions("accession1, accession2, accession3");
        Mockito.when(samplesService.getFilteredAccessions("field", "value")).thenReturn(accessionPage);

        String input = 3 + "\n" + "field:value";
        InputStream in = new ByteArrayInputStream(input.getBytes());

        CmdClient cmdClient = new CmdClient();
        boolean continueProgram = cmdClient.listenForInput(new Scanner(in), samplesService);
        Assert.assertTrue(continueProgram);
    }

    @Test
    public void testListenForInputAttributeQueryNotExist() {
        Page page = new Page();
        page.setTotalElements(0);
        AccessionPage accessionPage = new AccessionPage();
        accessionPage.setPage(page);
        accessionPage.setAccessions("");
        Mockito.when(samplesService.getFilteredAccessions("field", "value")).thenReturn(accessionPage);

        String input = 3 + "\n" + "field:value";
        InputStream in = new ByteArrayInputStream(input.getBytes());

        CmdClient cmdClient = new CmdClient();
        boolean continueProgram = cmdClient.listenForInput(new Scanner(in), samplesService);
        Assert.assertTrue(continueProgram);
    }

    @Test
    public void testListenForInputExit() {
        Mockito.when(samplesService.getNoOfSamples()).thenReturn(1L);

        String input = "exit";
        InputStream in = new ByteArrayInputStream(input.getBytes());

        CmdClient cmdClient = new CmdClient();
        boolean continueProgram = cmdClient.listenForInput(new Scanner(in), samplesService);
        Assert.assertFalse(continueProgram);
    }

    @Test
    public void testListenForInputInvalidInput() {
        Mockito.when(samplesService.getNoOfSamples()).thenReturn(1L);

        String input = "hello";
        InputStream in = new ByteArrayInputStream(input.getBytes());

        CmdClient cmdClient = new CmdClient();
        boolean continueProgram = cmdClient.listenForInput(new Scanner(in), samplesService);
        Assert.assertTrue(continueProgram);
    }
}
