package com.wan.todo;

import com.wan.todo.todo.Todo;
import com.wan.todo.todo.TodoRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;

@Component
@AllArgsConstructor
public class DataSetRunner implements ApplicationRunner {

    private final TodoRepository todoRepository;

    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        final Todo todo1 = new Todo("집안일", new ArrayList<>());
        todoRepository.save(todo1);
        final Todo todo2 = new Todo("빨래", Arrays.asList(todo1));
        todoRepository.save(todo2);

//        final Todo todo3 = new Todo("청소", Arrays.asList(todo1));
//        todoRepository.save(todo3);
//        final Todo todo4 = new Todo("방청소", Arrays.asList(todo1, todo3));
//        todoRepository.save(todo4);
    }
}
