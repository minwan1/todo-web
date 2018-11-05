package com.wan.todo.todo;


import com.wan.todo.common.PageVo;
import com.wan.todo.todo.dto.TodoCreateRequest;
import com.wan.todo.todo.dto.TodoResponse;
import com.wan.todo.todo.dto.TodoUpdateRequest;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;


@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/todos")
public class TodoController {

    private final TodoService todoService;

    @PostMapping
    public TodoResponse create(@RequestBody @Valid final TodoCreateRequest dto) {
        return new TodoResponse(todoService.create(dto));
    }

    @GetMapping
    public Page<TodoResponse> getTodos(final PageVo vo) {
        final Pageable pageable = vo.makePageable(0, "id");
        final Page<Todo> todos = todoService.getTodos(pageable);
        final List<TodoResponse> todoResponses = TodoResponse.valueOf(todos.getContent());
        return new PageImpl<>(todoResponses, pageable, todos.getTotalElements());
    }

    @PutMapping(value = "/{id}")
    public TodoResponse update(@PathVariable final long id, @RequestBody @Valid final TodoUpdateRequest dto) {
        return new TodoResponse(todoService.update(id, dto));
    }

    @PutMapping(value = "{id}/complete")
    public TodoResponse complete(@PathVariable final long id) {
        return new TodoResponse(todoService.completeTodo(id));
    }

    @GetMapping(value = "/{id}/ref")
    public List<TodoResponse> getReferenceParentTodos(@PathVariable final long id) {
        return TodoResponse.valueOf(new ArrayList<>(todoService.getParentTodoReferences(id)));
    }

    @GetMapping(value = "/{id}/ref-check")
    public void checkId(@PathVariable final long id) {
        todoService.verifyTodoIsReferable(id);
    }

}
