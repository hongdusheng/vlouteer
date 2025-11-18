package com.itcao.volunteer_back_system.Dto;

import java.io.Serializable;
import java.util.List;

/**
 * 志愿冲突检测结果实体类
 */
public class ConflictResult implements Serializable {

    private Integer studentExists; // 学生是否存在
    private Integer volunteerCount; // 志愿数量
    private Integer admissionCount; // 录取数据数量
    private List<VolunteerItem> volunteers; // 志愿列表
    private List<ConflictItem> conflicts; // 冲突列表

    public ConflictResult() {
    }

    public ConflictResult(Integer studentExists, Integer volunteerCount, Integer admissionCount,
            List<VolunteerItem> volunteers, List<ConflictItem> conflicts) {
        this.studentExists = studentExists;
        this.volunteerCount = volunteerCount;
        this.admissionCount = admissionCount;
        this.volunteers = volunteers;
        this.conflicts = conflicts;
    }

    public Integer getStudentExists() {
        return studentExists;
    }

    public void setStudentExists(Integer studentExists) {
        this.studentExists = studentExists;
    }

    public Integer getVolunteerCount() {
        return volunteerCount;
    }

    public void setVolunteerCount(Integer volunteerCount) {
        this.volunteerCount = volunteerCount;
    }

    public Integer getAdmissionCount() {
        return admissionCount;
    }

    public void setAdmissionCount(Integer admissionCount) {
        this.admissionCount = admissionCount;
    }

    public List<VolunteerItem> getVolunteers() {
        return volunteers;
    }

    public void setVolunteers(List<VolunteerItem> volunteers) {
        this.volunteers = volunteers;
    }

    public List<ConflictItem> getConflicts() {
        return conflicts;
    }

    public void setConflicts(List<ConflictItem> conflicts) {
        this.conflicts = conflicts;
    }
}