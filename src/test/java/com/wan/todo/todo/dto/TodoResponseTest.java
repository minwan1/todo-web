package com.wan.todo.todo.dto;

import com.wan.todo.todo.Todo;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class TodoResponseTest {

    private Todo todo1;
    private Todo todo2;
    private Todo todo3;

    @Before
    public void setUp() throws Exception {
        todo1 = new Todo(1L, "todo1", false, new HashSet<>(), new HashSet<>());
        todo2 = new Todo(2L, "todo2",false, new HashSet<>(), new HashSet<>());
        todo3 = new Todo(3L, "todo3", false, new HashSet<>(), new HashSet<>());
    }

    @Test
    public void 변환테스트1() {

        //given
        final List<Todo> todos = Arrays.asList(todo1, todo2, todo3);
        //when
        final List<TodoResponse> todoResponses = TodoResponse.valueOf(todos);
        //then
        assertThat(todoResponses.get(0).getId(), is(todoResponses.get(0).getId()));
        assertThat(todoResponses.get(0).getContent(), is(todoResponses.get(0).getContent()));
    }


}