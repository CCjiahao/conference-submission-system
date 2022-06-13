package com.ccjiahao.dto;

import lombok.Data;
import org.springframework.web.bind.annotation.RequestParam;

@Data
public class Rebuttal {
    private String token;
    private String reviewer;
    private String paperId;
    private String content;
}
