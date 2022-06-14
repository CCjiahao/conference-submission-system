package com.ccjiahao.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;
import java.util.Date;

public class PaperReview implements Serializable {

    @TableId(value = "id", type = IdType.AUTO)
    private int id;

    @TableField("paper_id")
    private int paperId;

    private String username;
    private String title;
    private String abstracts;
    private String expertise;
    private String collaborators;
    private String paper;
    private Date commitTime;
    private String state;
    private String reviewer;
}