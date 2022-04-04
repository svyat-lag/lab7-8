package com.company;

import java.net.URL;
import java.util.LinkedList;

public class URLPool {
    private static LinkedList<URLDepthPair> unprocessedPool;
    private static LinkedList<URLDepthPair> processedPool;

    private int maxDepth;

    public URLPool(int maxDepth){
        this.maxDepth = maxDepth;
        unprocessedPool = new LinkedList<>();
        processedPool = new LinkedList<>();
    }

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

    public static synchronized int size(){
        return unprocessedPool.size();
    }

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

    public void showResult(){
        for (URLDepthPair pair: processedPool){
            System.out.println(pair);
        }
    }
}
