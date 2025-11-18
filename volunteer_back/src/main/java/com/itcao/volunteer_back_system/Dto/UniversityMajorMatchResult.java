package com.itcao.volunteer_back_system.Dto;

import java.io.Serializable;

/**
 * 院校专业匹配结果实体类
 */
public class UniversityMajorMatchResult implements Serializable {

    private String universityName; // 大学名称
    private String universityId; // 大学ID
    private String universityLevel; // 大学层次
    private String majorName; // 专业名称
    private String majorId; // 专业ID
    private String majorCategory; // 专业类别
    private String location; // 地区
    private Integer admissionScore; // 录取分数
    private Integer scoreDifference; // 分数差距
    private Integer matchRate; // 匹配度

    public UniversityMajorMatchResult() {
    }

    public String getUniversityName() {
        return universityName;
    }

    public void setUniversityName(String universityName) {
        this.universityName = universityName;
    }

    public String getUniversityId() {
        return universityId;
    }

    public void setUniversityId(String universityId) {
        this.universityId = universityId;
    }

    public String getUniversityLevel() {
        return universityLevel;
    }

    public void setUniversityLevel(String universityLevel) {
        this.universityLevel = universityLevel;
    }

    public String getMajorName() {
        return majorName;
    }

    public void setMajorName(String majorName) {
        this.majorName = majorName;
    }

    public String getMajorId() {
        return majorId;
    }

    public void setMajorId(String majorId) {
        this.majorId = majorId;
    }

    public String getMajorCategory() {
        return majorCategory;
    }

    public void setMajorCategory(String majorCategory) {
        this.majorCategory = majorCategory;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getAdmissionScore() {
        return admissionScore;
    }

    public void setAdmissionScore(Integer admissionScore) {
        this.admissionScore = admissionScore;
    }

    public Integer getScoreDifference() {
        return scoreDifference;
    }

    public void setScoreDifference(Integer scoreDifference) {
        this.scoreDifference = scoreDifference;
    }

    public Integer getMatchRate() {
        return matchRate;
    }

    public void setMatchRate(Integer matchRate) {
        this.matchRate = matchRate;
    }
}