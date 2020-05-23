package com.pzb.webflux.demo.domain;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class TaskInfo {
    private int count = 0;
    private String message;
    private List<String> errors;
}
