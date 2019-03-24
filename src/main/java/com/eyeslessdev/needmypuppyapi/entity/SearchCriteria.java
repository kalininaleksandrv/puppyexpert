package com.eyeslessdev.needmypuppyapi.entity;

import org.springframework.data.jpa.domain.Specification;

public class SearchCriteria {

    private String key;
    private String operation;
    private Object value;

    public SearchCriteria(String key, String operation, Object value) {
        this.key = key;
        this.operation = operation;
        this.value = value;
    }

    public SearchCriteria() {
    }

    String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    String getOperation() {
        return operation;
    }

    public void setOperation(String operations) {
        this.operation = operations;
    }


    Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }


    @Override
    public String toString() {
        return "SearchCriteria{" +
                "key='" + key + '\'' +
                ", operation='" + operation + '\'' +
                ", value=" + value +
                '}';
    }
}
