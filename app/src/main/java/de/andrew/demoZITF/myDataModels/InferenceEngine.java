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
    final String[] keyPhrases =
            {"What activities are available here ",
            "Show me nearby waterfalls",
            "show me nearby mountains",
            "show me nearby schools",
            "show me nearby trade centers",
            "What are the visa requirements here",
            "What currency is used here",
            "What services are offered here",
            "What language is spoken here"};
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
//        if (hasContextWord(qn) == true){
//            getContextPlace();
//        }
//        for (String currentWord:keywords){
//            getRelatedWord(currentWord);
//        }
//        String[] fromQn = qn.split(" ");

        for(String s : keyPhrases){
            if (getDistance(qn,s)<=5){
                return getAnswer(s);
            }else if (getDistance(qn,s)>5){
                answer = "I'm not sure what you said. You can search for it on the ZTA website";
            }
//                if (getDistance(s,ss)>80)
//                if(ss.equalsIgnoreCase(s)){
//                    //do something
//                    answer = "Keyword "+ ss + " found";
//                    break;
//                }
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
    public String getAnswer(String s){
        String ans = "";
        switch (s){
            case "What activities are available here ":
                ans = "We have rugby and soccer";
                break;
            case "Show me nearby waterfalls":
                ans = "We have victoria falls";
                break;
            case "show me nearby mountains":
                ans= "We have mount Nyangani";
                break;
            case "show me nearby schools":
                ans = "Harare Institute of Technology";
                break;
            case "show me nearby trade centers":
                ans = "ZITF and Harare Exhibition center";
                break;
            case "What are the visa requirements here":
                ans ="Visa is required";
                break;
            case "What currency is used here":
                ans = "USD";
                break;
            case "What services are offered here":
                ans = "Hotel and Catering";
                break;
            case "What language is spoken here":
                ans = "Shona and English";
        }
        return ans;
    }
    public String getRelatedWord (String word){

        String token="";

        return token;
    }





}
