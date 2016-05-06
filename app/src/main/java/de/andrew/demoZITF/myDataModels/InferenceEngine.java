package de.andrew.demoZITF.myDataModels;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.andrew.demoZITF.AskTheGuideActivity;
import de.andrew.demoZITF.sessions.SessionManager;

/**
 * Created by Andrew on 5/4/16.
 */
public class InferenceEngine {
    final String[] keywords = {"Mountain", "Waterfall", "Services", "activities", "Visa", "currency","language" };
    final String[] keywordsForLanguage =
            {"What languages are spoken here",
            "Which language is spoken here",
            "what language do they speak"};
    final String[] keywordsForCurrency =
            {"What is the currency here",
            "what currency is used here",
            "which currency do they use here"};
    String question;
    Place contextPlace;
    Context context;

    public InferenceEngine(Context c){
        context = c;
    }
    public boolean hasContextWord(String question){
        String strOrig = question;
        int indexOfThis = strOrig.indexOf("this");
        int indexOfHere = strOrig.indexOf("here");
        if(indexOfThis == - 1 || indexOfHere == - 1){
            return false;
        }else{
            return true;
        }

    }

    public Place getContextPlace(){
        DatabaseHandler db = new DatabaseHandler(context);
        SessionManager manager = new SessionManager(context);
        if (manager.selectedPlace()==true) {
            contextPlace = db.getPlace(manager.getSelectedPlace());
        }
        return contextPlace;
    }
    public static int getDistance(String question, String compareString) {
        question = question.toLowerCase();
        compareString = compareString.toLowerCase();
        // i == 0
        int [] costs = new int [compareString.length() + 1];
        for (int j = 0; j < costs.length; j++)
            costs[j] = j;
        for (int i = 1; i <= question.length(); i++) {
            // j == 0; nw = lev(i - 1, j)
            costs[0] = i;
            int nw = i - 1;
            for (int j = 1; j <= compareString.length(); j++) {
                int cj = Math.min(1 + Math.min(costs[j], costs[j - 1]), question.charAt(i - 1) == compareString.charAt(j - 1) ? nw : nw + 1);
                nw = costs[j];
                costs[j] = cj;
            }
        }
        return costs[compareString.length()];
    }

    public String processQuery(String qn){
        String answer ="";
        if (hasContextWord(qn) == true){
            getContextPlace();
        }
        for (String currentWord:keywords){
            getRelatedWord(currentWord);
        }
        String[] fromQn = qn.split(" ");

        for(String s : fromQn){
            for(String ss : keywords){
                if(ss.equalsIgnoreCase(s)){
                    //do something
                    answer = "Keyword "+ ss + " found";
                    break;
                }
            }
        }
        return answer;
//        List<String> tokens = new ArrayList<String>();
//        tokens.add("123woods");
//        tokens.add("woods");
//
//        String patternString = "\\b(" + TextUtils.join("|",tokens) + ")\\b";
//        Pattern pattern = Pattern.compile(patternString);
//        Matcher matcher = pattern.matcher(qn);
//
//        while (matcher.find()) {
//            System.out.println(matcher.group(1));
//        }


    }
    public String getRelatedWord (String word){

        String token="";

        return token;
    }





}
