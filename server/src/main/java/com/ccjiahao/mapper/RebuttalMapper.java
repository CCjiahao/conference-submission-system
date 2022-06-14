package com.ccjiahao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ccjiahao.entity.Paper;
import com.ccjiahao.entity.Rebuttal;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
public interface RebuttalMapper extends BaseMapper<Rebuttal> {
    public default Rebuttal selectByPaperId(String paperId) {
        HashMap<String, Object> queryMap = new HashMap<>();
        queryMap.put("paper_id", paperId);
        List<Rebuttal> rebuttals = this.selectByMap(queryMap);
        if (rebuttals.size() == 0) return null;
        else return rebuttals.get(0);
    }
}
