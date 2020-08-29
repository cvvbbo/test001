package com.t.test001;

/**
 * Author: xiong
 * Time: 2020/8/20 17:21
 */
public class Adminbean {


    private String project_name;
    private String operator_name;
    private String rectification_name;
    private String check_name;
    private Boolean issue;
    private String operator_time;
    private String photo;
    private String pj_num;

    public String getPj_num() {
        return pj_num;
    }

    public void setPj_num(String pj_num) {
        this.pj_num = pj_num;
    }

    public String getProject_name() {
        return project_name;
    }

    public void setProject_name(String project_name) {
        this.project_name = project_name;
    }

    public String getOperator_name() {
        return operator_name;
    }

    public void setOperator_name(String operator_name) {
        this.operator_name = operator_name;
    }

    public String getRectification_name() {
        return rectification_name;
    }

    public void setRectification_name(String rectification_name) {
        this.rectification_name = rectification_name;
    }

    public String getCheck_name() {
        return check_name;
    }

    public void setCheck_name(String check_name) {
        this.check_name = check_name;
    }

    public Boolean getIssue() {
        return issue;
    }

    public void setIssue(Boolean issue) {
        this.issue = issue;
    }

    public String getOperator_time() {
        return operator_time;
    }

    public void setOperator_time(String operator_time) {
        this.operator_time = operator_time;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
