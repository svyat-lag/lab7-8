package com.company;

public class Crawlers {

    private static int numThreads = 10;

    public static void main(String[] args) throws InterruptedException {

//        Scanner scanner = new Scanner(System.in);
//        String[] url = scanner.nextLine().split();
//        int depth = (int) url[1];
//        System.out.println(url);
//        System.out.println(depthStr);

        URLPool pool = new URLPool(5);
        pool.push(new URLDepthPair("https://mtuci.ru/", 0));

        while (URLPool.size() != 0) {
            Thread thread = new Thread(new CrawlerTask(pool));
            thread.start();
            thread.join();
        }

//        pool.showResult();
    }
}
