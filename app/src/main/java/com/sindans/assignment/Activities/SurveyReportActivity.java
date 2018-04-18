package com.sindans.assignment.Activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.sindans.assignment.Adapter.SurveyReportAdapter;
import com.sindans.assignment.Classes.SqlDB;
import com.sindans.assignment.Classes.SurveyReportPojo;
import com.sindans.assignment.R;

import java.util.ArrayList;
import java.util.List;

public class SurveyReportActivity extends AppCompatActivity {
TextView tv_no_items;
RecyclerView rv_survey_report;
ProgressBar p_bar;
    SqlDB sqLiteDB;
    SQLiteDatabase sqLiteDatabaseWrite, sqLiteDatabaseRead;
    Cursor cursor;
    Context context;
    List<SurveyReportPojo> surveyReportPojoList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_report);
        initView();
        GetSurveyReport();
    }//onCreateClose

    public void initView()
    {
        context=this;
         tv_no_items=(TextView)findViewById(R.id.tv_no_items);
         rv_survey_report=(RecyclerView)findViewById(R.id.rv_survey_report);
         p_bar=(ProgressBar) findViewById(R.id.p_bar);
        sqLiteDB = new SqlDB(this);
        sqLiteDatabaseWrite = sqLiteDB.getWritableDatabase();
        sqLiteDatabaseRead = sqLiteDB.getReadableDatabase();
        surveyReportPojoList=new ArrayList<SurveyReportPojo>();
        setTitle("Survey Report");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }//initViewClose

    public void GetSurveyReport()
    {
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rv_survey_report.setLayoutManager(llm);
        rv_survey_report.setHasFixedSize(true);

        cursor = sqLiteDatabaseRead.rawQuery("select * from survey_form", null);
        if (cursor.moveToFirst()) {
            do {
                SurveyReportPojo surveyReportPojo=new SurveyReportPojo();
                surveyReportPojo.setId(cursor.getInt(0));
                surveyReportPojo.setName(cursor.getString(1));
                surveyReportPojo.setCity(cursor.getString(2));
                surveyReportPojo.setGender(cursor.getString(3));
                surveyReportPojo.setFirst_image_path(cursor.getString(4));
                surveyReportPojo.setSecond_image_path(cursor.getString(5));
                surveyReportPojo.setArea(cursor.getString(6));

                surveyReportPojoList.add(surveyReportPojo);
                /*item_list.add(new ItemList(cursor.getString(0), cursor.getString(1), cursor.getInt(2), cursor.getString(3), cursor.getString(4), cursor.getInt(5), cursor.getInt(6), cursor.getString(7)));
                count++;*/
            }
            while (cursor.moveToNext());
            if (cursor.getCount()>0)
            {
                tv_no_items.setVisibility(View.GONE);
                SurveyReportAdapter surveyReportAdapter=new SurveyReportAdapter(surveyReportPojoList,context);
                rv_survey_report.setAdapter(surveyReportAdapter);

            }
            else
            {
                tv_no_items.setVisibility(View.VISIBLE);
            }
        }


    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }
}
