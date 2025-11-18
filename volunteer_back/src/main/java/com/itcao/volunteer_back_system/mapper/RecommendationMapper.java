package com.itcao.volunteer_back_system.mapper;

import com.itcao.volunteer_back_system.Dto.RecommendationItem;
import com.itcao.volunteer_back_system.Dto.VolunteerItem;
import com.itcao.volunteer_back_system.Dto.ConflictItem;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 智能推荐数据访问层
 */
@Mapper
public interface RecommendationMapper {

        /**
         * 调用智能志愿推荐存储过程
         * 
         * @param studentId 学生ID
         * @return 推荐结果
         */
        List<Map<String, Object>> callFlexibleRecommendation(@Param("studentId") String studentId);

        /**
         * 调用志愿冲突检测存储过程
         * 
         * @param studentId 学生ID
         * @return 冲突检测结果
         */
        List<Map<String, Object>> callCheckVolunteerConflict(@Param("studentId") String studentId);

        /**
         * 调用录取概率预测存储过程
         * 
         * @param studentId    学生ID
         * @param universityId 院校ID
         * @param majorId      专业ID
         * @return 录取概率预测结果
         */
        List<Map<String, Object>> callPredictAdmissionProbability(
                        @Param("studentId") String studentId,
                        @Param("universityId") String universityId,
                        @Param("majorId") String majorId);

        /**
         * 调用专业就业前景分析存储过程
         * 
         * @param majorId 专业ID
         * @return 就业前景分析结果
         */
        List<Map<String, Object>> callAnalyzeMajorEmploymentProspects(@Param("majorId") String majorId);

        /**
         * 调用省份分数线分析存储过程
         * 
         * @param provinceId           省份ID
         * @param disciplineCategoryId 学科门类ID
         * @return 省份分数线分析结果
         */
        List<Map<String, Object>> callAnalyzeProvinceScoreTrends(
                        @Param("provinceId") Integer provinceId,
                        @Param("disciplineCategoryId") Integer disciplineCategoryId);

        /**
         * 调用志愿综合分析存储过程
         * 
         * @param studentId 学生ID
         * @return 志愿综合分析结果
         */
        List<Map<String, Object>> callAnalyzeStudentVolunteerStrategy(@Param("studentId") String studentId);

        /**
         * 调用院校专业匹配度分析存储过程
         * 
         * @param universityId 院校ID
         * @param majorId      专业ID
         * @return 院校专业匹配度分析结果
         */
        List<Map<String, Object>> callAnalyzeUniversityMajorMatch(
                        @Param("universityId") String universityId,
                        @Param("majorId") String majorId);

        /**
         * 获取学科门类列表
         * 
         * @return 学科门类列表
         */
        List<Map<String, Object>> getDisciplineCategories();
}