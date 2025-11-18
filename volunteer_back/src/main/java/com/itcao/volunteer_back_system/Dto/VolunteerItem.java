package com.itcao.volunteer_back_system.Dto;

import java.io.Serializable;

/**
 * 志愿项实体类
 */
public class VolunteerItem implements Serializable {

    private Integer volunteerOrder; // 志愿顺序
    private String universityName; // 大学名称
    private String majorName; // 专业名称
    private Integer year; // 年份
    private Integer admissionNumber; // 录取分数

    public VolunteerItem() {
    }

    public VolunteerItem(Integer volunteerOrder, String universityName, String majorName,
            Integer year, Integer admissionNumber) {
        this.volunteerOrder = volunteerOrder;
        this.universityName = universityName;
        this.majorName = majorName;
        this.year = year;
        this.admissionNumber = admissionNumber;
    }

    public Integer getVolunteerOrder() {
        return volunteerOrder;
    }

    public void setVolunteerOrder(Integer volunteerOrder) {
        this.volunteerOrder = volunteerOrder;
    }

    public String getUniversityName() {
        return universityName;
    }

    public void setUniversityName(String universityName) {
        this.universityName = universityName;
    }

    public String getMajorName() {
        return majorName;
    }

    public void setMajorName(String majorName) {
        this.majorName = majorName;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getAdmissionNumber() {
        return admissionNumber;
    }

    public void setAdmissionNumber(Integer admissionNumber) {
        this.admissionNumber = admissionNumber;
    }
}