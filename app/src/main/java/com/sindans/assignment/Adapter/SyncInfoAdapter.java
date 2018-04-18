package com.sindans.assignment.Adapter;

import android.content.Context;
import android.os.Environment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sindans.assignment.Classes.SurveyReportPojo;
import com.sindans.assignment.Classes.SyncInfoPojo;
import com.sindans.assignment.R;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

public class SyncInfoAdapter extends RecyclerView.Adapter<SyncInfoAdapter.ViewHolder> {
    List<SyncInfoPojo> syncinfoPojoList;
    Context context;

    public SyncInfoAdapter(List<SyncInfoPojo> syncinfoPojoList, Context context) {
        this.syncinfoPojoList = syncinfoPojoList;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return syncinfoPojoList.size();
    }

    public SyncInfoPojo getItem(int i) {
        return syncinfoPojoList.get(i);
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_sync_info, parent, false);
        return new ViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final SyncInfoPojo item=syncinfoPojoList.get(position);

        holder.tv_name.setText(item.getName());
        holder.tv_body.setText(item.getBody());
        holder.tv_email.setText(item.getEmail());
holder.text_id.setText("Id : "+item.getId());
holder.tv_id_post.setText("PostId : "+item.getPostId());

    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_email,tv_name,tv_body,tv_id_post,text_id;
        public ViewHolder(View itemView) {
            super(itemView);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tv_email = (TextView) itemView.findViewById(R.id.tv_email);
            tv_body = (TextView) itemView.findViewById(R.id.tv_body);
            tv_id_post=(TextView)itemView.findViewById(R.id.tv_id_post);
            text_id=(TextView)itemView.findViewById(R.id.text_id);

        }
    }
}
