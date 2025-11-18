package com.itcao.volunteer_back_system.Dto;

public class VolunteerInfoDTO {
    private Integer volunteer_order;
    private String university_name;
    private String major_name;

    public Integer getVolunteer_order() {
        return volunteer_order;
    }

    public void setVolunteer_order(Integer volunteer_order) {
        this.volunteer_order = volunteer_order;
    }

    public String getUniversity_name() {
        return university_name;
    }

    public void setUniversity_name(String university_name) {
        this.university_name = university_name;
    }

    public String getMajor_name() {
        return major_name;
    }

    public void setMajor_name(String major_name) {
        this.major_name = major_name;
    }
}
