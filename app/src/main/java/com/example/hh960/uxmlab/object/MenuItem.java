package com.example.hh960.uxmlab.object;

/**
 * Created by 630su on 2018-02-04.
 */

public class MenuItem {
    private String course_no;
    private String course_name;
    private String professor;

    public MenuItem(String course_no, String course_name, String professor) {
        this.course_no = course_no;
        this.course_name = course_name;
        this.professor = professor;
    }

    public String getCourse_no() {
        return course_no;
    }

    public String getCourse_name() {
        return course_name;
    }

    public String getProfessor() {
        return professor;
    }

    public void setCourse_no(String course_no) {
        this.course_no = course_no;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public void setProfessor(String professor) {
        this.professor = professor;
    }
}
