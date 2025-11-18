package com.itcao.volunteer_back_system.service.impl;

import com.itcao.volunteer_back_system.common.Result;
import com.itcao.volunteer_back_system.Dto.RecommendationItem;
import com.itcao.volunteer_back_system.Dto.RecommendationResult;
import com.itcao.volunteer_back_system.Dto.VolunteerItem;
import com.itcao.volunteer_back_system.Dto.ConflictItem;
import com.itcao.volunteer_back_system.Dto.ConflictResult;
import com.itcao.volunteer_back_system.Dto.ComprehensiveAnalysisResult;
import com.itcao.volunteer_back_system.Dto.ComprehensiveVolunteerItem;
import com.itcao.volunteer_back_system.Dto.UniversityMajorMatchResult;
import com.itcao.volunteer_back_system.mapper.RecommendationMapper;
import com.itcao.volunteer_back_system.service.RecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 智能推荐服务实现类
 */
@Service
public class RecommendationServiceImpl implements RecommendationService {

    @Autowired
    private RecommendationMapper recommendationMapper;

    /**
     * 智能志愿推荐
     * 
     * @param studentId 学生ID
     * @return 推荐结果
     */
    @Override
    public Result<RecommendationResult> getVolunteerRecommendation(String studentId) {
        try {
            List<Map<String, Object>> results = recommendationMapper.callFlexibleRecommendation(studentId);

            if (results == null || results.isEmpty()) {
                return Result.error("未找到匹配的推荐结果");
            }

            Double studentScore = null;
            List<RecommendationItem> recommendations = new ArrayList<>();

            // 遍历所有结果，提取学生分数和推荐列表
            for (Map<String, Object> result : results) {
                if (result.containsKey("student_score")) {
                    studentScore = Double.valueOf(result.get("student_score").toString());
                } else if (result.containsKey("university_name") &&
                        result.containsKey("major_name") &&
                        result.containsKey("last_year_admission") &&
                        result.containsKey("score_difference")) {

                    // 创建推荐项实体对象
                    RecommendationItem item = new RecommendationItem(
                            (String) result.get("university_name"),
                            result.containsKey("university_id") ? (String) result.get("university_id") : null,
                            (String) result.get("major_name"),
                            result.containsKey("major_id") ? (String) result.get("major_id") : null,
                            Integer.valueOf(result.get("last_year_admission").toString()),
                            Integer.valueOf(result.get("score_difference").toString()));
                    recommendations.add(item);
                }
            }

            // 创建推荐结果实体对象
            RecommendationResult recommendationResult = new RecommendationResult(studentScore, recommendations);

            return Result.success(recommendationResult);
        } catch (Exception e) {
            return Result.error("获取志愿推荐失败: " + e.getMessage());
        }
    }

    /**
     * 志愿冲突检测
     * 
     * @param studentId 学生ID
     * @return 冲突检测结果
     */
    @Override
    public Result<ConflictResult> getVolunteerConflict(String studentId) {
        try {
            List<Map<String, Object>> results = recommendationMapper.callCheckVolunteerConflict(studentId);

            if (results == null || results.isEmpty()) {
                return Result.error("未找到志愿冲突检测结果");
            }

            Integer studentExists = null;
            Integer volunteerCount = null;
            Integer admissionCount = null;
            List<VolunteerItem> volunteers = new ArrayList<>();
            List<ConflictItem> conflicts = new ArrayList<>();

            // 处理调试信息
            for (Map<String, Object> result : results) {
                if (result.containsKey("debug_info")) {
                    String debugInfo = (String) result.get("debug_info");
                    if (debugInfo.startsWith("学生存在:")) {
                        studentExists = Integer.valueOf(debugInfo.substring(debugInfo.indexOf(":") + 1).trim());
                    } else if (debugInfo.startsWith("志愿数量:")) {
                        volunteerCount = Integer.valueOf(debugInfo.substring(debugInfo.indexOf(":") + 1).trim());
                    } else if (debugInfo.startsWith("匹配的历史录取数据:")) {
                        admissionCount = Integer.valueOf(debugInfo.substring(debugInfo.indexOf(":") + 1).trim());
                    }
                }
            }

            // 提取志愿列表
            for (Map<String, Object> result : results) {
                if (result.containsKey("volunteer_order") &&
                        result.containsKey("university_name") &&
                        result.containsKey("major_name")) {

                    VolunteerItem item = new VolunteerItem(
                            Integer.valueOf(result.get("volunteer_order").toString()),
                            (String) result.get("university_name"),
                            (String) result.get("major_name"),
                            result.containsKey("year") ? Integer.valueOf(result.get("year").toString()) : null,
                            result.containsKey("admission_number")
                                    ? Integer.valueOf(result.get("admission_number").toString())
                                    : null);
                    volunteers.add(item);
                }
            }

            // 提取冲突列表
            for (Map<String, Object> result : results) {
                if (result.containsKey("order1") &&
                        result.containsKey("uni1") &&
                        result.containsKey("advice")) {

                    ConflictItem item = new ConflictItem(
                            Integer.valueOf(result.get("order1").toString()),
                            (String) result.get("uni1"),
                            (String) result.get("major1"),
                            result.containsKey("score1") ? Integer.valueOf(result.get("score1").toString()) : null,
                            Integer.valueOf(result.get("order2").toString()),
                            (String) result.get("uni2"),
                            (String) result.get("major2"),
                            result.containsKey("score2") ? Integer.valueOf(result.get("score2").toString()) : null,
                            (String) result.get("advice"));
                    conflicts.add(item);
                }
            }

            // 创建冲突检测结果实体对象
            ConflictResult conflictResult = new ConflictResult(
                    studentExists,
                    volunteerCount,
                    admissionCount,
                    volunteers,
                    conflicts);

            return Result.success(conflictResult);
        } catch (Exception e) {
            return Result.error("获取志愿冲突检测失败: " + e.getMessage());
        }
    }

    /**
     * 志愿综合分析
     * 
     * @param studentId 学生ID
     * @return 志愿综合分析结果
     */
    @Override
    public Result<ComprehensiveAnalysisResult> getComprehensiveAnalysis(String studentId) {
        try {
            List<Map<String, Object>> results = recommendationMapper.callAnalyzeStudentVolunteerStrategy(studentId);

            if (results == null || results.isEmpty()) {
                return Result.error("未找到志愿综合分析结果");
            }

            ComprehensiveAnalysisResult analysisResult = new ComprehensiveAnalysisResult();
            List<ComprehensiveVolunteerItem> volunteers = new ArrayList<>();
            Map<String, Integer> riskDistribution = new HashMap<>();

            // 解析结果
            for (Map<String, Object> result : results) {
                // 解析学生基本信息
                if (result.containsKey("student_name")) {
                    analysisResult.setStudentName((String) result.get("student_name"));
                }
                if (result.containsKey("score")) {
                    analysisResult.setScore(Integer.valueOf(result.get("score").toString()));
                }
                if (result.containsKey("rank")) {
                    analysisResult.setRank(Integer.valueOf(result.get("rank").toString()));
                }
                if (result.containsKey("volunteer_count")) {
                    analysisResult.setVolunteerCount(Integer.valueOf(result.get("volunteer_count").toString()));
                }
                if (result.containsKey("overall_rating")) {
                    analysisResult.setOverallRating((String) result.get("overall_rating"));
                }
                if (result.containsKey("rationality_score")) {
                    analysisResult.setRationalityScore(Double.valueOf(result.get("rationality_score").toString()));
                }
                if (result.containsKey("safety_score")) {
                    analysisResult.setSafetyScore(Double.valueOf(result.get("safety_score").toString()));
                }
                if (result.containsKey("gradient_score")) {
                    analysisResult.setGradientScore(Double.valueOf(result.get("gradient_score").toString()));
                }
                if (result.containsKey("major_match_score")) {
                    analysisResult.setMajorMatchScore(Double.valueOf(result.get("major_match_score").toString()));
                }

                // 解析风险分析
                if (result.containsKey("risk_analysis")) {
                    analysisResult.setRiskAnalysis((String) result.get("risk_analysis"));
                }
                if (result.containsKey("gradient_analysis")) {
                    analysisResult.setGradientAnalysis((String) result.get("gradient_analysis"));
                }
                if (result.containsKey("major_match_analysis")) {
                    analysisResult.setMajorMatchAnalysis((String) result.get("major_match_analysis"));
                }
                if (result.containsKey("overall_suggestion")) {
                    analysisResult.setOverallSuggestion((String) result.get("overall_suggestion"));
                }

                // 解析风险分布
                if (result.containsKey("high")) {
                    riskDistribution.put("high", Integer.valueOf(result.get("high").toString()));
                }
                if (result.containsKey("medium")) {
                    riskDistribution.put("medium", Integer.valueOf(result.get("medium").toString()));
                }
                if (result.containsKey("low")) {
                    riskDistribution.put("low", Integer.valueOf(result.get("low").toString()));
                }

                // 解析志愿列表
                if (result.containsKey("order") &&
                        result.containsKey("university_name") &&
                        result.containsKey("major_name")) {

                    ComprehensiveVolunteerItem item = new ComprehensiveVolunteerItem();
                    item.setOrder(Integer.valueOf(result.get("order").toString()));
                    item.setUniversityName((String) result.get("university_name"));
                    item.setMajorName((String) result.get("major_name"));

                    if (result.containsKey("last_year_score")) {
                        item.setLastYearScore(Integer.valueOf(result.get("last_year_score").toString()));
                    }
                    if (result.containsKey("score_difference")) {
                        item.setScoreDifference(Integer.valueOf(result.get("score_difference").toString()));
                    }
                    if (result.containsKey("admission_probability")) {
                        item.setAdmissionProbability(Double.valueOf(result.get("admission_probability").toString()));
                    }
                    if (result.containsKey("risk_level")) {
                        item.setRiskLevel((String) result.get("risk_level"));
                    }

                    volunteers.add(item);
                }
            }

            analysisResult.setVolunteers(volunteers);
            analysisResult.setRiskDistribution(riskDistribution);

            return Result.success(analysisResult);
        } catch (Exception e) {
            return Result.error("获取志愿综合分析失败: " + e.getMessage());
        }
    }

    /**
     * 院校专业匹配
     * 
     * @param universityId 院校ID
     * @param majorId      专业ID
     * @return 院校专业匹配结果
     */
    @Override
    public Result<UniversityMajorMatchResult> getUniversityMajorMatch(String universityId, String majorId) {
        try {
            List<Map<String, Object>> results = recommendationMapper.callAnalyzeUniversityMajorMatch(universityId,
                    majorId);

            if (results == null || results.isEmpty()) {
                return Result.error("未找到院校专业匹配结果");
            }

            // 获取第一条结果
            Map<String, Object> result = results.get(0);
            UniversityMajorMatchResult matchResult = new UniversityMajorMatchResult();

            // 解析结果
            if (result.containsKey("university_name")) {
                matchResult.setUniversityName((String) result.get("university_name"));
            }
            if (result.containsKey("university_id")) {
                matchResult.setUniversityId((String) result.get("university_id"));
            }
            if (result.containsKey("university_level")) {
                matchResult.setUniversityLevel((String) result.get("university_level"));
            }
            if (result.containsKey("major_name")) {
                matchResult.setMajorName((String) result.get("major_name"));
            }
            if (result.containsKey("major_id")) {
                matchResult.setMajorId((String) result.get("major_id"));
            }
            if (result.containsKey("major_category")) {
                matchResult.setMajorCategory((String) result.get("major_category"));
            }
            if (result.containsKey("location")) {
                matchResult.setLocation((String) result.get("location"));
            }
            if (result.containsKey("admission_score")) {
                matchResult.setAdmissionScore(Integer.valueOf(result.get("admission_score").toString()));
            }
            if (result.containsKey("score_difference")) {
                matchResult.setScoreDifference(Integer.valueOf(result.get("score_difference").toString()));
            }
            if (result.containsKey("match_rate")) {
                matchResult.setMatchRate(Integer.valueOf(result.get("match_rate").toString()));
            }

            return Result.success(matchResult);
        } catch (Exception e) {
            return Result.error("获取院校专业匹配失败: " + e.getMessage());
        }
    }

    /**
     * 录取概率预测
     * 
     * @param studentId    学生ID
     * @param universityId 院校ID
     * @param majorId      专业ID
     * @return 录取概率预测结果
     */
    @Override
    public Result<Map<String, Object>> getAdmissionProbability(String studentId, String universityId, String majorId) {
        try {
            List<Map<String, Object>> results = recommendationMapper.callPredictAdmissionProbability(studentId,
                    universityId, majorId);

            if (results == null || results.isEmpty()) {
                return Result.error("未找到录取概率预测结果");
            }

            return Result.success(results.get(0));
        } catch (Exception e) {
            return Result.error("获取录取概率预测失败: " + e.getMessage());
        }
    }

    /**
     * 专业就业前景分析
     * 
     * @param majorId 专业ID
     * @return 就业前景分析结果
     */
    @Override
    public Result<Map<String, Object>> getEmploymentProspects(String majorId) {
        try {
            List<Map<String, Object>> results = recommendationMapper.callAnalyzeMajorEmploymentProspects(majorId);

            if (results == null || results.isEmpty()) {
                return Result.error("未找到专业就业前景分析结果");
            }

            // 检查是否返回了错误信息
            if (results.get(0).containsKey("message") &&
                    results.get(0).get("message").toString().startsWith("ERROR:")) {
                return Result.error(results.get(0).get("message").toString());
            }

            return Result.success(results.get(0));
        } catch (Exception e) {
            return Result.error("获取专业就业前景分析失败: " + e.getMessage());
        }
    }

    /**
     * 省份分数线分析
     * 
     * @param provinceId           省份ID
     * @param disciplineCategoryId 学科门类ID
     * @return 省份分数线分析结果
     */
    @Override
    public Result<Map<String, Object>> getProvinceScoreTrends(Integer provinceId, Integer disciplineCategoryId) {
        try {
            List<Map<String, Object>> results = recommendationMapper.callAnalyzeProvinceScoreTrends(provinceId,
                    disciplineCategoryId);

            if (results == null || results.isEmpty()) {
                return Result.error("未找到省份分数线分析结果");
            }

            // 检查是否返回了错误信息
            if (results.get(0).containsKey("message") &&
                    results.get(0).get("message").toString().startsWith("错误:")) {
                return Result.error(results.get(0).get("message").toString());
            }

            return Result.success(results.get(0));
        } catch (Exception e) {
            return Result.error("获取省份分数线分析失败: " + e.getMessage());
        }
    }

    /**
     * 获取学科门类列表
     * 
     * @return 学科门类列表
     */
    @Override
    public Result<Object> getDisciplineCategories() {
        try {
            List<Map<String, Object>> categories = recommendationMapper.getDisciplineCategories();

            if (categories == null || categories.isEmpty()) {
                return Result.error("未找到学科门类数据");
            }

            return Result.success(categories);
        } catch (Exception e) {
            return Result.error("获取学科门类列表失败: " + e.getMessage());
        }
    }
}