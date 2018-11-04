package com.wan.todo.todo;

import com.wan.todo.todo.dto.TodoCreateRequest;
import com.wan.todo.todo.dto.TodoUpdateRequest;
import com.wan.todo.todo.exception.TodoNotFoundException;
import com.wan.todo.todoreference.TodoReference;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@Transactional
@AllArgsConstructor
public class TodoService {

    private final TodoRepository todoRepository;

    public Todo create(final TodoCreateRequest dto) {
        final List<Todo> referenceTodos = todoRepository.findByIdIn(dto.getRefIds());
        final Todo todo = todoRepository.save(new Todo(dto.getContent(), referenceTodos));
        return todo;
    }

    public Todo update(final long id, final TodoUpdateRequest dto) {
        final List<Todo> referenceTodos = todoRepository.findByIdIn(dto.getRefIds());
        Todo todo = findById(id);
        todo.updateContent(dto.getContent(), referenceTodos);
        return todo;
    }

    public Page<Todo> getTodos(final Pageable pageable) {
        final Page<Todo> todos = todoRepository.findAll(pageable);
        return todos;
    }

    public Todo completeTodo(final long id) {
        Todo todo = findById(id);
        todo.complete();
        return todo;
    }

    public void verifyTodoIsReferable(final long id) {
        final Todo todo = findById(id);
        todo.verifyTodoIsReferable();
    }

    public Set<TodoReference> getParentTodoReferences(final long id) {
        final Todo todo = findById(id);
        return todo.getReferenceParentTodos();
    }

    private Todo findById(final long id) {
        final Todo todo = todoRepository.findOne(id);
        if (todo == null) throw new TodoNotFoundException(id);
        return todo;
    }
}