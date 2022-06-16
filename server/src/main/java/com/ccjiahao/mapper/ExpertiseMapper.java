package com.ccjiahao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ccjiahao.entity.Expertise;
import com.ccjiahao.entity.Paper;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
public interface ExpertiseMapper extends BaseMapper<Expertise> {
    public default Expertise selectByName(String name) {
        HashMap<String, Object> queryMap = new HashMap<>();
        queryMap.put("name", name);
        List<Expertise> expertises = this.selectByMap(queryMap);
        if (expertises.size() == 0) return null;
        else return expertises.get(0);
    }
}
