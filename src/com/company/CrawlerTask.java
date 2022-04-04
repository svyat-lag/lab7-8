package com.company;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CrawlerTask implements Runnable{

    URLPool urlPool;

    public CrawlerTask(URLPool urlPool){
        this.urlPool = urlPool;
    }

    @Override
    public void run() {
        while (urlPool.size() != 0) {
            URLDepthPair currentPair = urlPool.pop();

            LinkedList<URLDepthPair> list =
                    search(currentPair.getCurrentURL(), currentPair.getCurrentDepth());
            if (!list.isEmpty()) {
                for (URLDepthPair pair : list) {
                    urlPool.push(pair);
                }
            }
        }
    }

    /** A search method that scans the webpage and searches for "https" links. **/
    public static LinkedList<URLDepthPair> search(String currentURL, int depth){
        LinkedList<URLDepthPair> list = new LinkedList<>();
        try {
            /** Setting up a connection. **/
            URL url = new URL(currentURL);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.connect();

            connection.setConnectTimeout(3000);

            /** Reader to save the page source info. **/
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(
                            connection.getInputStream()));

            /** Pattern that helps find refs. **/
            Pattern urlPattern = Pattern.compile( "<a href=\\\"https://.+?\\\"" );
            String line;

            /** Maximum number of links that we can find on the page. **/
            int maxLinksNumber = 5;


            while ((line = reader.readLine()) != null){
                if (maxLinksNumber <= 0) break;
                Matcher matcher = urlPattern.matcher(line);
                while (matcher.find()){
                    String link = line.substring(matcher.start() + 9, matcher.end() - 1);
                    list.add(new URLDepthPair(link, depth + 1));
                    maxLinksNumber--;
                }
            }

        } catch (UnknownHostException e) {
            System.out.println("UnknownHostException: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
        }

        return list;
    }
}