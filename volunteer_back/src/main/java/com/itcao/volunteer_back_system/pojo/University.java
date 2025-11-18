package com.itcao.volunteer_back_system.pojo;

public class University {
    private String universityId;
    private String universityName;
    private int provinceId;
    private int typeId;
    private String level;
    private String province;
    private int ranking;
    private String createTime;
    private Integer enrollmentNumber;

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

    public int getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(int provinceId) {
        this.provinceId = provinceId;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
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

    public Integer getEnrollmentNumber() {
        return enrollmentNumber;
    }

    public void setEnrollmentNumber(Integer enrollmentNumber) {
        this.enrollmentNumber = enrollmentNumber;
    }

    @Override
    public String toString() {
        return "University{" +
                "universityId='" + universityId + '\'' +
                ", universityName='" + universityName + '\'' +
                ", provinceId=" + provinceId +
                ", typeId=" + typeId +
                ", level='" + level + '\'' +
                ", province='" + province + '\'' +
                ", ranking=" + ranking +
                ", createTime='" + createTime + '\'' +
                ", enrollmentCount=" + enrollmentNumber +
                '}';
    }
}
