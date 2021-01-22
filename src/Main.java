import java.util.Scanner;
import handler.UserInputHandler;
import userinterface.UserInterface;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        UserInputHandler inputHandler = new UserInputHandler(scanner);
        UserInterface userInterface = new UserInterface(inputHandler);

        userInterface.launch();
    }
}