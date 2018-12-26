package com.chan.annotation;

import java.io.Serializable;


public enum Role implements Serializable {


    USER(11, "USER"),
    ADMIN(12, "ADMIN");

    private Integer code;
    private String value;

    private Role() {
    }

    private Role(Integer code, String value) {
        this.code = code;
        this.value = value;
    }

    @Override
    public String toString() {
        return this.code + this.value;
    }


    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Object getValue() {
        return value;
    }

}
