package com.deificindia.indifun1.interfaces;

public class Event {

    private static OnFilterUpdate onFilterUpdate;

    public static void setOnFilterUpdate(OnFilterUpdate listener){
        onFilterUpdate = listener;
    }

    public static void trigger(){
        if(onFilterUpdate!=null) onFilterUpdate.onFileter();
    }
}
