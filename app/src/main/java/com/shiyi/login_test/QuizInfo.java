package com.shiyi.login_test;

import java.util.ArrayList;

/**
 * Created by Shiyi on 19/11/2015.
 */
public class QuizInfo {
    private int mId;
    private int mType;
    private String mOptions;
    private String mChallenge;
    private String mAnswer;

    public QuizInfo(int id, int type, String o, String c, String a){
        mId = id;
        mType = type;
        mOptions = o;
        mChallenge = c;
        mAnswer = a;
    }
    public int getId(){return mId;}
    public int getType(){return mType;}
    public String getOptions(){return mOptions;}
    public String getChallenge(){return mChallenge;}
    public String getAnswer(){return mAnswer;}

    public static ArrayList<QuizInfo> getDemon_Values(){
        ArrayList<QuizInfo> results = new ArrayList<QuizInfo>();
        results.add(new QuizInfo(0, 1, "rien, le, langue, pas",	"01. Je ne parle ______ portugais.", "pas"));
        results.add(new QuizInfo(1, 1, "connaître, connaissez, connaissance, connaît", "02. ______ - vous les noms de famille français ?", "connaissez"));
        results.add(new QuizInfo(2, 1, "comme, comment, qui, que", "03. ______ est - ce ?", "qui"));
        results.add(new QuizInfo(3, 1, "suis, es, est, êtes", "04. Je ______ suédois.", "suis"));
        results.add(new QuizInfo(4, 1, "comme, comment, pour, de", "05. ______ on dit house en allemand ?", "comment"));
        results.add(new QuizInfo(5, 1, "à, de, pour, avec", "06. Voici le passeport ______ les langues.", "pour"));
        results.add(new QuizInfo(6, 1, "chinois, américain, anglais, chinoise", "07. Isabelle est ______ .", "chinoise"));
        results.add(new QuizInfo(7, 1, "m’appelle, t’appelles, s’appelle, vous appelez", "08. Je  ______  Smuel.", "m’appelle"));
        results.add(new QuizInfo(8, 1, "moi, toi, lui, elle", "09. Bonjour !______, c’est Amélie et toi ?", "moi"));
        results.add(new QuizInfo(9, 1, "cinq, huit, douze, quinze", "10. Dans la prononciation de ______ lettres, on entend le son / e / .", "huit"));
        return results;
    }
}
