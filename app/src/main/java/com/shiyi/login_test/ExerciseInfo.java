package com.shiyi.login_test;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Shiyi on 17/11/2015.
 */
public class ExerciseInfo {
    int mId;
    String mName;
    String mDescription;
    boolean mIsExam;
    int mTotalQuiz;
    boolean mCompleted;
    String mProgress;

    public ExerciseInfo(int id, String name, String desc, boolean isExam, int totalQuiz, boolean completed, String prog){
        mId = id;
        mName = name;
        mDescription = desc;
        mIsExam = isExam;
        mTotalQuiz = totalQuiz;
        mCompleted = completed;
        mProgress = prog;
    }

    public int getId(){return mId;}
    public String getName() {return mName;}
    public String getProgress(){return mProgress;}
    public int getTotalQuiz(){return mTotalQuiz;}
    public boolean getCompleted(){return mCompleted;}

    @Override
    public String toString() {
        String result = "{"+mId+", \""+mName+"\", \""+mDescription+"\", "+mIsExam+", "+mTotalQuiz+", "+mCompleted+", \""+mProgress+"\"}";
        return result;
    }

    public static ArrayList<ExerciseInfo> getDemon_Values (){
        ArrayList<ExerciseInfo> arr = new  ArrayList<ExerciseInfo>();
        arr.add(new ExerciseInfo(19, "第 1 课练习", "Je me présente", true, 10, true, "10"));
        arr.add(new ExerciseInfo(20, "第 2 课练习", "Tu es d’où ? ", true, 15, false, "5"));
        arr.add(new ExerciseInfo(62, "第 3 课练习", "Vous habitez où ? Qu’est-ce que vous faites ?", true, 13, false, "1"));
        arr.add(new ExerciseInfo(63, "Reflets 补充练习题 01", "", false, 7, false, "0"));
        arr.add(new ExerciseInfo(66, "第 4 课练习", "François, 35 ans, marié, deux enfants, fan de football", true, 13, false, "0"));
        arr.add(new ExerciseInfo(68, "第 5 课练习", "Quel est votre numéro de téléphone, s’il vous plaît ?", true, 12, false, "0"));
        arr.add(new ExerciseInfo(69, "第 6 课练习", "Allô, c’est moi, t’es où ?", true, 15, false, "0"));
        arr.add(new ExerciseInfo(71, "Reflets 补充练习题 02", "", false, 16, false, "0"));
        arr.add(new ExerciseInfo(72, "Reflets 补充练习题 03", "", false, 7, false, "0"));
        arr.add(new ExerciseInfo(73, "Reflets 补充练习题 04", "", false, 21, false, "0"));
        arr.add(new ExerciseInfo(76, "Reflets 补充练习题 05", "", false, 19, false, "0"));
        arr.add(new ExerciseInfo(79, "Reflets 补充练习题 06", "", false, 5, false, "3"));
        arr.add(new ExerciseInfo(81, "第 7 课练习", "Je pars de chez moi à 7h30", true, 13, false, "0"));
        arr.add(new ExerciseInfo(83, "第 8 课练习", "Je rencontre de nouveaux amis (révisions)", false, 38, false, "0"));
        arr.add(new ExerciseInfo(84, "第 9 课练习", "Nous faisons du vélo tous les week-ends.", false, 25, false, "0"));
        arr.add(new ExerciseInfo(97, "第 10 课练习", "Je fais des courses", false, 19, false, "0"));
        arr.add(new ExerciseInfo(98, "第 11 课练习", "Un panini jambon-fromage, s’il vous plaît !", false, 1, false, "0"));
        arr.add(new ExerciseInfo(99, "第 12 课练习", "Le programme pour aujoud’hui ", false, 26, false, "0"));
        arr.add(new ExerciseInfo(104, "第 15 课练习", "C’est grand chez toi ?", false, 10, false, "0"));
        return arr;
    }
    }