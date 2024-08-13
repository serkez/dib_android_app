package com.example.dibapp.model;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;

public class Dib {

    private ArrayList<String> messageList = new ArrayList<>();

    public Dib() {
        messageList = new ArrayList<>();
    }


    public Dib(ArrayList<String> messageList) {
        this.messageList = messageList;
    }

    public void resetMessages() {
        messageList.clear();
    }




    public String getAbout() {
        return "";
    }

    public static Dib getMessagesFromJSON(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, Dib.class);
    }

    public static String getJSONFromMessages(Dib obj) {
        Gson gson = new Gson();
        return gson.toJson(obj);
    }

    public ArrayList<String> getMessageList(){
        if(messageList == null){
            return new ArrayList<String>();
        }
        return messageList;
    }

    public void addMessage(String message){
        messageList.add(message);
    }

    public String getJSONFromCurrentGame() {
        return getJSONFromMessages(this);
    }
}



