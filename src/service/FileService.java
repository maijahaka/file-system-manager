package service;

import java.io.File;
import java.nio.file.Path;

public class FileService {
    private Path resourcePath;

    public FileService(Path resourcePath) {
        this.resourcePath = resourcePath;
    }

    public void listAllFiles() {
        File f = new File(resourcePath.toString());
        String[] filenames = f.list();

        System.out.println();

        System.out.println("Here are the files:");
        System.out.println("-------------------");

        for (String name : filenames) {
            System.out.println(name);
        }
        
        System.out.println();
    }

}
