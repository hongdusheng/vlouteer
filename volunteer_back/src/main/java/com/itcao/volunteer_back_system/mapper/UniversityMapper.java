package com.itcao.volunteer_back_system.mapper;

import com.itcao.volunteer_back_system.pojo.University;
import com.itcao.volunteer_back_system.pojo.UniversityType;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UniversityMapper {
        /**
         * 查询所有大学
         * 
         * @return 大学列表
         */
        List<University> getAllUniversities();

        /**
         * 根据ID查询大学信息
         * 
         * @param id 大学ID
         * @return 大学信息
         */
        University getUniversityById(String id);

        /**
         * 查询某个省份的大学
         * 
         * @param provinceId 省份ID
         * @return 大学列表
         */
        List<University> getUniversitiesByProvinceId(int provinceId);

        /**
         * 查询某个类型的大学
         * 
         * @param typeId 类型ID
         * @return 大学列表
         */
        List<University> getUniversitiesByTypeId(int typeId);

        /**
         * 模糊查询大学
         * 
         * @param name 大学名称
         * @return 大学列表
         */
        List<University> searchUniversitiesByName(String name);

        /**
         * 删除大学
         * 
         * @param id 大学ID
         * @return 影响行数
         */
        int deleteUniversityById(String id);

        /**
         * 分页查询大学列表
         * 
         * @param schoolCode 学校代码
         * @param schoolName 学校名称
         * @param level      学校层次
         * @param typeId     类型ID
         * @param province   省份名称
         * @param offset     偏移量
         * @param pageSize   每页大小
         * @return 大学列表
         */
        List<University> getUniversityPage(String schoolCode, String schoolName, String level,
                        Integer typeId, String province, Integer offset, Integer pageSize);

        /**
         * 查询大学总数
         * 
         * @param schoolCode 学校代码
         * @param schoolName 学校名称
         * @param level      学校层次
         * @param typeId     类型ID
         * @param province   省份名称
         * @return 大学总数
         */
        Long getUniversityCount(String schoolCode, String schoolName, String level,
                        Integer typeId, String province);

        /**
         * 根据编码查询大学
         * 
         * @param code 大学编码
         * @return 大学信息
         */
        University getUniversityByCode(String code);

        /**
         * 新增大学
         * 
         * @param university 大学信息
         * @return 影响行数
         */
        int insertUniversity(University university);

        /**
         * 更新大学
         * 
         * @param university 大学信息
         * @return 影响行数
         */
        int updateUniversity(University university);

        /**
         * 批量删除大学
         * 
         * @param ids 大学ID列表
         * @return 影响行数
         */
        int deleteUniversities(List<String> ids);

        /**
         * 查询所有大学类型
         * 
         * @return 大学类型列表
         */
        List<UniversityType> getUniversityTypes();
}
