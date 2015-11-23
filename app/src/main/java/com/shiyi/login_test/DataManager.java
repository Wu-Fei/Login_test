package com.shiyi.login_test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.TreeMap;

/**
 * Created by Shiyi on 17/11/2015.
 */
public class DataManager {
    private static DataManager ourInstance = new DataManager();

    public static DataManager getInstance() {
        return ourInstance;
    }
    private Connection mConn;
    private ArrayList<ExerciseInfo> mMyExercises;
    private TreeMap<Integer, ArrayList<QuizInfo>> mExerciseQuizzes;
    private  Statement mStmt;
    private ResultSet mResultSet;
    private UserData mUserData;
    private boolean mIsDemon;
    private DataManager() {
        mConn = null;
        mStmt = null;
        mResultSet = null;
        mIsDemon = false;
        mMyExercises = new ArrayList();
        mExerciseQuizzes = new TreeMap<Integer, ArrayList<QuizInfo>>();
        mUserData = new UserData(); //need to clean after user log out.
    }

    public Boolean open(boolean isDemon){
        mIsDemon = isDemon;
        if(mIsDemon){
            mMyExercises = ExerciseInfo.getDemon_Values();
            return true;
        }
        String connectionUrl = "jdbc:jtds:sqlserver://10.0.2.2:1433/eclasso2o_tables;instance=MSSQLSERVER;";
        String driver = "net.sourceforge.jtds.jdbc.Driver";
        String userName = "abc";
        String password = "abc";
        try{
            Class.forName(driver);
            mConn = DriverManager.getConnection(connectionUrl, userName, password);
            return queryUserExercises();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public void close(){
        if(mConn != null){
            try {
                if(mStmt != null)
                    mStmt.close();
                if(mResultSet != null)
                    mResultSet.close();
                mConn.close();
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        }
    }
    public boolean queryUserExercises(){
        if(mIsDemon)
            return true;
        if(mConn == null)
            return false;
        try {
            mStmt = mConn.createStatement();
            ResultSet rs = mStmt.executeQuery("SELECT Exersizes.Id, Exersizes.Name, Exersizes.Description, Exersizes.IsExam, Exersizes.TotalQuizzes, Exersizes.Category, UserExersizes.Completed, UserExersizes.Progress FROM Exersizes INNER JOIN UserExersizes ON UserExersizes.ExersizeId = Exersizes.Id where UserExersizes.userid=8 order by Exersizes.Id");
            while(rs.next()){
                int id = rs.getInt(1);
                boolean isExam = rs.getBoolean(4);
                mMyExercises.add(new ExerciseInfo(id, rs.getString(2), rs.getString(3), isExam, rs.getInt(5), rs.getBoolean(7), rs.getString(8)));
            }
            mStmt.close();
            rs.close();
            return true;
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }
    public ArrayList<ExerciseInfo> getUserExercises() {
        return mMyExercises;
    }

    public boolean queryQuiz(int eid){
        if(mIsDemon){
            if(eid == 19)
                mExerciseQuizzes.put(eid, QuizInfo.getDemon_Values());
            else
                return false;
            return true;
        }
        if(mConn == null)
            return false;
        String q = "";
        try {
            ResultSet rs = mStmt.executeQuery(q);
            while(rs.next()){

            }
            rs.close();
            mStmt.close();
            return true;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public ArrayList<QuizInfo> getQuizzes(int eid){
        return mExerciseQuizzes.get(eid);
    }
    public ResultSet getResults(){
        return mResultSet;
    }
    public void updateUserAnswer(int qid, String ans){
        mUserData.updateQuizAnswer(qid, ans);
    }
    public String getUserAnswer(int qid){
       return mUserData.getQuizAnswer(qid);
    }
}
