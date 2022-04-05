package com.company;

public class Crawlers {

    public static void main(String[] args) throws InterruptedException {

        /** Initialization of the URL-pool and adding first link to it.  **/
        URLPool pool = new URLPool(5);
        pool.push(new URLDepthPair("https://mtuci.ru/", 0));

        /**
         * If we have unprocessed links, we should continue
         * creating new threads that will handle with them.
         **/
        while (URLPool.size() != 0) {
            Thread thread = new Thread(new CrawlerTask(pool));
            thread.start();
            thread.join();
        }
    }
}
