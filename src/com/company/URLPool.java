package com.company;

import java.util.LinkedList;

public class URLPool {
    /** List with unprocessed links. **/
    private static LinkedList<URLDepthPair> unprocessedPool;
    /** List with processed links. **/
    private static LinkedList<URLDepthPair> processedPool;

    /** Maximum depth of search. **/
    private int maxDepth;

    public URLPool(int maxDepth){
        this.maxDepth = maxDepth;
        unprocessedPool = new LinkedList<>();
        processedPool = new LinkedList<>();
    }

    /**
     * Method that checks if the certain pair is
     * already in one of the list.
     **/
    private static boolean containURL(URLDepthPair pair){
        boolean in = false;
        for (URLDepthPair p: unprocessedPool){
            if (p.getCurrentURL().equals(pair.getCurrentURL())){
                in = true;
            }
        }
        for (URLDepthPair p: processedPool){
            if (p.getCurrentURL().equals(pair.getCurrentURL())){
                in = true;
            }
        }
        return in;
    }

    /** Method to get the size of unprocessed list. **/
    public static synchronized int size(){
        return unprocessedPool.size();
    }

    /**
     * Method that returns one pair and moves processed pair
     * from one list to another. If the unprocessed list doesn't
     * have one, the thread must wait till it appears.
     **/
    public synchronized URLDepthPair pop() {
        if (unprocessedPool.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        URLDepthPair pair = unprocessedPool.getFirst();
        processedPool.add(pair);
        System.out.println(pair + "\n" + unprocessedPool.size() + "\n");
        unprocessedPool.remove(pair);
        return pair;
    }

    /**
     * Method that adds a pair to unprocessed list. When the pair
     * was added, method should notify the waiting thread.
     **/
    public synchronized void push(URLDepthPair newPair){
        if (!containURL(newPair)) {
            if (newPair.getCurrentDepth() < maxDepth) {
                unprocessedPool.add(newPair);
                notify();
                return;
            }
            processedPool.add(newPair);
            System.out.println(newPair + "\n" + unprocessedPool.size() + "\n");
        }
    }
}
