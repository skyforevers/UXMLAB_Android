package e.sky64.retrofit_practice.Models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sky64 on 2018-02-18.
 */
// course DB에 저장되어 있는 data들을 가져오기 위해서 사용되는 데이터 관리 클래스
public class Courses {

    // serializedName 에서는 json으로 받아올 파라메터의 이름을 정의한다
    @SerializedName("course_no")
    private String courseNum;
    @SerializedName("course_name")
    private String courseName;

    public String getCourseNum() {
        return courseNum;
    }

    public String getCourseName() {
        return courseName;
    }

    public Courses(String courseNum, String courseName) {
        this.courseNum = courseNum;
        this.courseName = courseName;
    }
}
