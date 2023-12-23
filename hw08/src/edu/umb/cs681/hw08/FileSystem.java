package edu.umb.cs681.hw08;
import java.util.LinkedList;
import java.time.LocalDateTime;
import java.util.concurrent.locks.ReentrantLock;


public class FileSystem {
    private static FileSystem single_instance = null;
    private LinkedList<Directory> root;
    private static ReentrantLock lock = new ReentrantLock();
    
    private FileSystem() {
        this.root = new LinkedList<>();
    }

    public static FileSystem getFileSystem() {
        lock.lock();
        try {
            if(single_instance != null)
                return single_instance;
            return new FileSystem();
        } finally {
            lock.unlock();
        }
    }

    public LinkedList<Directory> getRootDirs() {
        return root;
    }

    public void appendRootDir(Directory root) {
        this.root.add(root);
    }

    public static void main(String[] args) throws IndexOutOfBoundsException {
        FileSystem fs1 = FileSystem.getFileSystem();
        FileSystem fs2 = FileSystem.getFileSystem();
        LocalDateTime time1 = LocalDateTime.now(); 

        Directory prjRoot = new Directory(null, "prjRoot", 1, time1);;
        fs1.appendRootDir(prjRoot);
        
        if(fs1 == fs2) {
            System.out.println("Multile same instances created");
        } else {
            System.out.println("fs1 and fs2 are two different instances");
        }

    }

}
