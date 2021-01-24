package handler;

import java.util.Scanner;

public class UserInputHandler {

    private Scanner scanner;

    public UserInputHandler(Scanner scanner) {
        this.scanner = scanner;
    }

	public int getChoice(String prompt) {
        // requests user to make a choice based on the given prompt
        System.out.print(prompt);
        try {
            return Integer.valueOf(scanner.nextLine());
        } catch (Exception e) {
            // if user does not enter a valid integer, -1 is returned to indicate invalid input
            return -1;
        }
    }
    
    public void printUnrecognizedSelectionErrorMessage() {
        System.out.println("================================================");
        System.out.println("ERROR: Unrecognized selection. Please try again.");
        System.out.println("================================================");
    }
}