package com.wan.todo.todo;

import com.wan.todo.common.PageVo;
import com.wan.todo.todo.dto.TodoCreateRequest;
import com.wan.todo.todo.dto.TodoUpdateRequest;
import com.wan.todo.todo.exception.TodoNotFoundException;
import com.wan.todo.todo.exception.TodoReferenceImpossibilityException;
import com.wan.todo.todoreference.TodoReference;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;

@RunWith(MockitoJUnitRunner.class)
public class TodoServiceTest {

    @InjectMocks
    private TodoService todoService;

    @Mock
    private TodoRepository todoRepository;


    @Test
    public void create_TodoCreateIsSuccess_Todo() {
        //given
        final TodoCreateRequest request = new TodoCreateRequest("test", Arrays.asList(0l));
        final List<Todo> referenceParentTodos = getReferenceParentTodos(request.getRefIds());
        final Todo mockTodo = new Todo(request.getContent(), referenceParentTodos);

        given(todoRepository.findByIdIn(request.getRefIds())).willReturn(referenceParentTodos);
        given(todoRepository.save(any(Todo.class))).willReturn(mockTodo);

        //when
        final Todo todo = todoService.create(request);

        //then
        assertThat(todo.getContent(), is(request.getContent()));
        assertThat(todo.getReferenceParentTodos().size(), is(referenceParentTodos.size()));
        assertThat(isReferenceCorrect(referenceParentTodos, mockTodo, todo), is(true));
    }

    @Test
    public void getTodos_GettingTodosIsSucess_Todos() {

        //given
        final PageVo pageVo = new PageVo();
        final Pageable peageable = pageVo.makePageable(1, "id");
        final List<Todo> mockTodos = getTodos();
        given(todoRepository.findAll(peageable)).willReturn(new PageImpl<>(mockTodos, peageable, mockTodos.size()));

        //when
        final Page<Todo> todos = todoService.getTodos(peageable);

        //then
        assertThat(todos.getTotalElements(), is(Long.valueOf(mockTodos.size())));
        assertThat(todos.getContent().get(0).getContent(), is(mockTodos.get(0).getContent()));

    }

    @Test
    public void update_TodoUpdateIsSuccess_Todo() {

        //given
        final String UPDATE_CONTENT = "update-test";
        final TodoUpdateRequest todoUpdateRequest = new TodoUpdateRequest(UPDATE_CONTENT, Arrays.asList(0l));
        final List<Todo> referenceParentTodos = getReferenceParentTodos(todoUpdateRequest.getRefIds());
        given(todoRepository.findByIdIn(any())).willReturn(referenceParentTodos);
        given(todoRepository.findOne(anyLong())).willReturn(new Todo("test", referenceParentTodos));

        //when
        final Todo todo = todoService.update(0l, todoUpdateRequest);

        //then
        assertThat(todo.getContent(), is(UPDATE_CONTENT));
        assertThat(todo.getReferenceParentTodos().size(), is(todoUpdateRequest.getRefIds().size()));

    }

    @Test
    public void complete_TodoIsCompleted_Todo() {
        //given
        given(todoRepository.findOne(anyLong())).willReturn(new Todo("test",  new ArrayList<>()));

        //when
        final Todo todo = todoService.completeTodo(0l);

        //then
        assertThat(todo.isComplete(), is(true));
    }

    private List<Todo> getTodos(){
        return Arrays.asList(
                new Todo("test1", new ArrayList<>()),
                new Todo("test2", new ArrayList<>())
        );
    }

    @Test
    public void verifyTodoIsReferable_TodoIsReferable_Void() {

        //given
        given(todoRepository.findOne(anyLong())).willReturn(new Todo("test",  new ArrayList<>()));

        //when
        todoService.verifyTodoIsReferable(0l);

        //then

    }

    @Test
    public void getParentTodoReferences_GettingParentTodoIsSuccess_Todoreferences() {
        //given
        given(todoRepository.findOne(anyLong())).willReturn(new Todo("test",  new ArrayList<>()));

        //when
        final Set<TodoReference> parentTodoReferences = todoService.getParentTodoReferences(0l);

        //then
        assertThat(parentTodoReferences.size(), is(0));
    }

    @Test(expected = TodoNotFoundException.class)
    public void getParentTodoReferences_GettingParentTodoIsSuccess_TodoNotFoundException() {
        //given

        //when
        final Set<TodoReference> parentTodoReferences = todoService.getParentTodoReferences(0l);

        //then
        assertThat(parentTodoReferences.size(), is(0));
    }

    private boolean isReferenceCorrect(List<Todo> referenceParentTodos, Todo mockTodo, Todo todo) {
        return todo.getReferenceParentTodos().contains(new TodoReference(mockTodo, referenceParentTodos.get(0)));
    }

    private List<Todo> getReferenceParentTodos(final List<Long> refIds) {
        return refIds.stream()
                .map(id -> new Todo("reference-todo", new ArrayList<>()))
                .collect(Collectors.toList());
    }
}