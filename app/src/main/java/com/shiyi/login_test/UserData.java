package com.shiyi.login_test;

import java.util.TreeMap;

/**
 * Created by Shiyi on 19/11/2015.
 */
public class UserData {
    private TreeMap<Integer, String> m_UserQuizzesAnswer;
    private int Uid;
    public UserData(){
        m_UserQuizzesAnswer = new TreeMap<Integer, String>();
    }
    public void updateQuizAnswer(int i, String ans){
        m_UserQuizzesAnswer.put(i, ans);
    }

    public String getQuizAnswer(int i){
        return m_UserQuizzesAnswer.get(i);
    }
}
