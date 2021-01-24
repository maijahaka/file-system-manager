package userinterface;

import handler.UserInputHandler;
import service.FileService;

public class UserInterface {
    private UserInputHandler inputHandler;
    private FileService fileService;

    public UserInterface(UserInputHandler inputHandler, FileService fileService) {
        this.inputHandler = inputHandler;
        this.fileService = fileService;
    }

	public void launch() {
        System.out.println("Welcome to File System Manager!");

        while (true) {
            System.out.println();
            showMainMenu();

            int choice = inputHandler.getChoice("Please select a function: ");

            if (choice == -99) {
                break;
            }

            switch (choice) {
                case 1:
                    fileService.listAllFiles();
                    break;
                case 2:
                    fileService.listFilesByExtension();
                    break;
                default:
                    inputHandler.printUnrecognizedSelectionErrorMessage();
            }
        }
    }

    private void showMainMenu() {
        System.out.println("Available functions:");
        System.out.println("1: List all files");
        System.out.println("2: List files with a specific extension");
        System.out.println("-99: Exit");
    }    
}
