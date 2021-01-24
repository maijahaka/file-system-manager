package service;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;

import handler.UserInputHandler;

public class FileService {
    private Path resourcePath;
    private UserInputHandler inputHandler;

    public FileService(Path resourcePath, UserInputHandler inputHandler) {
        this.resourcePath = resourcePath;
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
        
        System.out.println();
    }

	public void listFilesByExtension() {
        String[] filenames = getAllFiles();

        // HashMap with file extensions as keys and ArrayList of files with each extension
        // as values
        HashMap<String, ArrayList<String>> filesByExtension = getFilesByExtension(filenames);

        // a sorted ArrayList of the available extensions so that the user can be presented 
        // with a sorted list of options
        ArrayList<String> availableExtensions = getAvailableExtensions(filesByExtension.keySet());

        String selectedExtension = null;
        
        while (true) {
            System.out.println();
            printAvailableExtensions(availableExtensions);

            int choice = inputHandler.getChoice("Please select an extension " +
                "(1-" + availableExtensions.size() + "): ");
            
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
        File resources = resourcePath.toFile();
        return resources.list();
    }

}
