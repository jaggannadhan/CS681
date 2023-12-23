package edu.umb.cs681.hw16;

import java.time.LocalDateTime;

public class File extends FSElement {

    public File(Directory parent, String name, int size, LocalDateTime creationTime) {
        super(parent, name, size, creationTime);
        if(parent != null) parent.appendChild(this);
    }

    public boolean isDirectory() {
        return false;
    }

    public boolean isFile() {
        return true;
    }

    public boolean isLink() {
        return false;
    }

    public int getSize() {
        return this.size;
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
