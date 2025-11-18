package com.itcao.volunteer_back_system.Dto;

import java.io.Serializable;

/**
 * 志愿推荐项实体类
 */
public class RecommendationItem implements Serializable {

    private String universityName; // 大学名称
    private String universityId; // 大学ID
    private String majorName; // 专业名称
    private String majorId; // 专业ID
    private Integer lastYearAdmission; // 去年录取分数
    private Integer scoreDifference; // 分数差距

    public RecommendationItem() {
    }

    public RecommendationItem(String universityName, String universityId, String majorName, String majorId,
            Integer lastYearAdmission, Integer scoreDifference) {
        this.universityName = universityName;
        this.universityId = universityId;
        this.majorName = majorName;
        this.majorId = majorId;
        this.lastYearAdmission = lastYearAdmission;
        this.scoreDifference = scoreDifference;
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

    public Integer getLastYearAdmission() {
        return lastYearAdmission;
    }

    public void setLastYearAdmission(Integer lastYearAdmission) {
        this.lastYearAdmission = lastYearAdmission;
    }

    public Integer getScoreDifference() {
        return scoreDifference;
    }

    public void setScoreDifference(Integer scoreDifference) {
        this.scoreDifference = scoreDifference;
    }
}