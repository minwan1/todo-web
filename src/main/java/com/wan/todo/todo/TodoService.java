package com.wan.todo.todo;

import com.wan.todo.todo.dto.TodoCreateRequest;
import com.wan.todo.todo.dto.TodoUpdateRequest;
import com.wan.todo.todo.exception.TodoNotFoundException;
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
        return todoRepository.save(new Todo(dto.getContent(), referenceTodos));
    }

    public Todo update(final long id, final TodoUpdateRequest dto) {
        final List<Todo> referenceTodos = todoRepository.findByIdIn(dto.getRefIds());
        Todo todo = findById(id);
        todo.updateContent(dto.getContent(), referenceTodos);
        return todo;
    }

    public Page<Todo> getTodos(final Pageable pageable) {
        final Page<Todo> todos = todoRepository.findAllFechJoinParents(pageable);
        return todos;
    }

    public Todo completeTodo(final long id) {
        Todo todo = findByIdFetchJoin(id);
        todo.complete();
        return todo;
    }

    public void verifyTodoIsReferable(final long id) {
        final Todo todo = findById(id);
        todo.verifyTodoIsReferable();
    }

    public Set<Todo> getParentTodoReferences(final long id) {
        final Todo todo = findByIdFetchJoin(id);
        return todo.getReferenceParentTodos();
    }

    private Todo findById(final long id) {
        final Todo todo = todoRepository.findOne(id);
        if (todo == null) throw new TodoNotFoundException(id);
        return todo;
    }

    private Todo findByIdFetchJoin(final long id) {
        final Todo todo = todoRepository.findbyOneFetchJoin(id);
        if (todo == null) throw new TodoNotFoundException(id);
        return todo;
    }

    public void test(long id) {
//        todo
    }
}