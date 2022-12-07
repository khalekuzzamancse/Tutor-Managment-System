package com.example.blooddonation.firebasetemplate;

public class DomainUserInfo {
    public String name,phoneNo,email,userType,className,subject,district;
    @Override
    public String toString() {
        return "DomainUserInfo{" +
                "name='" + name + '\'' +
                ", phoneNo='" + phoneNo + '\'' +
                ", email='" + email + '\'' +
                ", userType='" + userType + '\'' +
                ", className='" + className + '\'' +
                ", subject='" + subject + '\'' +
                ", district='" + district + '\'' +
                '}';
    }

}
