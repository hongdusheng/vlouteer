package com.itcao.volunteer_back_system.mapper;

import com.itcao.volunteer_back_system.pojo.Province;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CommonMapper {

    @Select("SELECT * FROM province")
    List<Province> getAllProvinces();
}