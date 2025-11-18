package com.itcao.volunteer_back_system.controller;

import com.itcao.volunteer_back_system.common.Result;
import com.itcao.volunteer_back_system.Dto.RecommendationResult;
import com.itcao.volunteer_back_system.Dto.ConflictResult;
import com.itcao.volunteer_back_system.Dto.ComprehensiveAnalysisResult;
import com.itcao.volunteer_back_system.Dto.UniversityMajorMatchResult;
import com.itcao.volunteer_back_system.service.RecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 智能推荐控制器
 */
@RestController
@RequestMapping("/api/recommendation")
public class RecommendationController {

    @Autowired
    private RecommendationService recommendationService;

    /**
     * 智能志愿推荐
     * 
     * @param studentId 学生ID
     * @return 推荐结果
     */
    @GetMapping("/volunteer")
    public Result<RecommendationResult> getVolunteerRecommendation(@RequestParam String studentId) {
        return recommendationService.getVolunteerRecommendation(studentId);
    }

    /**
     * 志愿冲突检测
     * 
     * @param studentId 学生ID
     * @return 冲突检测结果
     */
    @GetMapping("/conflict")
    public Result<ConflictResult> getVolunteerConflict(@RequestParam String studentId) {
        return recommendationService.getVolunteerConflict(studentId);
    }

    /**
     * 志愿综合分析
     * 
     * @param studentId 学生ID
     * @return 志愿综合分析结果
     */
    @GetMapping("/analysis")
    public Result<ComprehensiveAnalysisResult> getComprehensiveAnalysis(@RequestParam String studentId) {
        return recommendationService.getComprehensiveAnalysis(studentId);
    }

    /**
     * 院校专业匹配
     * 
     * @param universityId 院校ID
     * @param majorId      专业ID
     * @return 院校专业匹配结果
     */
    @GetMapping("/match")
    public Result<UniversityMajorMatchResult> getUniversityMajorMatch(
            @RequestParam String universityId,
            @RequestParam String majorId) {
        return recommendationService.getUniversityMajorMatch(universityId, majorId);
    }

    /**
     * 录取概率预测
     * 
     * @param studentId    学生ID
     * @param universityId 院校ID
     * @param majorId      专业ID
     * @return 录取概率预测结果
     */
    @GetMapping("/probability")
    public Result<Map<String, Object>> getAdmissionProbability(
            @RequestParam String studentId,
            @RequestParam String universityId,
            @RequestParam String majorId) {
        return recommendationService.getAdmissionProbability(studentId, universityId, majorId);
    }

    /**
     * 专业就业前景分析
     * 
     * @param majorId 专业ID
     * @return 就业前景分析结果
     */
    @GetMapping("/employment")
    public Result<Map<String, Object>> getEmploymentProspects(@RequestParam String majorId) {
        return recommendationService.getEmploymentProspects(majorId);
    }

    /**
     * 省份分数线分析
     * 
     * @param provinceId           省份ID
     * @param disciplineCategoryId 学科门类ID
     * @return 省份分数线分析结果
     */
    @GetMapping("/score-trends")
    public Result<Map<String, Object>> getProvinceScoreTrends(
            @RequestParam Integer provinceId,
            @RequestParam Integer disciplineCategoryId) {
        return recommendationService.getProvinceScoreTrends(provinceId, disciplineCategoryId);
    }

    /**
     * 获取学科门类列表
     * 
     * @return 学科门类列表
     */
    @GetMapping("/disciplines")
    public Result<Object> getDisciplineCategories() {
        return recommendationService.getDisciplineCategories();
    }
}