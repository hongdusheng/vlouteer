package com.itcao.volunteer_back_system.mapper;

import com.itcao.volunteer_back_system.pojo.DisciplineCategory;
import com.itcao.volunteer_back_system.pojo.Major;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MajorMapper {

    /**
     * 分页查询专业列表
     * 
     * @param majorCode  专业代码
     * @param majorName  专业名称
     * @param schoolCode 学校代码
     * @param schoolName 学校名称
     * @param offset     偏移量
     * @param pageSize   每页大小
     * @return 专业列表
     */
    List<Major> getMajorPage(String majorCode, String majorName, String schoolCode,
            String schoolName, Integer offset, Integer pageSize);

    /**
     * 查询专业总数
     * 
     * @param majorCode  专业代码
     * @param majorName  专业名称
     * @param schoolCode 学校代码
     * @param schoolName 学校名称
     * @return 专业总数
     */
    Long getMajorCount(String majorCode, String majorName, String schoolCode, String schoolName);

    /**
     * 根据ID查询专业信息
     * 
     * @param id 专业ID
     * @return 专业信息
     */
    Major getMajorById(String id);

    /**
     * 根据编码查询专业信息
     * 
     * @param code 专业编码
     * @return 专业信息
     */
    Major getMajorByCode(String code);

    /**
     * 新增专业信息
     * 
     * @param major 专业信息
     * @return 影响行数
     */
    int insertMajor(Major major);

    /**
     * 更新专业信息
     * 
     * @param major 专业信息
     * @return 影响行数
     */
    int updateMajor(Major major);

    /**
     * 批量删除专业信息
     * 
     * @param ids 专业ID列表
     * @return 影响行数
     */
    int deleteMajors(List<String> ids);

    /**
     * 查询所有学科类别
     * 
     * @return 学科类别列表
     */
    List<DisciplineCategory> getDisciplineCategories();
}
