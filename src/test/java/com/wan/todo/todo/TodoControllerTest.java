package com.wan.todo.todo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wan.todo.todo.dto.TodoCreateRequest;
import com.wan.todo.todo.dto.TodoResponse;
import com.wan.todo.todo.dto.TodoUpdateRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.HashSet;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class TodoControllerTest {

    private static final String BASE_API_URI = "/api/v1/todos";

    private MockMvc mockMvc;
    private ObjectMapper mapper;

    @InjectMocks
    private TodoController todoController;

    @Mock
    private TodoService todoService;

    @Before
    public void setUp() {
        mapper = new ObjectMapper();
        mockMvc = MockMvcBuilders
                .standaloneSetup(todoController)
                .build();
    }

    @Test
    public void create_TodoCreateIsSuccess_TodoResponse() throws Exception {

        //given
        final TodoCreateRequest request = new TodoCreateRequest("test", new ArrayList<>());
        given(todoService.create(any())).willReturn(new Todo(request.getContent(), new ArrayList<>()));

        //when
        final String todoAsString = mockMvc.perform(post(BASE_API_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        final TodoResponse todoResponse = mapper.readValue(todoAsString, TodoResponse.class);

        //then
        assertThat(request.getContent(), is(todoResponse.getContent()));
    }


    @Test
    public void create_TodoCreateInputIsNotValid_400() throws Exception {

        //given
        final TodoCreateRequest request = new TodoCreateRequest("", new ArrayList<>());

        //when
        //then
        mockMvc.perform(post(BASE_API_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());

    }

    @Test
    public void update_TodoUpdateIsSuccess_TodoResponse() throws Exception {

        //given
        final TodoUpdateRequest request = new TodoUpdateRequest("update-test", new ArrayList<>());
        given(todoService.update(anyLong(), any())).willReturn(new Todo(request.getContent(), new ArrayList<>()));

        //when
        final String todoAsString = mockMvc.perform(put(BASE_API_URI + "/0")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        final TodoResponse todoResponse = mapper.readValue(todoAsString, TodoResponse.class);

        //then
        assertThat(request.getContent(), is(todoResponse.getContent()));
    }

    @Test
    public void complete_TodoCompleteIsSuccess_TodoResponse() throws Exception {

        //given
        final Todo mockTodo = new Todo(0, "test", true, new HashSet<>(), new HashSet<>());
        given(todoService.completeTodo(0)).willReturn(mockTodo);

        //when
        final String todoAsString = mockMvc.perform(put(BASE_API_URI + "/0/complete")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        final TodoResponse todoResponse = mapper.readValue(todoAsString, TodoResponse.class);

        //then
        assertThat(mockTodo.getContent(), is(todoResponse.getContent()));
        assertThat(mockTodo.isComplete(), is(todoResponse.isComplete()));
        assertThat(mockTodo.getId(), is(todoResponse.getId()));
    }


    @Test
    public void verifyTodoIsReferable_TodoIsReferable_200() throws Exception {

        //given
        doNothing().when(todoService).verifyTodoIsReferable(0l);

        //when
        mockMvc.perform(get(BASE_API_URI + "/0/ref-check")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

    }

}