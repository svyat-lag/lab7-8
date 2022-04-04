package com.company;

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

    public synchronized int size(){
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
        unprocessedPool.remove(pair);
        return pair;
    }

    public synchronized void push(URLDepthPair newPair){
        if (newPair.getCurrentDepth() < maxDepth) {
            unprocessedPool.add(newPair);
            notify();
            return;
        }
        processedPool.add(newPair);
    }

    public void showResult(){
        for (URLDepthPair pair: processedPool){
            System.out.println(pair);
        }
    }
}