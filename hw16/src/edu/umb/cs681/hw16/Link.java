package edu.umb.cs681.hw16;

import java.time.LocalDateTime;


public class Link extends FSElement {

    private FSElement target = null;

    public Link(Directory parent, String name, int size, LocalDateTime creationTime, FSElement target) {
        super(parent, name, size, creationTime);
        this.target = target;
    }

    public boolean isDirectory() {
        return false;
    }

    public boolean isFile() {
        return false;
    }

    public boolean isLink() {
        return true;
    }

    public FSElement getTarget() {
        return this.target;
    }

    public void setTarget(FSElement target) {
        lock.lock();
        try {
            this.target = target;
        } catch(Exception e) {
            System.out.println(e);
        } finally {
            lock.unlock();
        }
        
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
