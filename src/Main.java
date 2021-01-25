import java.util.Scanner;

import handler.OutputHandler;
import handler.UserInputHandler;
import service.FileService;
import service.LoggingService;
import userinterface.UserInterface;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        // relative path to the directory that contains the files to be manipulated
        String resourcePathString = "resources";

        UserInputHandler inputHandler = new UserInputHandler(scanner);
        LoggingService logger = new LoggingService();
        OutputHandler outputHandler = new OutputHandler(logger);
        FileService fileService = new FileService(resourcePathString, inputHandler, outputHandler);

        UserInterface userInterface = new UserInterface(inputHandler, fileService);

        userInterface.launch();
    }
}