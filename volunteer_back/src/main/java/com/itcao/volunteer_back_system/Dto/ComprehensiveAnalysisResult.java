package com.itcao.volunteer_back_system.Dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 志愿综合分析结果实体类
 */
public class ComprehensiveAnalysisResult implements Serializable {
    
    private String studentName;              // 学生姓名
    private Integer score;                   // 分数
    private Integer rank;                    // 排名
    private Integer volunteerCount;          // 志愿数量
    private String overallRating;            // 总体评价
    private Double rationalityScore;         // 合理性得分
    private Double safetyScore;              // 安全性得分
    private Double gradientScore;            // 梯度得分
    private Double majorMatchScore;          // 专业匹配得分
    private List<ComprehensiveVolunteerItem> volunteers;  // 志愿列表
    private Map<String, Integer> riskDistribution;        // 风险分布
    private String riskAnalysis;             // 风险分析
    private String gradientAnalysis;         // 梯度分析
    private String majorMatchAnalysis;       // 专业匹配分析
    private String overallSuggestion;        // 整体建议
    
    public ComprehensiveAnalysisResult() {
    }
    
    // Getters and setters
    
    public String getStudentName() {
        return studentName;
    }
    
    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }
    
    public Integer getScore() {
        return score;
    }
    
    public void setScore(Integer score) {
        this.score = score;
    }
    
    public Integer getRank() {
        return rank;
    }
    
    public void setRank(Integer rank) {
        this.rank = rank;
    }
    
    public Integer getVolunteerCount() {
        return volunteerCount;
    }
    
    public void setVolunteerCount(Integer volunteerCount) {
        this.volunteerCount = volunteerCount;
    }
    
    public String getOverallRating() {
        return overallRating;
    }
    
    public void setOverallRating(String overallRating) {
        this.overallRating = overallRating;
    }
    
    public Double getRationalityScore() {
        return rationalityScore;
    }
    
    public void setRationalityScore(Double rationalityScore) {
        this.rationalityScore = rationalityScore;
    }
    
    public Double getSafetyScore() {
        return safetyScore;
    }
    
    public void setSafetyScore(Double safetyScore) {
        this.safetyScore = safetyScore;
    }
    
    public Double getGradientScore() {
        return gradientScore;
    }
    
    public void setGradientScore(Double gradientScore) {
        this.gradientScore = gradientScore;
    }
    
    public Double getMajorMatchScore() {
        return majorMatchScore;
    }
    
    public void setMajorMatchScore(Double majorMatchScore) {
        this.majorMatchScore = majorMatchScore;
    }
    
    public List<ComprehensiveVolunteerItem> getVolunteers() {
        return volunteers;
    }
    
    public void setVolunteers(List<ComprehensiveVolunteerItem> volunteers) {
        this.volunteers = volunteers;
    }
    
    public Map<String, Integer> getRiskDistribution() {
        return riskDistribution;
    }
    
    public void setRiskDistribution(Map<String, Integer> riskDistribution) {
        this.riskDistribution = riskDistribution;
    }
    
    public String getRiskAnalysis() {
        return riskAnalysis;
    }
    
    public void setRiskAnalysis(String riskAnalysis) {
        this.riskAnalysis = riskAnalysis;
    }
    
    public String getGradientAnalysis() {
        return gradientAnalysis;
    }
    
    public void setGradientAnalysis(String gradientAnalysis) {
        this.gradientAnalysis = gradientAnalysis;
    }
    
    public String getMajorMatchAnalysis() {
        return majorMatchAnalysis;
    }
    
    public void setMajorMatchAnalysis(String majorMatchAnalysis) {
        this.majorMatchAnalysis = majorMatchAnalysis;
    }
    
    public String getOverallSuggestion() {
        return overallSuggestion;
    }
    
    public void setOverallSuggestion(String overallSuggestion) {
        this.overallSuggestion = overallSuggestion;
    }
} 