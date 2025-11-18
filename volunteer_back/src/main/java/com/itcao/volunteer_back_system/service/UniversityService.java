package com.itcao.volunteer_back_system.service;

import com.itcao.volunteer_back_system.common.PageResult;
import com.itcao.volunteer_back_system.common.Result;
import com.itcao.volunteer_back_system.pojo.University;
import com.itcao.volunteer_back_system.pojo.UniversityType;

import java.util.List;

public interface UniversityService {

    Result<PageResult<University>> getUniversityPage(String schoolCode, String schoolName, String level,
            Integer typeId, String province, Integer pageNum, Integer pageSize);

    Result<University> getUniversityDetail(String id);

    Result<University> getUniversityByCode(String code);

    Result<Void> saveUniversity(University university);

    Result<Void> deleteUniversity(String ids);

    Result<List<UniversityType>> getUniversityTypes();
}
