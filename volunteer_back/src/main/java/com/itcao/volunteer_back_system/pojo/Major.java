package com.itcao.volunteer_back_system.pojo;

/**
 * 专业实体类
 */
public class Major {
    /**
     * 专业编号
     */
    private String majorId;

    /**
     * 专业名称
     */
    private String majorName;

    /**
     * 学科门类ID
     */
    private int disciplineCategoryId;

    /**
     * 学制
     */
    private int schoolingLength;

    /**
     * 学位类型
     */
    private String degreeType;

    /**
     * 学科门类名称
     */
    private String disciplineCategoryName;

    /**
     * 分数线
     */
    private float lineScore;

    /**
     * 大学ID
     */
    private String universityId;

    /**
     * 大学名称
     */
    private String universityName;

    /**
     * 创建时间
     */
    private String createTime;

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

    public int getDisciplineCategoryId() {
        return disciplineCategoryId;
    }

    public void setDisciplineCategoryId(int disciplineCategoryId) {
        this.disciplineCategoryId = disciplineCategoryId;
    }

    public int getSchoolingLength() {
        return schoolingLength;
    }

    public void setSchoolingLength(int schoolingLength) {
        this.schoolingLength = schoolingLength;
    }

    public String getDegreeType() {
        return degreeType;
    }

    public void setDegreeType(String degreeType) {
        this.degreeType = degreeType;
    }

    public String getDisciplineCategoryName() {
        return disciplineCategoryName;
    }

    public void setDisciplineCategoryName(String disciplineCategoryName) {
        this.disciplineCategoryName = disciplineCategoryName;
    }

    public float getLineScore() {
        return lineScore;
    }

    public void setLineScore(float lineScore) {
        this.lineScore = lineScore;
    }

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

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "Major{" +
                "majorId='" + majorId + '\'' +
                ", majorName='" + majorName + '\'' +
                ", disciplineCategoryId=" + disciplineCategoryId +
                ", schoolingLength=" + schoolingLength +
                ", degreeType='" + degreeType + '\'' +
                ", disciplineCategoryName='" + disciplineCategoryName + '\'' +
                ", lineScore=" + lineScore +
                ", universityId='" + universityId + '\'' +
                ", universityName='" + universityName + '\'' +
                ", createTime='" + createTime + '\'' +
                '}';
    }
}
