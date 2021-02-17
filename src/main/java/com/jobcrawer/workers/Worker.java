package com.jobcrawer.workers;


import com.jobcrawer.factory.Page;

import java.util.ArrayList;
import java.util.List;

/**
 * Base class to manage workers
 *
 * Observer Design Pattern
 *
 * @author Hristo Nikolov
 */
public class Worker {

    private List<Page> workers;
    private final Object MUTEX= new Object();

    public Worker() {
        this.workers = new ArrayList<Page>();
    }

    public boolean addWorker(Page page) {

        if(page == null) throw new NullPointerException("Null Observer");
        synchronized (MUTEX) {
            if(!workers.contains(page)) workers.add(page);
        }

        return true; }

    public void removeWorker(Page page) {
        synchronized (MUTEX) {
            workers.remove(page);
        }
    }

    public List<Page> getWorkers() {
        return workers;
    }

    public void runAllWorkers() {
        workers.forEach(Page::downloadPage);
    }
}
