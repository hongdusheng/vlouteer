/**
 * 志愿推荐系统模拟数据
 * 包含四个接口：志愿推荐、冲突检测、志愿综合分析、院校专业匹配
 */

// 志愿推荐模拟数据
export const volunteerRecommendationData = {
    code: 200,
    message: "操作成功",
    data: {
        student_score: 650,
        recommendations: [
            {
                university_name: "清华大学",
                university_id: "C001",
                major_name: "计算机科学与技术",
                major_id: "M001",
                last_year_admission: 680,
                score_difference: -30
            },
            {
                university_name: "北京大学",
                university_id: "C002",
                major_name: "软件工程",
                major_id: "M002",
                last_year_admission: 675,
                score_difference: -25
            },
            {
                university_name: "浙江大学",
                university_id: "C003",
                major_name: "人工智能",
                major_id: "M003",
                last_year_admission: 660,
                score_difference: -10
            },
            {
                university_name: "复旦大学",
                university_id: "C004",
                major_name: "数据科学与大数据技术",
                major_id: "M004",
                last_year_admission: 655,
                score_difference: -5
            },
            {
                university_name: "南京大学",
                university_id: "C005",
                major_name: "信息安全",
                major_id: "M005",
                last_year_admission: 645,
                score_difference: 5
            }
        ]
    }
};

// 冲突检测模拟数据
export const volunteerConflictData = {
    code: 200,
    message: "操作成功",
    data: {
        student_exists: 1,
        volunteer_count: 3,
        admission_count: 15,
        volunteers: [
            {
                volunteer_order: 1,
                university_name: "清华大学",
                major_name: "计算机科学与技术",
                year: 2022,
                admission_number: 680
            },
            {
                volunteer_order: 2,
                university_name: "北京大学",
                major_name: "软件工程",
                year: 2022,
                admission_number: 675
            },
            {
                volunteer_order: 3,
                university_name: "复旦大学",
                major_name: "数据科学与大数据技术",
                year: 2022,
                admission_number: 655
            }
        ],
        conflicts: [
            {
                order1: 1,
                uni1: "清华大学",
                major1: "计算机科学与技术",
                score1: 680,
                order2: 2,
                uni2: "北京大学",
                major2: "软件工程",
                score2: 675,
                advice: "梯度不足提示：两所院校分数线相差仅5分，建议增加梯度差异"
            }
        ]
    }
};

// 志愿综合分析模拟数据
export const volunteerComprehensiveData = {
    code: 200,
    message: "操作成功",
    data: {
        student_name: "张三",
        score: 650,
        rank: 5000,
        volunteer_count: 3,
        overall_rating: "良好",
        rationality_score: 4,
        safety_score: 3.5,
        gradient_score: 4.5,
        major_match_score: 4,
        volunteers: [
            {
                order: 1,
                university_name: "清华大学",
                major_name: "计算机科学与技术",
                last_year_score: 680,
                score_difference: -30,
                admission_probability: 75,
                risk_level: "中风险"
            },
            {
                order: 2,
                university_name: "北京大学",
                major_name: "软件工程",
                last_year_score: 675,
                score_difference: -25,
                admission_probability: 80,
                risk_level: "中风险"
            },
            {
                order: 3,
                university_name: "复旦大学",
                major_name: "数据科学与大数据技术",
                last_year_score: 655,
                score_difference: -5,
                admission_probability: 95,
                risk_level: "低风险"
            }
        ],
        risk_distribution: {
            high: 0,
            medium: 67,
            low: 33
        },
        risk_analysis: "您的志愿填报风险分布较为合理，中风险和低风险志愿比例适当，但缺少冲刺性志愿，可考虑适当增加一个高风险志愿以提高录取院校的质量上限。",
        gradient_analysis: "志愿之间的分数梯度合理，相邻志愿之间分数差异适中，有利于提高录取成功率。第一、二志愿梯度略小，可以考虑调整。",
        major_match_analysis: "所选专业均为计算机相关领域，专业方向一致性高，与您的兴趣和能力匹配度较高，有利于未来的学习和发展。",
        overall_suggestion: "总体来看，您的志愿填报策略较为稳健，建议可以考虑增加1个冲刺志愿，同时保持当前的梯度分布，进一步优化志愿组合。"
    }
};

// 院校专业匹配模拟数据
export const universityMajorMatchData = {
    code: 200,
    message: "操作成功",
    data: [
        {
            university_name: "清华大学",
            university_id: "C001",
            university_level: "985",
            major_name: "计算机科学与技术",
            major_id: "M001",
            major_category: "工学",
            location: "北京",
            admission_score: 680,
            score_difference: -30,
            match_rate: 92
        },
        {
            university_name: "北京大学",
            university_id: "C002",
            university_level: "985",
            major_name: "软件工程",
            major_id: "M002",
            major_category: "工学",
            location: "北京",
            admission_score: 675,
            score_difference: -25,
            match_rate: 90
        },
        {
            university_name: "浙江大学",
            university_id: "C003",
            university_level: "985",
            major_name: "人工智能",
            major_id: "M003",
            major_category: "工学",
            location: "浙江",
            admission_score: 660,
            score_difference: -10,
            match_rate: 88
        },
        {
            university_name: "复旦大学",
            university_id: "C004",
            university_level: "985",
            major_name: "数据科学与大数据技术",
            major_id: "M004",
            major_category: "工学",
            location: "上海",
            admission_score: 655,
            score_difference: -5,
            match_rate: 85
        },
        {
            university_name: "南京大学",
            university_id: "C005",
            university_level: "985",
            major_name: "信息安全",
            major_id: "M005",
            major_category: "工学",
            location: "江苏",
            admission_score: 645,
            score_difference: 5,
            match_rate: 83
        },
        {
            university_name: "上海交通大学",
            university_id: "C006",
            university_level: "985",
            major_name: "计算机科学与技术",
            major_id: "M006",
            major_category: "工学",
            location: "上海",
            admission_score: 670,
            score_difference: -20,
            match_rate: 82
        },
        {
            university_name: "中国科学技术大学",
            university_id: "C007",
            university_level: "985",
            major_name: "计算机科学与技术",
            major_id: "M007",
            major_category: "工学",
            location: "安徽",
            admission_score: 665,
            score_difference: -15,
            match_rate: 80
        },
        {
            university_name: "北京航空航天大学",
            university_id: "C008",
            university_level: "985",
            major_name: "软件工程",
            major_id: "M008",
            major_category: "工学",
            location: "北京",
            admission_score: 650,
            score_difference: 0,
            match_rate: 78
        },
        {
            university_name: "华中科技大学",
            university_id: "C009",
            university_level: "985",
            major_name: "计算机科学与技术",
            major_id: "M009",
            major_category: "工学",
            location: "湖北",
            admission_score: 640,
            score_difference: 10,
            match_rate: 75
        },
        {
            university_name: "武汉大学",
            university_id: "C010",
            university_level: "985",
            major_name: "软件工程",
            major_id: "M010",
            major_category: "工学",
            location: "湖北",
            admission_score: 635,
            score_difference: 15,
            match_rate: 72
        }
    ]
};

// 省份分数线分析模拟数据
export const provinceScoreData = {
    code: 200,
    message: "操作成功",
    data: {
        province_id: "1",
        province_name: "北京市",
        region: "华北",
        category_id: "8",
        category_name: "工学",
        min_score_current_year: 520,
        min_score_last_year: 510,
        min_score_two_years_ago: 505,
        avg_score_current_year: 580,
        avg_score_last_year: 575,
        avg_score_two_years_ago: 570,
        max_score_current_year: 680,
        max_score_last_year: 675,
        max_score_two_years_ago: 670,
        score_trend_analysis: "分数线呈上升趋势，竞争日益激烈。近三年来，该省份该学科门类的分数线整体呈现稳步上升态势，反映了报考人数增加和竞争加剧的现状。",
        popular_universities: "北京大学、清华大学、北京航空航天大学、北京理工大学、北京师范大学",
        admission_difficulty: "高",
        recommendation: "建议关注一流大学和特色学科建设高校，合理设置冲刺、稳妥和保底志愿，提前了解各高校在本省的招生计划和历年录取情况。"
    }
};

// 学科门类模拟数据
export const disciplineCategoriesData = {
    code: 200,
    message: "操作成功",
    data: [
        { id: 1, name: "哲学" },
        { id: 2, name: "经济学" },
        { id: 3, name: "法学" },
        { id: 4, name: "教育学" },
        { id: 5, name: "文学" },
        { id: 6, name: "历史学" },
        { id: 7, name: "理学" },
        { id: 8, name: "工学" },
        { id: 9, name: "农学" },
        { id: 10, name: "医学" },
        { id: 11, name: "管理学" },
        { id: 12, name: "艺术学" }
    ]
}; 