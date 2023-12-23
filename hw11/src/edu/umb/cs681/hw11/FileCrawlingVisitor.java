package edu.umb.cs681.hw11;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;
import java.lang.ThreadLocal;

public class FileCrawlingVisitor implements FSVisitor {
    private LinkedList<File> files;
    private AtomicBoolean shouldTerminate = new AtomicBoolean(false);
    private static ReentrantLock lock = new ReentrantLock();

    @Override
    public void visit(Link link) {
        
    }

    @Override
    public void visit(Directory dir) {
        if(!shouldTerminate.get()) {
            this.files = dir.getFiles();
        }
    }

    @Override
    public void visit(File file) {
        
    }

    public LinkedList<File> getFiles() {
        return this.files;
    }

    public void terminateThread() {
        this.shouldTerminate.set(true);
    }
    
    public static void main(String[] args) {

        FileCrawlingVisitor visitor1 = new FileCrawlingVisitor(); 
        FileCrawlingVisitor visitor2 = new FileCrawlingVisitor(); 
        FileCrawlingVisitor visitor3 = new FileCrawlingVisitor(); 

        LocalDateTime time1 = LocalDateTime.now(); 
        LocalDateTime time2 = LocalDateTime.now(); 
        LocalDateTime time3 = LocalDateTime.now(); 

        Directory root1 = new Directory(null, "root1", 1, time1);
        Directory root2 = new Directory(null, "root2", 1, time2);
        Directory root3 = new Directory(null, "root3", 1, time3);

        ThreadLocal<FileCrawlingVisitor> localThread = new ThreadLocal<>();

        Runnable task1 = () -> {
            System.out.println("Thread local 1: " + localThread.get());

            try {
                lock.lock();
                root1.accept(visitor1);
                visitor1.terminateThread();
                localThread.set(visitor1);
            } finally {
                lock.unlock();
            }

            System.out.println("Thread local 1: " + localThread.get());
            
        };


        Runnable task2 = () -> {
            System.out.println("Thread local 2: " + localThread.get());

            try {
                lock.lock();
                root2.accept(visitor2);
                visitor2.terminateThread();
                localThread.set(visitor2);
            } finally {
                lock.unlock();
            }

            System.out.println("Thread local 2: " + localThread.get());
            
        };
        
        
        Runnable task3 = () -> {
            System.out.println("Thread local 3: " + localThread.get());

            try {
                lock.lock();
                root3.accept(visitor3);
                visitor3.terminateThread();
                localThread.set(visitor3);
            } finally {
                lock.unlock();
            }

            System.out.println("Thread local 3: " + localThread.get());
            
        };


        Thread thread1 = new Thread(task1);
        Thread thread2 = new Thread(task2);
        Thread thread3 = new Thread(task3);

        thread1.start();
        thread2.start();
        thread3.start();

        try {
            thread1.join();
            thread2.join();
            thread3.join();
        } catch(InterruptedException e) {
            System.out.println(e);
        }
        
    }

}
