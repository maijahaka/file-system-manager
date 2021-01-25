package service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;

import handler.UserInputHandler;

public class FileService {
    private String resourcePathString;
    private UserInputHandler inputHandler;

    public FileService(String resourcePathString, UserInputHandler inputHandler) {
        this.resourcePathString = resourcePathString;
        this.inputHandler = inputHandler;
    }

    public void listAllFiles() {
        String[] filenames = getAllFiles();

        System.out.println();

        System.out.println("Here are the files:");
        System.out.println("-------------------");

        for (String name : filenames) {
            System.out.println(name);
        }
    }

    public void listFilesByExtension() {
        String[] filenames = getAllFiles();

        // HashMap with file extensions as keys and ArrayList of files with each
        // extension
        // as values
        HashMap<String, ArrayList<String>> filesByExtension = getFilesByExtension(filenames);

        // a sorted ArrayList of the available extensions so that the user can be
        // presented
        // with a sorted list of options
        ArrayList<String> availableExtensions = getAvailableExtensions(filesByExtension.keySet());

        String selectedExtension = null;

        while (true) {
            System.out.println();
            printAvailableExtensions(availableExtensions);

            int choice = inputHandler
                    .getChoice("Please select an extension " + "(1-" + availableExtensions.size() + "): ");

            if (choice > 0 && choice <= availableExtensions.size()) {
                selectedExtension = availableExtensions.get(choice - 1);
                break;
            } else {
                inputHandler.printUnrecognizedSelectionErrorMessage();
            }
        }

        ArrayList<String> filesToView = filesByExtension.get(selectedExtension);

        System.out.println();

        if (!selectedExtension.equals("")) {
            System.out.println("Here are the files with the extension '" + selectedExtension + "':");
        } else {
            System.out.println("Here are the files with no file extension:");
        }
        System.out.println("----------------------------------------------");

        for (String filename : filesToView) {
            System.out.println(filename);
        }
    }

    public void showTextFileOptions() {
        String[] filenames = getAllFiles();

        // initialize an empty ArrayList for available .txt files
        ArrayList<String> textFiles = new ArrayList<>();

        String selectedTextFile = "";

        HashMap<String, ArrayList<String>> filesByExtension = getFilesByExtension(filenames);

        System.out.println();

        if (filesByExtension.containsKey(".txt")) {
            // can ArrayList of available .txt files
            textFiles = filesByExtension.get(".txt");
        } else {
            // if no .txt files are found, print a message and return from the method
            System.out.println("---------------------------------------------------");
            System.out.println("No text files were found in the resources directory");
            System.out.println("---------------------------------------------------");
            return;
        }

        if (textFiles.size() == 1) {
            // if only 1 .txt file is found, that file is automatically selected for
            // analysis
            selectedTextFile = textFiles.get(0);
            System.out.println("There is 1 text file in the resources directory: " + selectedTextFile);
        } else {
            while (true) {
                System.out.println("Available text files:");

                for (int i = 0; i < textFiles.size(); i++) {
                    System.out.println((i + 1) + ": " + textFiles.get(i));
                }

                int choice = inputHandler.getChoice("Please select a file (1-" + textFiles.size() + "): ");

                if (choice > 0 && choice <= textFiles.size()) {
                    selectedTextFile = textFiles.get(choice - 1);
                    break;
                } else {
                    inputHandler.printUnrecognizedSelectionErrorMessage();
                }
            }

            System.out.println();
            System.out.println("The selected file is " + selectedTextFile);
        }

        manipulateFile(selectedTextFile);
    }

    private void manipulateFile(String textFile) {
        System.out.println();
        System.out.println(textFile);
        System.out.println("------------------------");

        while (true) {
            showFileManipulationMenu(textFile);
            int choice = inputHandler.getChoice("Please select a function: ");

            if (choice == -99) {
                break;
            }

            switch (choice) {
                case 1:
                    printFilename(textFile);
                    break;
                case 2:
                    printFileSize(textFile);
                    break;
                case 3:
                    printNumberOfLines(textFile);
                    break;
                case 4:
                    lookForWord(textFile);
                    break;
                default:
                    inputHandler.printUnrecognizedSelectionErrorMessage();
            }
        }
    }

    private void showFileManipulationMenu(String textFile) {
        System.out.println("Available functions for " + textFile + ":");
        System.out.println("1: Print the name of the file");
        System.out.println("2: Print the size of the file");
        System.out.println("3: Print the number of lines in the file");
        System.out.println("4: Look for a specific word in the file");
        System.out.println("-99: Return to the main menu");
    }

    private void printFilename(String textFile) {
        System.out.println();
        System.out.println("Filename: " + textFile);
        System.out.println();
    }

    private void printFileSize(String textFile) {
        // get path to the file relative to the resources directory
        Path pathToFile = Paths.get(resourcePathString, textFile);

        // initialize values for file size and size unit
        long fileSize = 0;
        String unit = "B";

        try {
            fileSize = Files.size(pathToFile);
        } catch (IOException e) {
            // print error message and return from function if file size is not retrieved
            // successfully
            System.out.println("ERROR: " + e.getMessage());
            return;
        }

        // convert the file size to convenient units
        if (fileSize < 1024) {
            // no need for unit conversion
        } else if (fileSize < 1048576) {
            fileSize = fileSize / 1024;
            unit = "KB";
        } else if (fileSize < 1073741824) {
            fileSize = fileSize / 1048576;
            unit = "MB";
        } else {
            fileSize = fileSize / 1073741824;
            unit = "GB";
        }

        System.out.println();
        System.out.println("File size: " + fileSize + " " + unit);
        System.out.println();
    }

    private void printNumberOfLines(String textFile) {
        // get path to the file relative to the resources directory
        Path pathToFile = Paths.get(resourcePathString, textFile);

        // initialize value for the number of lines in the file
        long lines = 0;

        try {
            lines = Files.lines(pathToFile).count();
        } catch (IOException e) {
            // print error message and return from function if file size is not retrieved
            // successfully
            System.out.println("ERROR: " + e.getMessage());
            return;
        }

        System.out.println();
        System.out.println("Number of lines: " + lines);
        System.out.println();
    }

    private void lookForWord(String textFile) {
        String word = inputHandler.getWord();

        if (wordIsFound(textFile, word)) {
            System.out.println("The word '" + word + "' exists in the file " + textFile);
        } else {
            System.out.println("The word '" + word + "' does not exist in the file " + textFile);
        }

        System.out.println();
    }

    private boolean wordIsFound(String textFile, String word) {
        try {
            BufferedReader reader = 
                new BufferedReader(new FileReader(resourcePathString + "/" + textFile));

            // the file is read line by line
            String line;
            while ((line = reader.readLine()) != null) {
                // if a line contains the selected word, the method returns true
                if (line.toLowerCase().contains(word.toLowerCase())) {
                    return true;
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("ERROR: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("ERROR: " + e.getMessage());
        }

        return false;
    }

    private void printAvailableExtensions(ArrayList<String> availableExtensions) {       
        System.out.println("Available file extensions:");

        // each option is given a number (starting from 1) to enable easier selection
        for (int i = 0; i < availableExtensions.size(); i++) {
            String extension = availableExtensions.get(i);
            if (!extension.equals("")) {
                System.out.println((i + 1) + ": " + extension);
            } else {
                System.out.println((i +1) + ": no extension");
            }
        }        
    }

    private ArrayList<String> getAvailableExtensions(Set<String> extensions) {
        // transforming a set of extensions to a sorted list 
        ArrayList<String> availableExtensions = new ArrayList<>();

        for (String extension : extensions) {
            availableExtensions.add(extension);
        }

        Collections.sort(availableExtensions);

        return availableExtensions;
    }

    private HashMap<String, ArrayList<String>> getFilesByExtension(String[] filenames) {
        // sorting filenames by their extension to a HashMap with extensions as keys and 
        // an ArrayList of files with each extension as values 
        HashMap<String, ArrayList<String>> filesByExtension = new HashMap<>();

        for (String name : filenames) {
            String extension = getFileExtension(name);

            filesByExtension.putIfAbsent(extension, new ArrayList<>());
            filesByExtension.get(extension).add(name);
        }

        return filesByExtension;
    }

    private String getFileExtension(String filename) {
        // return an empty String by default if a filename does not have an extension
        String extension = "";

        // file extension is the part of the filename that comes after the last '.' symbol
        int extensionIndex = filename.lastIndexOf(".");
        // taking into consideration that a file may not have an extension
        if (extensionIndex > -1) {
            extension = filename.substring(extensionIndex);
        }

        return extension;
    }
    
    private String[] getAllFiles() {
        File resources = new File(resourcePathString);
        return resources.list();
    }

}
