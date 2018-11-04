package com.wan.todo.todo.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TodoCreateRequest {

    @NotEmpty
    public String content;

    public List<Long> refIds = new ArrayList<>();

    public TodoCreateRequest(String content, List<Long> refIds) {
        this.content = content;
        this.refIds = refIds;
    }
}
