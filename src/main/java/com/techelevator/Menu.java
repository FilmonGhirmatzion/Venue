package com.techelevator;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

public class Menu {

    private PrintWriter out;
    private Scanner in;

    public Menu(InputStream input, OutputStream output) {
        this.out = new PrintWriter(output);
        this.in = new Scanner(input);
    }

    public Object getChoiceFromVenueOptions(Object[] options) {
        Object choice = null;
        while(choice == null) {
            displayListVenuesOptions(options);
            choice = getChoiceFromUserInput(options);
        }

        return choice;
    }

    private void displayListVenuesOptions(Object[] options) {
        out.println();
        for(int i = 0; i < options.length; i++) {
            int optionNum = i+1;
            out.println(optionNum+") " + options[i]);
        }
        out.flush();
    }

    public Object getChoiceFromMainMenuOptions(Object[] options) {
        Object choice = null;
        while(choice == null) {
            displayMainMenuOptions(options);
            choice = getChoiceFromUserInput(options);
        }

        return choice;
    }

    private void displayMainMenuOptions(Object[] options) {
        out.println();
        for(int i = 0; i < options.length; i++) {
            int optionNum = i+1;
            out.println(optionNum+") " + options[i]);
        }
        out.println("\nPlease choose an option: ");
        out.flush();
    }

    private Object getChoiceFromUserInput(Object[] options) {
        Object choice = null;
        String input = in.nextLine();
        try {
            int selectedOption = Integer.parseInt(input);
            if(selectedOption <= options.length) {
                choice = options[selectedOption - 1];
            }
        } catch(NumberFormatException e) {

        }
        if(choice == null) {
            out.println("\n*** " + input + " is not a valid option ***\n");
        }

        return choice;
    }

    public String getUserInput(String prompt) {
        String userInput = "";
        Scanner input = new Scanner(System.in);
        while (userInput.isEmpty())
        {
            out.print(prompt + " >>> ");
            userInput = input.nextLine();
        }

        return userInput;
    }

    public void printHeading(String headingText) {
        out.println("\n" + headingText);
    }

}