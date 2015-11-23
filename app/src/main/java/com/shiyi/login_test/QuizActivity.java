package com.shiyi.login_test;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class QuizActivity extends AppCompatActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener{
    private int mCurrent;
    private int mExerciseIndex;
    private boolean mReporting;
    private ArrayList<QuizInfo> mQuizzes;
    LinearLayout m_vwParentLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent intent = getIntent();
        mExerciseIndex = intent.getIntExtra("Exercise_index", 0);
        mReporting = intent.getBooleanExtra("Exercise_reporting", false);
        ExerciseInfo ex = DataManager.getInstance().getUserExercises().get(mExerciseIndex);
        toolbar.setTitle(ex.getName());
        mQuizzes = DataManager.getInstance().getQuizzes(ex.getId());
        mCurrent = 0;
        m_vwParentLayout = (LinearLayout) this.findViewById(R.id.layout_quiz_content);
        Button b = (Button) this.findViewById(R.id.button_prev);
        b.setEnabled(false);
        b.setOnClickListener(this);
        b = (Button) this.findViewById(R.id.button_next);
        if(mQuizzes.size()==1)
            b.setEnabled(false);
        b.setOnClickListener(this);
        b = (Button) this.findViewById(R.id.button_save);
        if(mReporting)
            b.setText("返回");
        b.setOnClickListener(this);
        if(mQuizzes.get(mCurrent).getType()==1)
            configureView4SingleSelection();
    }

    private void configureView4SingleSelection(){
        m_vwParentLayout.removeAllViewsInLayout();
        String ans = DataManager.getInstance().getUserAnswer(mQuizzes.get(mCurrent).getId());
        LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        if(mReporting){
            TextView text = new TextView(this);
            if(ans == null || ans.length()==0)
                text.setText("学生未回答" + ", 标准答案: " + mQuizzes.get(mCurrent).getAnswer());
            else
                text.setText("学生回答: " + ans + ", 标准答案: " + mQuizzes.get(mCurrent).getAnswer());
            text.setLayoutParams(lparams);
            m_vwParentLayout.addView(text);
        }
        TextView text = new TextView(this);
        text.setText(mQuizzes.get(mCurrent).getChallenge());
        text.setLayoutParams(lparams);
        m_vwParentLayout.addView(text);
        RadioGroup radios = new RadioGroup(this);
        radios.setOrientation(LinearLayout.VERTICAL);
        radios.setLayoutParams(lparams);
        String[] options =mQuizzes.get(mCurrent).getOptions().split(", ");
        for (String s : options){
            RadioButton rb = new RadioButton(this);
            rb.setText(s);
            rb.setLayoutParams(lparams);
            rb.setId(View.generateViewId());
            if(!mReporting && ans != null && ans.length() > 0 && ans.equals(s))
                rb.setChecked(true);
            radios.addView(rb);
        }
        if(!mReporting)
            radios.setOnCheckedChangeListener(this);
        m_vwParentLayout.addView(radios);
    }
    public void onClick(View tv) {
        int id = tv.getId();
        if(id == R.id.button_save){
            if(!mReporting) {
                int progress = 0;
                for (QuizInfo q : mQuizzes) {
                    String ans = DataManager.getInstance().getUserAnswer(q.getId());
                    if (ans != null && ans.length() > 0)
                        ++progress;
                }
                DataManager.getInstance().getUserExercises().get(mExerciseIndex).setProgress(Integer.toString(progress));
            }
            finish();
            return;
        }
        if(id == R.id.button_prev) {
            if(mCurrent == mQuizzes.size() - 1) {
                Button b = (Button) this.findViewById(R.id.button_next);
                b.setEnabled(true);
            }
            --mCurrent;
            if(mCurrent == 0)
                tv.setEnabled(false);

        }
        else if(id == R.id.button_next){
            if(mCurrent == 0){
                Button b = (Button) this.findViewById(R.id.button_prev);
                b.setEnabled(true);
            }
            ++mCurrent;
            if(mCurrent == mQuizzes.size() - 1)
                tv.setEnabled(false);
        }
        if(mQuizzes.get(mCurrent).getType()==1)
            configureView4SingleSelection();
    }

    public void onCheckedChanged(RadioGroup rg, int index) {
        if(index < 0){
            DataManager.getInstance().updateUserAnswer(mQuizzes.get(mCurrent).getId(), "");
        }
        else{
            RadioButton btn = (RadioButton)rg.findViewById(index);
            String ans = btn.getText().toString();
            DataManager.getInstance().updateUserAnswer(mQuizzes.get(mCurrent).getId(), ans);
        }
    }
}
