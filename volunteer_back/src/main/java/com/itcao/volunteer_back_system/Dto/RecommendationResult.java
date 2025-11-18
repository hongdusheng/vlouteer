package com.itcao.volunteer_back_system.Dto;

import java.io.Serializable;
import java.util.List;

/**
 * 志愿推荐结果实体类
 */
public class RecommendationResult implements Serializable {

    private Double studentScore; // 学生分数
    private List<RecommendationItem> recommendations; // 推荐列表

    public RecommendationResult() {
    }

    public RecommendationResult(Double studentScore, List<RecommendationItem> recommendations) {
        this.studentScore = studentScore;
        this.recommendations = recommendations;
    }

    public Double getStudentScore() {
        return studentScore;
    }

    public void setStudentScore(Double studentScore) {
        this.studentScore = studentScore;
    }

    public List<RecommendationItem> getRecommendations() {
        return recommendations;
    }

    public void setRecommendations(List<RecommendationItem> recommendations) {
        this.recommendations = recommendations;
    }
}