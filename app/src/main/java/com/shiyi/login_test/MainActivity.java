package com.shiyi.login_test;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    ArrayList<ExerciseInfo> mExercises;
    int [] mProgressView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent intent = getIntent();
        LinearLayout vwParentLayout = (LinearLayout) this.findViewById(R.id.display_exercises);
        vwParentLayout.setVerticalScrollBarEnabled(true);
        mExercises = DataManager.getInstance().getUserExercises();
        LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        LinearLayout.LayoutParams lparams1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        LinearLayout.LayoutParams lparams2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lparams1.weight = 8;
        lparams2.weight = 1;
        if(mExercises.size() > 0)
            mProgressView = new int[mExercises.size()];
        try {
            for (Integer i = 0; i < mExercises.size(); ++i) {
                ExerciseInfo rs = mExercises.get(i);
                LinearLayout record = new LinearLayout(this);
                record.setOrientation(LinearLayout.HORIZONTAL);
                vwParentLayout.addView(record, lparams);

                TextView textViewName = new TextView(this);
                textViewName.setText(rs.getName());
                textViewName.setTag(R.id.viewCategory, "textView");
                textViewName.setTag(R.id.exerciseIndex, i);
                record.addView(textViewName, lparams1);

                TextView textViewProg = new TextView(this);
                textViewProg.setText(rs.getProgress() + "/" + rs.getTotalQuiz());
                mProgressView[i] = View.generateViewId();
                textViewProg.setId(mProgressView[i]);
                record.addView(textViewProg, lparams2);

                Button button = new Button(this);
                button.setTag(R.id.viewCategory, "Report_button");
                button.setTag(R.id.exerciseIndex, i);
                button.setOnClickListener(this);
                if(rs.getCompleted())
                    button.setText("查看报告");
                else {
                    button.setText("递交");
                    button.setTag(R.id.viewCategory, "Submit_button");
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

    @Override
    protected void onResume() {
        super.onResume();
        for (Integer i = 0; i < mExercises.size(); ++i) {
            ExerciseInfo rs = mExercises.get(i);
            if(rs.isDirty())
            {
                TextView textViewProg = (TextView)this.findViewById(mProgressView[i]);
                textViewProg.setText(rs.getProgress() + "/" + rs.getTotalQuiz());
            }
        }
    }
    public void onClick(View tv) {
        if(tv.getTag(R.id.viewCategory) == null || tv.getTag(R.id.exerciseIndex) == null)
            return;
        String tag = (String)tv.getTag(R.id.viewCategory);
        Integer index = (Integer)tv.getTag(R.id.exerciseIndex);
        if(tag.equals("textView")) {
            FetchQuizzesTask task = new FetchQuizzesTask( index, false, this);
            task.execute((Void) null);
        }
        else if (tag.equals("Report_button")){
            FetchQuizzesTask task = new FetchQuizzesTask( index, true, this);
            task.execute((Void) null);
        }
        else if(tag.equals("Submit_button")) {
            mExercises.get(index).setCompleted();
            tv.setTag(R.id.viewCategory, "Report_button");
            ((Button) tv).setText("查看报告");
            ViewGroup vg = (ViewGroup) tv.getParent();
            vg.getChildAt(0).setOnClickListener(null);
        }
    }

    public class FetchQuizzesTask extends AsyncTask<Void, Void, Boolean> {
        private int mExerciseIndex;
        private boolean mReporting;
        private AppCompatActivity mContext;

        FetchQuizzesTask(int index, boolean reporting, AppCompatActivity a) {
            mExerciseIndex = index;
            mReporting = reporting;
            mContext = a;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
                return DataManager.getInstance().queryQuiz(DataManager.getInstance().getUserExercises().get(mExerciseIndex).getId());
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            if (success) {
                Intent intent = new Intent(mContext, QuizActivity.class);
                intent.putExtra("Exercise_index", mExerciseIndex);
                intent.putExtra("Exercise_reporting", mReporting);
                startActivity(intent);
            }
        }

        @Override
        protected void onCancelled() {
        }
    }
}
