package e.sky64.retrofit_practice.Api;

import java.util.List;

import e.sky64.retrofit_practice.Models.Courses;
import e.sky64.retrofit_practice.Models.Result;
import e.sky64.retrofit_practice.Models.User;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by sky64 on 2018-02-18.
 */
// Api 정의
public interface Api {
    /*
        URL 이후 접근할 파일 혹은 위치 지정 + Get, Post, Put 등 어떤 방식을 이용할 것인지를 정하고
        그 데이터를 어떤 방식으로 저장할 것인지 지정

        <Retrofit url 참고사항>
        if BASE_URL = "http://192.168.22.24/UXMLAB_App_php/"
        and if @POST("public/login") then
        login == "http://192.168.22.24/UXMLAB_App_php/public/login"

        ref - https://stackoverflow.com/questions/32431525/using-call-enqueue-function-in-retrofit
    */

    //The register call
    @FormUrlEncoded
    @POST("public/register")
    Call<Result> createUser(
            @Field("id") int id,
            @Field("password") String password,
            @Field("name") String name,
            @Field("email") String email
    );


    // The signin call
    @FormUrlEncoded
    @POST("public/login")
    Call<Result> userLogin( // php->DbOperation의 함수
            @Field("id") int id,
            @Field("password") String password
    );
}
