package com.eyeslessdev.needmypuppyapi.entity.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Set;

@JsonIgnoreProperties (ignoreUnknown = true)
public class CaptchaResponseDto {
    private boolean sucsess;

    @JsonAlias("error-codes")
    private Set<String> errorCodes;

    @JsonProperty("success")
    public boolean isSucsess() {
        return sucsess;
    }

    public void setSucsess(boolean sucsess) {
        this.sucsess = sucsess;
    }

    public Set<String> getErrorCodes() {
        return errorCodes;
    }

    public void setErrorCodes(Set<String> errorCodes) {
        this.errorCodes = errorCodes;
    }
}
