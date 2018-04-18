package com.sindans.assignment.Activities;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.sindans.assignment.Adapter.SyncInfoAdapter;
import com.sindans.assignment.Classes.AppMainClass;
import com.sindans.assignment.Classes.SyncInfoPojo;
import com.sindans.assignment.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SyncInfoActivity extends AppCompatActivity {
RecyclerView rv_sync_info;
ProgressBar p_bar;
TextView tv_no_items;
Context context;
List<SyncInfoPojo> syncInfoPojoList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sync_info);
        initView();
        getSyncInfo();
    }//onCreateClose
    public void initView()
    {   context=this;
        rv_sync_info=(RecyclerView)findViewById(R.id.rv_sync_info);
        p_bar=(ProgressBar) findViewById(R.id.p_bar);
        tv_no_items=(TextView) findViewById(R.id.tv_no_items);
        syncInfoPojoList=new ArrayList<SyncInfoPojo>();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Sync Info");

    }//initViewClose

    public void getSyncInfo()
    {
        p_bar.setVisibility(View.VISIBLE);








        JsonArrayRequest postRequest1 = new JsonArrayRequest("http://jsonplaceholder.typicode.com/comments/",
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        p_bar.setVisibility(View.GONE);
                        try {

                                 for (int i = 0; i < response.length(); i++) {
                                    JSONObject jsonObject=response.getJSONObject(i);
                                    SyncInfoPojo syncInfoPojo=new SyncInfoPojo();
                                    int postId=jsonObject.getInt("postId");
                                    syncInfoPojo.setPostId(postId);
                                    int id=jsonObject.getInt("id");
                                    syncInfoPojo.setId(id);
                                    String name=jsonObject.getString("name");
                                    syncInfoPojo.setName(name);
                                    String email=jsonObject.getString("email");
                                    syncInfoPojo.setEmail(email);
                                    String body=jsonObject.getString("body");
                                    syncInfoPojo.setBody(body);
                                    syncInfoPojoList.add(syncInfoPojo);
                                }
                                if (response.length() > 0) {
                                    LinearLayoutManager llm = new LinearLayoutManager(context);
                                    llm.setOrientation(LinearLayoutManager.VERTICAL);
                                    rv_sync_info.setLayoutManager(llm);
                                    rv_sync_info.setHasFixedSize(true);

                                    SyncInfoAdapter syncInfoAdapter=new SyncInfoAdapter(syncInfoPojoList,context);
                                    rv_sync_info.setAdapter(syncInfoAdapter);
                                }
                            } catch (JSONException e1) {
                            e1.printStackTrace();
                        }



                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                p_bar.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), "Could Not Connect", Toast.LENGTH_LONG).show(); }
        });


        // Adding request to request queue
        RetryPolicy policy = new DefaultRetryPolicy(90000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        postRequest1.setRetryPolicy(policy);
        postRequest1.setShouldCache(false);
        AppMainClass.getInstance().addToReqQueue(postRequest1);



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
