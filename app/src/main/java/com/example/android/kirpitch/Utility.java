package com.example.android.kirpitch;

public class Utility {


    private static String[] item = {"Draft","Cancel", "Application Send", "Interview",
            "Technical Interview", "Completed", "Closed"};

    public static String getStage(int index){
        return item[index];
    }

}
