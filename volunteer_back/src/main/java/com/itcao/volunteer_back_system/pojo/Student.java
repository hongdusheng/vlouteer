package com.itcao.volunteer_back_system.pojo;

public class Student {
    private String studentId;
    private String studentName;
    private String idCard;
    private String gender;
    private float collegeEntranceExamScore;
    private int provinceId;
    private int ranking;
    private String createTime;
    private String provinceName;

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public float getCollegeEntranceExamScore() {
        return collegeEntranceExamScore;
    }

    public void setCollegeEntranceExamScore(float collegeEntranceExamScore) {
        this.collegeEntranceExamScore = collegeEntranceExamScore;
    }

    public int getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(int provinceId) {
        this.provinceId = provinceId;
    }

    public int getRanking() {
        return ranking;
    }

    public void setRanking(int ranking) {
        this.ranking = ranking;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    @Override
    public String toString() {
        return "Student{" +
                "studentId='" + studentId + '\'' +
                ", studentName='" + studentName + '\'' +
                ", idCard='" + idCard + '\'' +
                ", gender='" + gender + '\'' +
                ", collegeEntranceExamScore=" + collegeEntranceExamScore +
                ", provinceId=" + provinceId +
                ", ranking=" + ranking +
                ", createTime='" + createTime + '\'' +
                ", provinceName='" + provinceName + '\'' +
                '}';
    }
}
