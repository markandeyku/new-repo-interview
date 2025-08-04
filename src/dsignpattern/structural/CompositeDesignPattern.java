package dsignpattern.structural;

import java.util.ArrayList;
import java.util.List;

public class CompositeDesignPattern {
}

// where it is used
/**
 * The Composite Design Pattern is a structural design pattern that allows you to compose objects
 * into tree structures to represent part-whole hierarchies. It lets clients treat individual
 * objects and groups of objects uniformly.
 */

/**
 * 🚀 When to Use Composite Pattern?
 * ✅ When your application needs to represent part-whole hierarchies.
 * ✅ When clients should treat individual objects and compositions of objects uniformly.
 * ✅ When working with tree structures like file systems, UI components, or organization charts.
 */


/**
 * Real-World Example – File System Hierarchy
 * Imagine designing a File System where:
 *
 * A file is a leaf node (cannot have children).
 * A folder can contain multiple files and subfolders.
 */

//file component

interface FileComponent{
    void display(String indent);
}

//File

class File implements FileComponent{

    private String name;

    public File(String name) {
        this.name = name;
    }

    @Override
    public void display(String indent) { // whenever File comes in  List<FileComponent> fileComponents , this method will be executed
        System.out.println(indent+" "+name);
    }
}

// composite folder

class Folder implements FileComponent{

    private String name;

    private List<FileComponent> fileComponents = new ArrayList<>();

    public Folder(String name) {
        this.name = name;
    }

    public void addFileComponent(FileComponent fileComponent){
        fileComponents.add(fileComponent);
    }

    @Override
    public void display(String indent) {
        System.out.println(fileComponents.size());  //whenever a folder comes in list , this method will be executed
        System.out.println(indent+" "+name);
        for (var fileComponent: fileComponents){
            fileComponent.display(indent);
        }
    }
}

// client code

class CompositePatternDemo{
    public static void main(String[] args) {
        FileComponent file1 = new File("file.text1");
        FileComponent file2 = new File("file.text2");
        FileComponent file3 = new File("file.text3");


        Folder subFolder = new Folder("folder1");
        subFolder.addFileComponent(file1);
        subFolder.addFileComponent(file2);

        Folder rootFolder = new Folder("root folder");
        rootFolder.addFileComponent(file3);

        rootFolder.addFileComponent(subFolder);


        rootFolder.display("");




    }

    /**
     * 🧩 Real-World Use Cases for Composite Pattern
     * File Systems — Files and folders in a hierarchical structure.
     * Menu System in UI Design — Menus containing submenus and menu items.
     * Organization Structure — Employees (leaf nodes) and Departments (composite nodes).
     * Graphics Editor — Shapes (leaf) and groups of shapes (composite).
     */
}
