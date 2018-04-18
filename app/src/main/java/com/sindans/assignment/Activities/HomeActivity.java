package com.sindans.assignment.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.sindans.assignment.R;

public class HomeActivity extends AppCompatActivity {
    Button btn_new_survey, btn_survey_report, btn_sync_info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initView();
        onClick();
    }

    public void initView() {
        setTitle("Home");
        btn_new_survey = (Button) findViewById(R.id.btn_new_survey);
        btn_survey_report = (Button) findViewById(R.id.btn_survey_report);
        btn_sync_info = (Button) findViewById(R.id.btn_sync_info);
    }//initViewClose

    public void onClick() {
        btn_new_survey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(HomeActivity.this,SurveyFormActivity.class);
                startActivity(i);
            }
        });

        btn_survey_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(HomeActivity.this,SurveyReportActivity.class);
                startActivity(i);
            }
        });

        btn_sync_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(HomeActivity.this,SyncInfoActivity.class);
                startActivity(i);
            }
        });
    }//onClickClose

}