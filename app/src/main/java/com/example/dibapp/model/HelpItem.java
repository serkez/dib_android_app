package com.example.dibapp.model;

// This is the model for the items in Help activity

public class HelpItem {
    private final String prompt;
    private final String explanation;

    public HelpItem(String prompt, String explanation) {
        this.prompt = prompt;
        this.explanation = explanation;
    }

    public String getPrompt() {
        return prompt;
    }

    public String getExplanation() {
        return explanation;
    }
}


