package com.ccjiahao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ccjiahao.entity.VerificationCode;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Repository
public interface VerificationCodeMapper extends BaseMapper<VerificationCode> {
    public default void deleteByEmail(String email) {
        HashMap<String, Object> queryMap = new HashMap<>();
        queryMap.put("email", email);
        this.deleteByMap(queryMap);
    }

    public default void deleteByEmailAndCode(String email, String code) {
        HashMap<String, Object> queryMap = new HashMap<>();
        queryMap.put("email", email);
        queryMap.put("code", code);
        this.deleteByMap(queryMap);
    }

    public default boolean selectByEmailAndCodeAndTime(String email, String code) {
        HashMap<String, Object> queryMap = new HashMap<>();
        queryMap.put("email", email);
        queryMap.put("code", code);
        List<VerificationCode> verificationCodes = this.selectByMap(queryMap);
        if(verificationCodes.size() == 0) return false;
        VerificationCode verificationCode = verificationCodes.get(0);
        if(verificationCode.getExpirationTime().compareTo(new Date(System.currentTimeMillis())) <= 0) {
            deleteByEmailAndCode(email, code);
            return false;
        }
        return true;
    }
}
