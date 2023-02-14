package com.algaworks.algafood.api.exceptionhandler;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Builder
public class Problem {

    private Integer status;
    private String type;
    private String title;
    private String detail;


    private String userMessage;
    private LocalDate timeStamp;
    private List<Object> objects;

    @Getter
    @Builder
    public static class Objects {
        private String name;
        private String userMessage;
    }
}