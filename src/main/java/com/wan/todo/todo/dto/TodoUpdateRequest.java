package com.wan.todo.todo.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TodoUpdateRequest {

    @NotEmpty
    private String content;

    public TodoUpdateRequest(String content) {
        this.content = content;
    }
}
