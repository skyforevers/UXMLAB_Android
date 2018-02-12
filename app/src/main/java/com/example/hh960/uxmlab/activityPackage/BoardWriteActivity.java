package com.example.hh960.uxmlab.activityPackage;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hh960.uxmlab.R;
import com.example.hh960.uxmlab.constant.Constants;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Created by HoeYongJin on 2018-02-12.
 */

/*
* 관리자 계정을 이용하여 강좌에 게시글을 올릴 수 있는 클래스
 */

public class BoardWriteActivity  extends Activity {

    private EditText editTextTitle;
    private EditText editTextContent;
    private Button button;
    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_write);

        editTextTitle = findViewById(R.id.board_title);
        editTextContent = findViewById(R.id.board_content);
        button= findViewById(R.id.btn_submit);

        Intent intent= getIntent();
        id = intent.getStringExtra("id");

        button.setOnClickListener(new Button.OnClickListener() {
            @Override public void onClick(View view) {
                String board_title = editTextTitle.getText().toString();
                String board_content = editTextContent.getText().toString();
                String author=id;

                insertoToDatabase(board_title, board_content,author);
            } }) ;
    }


    private void insertoToDatabase(String board_title, String board_content, String author) {
        class InsertData extends AsyncTask<String, Void, String> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(BoardWriteActivity.this, "Please Wait", null, true, true);
            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
//                Toast.makeText(getApplicationContext(), "여기까진 되는건가??", Toast.LENGTH_SHORT).show();
                if(s.equals("failure")){
                    Toast.makeText(getApplicationContext(), "게시물 작성 실패", Toast.LENGTH_LONG).show();
                } else if(s.equals("success")) {
                    Toast.makeText(getApplicationContext(), "게시물 작성 성공!", Toast.LENGTH_LONG).show();
                    finish();
                }
            }
            @Override
            protected String doInBackground(String... params) {

                try {
                    String board_title = params[0];
                    String board_content = params[1];
                    String author = params[2];


                    String link = Constants.linkHTTP + Constants.writeBoard;

                    String data =  URLEncoder.encode("board_title", "UTF-8") + "=" + URLEncoder.encode(board_title, "UTF-8");
                    data += "&" + URLEncoder.encode("board_content", "UTF-8") + "=" + URLEncoder.encode(board_content, "UTF-8");
                    data += "&" + URLEncoder.encode("author", "UTF-8") + "=" + URLEncoder.encode(author, "UTF-8");

                    URL url = new URL(link);
                    URLConnection conn = url.openConnection();

                    conn.setDoOutput(true);
                    OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

                    wr.write(data);
                    wr.flush();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                    StringBuilder sb = new StringBuilder();
                    String line = null;

                    // Read Server Response
                    while ((line = reader.readLine()) != null) {
                        sb.append(line);
                        break;
                    }
                    return sb.toString();
                } catch (Exception e) {
                    return new String("Exception: " + e.getMessage());
                }
            }
        }

        InsertData task = new InsertData();
        task.execute(board_title, board_content,author);
    }
}
