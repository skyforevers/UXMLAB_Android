package com.example.hh960.uxmlab.activityPackage;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.view.View;
import android.widget.Toast;

import com.example.hh960.uxmlab.object.GlobalIdApplication;
import com.example.hh960.uxmlab.R;
import com.example.hh960.uxmlab.adapterPackage.MenuAdapter;
import com.example.hh960.uxmlab.constant.Constants;
import com.example.hh960.uxmlab.object.MenuItem;

import org.apache.http.NameValuePair;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/*
* 로그인 후 강좌 list를 보여주기 위한 클래스
* 내 강좌와 전체 강좌로 나누어서 확인한다
*/
    public class CourseListActivity extends AppCompatActivity {
        private DefaultHttpClient httpClient;
        private HttpPost httpPost;
        private ArrayList<NameValuePair> nameValuePairArrayList;
        private ListView myCourseListView;
        private ListView allCourseListView;
        private MenuAdapter menuAdapter;
        private MenuAdapter failedAdapter;
        private List<MenuItem> menuItemListAll;
        private List<MenuItem> menuItemListMyCourse;
        private Button add_course_btn;
        private GlobalIdApplication idApp;


    /**/
    private void setCourse(String response, String course, List<MenuItem> listCourse, ListView listView) throws Exception{
        JSONArray jsonArray = new JSONObject(response).getJSONArray(course);
        for(int i = 0; i < jsonArray.length(); i++){
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String course_no = jsonObject.optString("course_no");
            String course_name = jsonObject.optString("course_name");
            String professor = jsonObject.optString("professor");
            listCourse.add(new MenuItem(course_no, course_name, professor));
        }
        menuAdapter = new MenuAdapter(getApplicationContext(), listCourse);
        listView.setAdapter(menuAdapter);
        setListViewHeightBaseOnChildren(listView);
}

    private void initUI() {
        idApp = (GlobalIdApplication) getApplication();
        add_course_btn = (Button) findViewById(R.id.add_course_btn);
        if(idApp.getIsStudent()==false){
            add_course_btn.setVisibility(View.VISIBLE);
        }
        myCourseListView = (ListView) findViewById(R.id.listview_my_course);
        allCourseListView = (ListView) findViewById(R.id.listview_all_course);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courselist);
        menuItemListMyCourse = new ArrayList<MenuItem>();
        menuItemListAll = new ArrayList<MenuItem>();
        initUI();
        try {
            httpClient = new DefaultHttpClient();
            httpPost = new HttpPost(Constants.linkHTTP + Constants.courseList);
            nameValuePairArrayList = new ArrayList<NameValuePair>(1);
            nameValuePairArrayList.add(new BasicNameValuePair("id", idApp.getId()));
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairArrayList));
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            final String response = httpClient.execute(httpPost, responseHandler);
            int check_my_course = new JSONObject(response).optInt("my_course");
            if(check_my_course==1){
                setCourse(response,
                        "course",
                        menuItemListMyCourse,
                        myCourseListView);
                /* */
                setCourse(response,
                        "all_course",
                        menuItemListAll,
                        allCourseListView);
            } else if(check_my_course==0){
                final List<MenuItem> menu_itemList2 = new ArrayList<>();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        menu_itemList2.add(new MenuItem("", "등록된 강의가 없습니다.", ""));
                        MenuAdapter menuAdapter2 = new MenuAdapter(getApplicationContext(), menu_itemList2);
                        myCourseListView.setAdapter(menuAdapter2);
                        setListViewHeightBaseOnChildren(myCourseListView);
                    }
                });


                menuItemListMyCourse = new ArrayList<>();
                JSONArray jsonArray2 = new JSONObject(response).getJSONArray("all_course");
                for(int i = 0; i < jsonArray2.length(); i++){
                    JSONObject jsonObject = jsonArray2.getJSONObject(i);
                    String course_no = jsonObject.optString("course_no");
                    String course_name = jsonObject.optString("course_name");
                    String professor = jsonObject.optString("professor");
                    menuItemListMyCourse.add(new MenuItem(course_no, course_name, professor));
                }
                failedAdapter = new MenuAdapter(getApplicationContext(), menuItemListMyCourse);
                allCourseListView.setAdapter(failedAdapter);
                setListViewHeightBaseOnChildren(allCourseListView);
            }

            myCourseListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent = new Intent(CourseListActivity.this, CourseActivity.class);
                    intent.putExtra("course_no", String.valueOf(menuItemListAll.get(i).getCourse_no()));
                    startActivity(intent);
                }
            });

            allCourseListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    showRegisterCourse(String.valueOf(menuItemListMyCourse.get(i).getCourse_no()));
                }
            });
        } catch (Exception e){

        }
    }

    public void clickAddCourse(View view)
    {
        Intent intent = new Intent(this, AddCourseActivity.class);
        startActivity(intent);
    }

    /* UI for responsive */
    public void setListViewHeightBaseOnChildren(ListView listView){
        ListAdapter listAdapter = listView.getAdapter();
        if(listAdapter==null){
            return;
        }

        int totalHeight = 0;

        for(int i = 0; i < listAdapter.getCount(); i++){
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount()-1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

    public void showRegisterCourse(final String course_no){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("강의 등록");
        alert.setMessage("키를 입력해주세요.");

        final EditText edit_key = new EditText(this);
        alert.setView(edit_key);

        alert.setPositiveButton("입력", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String course_key = edit_key.getText().toString();
                findKey(course_key, course_no);
            }
        });

        alert.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        alert.show();
    }

    public void findKey(String course_key, String course_no){
        class findData extends AsyncTask<String, Void, String>{
            @Override
            protected String doInBackground(String... strings) {
                try {
                    String course_key = (String) strings[0];
                    String course_no = (String) strings[1];
                    GlobalIdApplication idApplication = (GlobalIdApplication) getApplication();
                    String id = idApplication.getId();

                    String link = Constants.linkHTTP + Constants.courseRegister;
                    String data = URLEncoder.encode("course_key", "UTF-8") + "=" + URLEncoder.encode(course_key, "UTF-8");
                    data += "&"+URLEncoder.encode("course_no", "UTF-8")+"="+URLEncoder.encode(course_no, "UTF-8");
                    data += "&"+URLEncoder.encode("id", "UTF-8")+"="+URLEncoder.encode(id, "UTF-8");

                    URL url = new URL(link);
                    URLConnection conn = url.openConnection();

                    conn.setDoOutput(true);
                    OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

                    wr.write(data);
                    wr.flush();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                    StringBuilder sb = new StringBuilder();
                    String line = null;

                    while((line = reader.readLine())!=null){
                        sb.append(line);
                        break;
                    }
                    return sb.toString();
                } catch(Exception e){
                    return new String("Exception: "+e.getMessage());
                }
            }

            @Override
            protected void onPostExecute(String s){
                super.onPostExecute(s);
                if(s.equals("failed")){  //키가 틀린 경우
                    Toast.makeText(getApplicationContext(), "key가 올바르지 않습니다.", Toast.LENGTH_SHORT).show();
                } else if(s.equals("success")){ // insert 성공한 경우
                    Toast.makeText(getApplicationContext(), "성공적으로 등록되었습니다.", Toast.LENGTH_SHORT).show();
                } else if(s.equals("failure")){ // 키는 맞았지만 insert가 되지 않은 경우
                    Toast.makeText(getApplicationContext(), "강의가 등록되지 않았습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        }

        findData task = new findData();
        task.execute(course_key, course_no);
    }
}