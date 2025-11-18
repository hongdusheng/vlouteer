package com.itcao.volunteer_back_system.pojo;

/**
 * 志愿申请实体类
 */
public class Application {
    /**
     * 关系ID
     */
    private Integer relationId;

    /**
     * 学生ID
     */
    private String studentId;

    /**
     * 大学ID
     */
    private String universityId;

    /**
     * 专业ID
     */
    private String majorId;

    /**
     * 志愿顺序
     */
    private Integer volunteerOrder;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 大学名称
     */
    private String universityName;

    /**
     * 专业名称
     */
    private String majorName;

    public Integer getRelationId() {
        return relationId;
    }

    public void setRelationId(Integer relationId) {
        this.relationId = relationId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getUniversityId() {
        return universityId;
    }

    public void setUniversityId(String universityId) {
        this.universityId = universityId;
    }

    public String getMajorId() {
        return majorId;
    }

    public void setMajorId(String majorId) {
        this.majorId = majorId;
    }

    public Integer getVolunteerOrder() {
        return volunteerOrder;
    }

    public void setVolunteerOrder(Integer volunteerOrder) {
        this.volunteerOrder = volunteerOrder;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
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

    @Override
    public String toString() {
        return "Application{" +
                "relationId=" + relationId +
                ", studentId='" + studentId + '\'' +
                ", universityId='" + universityId + '\'' +
                ", majorId='" + majorId + '\'' +
                ", volunteerOrder=" + volunteerOrder +
                ", createTime='" + createTime + '\'' +
                ", universityName='" + universityName + '\'' +
                ", majorName='" + majorName + '\'' +
                '}';
    }
}