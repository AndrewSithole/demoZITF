package de.andrew.demoZITF.myDataModels;

import io.realm.RealmObject;

/**
 * Created by Andrew on 3/29/16.
 */
public class Information extends Object {

    private String question;
    private String answer;


    public Information() { }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

}