package com.itcao.volunteer_back_system.service.impl;

import com.itcao.volunteer_back_system.common.PageResult;
import com.itcao.volunteer_back_system.common.Result;
import com.itcao.volunteer_back_system.mapper.UniversityMapper;
import com.itcao.volunteer_back_system.pojo.University;
import com.itcao.volunteer_back_system.pojo.UniversityType;
import com.itcao.volunteer_back_system.service.UniversityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UniversityServiceImpl implements UniversityService {

    @Autowired
    private UniversityMapper universityMapper;

    @Override
    public Result<PageResult<University>> getUniversityPage(String schoolCode, String schoolName, String level,
            Integer typeId, String province, Integer pageNum, Integer pageSize) {
        Integer offset = (pageNum - 1) * pageSize;
        List<University> universities = universityMapper.getUniversityPage(schoolCode, schoolName, level, typeId,
                province, offset, pageSize);
        Long total = universityMapper.getUniversityCount(schoolCode, schoolName, level, typeId, province);
        PageResult<University> pageResult = new PageResult<>(universities, total, pageSize, pageNum);
        return Result.success(pageResult);
    }

    @Override
    public Result<University> getUniversityDetail(String id) {
        University university = universityMapper.getUniversityById(id);
        if (university != null) {
            return Result.success(university);
        }
        return Result.error("院校不存在");
    }

    @Override
    public Result<University> getUniversityByCode(String code) {
        University university = universityMapper.getUniversityByCode(code);
        if (university != null) {
            return Result.success(university);
        }
        return Result.error("院校不存在");
    }

    @Override
    public Result<Void> saveUniversity(University university) {
        int result;
        if (university.getUniversityId() != null && !university.getUniversityId().isEmpty()) {
            // 更新院校
            result = universityMapper.updateUniversity(university);
        } else {
            // 新增院校
            result = universityMapper.insertUniversity(university);
        }

        if (result > 0) {
            return Result.success();
        }
        return Result.error("操作失败");
    }

    @Override
    public Result<Void> deleteUniversity(String ids) {
        List<String> idList = Arrays.asList(ids.split(","));
        int result = universityMapper.deleteUniversities(idList);
        if (result > 0) {
            return Result.success("删除成功");
        }
        return Result.error("删除失败");
    }

    @Override
    public Result<List<UniversityType>> getUniversityTypes() {
        List<UniversityType> types = universityMapper.getUniversityTypes();
        return Result.success(types);
    }
}