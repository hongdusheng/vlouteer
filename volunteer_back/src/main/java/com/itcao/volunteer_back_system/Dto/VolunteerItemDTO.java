package com.itcao.volunteer_back_system.Dto;

/**
 * 志愿项DTO
 * 用于存储单个志愿信息
 */
public class VolunteerItemDTO {
    /**
     * 院校代码
     */
    private String universityId;

    /**
     * 院校名称
     */
    private String universityName;

    /**
     * 专业代码
     */
    private String majorId;

    /**
     * 专业名称
     */
    private String majorName;

    public String getUniversityId() {
        return universityId;
    }

    public void setUniversityId(String universityId) {
        this.universityId = universityId;
    }

    public String getUniversityName() {
        return universityName;
    }

    public void setUniversityName(String universityName) {
        this.universityName = universityName;
    }

    public String getMajorId() {
        return majorId;
    }

    public void setMajorId(String majorId) {
        this.majorId = majorId;
    }

    public String getMajorName() {
        return majorName;
    }

    public void setMajorName(String majorName) {
        this.majorName = majorName;
    }
}