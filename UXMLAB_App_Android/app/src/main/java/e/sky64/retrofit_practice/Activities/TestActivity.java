package e.sky64.retrofit_practice.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import e.sky64.retrofit_practice.Api.Api;
import e.sky64.retrofit_practice.Api.ApiUrl;
import e.sky64.retrofit_practice.Models.Courses;
import e.sky64.retrofit_practice.R;
import e.sky64.retrofit_practice.helper.SharedPrefManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TestActivity extends AppCompatActivity implements View.OnClickListener {

    Button buttonTest, buttonLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        buttonTest = (Button) findViewById(R.id.buttonTest);
        buttonLogout = (Button) findViewById(R.id.buttonLogout);

        buttonTest.setOnClickListener(this);
        buttonLogout.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == buttonTest) {
            System.out.println("로그인 테스트!!");
            System.out.println(SharedPrefManager.getInstance(this).isLoggedIn());
            System.out.println(SharedPrefManager.getInstance(this).getUser());
            System.out.println(SharedPrefManager.getInstance(this).getUser().getId());
            System.out.println(SharedPrefManager.getInstance(this).getUser().getName());
            System.out.println(SharedPrefManager.getInstance(this).getUser().getEmail());
            System.out.println(SharedPrefManager.getInstance(this).getUser().getIs_student());
        } else if(view == buttonLogout) {
            SharedPrefManager.getInstance(this).logout();

            System.out.println("로그아웃!!");
            System.out.println(SharedPrefManager.getInstance(this).isLoggedIn());
            System.out.println(SharedPrefManager.getInstance(this).getUser());
            System.out.println(SharedPrefManager.getInstance(this).getUser().getId());
            System.out.println(SharedPrefManager.getInstance(this).getUser().getName());
            System.out.println(SharedPrefManager.getInstance(this).getUser().getEmail());
            System.out.println(SharedPrefManager.getInstance(this).getUser().getIs_student());
        }
    }

    private void logout() {
        SharedPrefManager.getInstance(this).logout();
        finish();
        startActivity(new Intent(this, SignInActivity.class));
    }

}
