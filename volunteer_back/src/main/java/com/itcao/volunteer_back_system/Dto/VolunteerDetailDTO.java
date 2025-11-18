package com.itcao.volunteer_back_system.Dto;

/**
 * 志愿详情DTO
 * 用于前端展示志愿详情
 */
public class VolunteerDetailDTO {
    /**
     * 学号
     */
    private String studentNo;

    /**
     * 学生姓名
     */
    private String studentName;

    /**
     * 省份
     */
    private String province;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 第一志愿
     */
    private VolunteerItemDTO firstVolunteer;

    /**
     * 第二志愿
     */
    private VolunteerItemDTO secondVolunteer;

    /**
     * 第三志愿
     */
    private VolunteerItemDTO thirdVolunteer;

    public String getStudentNo() {
        return studentNo;
    }

    public void setStudentNo(String studentNo) {
        this.studentNo = studentNo;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public VolunteerItemDTO getFirstVolunteer() {
        return firstVolunteer;
    }

    public void setFirstVolunteer(VolunteerItemDTO firstVolunteer) {
        this.firstVolunteer = firstVolunteer;
    }

    public VolunteerItemDTO getSecondVolunteer() {
        return secondVolunteer;
    }

    public void setSecondVolunteer(VolunteerItemDTO secondVolunteer) {
        this.secondVolunteer = secondVolunteer;
    }

    public VolunteerItemDTO getThirdVolunteer() {
        return thirdVolunteer;
    }

    public void setThirdVolunteer(VolunteerItemDTO thirdVolunteer) {
        this.thirdVolunteer = thirdVolunteer;
    }
}