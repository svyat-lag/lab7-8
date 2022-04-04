package com.company;

public class Crawlers {

    private static int numThreads = 1;

    public static void main(String[] args) throws InterruptedException {

//        Scanner scanner = new Scanner(System.in);
//        String[] url = scanner.nextLine().split();
//        int depth = (int) url[1];
//        System.out.println(url);
//        System.out.println(depthStr);

        URLPool pool = new URLPool(4);
        pool.push(new URLDepthPair("https://mtuci.ru/", 0));

        for (int i = 1; i <= numThreads; i++){
            Thread thread = new Thread(new CrawlerTask(pool));
            thread.start();
            thread.join();
        }

        pool.showResult();
    }
}
