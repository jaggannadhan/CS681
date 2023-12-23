package edu.umb.cs681.hw11;

import java.util.LinkedList;
import java.time.LocalDateTime;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class FileSystem implements Runnable {
    private static FileSystem single_instance = null;
    private AtomicReference<LinkedList<Directory>> root;
    private final AtomicBoolean shouldTerminate;
    private static ReentrantLock lock = new ReentrantLock();

    private FileSystem() {
        this.root = new AtomicReference<>(new LinkedList<>());
        shouldTerminate = new AtomicBoolean(false);
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

    public void terminateThread() {
        shouldTerminate.set(true);
    }

    public LinkedList<Directory> getRootDirs() {
        return root.get();
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
    }

    public void appendRootDir(Directory root) {
        if(!shouldTerminate.get()) {
            LinkedList<Directory> temp = this.root.get();
            temp.add(root);
            this.root.set(temp);
        }
    }

    public static void main(String[] args) {
        System.out.println("Main thread started!");
        FileSystem fs1 = FileSystem.getFileSystem();
        LocalDateTime time = LocalDateTime.now(); 
        Thread[] threads = new Thread[13];

        Directory prjRoot = new Directory(null, "prjRoot", 1, time);;
        fs1.appendRootDir(prjRoot);

        for (int i = 0; i < 13; i++) {
            threads[i] = new Thread(fs1);
            threads[i].start();
            System.out.println("Thread #" + (i + 1) + " started!");
        }

        try {
            System.out.println("Setting AtomicBoolean flag to true...");
            Thread.sleep(1000);
            fs1.terminateThread();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < 13; i++) {
            try {
                System.out.println("Waiting for Thread #" + (i + 1) + " to terminate!");
                Thread.sleep(200);
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    
        
        System.out.println("All threads terminated, terminating Main thread!");

    }
}
