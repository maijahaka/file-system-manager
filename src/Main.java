import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import handler.UserInputHandler;
import service.FileService;
import userinterface.UserInterface;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        Path resourcePath = Paths.get("resources");

        UserInputHandler inputHandler = new UserInputHandler(scanner);
        FileService fileService = new FileService(resourcePath);

        UserInterface userInterface = new UserInterface(inputHandler, fileService);

        userInterface.launch();
    }
}