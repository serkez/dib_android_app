package com.example.dibapp.model;


import com.google.gson.Gson;

import java.util.ArrayList;

// This is the dib or chat model


public class Dib {

    private ArrayList<String> messageList = new ArrayList<>();

    public Dib() {
        messageList = new ArrayList<>();
    }


    public static Dib getMessagesFromJSON(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, Dib.class);
    }

    public static String getJSONFromMessages(Dib obj) {
        Gson gson = new Gson();
        return gson.toJson(obj);
    }

    public ArrayList<String> getMessageList() {
        if (messageList == null) {
            return new ArrayList<String>();
        }
        return messageList;
    }

    public void addMessageToList(String message) {
        messageList.add(message);
    }

    public String getResponse(String userInput) {
        String response = "I don't understand that, here is what you can ask me...";
        if (userInput.equals("hi") || userInput.equals("hello")) response = "Hello there!";
        else if (userInput.contains("bring me home")) {
            response = "The directions feature is currently under construction, try again another time.";
        }
        return response;
    }

    public void resetMessages() {
        messageList.clear();
    }

}



