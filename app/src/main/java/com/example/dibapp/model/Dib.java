package com.example.dibapp.model;


import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Random;

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


    public void resetMessages() {
        messageList.clear();
    }

    public void addMessageToList(String message) {
        messageList.add(message);
    }

    public String getResponse(String userInput) {
        String response = "I don't understand that, here is what you can ask me...";
        if (userInput.equals("hi") || userInput.equals("hello")) response = "Hello there!";
        else if (userInput.contains("bring me home")) {
            response = "The directions feature is currently under construction, try again another time.";
        } else if (userInput.contains("help")) {
            response = "For assistance using this chat, visit the Help Center by clicking on the question mark icon on the top of the screen.\nFor information about this app, click on the info button under the three circle buttn, or the i button at the bottom of the Help Center";
        } else if (userInput.contains("tell me a joke")) {
            response = getJoke();
        }
        return response;
    }

    String[] jokes = {
            "Why do programmers prefer dark mode?\n Because the light attracts bugs!",
            "How many programmers does it take to change a light bulb?\n None, that's a hardware problem!",
            "Why do Java developers wear glasses?\n  Because they can't C#!",
            "I would tell you a UDP joke, but you might not get it.",
            "Why don't programmers like nature? \n It has too many bugs.",
            "Why was the computer cold?\n  It left its Windows open!",
            "What's a programmer's favorite hangout place?\n  The Foo Bar.",
            "Why did the programmer quit his job?\n  Because he didn't get arrays.",
            "There are 10 kinds of people in this world: \n those who understand binary and those who don't.",
            "Why was the JavaScript developer sad?\n  Because he didn't know how to 'null' his feelings."
    };
    Random random = new Random();
    int randomIndex;


    private String getJoke() {
        randomIndex = random.nextInt(jokes.length);
        return jokes[randomIndex];
    }

}



