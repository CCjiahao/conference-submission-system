package com.ccjiahao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ccjiahao.entity.Paper;
import com.ccjiahao.entity.User;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
public interface PaperMapper extends BaseMapper<Paper> {
    public default Paper selectPaperById(String id) {
        HashMap<String, Object> queryMap = new HashMap<>();
        queryMap.put("id", id);
        List<Paper> papers = this.selectByMap(queryMap);
        if (papers.size() == 0) return null;
        else return papers.get(0);
    }
}
