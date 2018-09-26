package com.isuru.ebi;

import com.isuru.ebi.services.SamplesService;

import java.util.Scanner;

public class CmdClient {
    public static void main(String[] args) {
        System.out.println("Welcome to EBI-BioSamples Client CMD interface");
        System.out.println("1. To view total number of samples");
        System.out.println("------------------------------------------------------");
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
                    System.out.println("Total number of samples: " + samplesService.getNoOfSamples() + "\n");
                    break;
                default:
                    System.out.println("Invalid number\n");
                    break;
            }
        } catch (NumberFormatException e) {
            if (function.equalsIgnoreCase("exit")) {
                continueProgram = false;
            } else {
                System.out.println("Please enter a correct function number\n");
            }
        }
        return continueProgram;
    }
}
