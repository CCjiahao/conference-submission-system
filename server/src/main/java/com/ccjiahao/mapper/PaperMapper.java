package com.ccjiahao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ccjiahao.dto.PaperReview;
import com.ccjiahao.entity.Paper;
import com.ccjiahao.entity.User;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
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

    public default List<Paper> selectPaperByState(String state) {
        HashMap<String, Object> queryMap = new HashMap<>();
        queryMap.put("state", state);
        return this.selectByMap(queryMap);
    }

    public default List<Paper> selectPaperByAuthor(String author) {
        List<Paper> papers = this.selectList(null);
        List<Paper> data = new ArrayList<Paper>();
        for (Paper paper: papers) {
            if(paper.isAuthor(author)){
                data.add(paper);
            }
        }
        return data;
    }

    @Select("SELECT paper.*,review.`reviewer` FROM paper,review WHERE paper.id=review.paper_id")
    public List<PaperReview> getPapersWithReviewer();

}
