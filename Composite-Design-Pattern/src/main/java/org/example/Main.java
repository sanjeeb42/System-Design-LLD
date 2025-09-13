package org.example;

import java.util.ArrayList;
import java.util.List;

interface IFileSystem{
    void openAll();
};

class File implements IFileSystem{
    String name;
    @Override
    public void openAll() {
        System.out.println(" "+ name);
    }
}

class Folder implements IFileSystem{

    List<IFileSystem>fileSystems=new ArrayList<>();
    @Override
    public void openAll() {
        for(IFileSystem fileSystem:fileSystems)fileSystem.openAll();
    }
}

public class Main {
    public static void main(String[] args) {
        File file1 = new File();
                file1.name = "file1.txt";
                File file2 = new File();
                file2.name = "file2.txt";

                Folder folder = new Folder();
                folder.fileSystems.add(file1);
                folder.fileSystems.add(file2);

                folder.openAll();
    }
}