package edu.umb.cs681.hw16;

import java.time.LocalDateTime;
import java.util.concurrent.ConcurrentLinkedQueue;


public class Directory extends FSElement {
    
    private ConcurrentLinkedQueue<FSElement> children = new ConcurrentLinkedQueue<FSElement>();

    public Directory(Directory parent, String name, int size, LocalDateTime creationTime) {
        super(parent, name, size, creationTime);
        if(parent != null) parent.appendChild(this);
    }

    public ConcurrentLinkedQueue<FSElement> getChildren() {
        return this.children;
    }

    public void appendChild(FSElement child) {
        lock.lock();
        try {
            
            this.children.add(child);
            this.size += child.size;
            
            // Append size of all the parents
            Directory parent = this.parent;
            while(parent != null) {
                parent.size = child.size;
                parent = parent.parent;
            }

        } catch(Exception e) {
            System.out.println(e);
        } finally {
            lock.unlock();
        }
    }

    public int countChildren() {
        return this.children.size();
    }

    public ConcurrentLinkedQueue<Directory> getSubDirectories() {
        ConcurrentLinkedQueue<Directory> directories = new ConcurrentLinkedQueue<>();
        this.children.forEach(child -> {
            if(child.isDirectory()) directories.add((Directory)child);
        });
        return directories;
    }

    public ConcurrentLinkedQueue<File> getFiles() {
        ConcurrentLinkedQueue<File> files = new ConcurrentLinkedQueue<>();
        this.children.forEach(child -> {
            if(child.isFile()) files.add((File)child);
        });
        return files;
    }

    public ConcurrentLinkedQueue<Link> getLinks() {
        ConcurrentLinkedQueue<Link> links = new ConcurrentLinkedQueue<>();
        this.children.forEach(child -> {
            if(child.isLink()) links.add((Link)child);
        });
        return links;
    }

    public int getTotalSize() { 
        // Total size of the current directory including all sub-directories and files       
        var totalSize = new Object() {int size = 0;};
        
        // Iterating through all sub-direcotries in the current level
        ConcurrentLinkedQueue<Directory> dirs = this.getSubDirectories();
        dirs.forEach(child -> {
            totalSize.size += child.size;
        });

        // Iterating through all files in the current level
        ConcurrentLinkedQueue<File> files = this.getFiles();
        files.forEach(file -> {
            totalSize.size += file.size;
        });

        return totalSize.size;
    }

    public boolean isDirectory() {
        return true;
    }

    public boolean isFile() {
        return true;
    }

    public boolean isLink() {
        return false;
    }

    @Override
    public void accept(FSVisitor aVisitor) {
        lock.lock();
        try {
            aVisitor.visit(this);
        } catch(Exception e) {
            System.out.println(e);
        } finally {
            lock.unlock();
        }
    }
}
