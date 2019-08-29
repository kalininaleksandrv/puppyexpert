package com.eyeslessdev.needmypuppyapi.entity.dto;

import java.io.Serializable;

public class MessageMap implements Serializable {
    private String key;
    private Integer[] value;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MessageMap that = (MessageMap) o;

        return getKey() != null ? getKey().equals(that.getKey()) : that.getKey() == null;
    }

    @Override
    public int hashCode() {
        return getKey() != null ? getKey().hashCode() : 0;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Integer[] getValue() {
        return value;
    }

    public void setValue(Integer[] value) {
        this.value = value;
    }
}
