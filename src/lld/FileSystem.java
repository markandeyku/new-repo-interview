package lld;
import java.util.*;

// ---------------- Base Component ----------------
abstract class FileSystemEntity {
    protected String name;

    public FileSystemEntity(String name) {
        this.name = name;
    }

    public String getName() { return name; }

    public abstract int getSize();  // size in KB
    public abstract void display(String indent);

    // Default unsupported ops (only Folder overrides)
    public void add(FileSystemEntity entity) {
        throw new UnsupportedOperationException("Not supported for file");
    }

    public void remove(String name) {
        throw new UnsupportedOperationException("Not supported for file");
    }

    public FileSystemEntity search(String name) {
        return null;
    }
}

// ---------------- File (Leaf) ----------------
class File extends FileSystemEntity {
    private int size;  // in KB
    private String content;

    public File(String name, int size, String content) {
        super(name);
        this.size = size;
        this.content = content;
    }

    @Override
    public int getSize() {
        return size;
    }

    public String getContent() {
        return content;
    }

    @Override
    public void display(String indent) {
        System.out.println(indent + "- File: " + name + " (" + size + "KB)");
    }

    @Override
    public FileSystemEntity search(String name) {
        if (this.name.equalsIgnoreCase(name)) return this;
        return null;
    }
}

// ---------------- Folder (Composite) ----------------
class Folder extends FileSystemEntity {
    private List<FileSystemEntity> children;

    public Folder(String name) {
        super(name);
        this.children = new ArrayList<>();
    }

    @Override
    public int getSize() {
        int total = 0;
        for (FileSystemEntity entity : children) {
            total += entity.getSize();
        }
        return total;
    }

    @Override
    public void add(FileSystemEntity entity) {
        children.add(entity);
    }

    @Override
    public void remove(String name) {
        children.removeIf(e -> e.getName().equalsIgnoreCase(name));
    }

    @Override
    public void display(String indent) {
        System.out.println(indent + "+ Folder: " + name);
        for (FileSystemEntity entity : children) {
            entity.display(indent + "   ");
        }
    }

    @Override
    public FileSystemEntity search(String name) {
        if (this.name.equalsIgnoreCase(name)) return this;
        for (FileSystemEntity entity : children) {
            FileSystemEntity found = entity.search(name);
            if (found != null) return found;
        }
        return null;
    }
}

// ---------------- Factory ----------------
class FileSystemFactory {
    public static FileSystemEntity createFile(String name, int size, String content) {
        return new File(name, size, content);
    }

    public static FileSystemEntity createFolder(String name) {
        return new Folder(name);
    }
}

// ---------------- Singleton FileSystem ----------------
class FileSystem {
    private static FileSystem instance;
    private Folder root;

    private FileSystem() {
        root = new Folder("root");
    }

    public static synchronized FileSystem getInstance() {
        if (instance == null) {
            instance = new FileSystem();
        }
        return instance;
    }

    public Folder getRoot() {
        return root;
    }
}

// ---------------- Main ----------------
 class FileMain {
    public static void main(String[] args) {
        FileSystem fs = FileSystem.getInstance();
        Folder root = fs.getRoot();

        Folder docs = new Folder("Documents");
        Folder pics = new Folder("Pictures");

        File file1 = new File("Resume.docx", 120, "My Resume Content");
        File file2 = new File("Notes.txt", 50, "Some notes");
        File img1 = new File("photo1.png", 500, "Image content");
        File img2 = new File("photo2.png", 600, "Image content");

        docs.add(file1);
        docs.add(file2);
        pics.add(img1);
        pics.add(img2);

        root.add(docs);
        root.add(pics);

        // Display hierarchy
        root.display("");

        // Search
        System.out.println("\nSearching for 'Notes.txt'...");
        FileSystemEntity result = root.search("Notes.txt");
        if (result != null) {
            result.display("   ");
        }

        // Total size
        System.out.println("\nTotal size of root = " + root.getSize() + "KB");
    }
}