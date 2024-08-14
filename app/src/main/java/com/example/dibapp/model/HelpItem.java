package com.example.dibapp.model;

public class HelpItem {
    private String prompt;
    private String explanation;

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


