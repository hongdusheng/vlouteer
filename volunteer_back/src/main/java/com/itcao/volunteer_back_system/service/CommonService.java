package com.itcao.volunteer_back_system.service;

import com.itcao.volunteer_back_system.common.Result;
import com.itcao.volunteer_back_system.pojo.Province;

import java.util.List;

public interface CommonService {

    Result<List<Province>> getAllProvinces();
}