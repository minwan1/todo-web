package com.wan.todo.todo.dto;

import com.wan.todo.todo.Todo;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TodoResponse {

    private long id;
    private String content;
    private String originContent;
    private boolean complete;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    public TodoResponse(final Todo todo) {
        this.id = todo.getId();
        this.content = todo.getFullContent();
        this.originContent = todo.getContent();
        this.complete = todo.isComplete();
        this.createdAt = todo.getCreatedAt();
        this.updatedAt = todo.getUpdatedAt();
    }

    public static List<TodoResponse> valueOf(List<Todo> content) {
        return content.stream()
                .map(TodoResponse::new)
                .collect(Collectors.toList());
    }
}
