package com.example.myapplication;

public class Item {
    private String question, answer;
    public Item(String setQuestion, String setAnswer) {
        this.question = setQuestion;
        this.answer = setAnswer;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }
}
