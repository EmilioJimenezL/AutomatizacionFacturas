package com.example.hellofx;

import javafx.collections.ObservableList;

public class Tester {
    public static void main(String[] args) {
        try{
            SQLManager sqlManager = new SQLManager();
            sqlManager.checkServer();

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
