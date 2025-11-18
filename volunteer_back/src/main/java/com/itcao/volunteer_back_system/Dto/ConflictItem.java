package com.itcao.volunteer_back_system.Dto;

import java.io.Serializable;

/**
 * 志愿冲突项实体类
 */
public class ConflictItem implements Serializable {
    
    private Integer order1;          // 第一志愿顺序
    private String uni1;             // 第一志愿大学
    private String major1;           // 第一志愿专业
    private Integer score1;          // 第一志愿分数
    private Integer order2;          // 第二志愿顺序
    private String uni2;             // 第二志愿大学
    private String major2;           // 第二志愿专业
    private Integer score2;          // 第二志愿分数
    private String advice;           // 建议
    
    public ConflictItem() {
    }
    
    public ConflictItem(Integer order1, String uni1, String major1, Integer score1,
                       Integer order2, String uni2, String major2, Integer score2, String advice) {
        this.order1 = order1;
        this.uni1 = uni1;
        this.major1 = major1;
        this.score1 = score1;
        this.order2 = order2;
        this.uni2 = uni2;
        this.major2 = major2;
        this.score2 = score2;
        this.advice = advice;
    }
    
    public Integer getOrder1() {
        return order1;
    }
    
    public void setOrder1(Integer order1) {
        this.order1 = order1;
    }
    
    public String getUni1() {
        return uni1;
    }
    
    public void setUni1(String uni1) {
        this.uni1 = uni1;
    }
    
    public String getMajor1() {
        return major1;
    }
    
    public void setMajor1(String major1) {
        this.major1 = major1;
    }
    
    public Integer getScore1() {
        return score1;
    }
    
    public void setScore1(Integer score1) {
        this.score1 = score1;
    }
    
    public Integer getOrder2() {
        return order2;
    }
    
    public void setOrder2(Integer order2) {
        this.order2 = order2;
    }
    
    public String getUni2() {
        return uni2;
    }
    
    public void setUni2(String uni2) {
        this.uni2 = uni2;
    }
    
    public String getMajor2() {
        return major2;
    }
    
    public void setMajor2(String major2) {
        this.major2 = major2;
    }
    
    public Integer getScore2() {
        return score2;
    }
    
    public void setScore2(Integer score2) {
        this.score2 = score2;
    }
    
    public String getAdvice() {
        return advice;
    }
    
    public void setAdvice(String advice) {
        this.advice = advice;
    }
} 