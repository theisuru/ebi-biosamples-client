package com.isuru.ebi;

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
