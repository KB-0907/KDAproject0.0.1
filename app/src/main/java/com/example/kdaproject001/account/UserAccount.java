package com.example.kdaproject001.account;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

/**
 * 사용자 계정 정보 모델 클래스
 */
public class UserAccount {
    private String emailId;
    private String password;
    private String idToken;
    private String studentId;
    private String department;
    private String name;
    boolean Authentication;

    public UserAccount(){} //파이어 베이스 데이터베이스 필수

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIdToken() {
        return idToken;
    }

    public void setIdToken(String idToken) {
        this.idToken = idToken;
    }

    public boolean isAuthentication() {
        return Authentication;
    }

    public void setAuthentication(boolean authentication) {
        Authentication = authentication;
    }

    public String getStudentId() { return studentId; }

    public void setStudentId(String studentId){
        this.studentId = studentId;
    }

    public String getDepartment() {return department;}

    public void setDepartment(String department){
        this.department = department;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
