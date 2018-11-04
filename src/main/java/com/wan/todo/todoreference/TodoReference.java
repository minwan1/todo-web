package com.wan.todo.todoreference;

import com.wan.todo.todo.Todo;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;


@Entity
@Getter
@Table(name = "todo_reference")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TodoReference implements Serializable{

    @Id
    @ManyToOne
    @JoinColumn(name="todo_id")
    private Todo todo;

    @Id
    @ManyToOne
    @JoinColumn(name="ref_todo_id")
    private Todo refTodo;

    public TodoReference(Todo todo, Todo refTodo) {
        this.todo = todo;
        this.refTodo = refTodo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TodoReference that = (TodoReference) o;
        return Objects.equals(todo.getId(), that.todo.getId()) &&
                Objects.equals(refTodo.getId(), that.refTodo.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(todo.getId(), refTodo.getId());
    }
}