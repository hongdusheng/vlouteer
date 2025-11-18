package com.itcao.volunteer_back_system.service;

import com.itcao.volunteer_back_system.Dto.VolunteerInfoDTO;
import com.itcao.volunteer_back_system.mapper.VolunteerRelationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VolunteerRelationService {

    @Autowired
    private VolunteerRelationMapper volunteerRelationMapper;

    public List<VolunteerInfoDTO> getStudentVolunteers(String studentId)
    {
        return volunteerRelationMapper.getStudentVolunteers(studentId);
    }
}
