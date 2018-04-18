package com.sindans.assignment.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sindans.assignment.Classes.SurveyReportPojo;
import com.sindans.assignment.R;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

public class SurveyReportAdapter extends RecyclerView.Adapter<SurveyReportAdapter.ViewHolder> {
    List<SurveyReportPojo> surveyReportPojoList;
    Context context;

    public SurveyReportAdapter(List<SurveyReportPojo> surveyReportPojoList, Context context) {
        this.surveyReportPojoList = surveyReportPojoList;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return surveyReportPojoList.size();
    }

    public SurveyReportPojo getItem(int i) {
        return surveyReportPojoList.get(i);
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_survey_report, parent, false);
        return new ViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final SurveyReportPojo item=surveyReportPojoList.get(position);
        Picasso.with(context).load(new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/SurveyImages/" + item.getFirst_image_path())).into(holder.iv_survey_image);

        holder.tv_name.setText(item.getName());
        holder.tv_gender.setText(item.getGender());
        holder.tv_city.setText(item.getCity());
        holder.cv_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenReportDetailDialog(item.getName(),item.getGender(),item.getCity(),item.getFirst_image_path(),item.getSecond_image_path(),item.getArea());
            }
        });
    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_survey_image;
        TextView tv_gender,tv_name,tv_city;
        CardView cv_report;
        public ViewHolder(View itemView) {
            super(itemView);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tv_gender = (TextView) itemView.findViewById(R.id.tv_gender);
            tv_city = (TextView) itemView.findViewById(R.id.tv_city);
            iv_survey_image = (ImageView) itemView.findViewById(R.id.iv_survey_image);
            cv_report = (CardView) itemView.findViewById(R.id.cv_report);

        }
    }

    public void OpenReportDetailDialog(String Name,String gender,String city,String first_image_path,String second_image_path,String area)
    {
        Dialog dialog=new Dialog(context);
        dialog.setContentView(R.layout.dialog_report_details);
        dialog.setTitle("Survey Report Details");
        ImageView iv_first_image=(ImageView)dialog.findViewById(R.id.iv_first_image);
        ImageView iv_second_image=(ImageView)dialog.findViewById(R.id.iv_second_image);
        TextView tv_city=(TextView) dialog.findViewById(R.id.tv_city);
        TextView tv_gender=(TextView) dialog.findViewById(R.id.tv_gender);
        TextView tv_name=(TextView) dialog.findViewById(R.id.tv_name);
        TextView tv_area=(TextView) dialog.findViewById(R.id.tv_area);
        tv_city.setText(city);
        tv_gender.setText(gender);
        tv_name.setText(Name);
        tv_area.setText(area);
        Picasso.with(context).load(new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/SurveyImages/" + first_image_path)).into(iv_first_image);
        Picasso.with(context).load(new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/SurveyImages/" + second_image_path)).into(iv_second_image);
        dialog.show();
    }
}
