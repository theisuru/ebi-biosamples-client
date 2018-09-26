package com.isuru.ebi;

import com.isuru.ebi.beans.AccessionPage;
import com.isuru.ebi.services.SamplesService;

import java.util.Scanner;

public class CmdClient {
    public static void main(String[] args) {
        System.out.println("Welcome to EBI-BioSamples Client command line interface. Please enter");
        System.out.println("1 to view total number of samples");
        System.out.println("2 to view sample name of the given accession");
        System.out.println("3 to get accessions of custom query");
        System.out.println("4 to run example, attribute query: organism+part:liver");
        System.out.println("5 to run example, attribute query: organism+part:liver:10:5");
        System.out.println("6 to run example, attribute query: disease:leukaemia:1:5");
        System.out.println("7 to run example, sample name from accession = SAMD00000001");
        System.out.println("------------------------------------------------------\n");
        SamplesService samplesService = new SamplesService();

        Scanner scanner = new Scanner(System.in);
        CmdClient cmdClient = new CmdClient();

        System.out.print("Please enter a function number: ");
        while(cmdClient.listenForInput(scanner, samplesService)) {
            System.out.print("Please enter a function number: ");
        }
        System.out.println("Good-bye");

    }

    boolean listenForInput(Scanner scanner, SamplesService samplesService) {
        boolean continueProgram = true;
        String function = scanner.next();
        try {
            int functionNumber = Integer.parseInt(function);
            switch (functionNumber) {
                case 1:
                    printNoOfSamples(samplesService);
                    break;
                case 2:
                    printNameOfAccession(scanner, samplesService);
                    break;
                case 3:
                    printAccessionsForFilter(scanner, samplesService);
                    break;
                case 4:
                    printAccessionsForFilter(samplesService, "organism+part:liver");
                    break;
                case 5:
                    printAccessionsForFilter(samplesService,"organism+part:liver:10:5");
                    break;
                case 6:
                    printAccessionsForFilter(samplesService,"disease:leukaemia:1:5");
                    break;
                case 7:
                    printNameOfAccession(samplesService, "SAMD00000001");
                    break;
                default:
                    System.out.println("Invalid number\n");
                    break;
            }
        } catch (NumberFormatException e) {
            if ("exit".equalsIgnoreCase(function)) {
                continueProgram = false;
            } else {
                System.out.println("Please enter a correct function number\n");
            }
        }
        return continueProgram;
    }

    private void printNoOfSamples(SamplesService samplesService) {
        System.out.println("Total number of samples: " + samplesService.getNoOfSamples() + "\n");
    }

    private void printNameOfAccession(Scanner scanner, SamplesService samplesService) {
        System.out.print("Please enter accession: ");
        String accession = scanner.next();
        printNameOfAccession(samplesService, accession);
    }

    private void printNameOfAccession(SamplesService samplesService, String accession) {
        String sampleName = samplesService.getSampleNameFromAccession(accession);
        if (sampleName != null) {
            System.out.println("Name of the sample for accession " + accession + " is " + sampleName + "\n");
        } else {
            System.out.println("Accession " + accession + " does not exist\n");
        }
    }

    private void printAccessionsForFilter(Scanner scanner, SamplesService samplesService) {
        System.out.print("Please enter search query in format {attribute;value;page;size}: ");
        String query = scanner.next();
        printAccessionsForFilter(samplesService, query);
    }

    private void printAccessionsForFilter(SamplesService samplesService, String query) {
        String[] queryParam = query.split(":");
        AccessionPage accessionPage;
        if (queryParam.length == 2) {
            accessionPage = samplesService.getFilteredAccessions(queryParam[0], queryParam[1]);
            System.out.println("Query: attribute = " + queryParam[0] + ", value = " + queryParam[1]);
        } else if (queryParam.length == 4) {
            accessionPage = samplesService.getFilteredAccessions(
                    queryParam[0], queryParam[1], Long.parseLong(queryParam[2]), Integer.parseInt(queryParam[3]));
            System.out.println("Query: attribute = " + queryParam[0] + ", value = "
                    + queryParam[1] + ", page = " + queryParam[2] + ", size = " + queryParam[3]);
        } else {
            System.out.println("Invalid query format\n");
            accessionPage = null;
        }

        if (accessionPage != null) {
            System.out.println("Total number of records: " + accessionPage.getPage().getTotalElements());
            System.out.println("Accessions: " + accessionPage.getAccessions() + "\n");
        }
    }
}
