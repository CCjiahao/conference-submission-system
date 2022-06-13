package com.ccjiahao.dto;

import lombok.Data;

@Data
public class Forget {
    private String username;
    private String password;
    private String email;
    private String code;
}
