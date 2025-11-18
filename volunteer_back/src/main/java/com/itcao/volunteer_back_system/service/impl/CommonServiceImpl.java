package com.itcao.volunteer_back_system.service.impl;

import com.itcao.volunteer_back_system.common.Result;
import com.itcao.volunteer_back_system.mapper.CommonMapper;
import com.itcao.volunteer_back_system.pojo.Province;
import com.itcao.volunteer_back_system.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommonServiceImpl implements CommonService {

    @Autowired
    private CommonMapper commonMapper;

    @Override
    public Result<List<Province>> getAllProvinces() {
        List<Province> provinces = commonMapper.getAllProvinces();
        return Result.success(provinces);
    }
}