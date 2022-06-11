package com.ccjiahao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ccjiahao.entity.Paper;
import com.ccjiahao.entity.Review;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
@Repository
public interface ReviewMapper extends BaseMapper<Review> {
    public default Review selectReviewById(String id) {
        HashMap<String, Object> queryMap = new HashMap<>();
        queryMap.put("id", id);
        List<Review> reviews = this.selectByMap(queryMap);
        if (reviews.size() == 0) return null;
        else return reviews.get(0);
    }
}
