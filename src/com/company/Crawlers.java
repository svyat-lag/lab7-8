package com.company;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Crawlers {

    /** List with unprocessed links. **/
    static LinkedList<URLDepthPair> unprocessed = new LinkedList<>();
    /** List with processed links. **/
    static LinkedList<URLDepthPair> processed = new LinkedList<>();


    /** Method to print all links and their depth. **/
    public static void getSites(LinkedList<URLDepthPair> list){ // выводит все результаты
        for (URLDepthPair pair: list) { System.out.println(pair); }
    }


    /** A search method that scans the webpage and searches for "https" links. **/
    public static void search(String currentURL, int depth){

        try {
            /** Setting up a connection. **/
            URL url = new URL(currentURL);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.connect();

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
                if (maxLinksNumber <= 0) return;
                Matcher matcher = urlPattern.matcher(line);
                while (matcher.find()){
                    String link = line.substring(matcher.start() + 9, matcher.end() - 1);
                    unprocessed.add(new URLDepthPair(link, depth + 1));
                    maxLinksNumber--;
                }
            }

        } catch (UnknownHostException e) {
            System.out.println("UnknownHostException: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
        }
    }

    public static void main(String[] args){

//        Scanner scanner = new Scanner(System.in);
//        String[] url = scanner.nextLine().split();
//        int depth = (int) url[1];
//        System.out.println(url);
//        System.out.println(depthStr);

        unprocessed.add(new URLDepthPair("https://mtuci.ru/", 0));
        int depth = 0;
        while (!(unprocessed.isEmpty()) && (depth < 4)){
            URLDepthPair newPair = unprocessed.getFirst();
            processed.add(newPair);
            unprocessed.remove(newPair);
            search(newPair.getCurrentURL(), newPair.getCurrentDepth());
            if (!URLDepthPair.containsDepth(unprocessed, depth)) depth++;
        }
        getSites(processed);
    }
}
