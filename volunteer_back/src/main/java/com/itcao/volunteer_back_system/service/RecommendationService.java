package com.itcao.volunteer_back_system.service;

import com.itcao.volunteer_back_system.common.Result;
import com.itcao.volunteer_back_system.Dto.RecommendationResult;
import com.itcao.volunteer_back_system.Dto.ConflictResult;
import com.itcao.volunteer_back_system.Dto.ComprehensiveAnalysisResult;
import com.itcao.volunteer_back_system.Dto.UniversityMajorMatchResult;

import java.util.List;
import java.util.Map;

/**
 * 智能推荐服务接口
 */
public interface RecommendationService {

    /**
     * 智能志愿推荐
     * 
     * @param studentId 学生ID
     * @return 推荐结果
     */
    Result<RecommendationResult> getVolunteerRecommendation(String studentId);

    /**
     * 志愿冲突检测
     * 
     * @param studentId 学生ID
     * @return 冲突检测结果
     */
    Result<ConflictResult> getVolunteerConflict(String studentId);

    /**
     * 志愿综合分析
     * 
     * @param studentId 学生ID
     * @return 志愿综合分析结果
     */
    Result<ComprehensiveAnalysisResult> getComprehensiveAnalysis(String studentId);

    /**
     * 院校专业匹配
     * 
     * @param universityId 院校ID
     * @param majorId 专业ID
     * @return 院校专业匹配结果
     */
    Result<UniversityMajorMatchResult> getUniversityMajorMatch(String universityId, String majorId);

    /**
     * 录取概率预测
     * 
     * @param studentId    学生ID
     * @param universityId 院校ID
     * @param majorId      专业ID
     * @return 录取概率预测结果
     */
    Result<Map<String, Object>> getAdmissionProbability(String studentId, String universityId, String majorId);

    /**
     * 专业就业前景分析
     * 
     * @param majorId 专业ID
     * @return 就业前景分析结果
     */
    Result<Map<String, Object>> getEmploymentProspects(String majorId);

    /**
     * 省份分数线分析
     * 
     * @param provinceId           省份ID
     * @param disciplineCategoryId 学科门类ID
     * @return 省份分数线分析结果
     */
    Result<Map<String, Object>> getProvinceScoreTrends(Integer provinceId, Integer disciplineCategoryId);

    /**
     * 获取学科门类列表
     * 
     * @return 学科门类列表
     */
    Result<Object> getDisciplineCategories();
}