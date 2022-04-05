package com.company;

import java.util.LinkedList;

public class Main {
    static LinkedList<String> list = new LinkedList<>();
    public static void main(String[] args) {
        list.add("1");
        list.add("2");
        list.add("3");
        System.out.println(list.getFirst());
    }
}
