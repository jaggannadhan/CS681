package edu.umb.cs681.hw16;

import java.time.LocalDateTime;
import java.util.concurrent.locks.ReentrantLock;

public abstract class FSElement {
    protected String name;
    protected int size = 0;
    protected LocalDateTime creationTime;
    protected Directory parent;
    protected final ReentrantLock lock;

    public FSElement(Directory parent, String name, int size, LocalDateTime creationTime) {
        this.parent = parent;
        this.name = name;
        this.size = size;
        this.creationTime = creationTime;
        this.lock = new ReentrantLock();
    }
    
    public abstract boolean isDirectory();

    public abstract boolean isFile();

    public abstract boolean isLink();

    public Directory getParent() {
        return this.parent;
    }

    public void setParent(Directory parent) {
        this.parent = parent;
    }

    public int getSize() {
        return this.size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getCreationDate() {
        return this.creationTime;
    }

    public void setCreationDate(LocalDateTime time) {
        this.creationTime = time;
    }

    public abstract void accept(FSVisitor aVisitor);
}
