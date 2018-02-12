package com.example.hh960.uxmlab.activityPackage;

import android.app.ProgressDialog;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ViewFlipper;


import android.view.View.OnClickListener;
import android.widget.TextView;

import com.example.hh960.uxmlab.object.GlobalIdApplication;
import com.example.hh960.uxmlab.R;
import com.example.hh960.uxmlab.constant.Constants;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/*
* 로그인 / 회원 가입을 유도하기 위한 클래스
* 첫 페이지로 로그인을 하지 않으면 다음 화면으로 넘어가지 않게 제작
*/
public class MainActivity extends AppCompatActivity {

    private Button BtnSignIn, BtnSignUp;
    private EditText inputID, inputPW;
    private HttpPost httppost;
    private StringBuffer buffer;
    private HttpResponse response;
    private HttpClient httpclient;
    private ArrayList<NameValuePair> nameValuePairs;
    private ProgressDialog dialog = null;


    private void initUI() {
        BtnSignUp = (Button)findViewById(R.id.btn_signup);
        BtnSignIn = (Button)findViewById(R.id.btn_signin);
        inputID = (EditText)findViewById(R.id.user_id);
        inputPW = (EditText)findViewById(R.id.user_pw);
    }

    private void initListener() {
        BtnSignIn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = ProgressDialog.show(MainActivity.this, "",
                        "Validating user...", true);
                new Thread(new Runnable() {
                    public void run() {
                        login();
                    }
                }).start();
                onPause();
                dialog.dismiss();
                finish();
            }
        });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        initUI();
        initListener();
    }



    public void checkLogin(String response) throws Exception {
        if(response.equalsIgnoreCase("No Such User Found")){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(MainActivity.this, "아이디 혹은 비밀번호 오류입니다.", Toast.LENGTH_SHORT).show();
                }
            });
            dialog.dismiss();
        } else {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(MainActivity.this, "Login Success", Toast.LENGTH_SHORT).show();
                }
            });
            /*
               전역변수
            */
            GlobalIdApplication idApp = (GlobalIdApplication) getApplication();
            idApp.setId(inputID.getText().toString());
            idApp.setIsStudent(GetIsStudent(response));
            startActivity((new Intent(MainActivity.this, CourseListActivity.class)));
            finish();
        }
    }

    private void login() {
        try {
            httpclient = new DefaultHttpClient();
            httppost = new HttpPost(Constants.linkHTTP + Constants.login);
            nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("id", inputID.getText().toString()));
            nameValuePairs.add(new BasicNameValuePair("password", inputPW.getText().toString()));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
//            response = httpclient.execute(httppost);
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            final String response = httpclient.execute(httppost, responseHandler);
            System.out.println("Response : " + response);

            /*

            */
            checkLogin(response);
        }
        catch(Exception e)
        {
            dialog.dismiss();
            System.out.println("Exception : " + e.getMessage());
        }
    }

    public void clickSignUp(View view)
    {
        Intent intent = new Intent(this, SignupAcitivty.class);
        startActivity(intent);
    }

    private boolean GetIsStudent(String response){
        boolean is_student = true;
        try {
            JSONArray jsonArray = new JSONObject(response).getJSONArray("result");
            JSONObject jsonObject = jsonArray.getJSONObject(0);
            is_student = (1==jsonObject.optInt("is_student"));
        } catch (Exception e){

        }
        return is_student;
    }
}
