package userinterface;

import handler.UserInputHandler;

public class UserInterface {
    private UserInputHandler inputHandler;

    public UserInterface(UserInputHandler inputHandler) {
        this.inputHandler = inputHandler;
    }

	public void launch() {
        System.out.println("Welcome to File System Manager!");
        System.out.println();

        while (true) {
            showMainMenu();

            int choice = inputHandler.getChoice();

            if (choice == -99) {
                break;
            }

            switch (choice) {
                case 1:
                    System.out.println("picked 1");
                    break;
                case 2:
                    System.out.println("picked 2");
                    break;
                case 3:
                    System.out.println("picked 3");
                    break;
                default:
                    printUnrecognizedSelectionErrorMessage();
            }
        }
    }
    
    private void showMainMenu() {
        System.out.println("Available functions:");
        System.out.println("1: List all files");
        System.out.println("2: List files with a specific extension");
        System.out.println("3: Request information about a text file");
        System.out.println("-99: Exit");
    }

    private void printUnrecognizedSelectionErrorMessage() {
        System.out.println("================================================");
        System.out.println("ERROR: Unrecognized selection. Please try again.");
        System.out.println("================================================");
    }


    
}
