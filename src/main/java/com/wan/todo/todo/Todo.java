package com.wan.todo.todo;

import com.wan.todo.todo.exception.CompleteRequirementFailException;
import com.wan.todo.todo.exception.TodoReferenceImpossibilityException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Getter
@Table(name = "todo")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Todo {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "complete", nullable = false)
    private boolean complete;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "todo_reference",
            joinColumns = {@JoinColumn(name = "todo_id", nullable = false)},
            inverseJoinColumns = {@JoinColumn(name = "ref_parent_id", nullable = false)}
    )
    private Set<Todo> referenceParentTodos = new HashSet<>();

    @ManyToMany(mappedBy = "referenceParentTodos" , fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Todo> referenceChildTodos = new HashSet<>();


    @CreationTimestamp
    @Column(name = "created_at")
    private Timestamp createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Timestamp updatedAt;

    public Todo(String content, List<Todo> referenceParentTodos) {
            this.content = content;
            this.complete = false;
            addTodoReference(referenceParentTodos);
    }

    //for test
    public Todo(long id, String content, boolean complete, Set<Todo> referenceParentTodos, Set<Todo> referenceChildTodos) {
        this.id = id;
        this.content = content;
        this.complete = complete;
        this.referenceParentTodos = referenceParentTodos;
        this.referenceChildTodos = referenceChildTodos;
    }

    public void updateContent(String content) {
        this.content = content;
    }

    public void complete() {
        if (canComplete()) {
            this.complete = true;
        } else {
            throw new CompleteRequirementFailException();
        }
    }

    public String getFullContent() {
        if (referenceParentTodos.isEmpty()) {
            return this.content;
        } else {
            return String.format("%s %s", this.content, getReferenceIdTags());
        }
    }

    public void addRefParent(Todo todo) {
        this.referenceParentTodos.add(todo);
    }

    public void addRefChild(Todo todo) {
        this.referenceChildTodos.add(todo);
    }

    public void verifyTodoIsReferable() {
        if (this.isComplete()) {
            throw new TodoReferenceImpossibilityException();
        }
    }

    private boolean canComplete() {
        return this.referenceChildTodos
                .stream()
                .allMatch(todo -> todo.isComplete());
    }

    private String getReferenceIdTags() {
        return referenceParentTodos.stream()
                .map(t -> "@" + t.getId())
                .sorted(String::compareTo)
                .collect(Collectors.joining(" "));
    }

    private void addTodoReference(List<Todo> todoReferences) {
        todoReferences.stream()
                .forEach(todo -> {
                    todo.verifyTodoIsReferable();
                    this.referenceParentTodos.add(todo);
                });
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Todo todo = (Todo) o;
        return id == todo.id;
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }
}
