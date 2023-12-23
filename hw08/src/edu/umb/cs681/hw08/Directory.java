package edu.umb.cs681.hw08;

import java.time.LocalDateTime;
import java.util.LinkedList;

public class Directory extends FSElement {
    
    private LinkedList<FSElement> children = new LinkedList<>();

    public Directory(Directory parent, String name, int size, LocalDateTime creationTime) {
        super(parent, name, size, creationTime);
        if(parent != null) parent.appendChild(this);
    }

    public LinkedList<FSElement> getChildren() {
        return this.children;
    }

    public void appendChild(FSElement child) {
        this.children.add(child);
        this.size += child.size;
        
        // Append size of all the parents
        Directory parent = this.parent;
        while(parent != null) {
            parent.size = child.size;
            parent = parent.parent;
        }
    }

    public int countChildren() {
        return this.children.size();
    }

    public LinkedList<Directory> getSubDirectories() {
        LinkedList<Directory> directories = new LinkedList<>();
        this.children.forEach(child -> {
            if(child.isDirectory()) directories.add((Directory)child);
        });
        return directories;
    }

    public  LinkedList<File> getFiles() {
        LinkedList<File> files = new LinkedList<>();
        this.children.forEach(child -> {
            if(!child.isDirectory()) files.add((File)child);
        });
        return files;
    }

    public int getTotalSize() { 
        // Total size of the current directory including all sub-directories and files       
        var totalSize = new Object() {int size = 0;};
        
        // Iterating through all sub-direcotries in the current level
        LinkedList<Directory> dirs = this.getSubDirectories();
        dirs.forEach(child -> {
            totalSize.size += child.size;
        });

        // Iterating through all files in the current level
        LinkedList<File> files = this.getFiles();
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
        aVisitor.visit(this);
    }
}
