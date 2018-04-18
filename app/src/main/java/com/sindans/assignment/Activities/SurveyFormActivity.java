package com.sindans.assignment.Activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.sindans.assignment.Classes.SqlDB;
import com.sindans.assignment.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class SurveyFormActivity extends AppCompatActivity {
   ImageView iv_first_image,iv_second_image,iv_back;
   Button btn_save;
   Spinner sp_cities;
   RadioGroup rg_gender;
   RadioButton rb_male,rb_female;
   EditText et_name,et_area;
   TextView tv_title;
   boolean image_one=false,image_two=false;
    private final int requestCode = 20;
    String storeSecondFilename="",storeFilename="";
    String GenderType="Male";
    View toolbar;
    SqlDB sqLiteDB;
    SQLiteDatabase sqLiteDatabaseWrite, sqLiteDatabaseRead;
    SQLiteStatement sqLiteStatementdelete, sqLiteStatement;
    Cursor cursor;
    Context context;
    private static final int STORAGE_PERMISSIONS_REQUEST = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_form);
        initView();
        onclick();
    }

    public void initView()
    {
        context=this;
        iv_first_image=(ImageView)findViewById(R.id.iv_first_image);
        iv_second_image=(ImageView)findViewById(R.id.iv_second_image);
        sp_cities=(Spinner) findViewById(R.id.sp_cities);
        rg_gender=(RadioGroup) findViewById(R.id.rg_gender);
        rb_male=(RadioButton) findViewById(R.id.rb_male);
        rb_female=(RadioButton) findViewById(R.id.rb_female);
        et_name=(EditText) findViewById(R.id.et_name);
        et_area=(EditText) findViewById(R.id.et_area);
        toolbar=(View) findViewById(R.id.toolbar);
        iv_back=(ImageView)toolbar.findViewById(R.id.iv_back);
        btn_save=(Button) toolbar.findViewById(R.id.btn_save);
        tv_title=(TextView) toolbar.findViewById(R.id.tv_title);
        tv_title.setText("Survey Form");
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.india_top_cities, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_cities.setAdapter(adapter);
        sqLiteDB = new SqlDB(context);
        sqLiteDatabaseWrite = sqLiteDB.getWritableDatabase();
        sqLiteDatabaseRead = sqLiteDB.getReadableDatabase();
        et_name.setFilters(new InputFilter[] {
                new InputFilter() {
                    @Override
                    public CharSequence filter(CharSequence cs, int start,
                                               int end, Spanned spanned, int dStart, int dEnd) {
                        // TODO Auto-generated method stub
                        if(cs.equals("")){ // for backspace
                            return cs;
                        }
                        if(cs.toString().matches("[a-zA-Z ]+")){
                            return cs;
                        }
                        return "";
                    }
                }
        });
    }//initViewClose
    public void onclick()
    {
        iv_first_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                image_one=true;
                image_two=false;
                if (isStoragePermissionGranted())
                {
                    Intent photoCaptureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(photoCaptureIntent, requestCode);
                }
                else
                {

                    requestPermission();

                }

            }
        });
        iv_second_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                image_two=true;
                image_one=false;
                if (isStoragePermissionGranted())
                {

                    Intent photoCaptureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(photoCaptureIntent, requestCode);
                }
                else
                {
                    requestPermission();

                }

            }
        });

        rg_gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i==R.id.rb_male)
                {
                    GenderType="Male";

                }
                else
                {

                    GenderType="Female";

                }
            }
        });
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (et_name.getText().toString().trim().length()==0)
                {
                    Toast.makeText(SurveyFormActivity.this, "Please Enter Name", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(et_area.getText().toString().trim().length()==0)
                {
                    Toast.makeText(SurveyFormActivity.this, "Please Enter Area", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (sp_cities.getSelectedItem().toString().equals("Select City"))
                {
                    Toast.makeText(SurveyFormActivity.this, "Please Select City", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (storeFilename.equals(""))
                {
                    Toast.makeText(SurveyFormActivity.this, "Please Capture First Image ", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (storeSecondFilename.equals(""))
                {
                    Toast.makeText(SurveyFormActivity.this, "Please Capture Second Image ", Toast.LENGTH_SHORT).show();
                    return;
                }
                submitForm();
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(this.requestCode == requestCode && resultCode == RESULT_OK){


            if (image_one){
                Bitmap bitmap = (Bitmap)data.getExtras().get("data");

                String partFilename = currentDateFormat();
                storeCameraPhotoInSDCard(bitmap, partFilename);
                 storeFilename = "assignment_" + partFilename + ".jpg";
                 iv_first_image.setImageBitmap(bitmap);
            }
            if (image_two)
            {
                Bitmap bitmap2 = (Bitmap)data.getExtras().get("data");

                String partFilename = currentDateFormat();
                storeCameraPhotoInSDCard(bitmap2, partFilename);

                // display the image from SD Card to ImageView Control
                 storeSecondFilename = "assignment_" + partFilename + ".jpg";

                iv_second_image.setImageBitmap(bitmap2);
            }

        }
    }
    private String currentDateFormat(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HH_mm_ss");
        String  currentTimeStamp = dateFormat.format(new Date());
        return currentTimeStamp;
    }

    private void storeCameraPhotoInSDCard(Bitmap bitmap, String currentDate){
        File PFolder = new File(Environment.getExternalStorageDirectory() + "/SurveyImages");
        if (!PFolder.exists()) {
            PFolder.mkdir();
        }
        String result= "assignment_" + currentDate + ".jpg";
        try {
            OutputStream output = new FileOutputStream(PFolder + "/" + result);
           // FileOutputStream fileOutputStream = new FileOutputStream(outputFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, output);
            output.flush();
            output.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public void submitForm()
    {
        //Insert in to first table passengerlist
        sqLiteStatement = sqLiteDatabaseWrite.compileStatement("insert into survey_form(name,city,gender,first_image_path,second_image_path,area) values(?,?,?,?,?,?)");
        sqLiteStatement.bindString(1, et_name.getText().toString());
        sqLiteStatement.bindString(2, sp_cities.getSelectedItem().toString());
        sqLiteStatement.bindString(3,GenderType);
        sqLiteStatement.bindString(4, storeFilename);
        sqLiteStatement.bindString(5, storeSecondFilename);
        sqLiteStatement.bindString(6, et_area.getText().toString());



        long res = sqLiteStatement.executeInsert();
        if(res>0)
        {
            Toast.makeText(context,"Form Successfully Submitted ",Toast.LENGTH_SHORT).show();
            et_name.setText("");
            et_area.setText("");
            sp_cities.setSelection(0);
            rb_male.setChecked(true);
            rb_female.setChecked(false);
            GenderType="Male";
            storeFilename="";
            storeSecondFilename="";
            iv_first_image.setImageDrawable(getResources().getDrawable(R.drawable.ic_camera));
            iv_second_image.setImageDrawable(getResources().getDrawable(R.drawable.ic_camera));
        }


    }
    public  boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {

                return true;
            } else {


                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }
        else {

            return true;
        }
    }
    private void requestPermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(SurveyFormActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {


        } else {
            ActivityCompat.requestPermissions(SurveyFormActivity.this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
try {


    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
        Intent photoCaptureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(photoCaptureIntent, 20);

    } else {
        Toast.makeText(this, "permission denied", Toast.LENGTH_SHORT).show();
    }
}catch (Exception e)
{
    e.printStackTrace();
}
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
