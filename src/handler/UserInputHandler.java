package handler;

import java.util.Scanner;

public class UserInputHandler {

    private Scanner scanner;

    public UserInputHandler(Scanner scanner) {
        this.scanner = scanner;
    }

	public int getChoice() {
        System.out.print("Please select a function: ");
        try {
            return Integer.valueOf(scanner.nextLine());
        } catch (Exception e) {
            // If user does not enter a valid integer, -1 is returned to indicate invalid input
            return -1;
        }
	}
}