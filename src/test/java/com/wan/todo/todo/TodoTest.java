package com.wan.todo.todo;

import com.wan.todo.todo.exception.CompleteRequirementFailException;
import com.wan.todo.todo.exception.TodoReferenceImpossibilityException;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class TodoTest {

    private Todo parentTodo;
    private Todo todo;
    private Todo childTodo;

    private final String TODO_CONTENT = "TODO_CONTENT";

    @Before
    public void setUp() throws Exception {
        parentTodo = new Todo(1L, "parent", false, new HashSet<>(), new HashSet<>());
        todo = new Todo(2L, TODO_CONTENT,false, new HashSet<>(), new HashSet<>());
        childTodo = new Todo(3L, "child", false, new HashSet<>(), new HashSet<>());

        todo.addRefParent(parentTodo);
        todo.addRefChild(childTodo);

    }

    @Test
    public void 화면값_확인() {
        assertThat(todo.getContent(), is(TODO_CONTENT));
        assertThat(todo.getFullContent(), is(TODO_CONTENT + " @1"));
    }


    @Test
    public void TODO_완료처리() {
        //given
        todo = new Todo(2L, TODO_CONTENT,false, new HashSet<>(), new HashSet<>());
        //when
        todo.complete();
        //then
    }

    @Test
    public void TODO_자식참조_TODO와_함께_완료처리() {

        //given
        todo = new Todo(2L, TODO_CONTENT,false, new HashSet<>(), new HashSet<>());
        childTodo = new Todo(3L, "child", true, new HashSet<>(), new HashSet<>());
        todo.addRefChild(childTodo);
        //when
        //then
        todo.complete();
    }

    @Test(expected = CompleteRequirementFailException.class)
    public void TODO_완료처리시_자식참조_미완료인경우() {
        todo.complete();
    }

    @Test
    public void TODO_작업완료로_참조_가능한경우() {
        todo.verifyTodoIsReferable();
    }

    @Test(expected = TodoReferenceImpossibilityException.class)
    public void TODO_작업_이미_완료로_참조_불가능한경우() {

        //given
        todo = new Todo(2L, TODO_CONTENT,true, new HashSet<>(), new HashSet<>());
        //when
        //then
        todo.verifyTodoIsReferable();

    }

    @Test
    public void TODO_수정_완료처리() {
        //given
        final String UPDATE_CONTENT = "update-test";
        final Todo parentTodo1 = new Todo(4L, "parent", false, new HashSet<>(), new HashSet<>());
        final Todo parentTodo2 = new Todo(5L, "parent", false, new HashSet<>(), new HashSet<>());

        //when
        todo.updateContent("update-test", Arrays.asList(parentTodo1, parentTodo2));

        //then
        assertThat(todo.getContent(), is(UPDATE_CONTENT));
        assertThat(todo.getFullContent(), is("update-test" + " @1 @4 @5"));
    }

}