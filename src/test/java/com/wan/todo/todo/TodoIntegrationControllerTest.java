package com.wan.todo.todo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wan.todo.common.exception.ErrorCode;
import com.wan.todo.common.exception.ErrorResponse;
import com.wan.todo.todo.dto.TodoCreateRequest;
import com.wan.todo.todo.dto.TodoResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TodoIntegrationControllerTest {

    private static final String BASE_API_URI = "/api/v1/todos";

    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private ObjectMapper mapper;

    private MockMvc mockMvc;


    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .build();
    }

    @Test
    @Transactional
    public void create_TodoRequestIsInvalid_400() throws Exception {

        //given
        final TodoCreateRequest request = new TodoCreateRequest("", new ArrayList<>());

        //when
        final String contentAsString = mockMvc.perform(
                post(BASE_API_URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse()
                .getContentAsString();
        final ErrorResponse errorResponse = mapper.readValue(contentAsString, ErrorResponse.class);

        //then
        assertThat(errorResponse.getCode(), is(ErrorCode.INPUT_VALUE_INVALID.code()));

    }


    @Test
    @Transactional
    public void getReferenceParentTodos_TodoDoesNotExist_400() throws Exception {

        //given
        //when
        final String contentAsString = mockMvc.perform(
                get(BASE_API_URI + "/9999/ref")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse()
                .getContentAsString();
        final ErrorResponse errorResponse = mapper.readValue(contentAsString, ErrorResponse.class);

        //then
        assertThat(errorResponse.getCode(), is(ErrorCode.TODO_NOT_FOUND.code()));

    }





}