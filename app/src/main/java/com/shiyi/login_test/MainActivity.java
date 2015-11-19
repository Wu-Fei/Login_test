package com.shiyi.login_test;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent intent = getIntent();
        LinearLayout vwParentLayout = (LinearLayout) this.findViewById(R.id.display_exercises);
        vwParentLayout.setVerticalScrollBarEnabled(true);
        ArrayList<ExerciseInfo> exercises = DataBaseManager.getInstance().getUserExercises();
        LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        LinearLayout.LayoutParams lparams1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        LinearLayout.LayoutParams lparams2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lparams1.weight = 8;
        lparams2.weight = 1;
        try {
            for (ExerciseInfo rs : exercises) {
                LinearLayout record = new LinearLayout(this);
                record.setOrientation(LinearLayout.HORIZONTAL);
                vwParentLayout.addView(record, lparams);
                TextView textViewName = new TextView(this);
                textViewName.setText(rs.getName());
                textViewName.setId(rs.getId());
                record.addView(textViewName, lparams1);
                TextView textViewProg = new TextView(this);
                textViewProg.setText(rs.getProgress() + "/" + rs.mTotalQuiz);
                record.addView(textViewProg, lparams2);
                Button button = new Button(this);
                if(rs.getCompleted())
                    button.setText("查看报告");
                else {
                    button.setText("递交");
                    textViewName.setOnClickListener(this);
                }
                record.addView(button, lparams2);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    public void onClick(View tv) {
        FetchQuizzesTask task = new FetchQuizzesTask(tv.getId(), this);
        task.execute((Void) null);
    }
    public class FetchQuizzesTask extends AsyncTask<Void, Void, Boolean> {
        private int mEid;
        private AppCompatActivity mContext;

        FetchQuizzesTask(int id, AppCompatActivity a) {
            mEid = id;
            mContext = a;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
                return DataBaseManager.getInstance().queryQuiz(mEid);
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            if (success) {
                Intent intent = new Intent(mContext, QuizActivity.class);
                startActivity(intent);
            }
        }

        @Override
        protected void onCancelled() {
        }
    }
}
