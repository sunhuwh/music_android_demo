package com.sunhuwh.music.util;

/**
 * Created by junxue.rao on 2016/2/23.
 * 常量值
 */
public enum Constants {

    /**
     * 当前用户
     */
    USER("USER"),

    /**
     * 班级名称
     */
    CLASS_NAME("CLASS_NAME"),

    /**
     * TOKEN
     */
    TOKEN("TOKEN"),

    /**
     * USERNAME
     */
    USERNAME("USERNAME"),

    /**
     * UID
     */
    UID("UID"),

    /**
     * DISTRIBUTOR_ID
     */
    DISTRIBUTOR_ID("DISTRIBUTOR_ID"),

    /**
     * DISTRIBUTOR_TITLE
     */
    DISTRIBUTOR_TITLE("DISTRIBUTOR_TITLE"),

    /**
     * SKIP_UPDATE
     */
    SKIP_UPDATE("SKIP_UPDATE");

    /**
     * 全局存储的key值
     */
    private String code;

    public String getCode() {
        return code;
    }

    private Constants(String code){
        this.code = code;
    }
}
