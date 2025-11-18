package com.itcao.volunteer_back_system.service.impl;

import com.itcao.volunteer_back_system.Dto.ApplicationListDTO;
import com.itcao.volunteer_back_system.Dto.VolunteerDetailDTO;
import com.itcao.volunteer_back_system.Dto.VolunteerItemDTO;
import com.itcao.volunteer_back_system.common.PageResult;
import com.itcao.volunteer_back_system.common.Result;
import com.itcao.volunteer_back_system.mapper.ApplicationMapper;
import com.itcao.volunteer_back_system.mapper.StudentMapper;
import com.itcao.volunteer_back_system.pojo.Application;
import com.itcao.volunteer_back_system.pojo.Student;
import com.itcao.volunteer_back_system.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 志愿申请服务实现类
 */
@Service
public class ApplicationServiceImpl implements ApplicationService {

    @Autowired
    private ApplicationMapper applicationMapper;

    @Autowired
    private StudentMapper studentMapper;

    /**
     * 保存志愿信息
     * 
     * @param application 志愿信息
     * @return 操作结果
     */
    @Override
    public Result<Application> saveApplication(Application application) {
        // 先检查学生ID是否存在
        Student student = studentMapper.getStudentById(application.getStudentId());
        if (student == null) {
            return Result.error(404, "学生ID不存在，请先添加学生信息");
        }

        // 检查院校ID是否存在
        if (applicationMapper.checkUniversityExists(application.getUniversityId()) == 0) {
            return Result.error(404, "院校不存在");
        }

        // 检查专业ID是否存在
        if (applicationMapper.checkMajorExists(application.getMajorId()) == 0) {
            return Result.error(404, "专业不存在");
        }

        try {
            // 检查是否已存在相同学生ID和志愿顺序的记录
            Application existingApplication = applicationMapper.getApplicationByStudentIdAndOrder(
                    application.getStudentId(), application.getVolunteerOrder());

            if (existingApplication != null) {
                // 如果存在，则更新记录
                application.setRelationId(existingApplication.getRelationId());
                int result = applicationMapper.updateApplication(application);
                if (result > 0) {
                    // 获取更新后的完整记录
                    Application updatedApplication = applicationMapper.getApplicationById(application.getRelationId());
                    return Result.success("志愿更新成功", updatedApplication);
                }
                return Result.error("志愿更新失败");
            } else {
                // 如果不存在，则插入新记录
                int result = applicationMapper.insertApplication(application);
                if (result > 0) {
                    // 获取插入后的完整记录
                    Application insertedApplication = applicationMapper.getApplicationByStudentIdAndOrder(
                            application.getStudentId(), application.getVolunteerOrder());
                    return Result.success("志愿提交成功", insertedApplication);
                }
                return Result.error("志愿提交失败");
            }
        } catch (Exception e) {
            return Result.error("保存失败: " + e.getMessage());
        }
    }

    /**
     * 分页获取志愿信息列表
     * 
     * @param studentId   学生ID
     * @param studentName 学生姓名
     * @param province    省份
     * @param pageNum     当前页码
     * @param pageSize    每页大小
     * @return 志愿信息分页列表
     */
    @Override
    public Result<PageResult<ApplicationListDTO>> getApplicationList(String studentId, String studentName,
            String province, Integer pageNum, Integer pageSize) {
        Integer offset = (pageNum - 1) * pageSize;
        List<ApplicationListDTO> applications = applicationMapper.getApplicationManagePage(studentId, studentName,
                province, offset, pageSize);
        Long total = applicationMapper.getApplicationManageCount(studentId, studentName, province);
        PageResult<ApplicationListDTO> pageResult = new PageResult<>(applications, total, pageSize, pageNum);
        return Result.success(pageResult);
    }

    /**
     * 删除志愿信息
     * 
     * @param id 志愿ID
     * @return 操作结果
     */
    @Override
    public Result<Void> deleteApplication(Integer id) {
        int result = applicationMapper.deleteApplication(id);
        if (result > 0) {
            return Result.success("删除成功");
        }
        return Result.error("删除失败");
    }

    /**
     * 分页获取志愿管理列表
     * 
     * @param studentId   学生ID
     * @param studentName 学生姓名
     * @param province    省份
     * @param pageNum     当前页码
     * @param pageSize    每页大小
     * @return 志愿管理分页列表
     */
    @Override
    public Result<PageResult<ApplicationListDTO>> getApplicationManagePage(String studentId, String studentName,
            String province, Integer pageNum, Integer pageSize) {
        Integer offset = (pageNum - 1) * pageSize;
        List<ApplicationListDTO> applications = applicationMapper.getApplicationManagePage(studentId, studentName,
                province, offset, pageSize);
        Long total = applicationMapper.getApplicationManageCount(studentId, studentName, province);
        PageResult<ApplicationListDTO> pageResult = new PageResult<>(applications, total, pageSize, pageNum);
        return Result.success(pageResult);
    }

    /**
     * 获取志愿详情
     * 
     * @param studentId 学生ID
     * @return 志愿详情
     */
    @Override
    public Result<VolunteerDetailDTO> getVolunteerDetail(String studentId) {
        // 查询学生信息
        Student student = studentMapper.getStudentById(studentId);
        if (student == null) {
            return Result.error("学生信息不存在");
        }

        // 查询该学生的所有志愿信息
        List<Application> applications = applicationMapper.getApplicationsByStudentId(studentId);
        if (applications == null || applications.isEmpty()) {
            return Result.error("该学生未填报志愿");
        }

        // 构建志愿详情
        VolunteerDetailDTO detailDTO = new VolunteerDetailDTO();
        detailDTO.setStudentNo(student.getStudentId());
        detailDTO.setStudentName(student.getStudentName());
        detailDTO.setProvince(student.getProvinceName());
        detailDTO.setCreateTime(applications.get(0).getCreateTime());

        // 按志愿顺序分类
        for (Application app : applications) {
            VolunteerItemDTO itemDTO = new VolunteerItemDTO();
            itemDTO.setUniversityId(app.getUniversityId());
            itemDTO.setUniversityName(app.getUniversityName());
            itemDTO.setMajorId(app.getMajorId());
            itemDTO.setMajorName(app.getMajorName());

            // 根据志愿顺序设置
            if (app.getVolunteerOrder() == 1) {
                detailDTO.setFirstVolunteer(itemDTO);
            } else if (app.getVolunteerOrder() == 2) {
                detailDTO.setSecondVolunteer(itemDTO);
            } else if (app.getVolunteerOrder() == 3) {
                detailDTO.setThirdVolunteer(itemDTO);
            }
        }

        return Result.success(detailDTO);
    }
}