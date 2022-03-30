package com.company;

import java.util.LinkedList;

public class URLDepthPair {

    /** URL-address and current depth **/
    private String currentURL;
    private int currentDepth;

    public URLDepthPair(String currentURL, int currentDepth){
        this.currentURL = currentURL;
        this.currentDepth = currentDepth;
    }

    @Override
    public String toString() {
        return "URLDepthPair{" +
                "currentURL='" + currentURL + '\'' +
                ", currentDepth=" + currentDepth +
                '}';
    }

    /** This method returns current URL-address **/
    public String getCurrentURL(){
        return currentURL;
    }

    /** This method returns current depth **/
    public int getCurrentDepth() {
        return currentDepth;
    }

    /**
     *    This method checks whether a specified list
     *    has at least one member with a specified depth value.
     **/
    public static boolean containsDepth(LinkedList<URLDepthPair> list, int toFind){
        for (URLDepthPair i: list){
            if (i.getCurrentDepth() == toFind) { return true; }
        }
        return false;
    }

}
