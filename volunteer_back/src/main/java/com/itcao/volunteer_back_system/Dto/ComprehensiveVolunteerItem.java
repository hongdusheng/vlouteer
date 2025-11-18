package com.itcao.volunteer_back_system.Dto;

import java.io.Serializable;

/**
 * 综合分析志愿项实体类
 */
public class ComprehensiveVolunteerItem implements Serializable {

    private Integer order; // 志愿顺序
    private String universityName; // 大学名称
    private String majorName; // 专业名称
    private Integer lastYearScore; // 去年分数
    private Integer scoreDifference; // 分数差距
    private Double admissionProbability; // 录取概率
    private String riskLevel; // 风险等级

    public ComprehensiveVolunteerItem() {
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
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

    public Integer getLastYearScore() {
        return lastYearScore;
    }

    public void setLastYearScore(Integer lastYearScore) {
        this.lastYearScore = lastYearScore;
    }

    public Integer getScoreDifference() {
        return scoreDifference;
    }

    public void setScoreDifference(Integer scoreDifference) {
        this.scoreDifference = scoreDifference;
    }

    public Double getAdmissionProbability() {
        return admissionProbability;
    }

    public void setAdmissionProbability(Double admissionProbability) {
        this.admissionProbability = admissionProbability;
    }

    public String getRiskLevel() {
        return riskLevel;
    }

    public void setRiskLevel(String riskLevel) {
        this.riskLevel = riskLevel;
    }
}