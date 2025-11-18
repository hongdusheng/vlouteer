package com.itcao.volunteer_back_system.controller;

import com.itcao.volunteer_back_system.common.Result;
import com.itcao.volunteer_back_system.pojo.Province;
import com.itcao.volunteer_back_system.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/common")
public class CommonController {

    @Autowired
    private CommonService commonService;

    @GetMapping("/provinces")
    public Result<List<Province>> getAllProvinces() {
        return commonService.getAllProvinces();
    }
}