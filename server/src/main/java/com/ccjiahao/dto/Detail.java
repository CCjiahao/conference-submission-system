package com.ccjiahao.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
public class Detail {
    private String uuid;
    private String title;
    private String abstracts;
    private String expertise;
    private Date commitTime;
    private String state;
    private List<Author> authors;
}
