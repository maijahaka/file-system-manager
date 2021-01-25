package service;

import java.io.FileWriter;
import java.io.IOException;

public class LoggingService {

    public void log(String string) {
        try {
            FileWriter fileWriter = new FileWriter("logs.txt", true);
            fileWriter.append(string + "\n");
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }
}
