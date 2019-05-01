package com.example.myapplication;

public class Questions {
    public String mQuestions[] =  {
            "8 + 2 = 10?",
            "3 - 1 = 2?",
            "6 - 3 = 5?",
            "5 + 3 = 9?",
            "3 + 5 = 7?",
            "8 / 2 = 4?",
            "3 * 3 = 6?",
            "5 * 7 = 45?",
            "24 / 6 = 4?",
            "4 * 3 = 16?",
            "(16 + 4) / 4 = 5?",
            "(4 * 2) + 7 = 14?",
            "5 + 7 - 3 + 1 = 10?",
            "8 - (3 * 2) = 3?",
            "(9 - 4) * 3 = 15?",
            "(7 * 6) / (3 * 2) = 7?",
            "(2.5 * 4) - (3 - 2) = 8?",
            "9 - 6 + 2 -1 + 7 = 9?",
            "6 + 1 - (3 * 2) = 2?",
            "(8 / 4) * (5 + 3 - 6) = 4?"
    };

    private String mCorrectAnswers[] = {"True", "True", "False", "False", "False", "True", "False", "False", "True", "False", "True", "False", "True", "False", "True", "True", "False", "False", "False", "True"};

    public String getQuestion(int a) {
        return mQuestions[a];
    }

    public String getCorrectAnswer(int a) {
        return mCorrectAnswers[a];
    }
}
