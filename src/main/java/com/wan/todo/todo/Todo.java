package com.wan.todo.todo;

import com.wan.todo.todo.exception.CompleteRequirementFailException;
import com.wan.todo.todo.exception.TodoReferenceImpossibilityException;
import com.wan.todo.todoreference.TodoReference;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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

    @OneToMany(mappedBy = "todo", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<TodoReference> referenceParentTodos = new HashSet<>(); //내가 참조하는것(참조 부모)

    @OneToMany(mappedBy = "refTodo", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<TodoReference> referenceChildTodos = new HashSet<>(); // 내가 참조 되어지는것(참조 자식)

    @CreationTimestamp
    @Column(name = "created_at")
    private Timestamp createdAt;

    @CreationTimestamp
    @Column(name = "updated_at")
    private Timestamp updatedAt;

    public Todo(String content, List<Todo> referenceParentTodos) {
        this.content = content;
        this.complete = false;
        addTodoReference(referenceParentTodos);
    }

    //for test
    public Todo(long id, String content, boolean complete, Set<TodoReference> referenceParentTodos, Set<TodoReference> referenceChildTodos) {
        this.id = id;
        this.content = content;
        this.complete = complete;
        this.referenceParentTodos = referenceParentTodos;
        this.referenceChildTodos = referenceChildTodos;
    }

    public void updateContent(String content, List<Todo> referenceTodoParents) {
        this.content = content;
        addTodoReference(referenceTodoParents);
    }

    public void complete() {
        if (canComplete()) {
            this.complete = true;
        } else {
            throw new CompleteRequirementFailException();
        }
    }

    public String getFullContent() {
        if (referenceParentTodos.size() == 0) {
            return this.content;
        } else {
            return this.content + " " + getReferenceIdTags();
        }
    }

    public void addRefParent(TodoReference todoReference) {
        this.referenceParentTodos.add(todoReference);
    }

    public void addRefChild(TodoReference todoReference) {
        this.referenceChildTodos.add(todoReference);
    }

    public void verifyTodoIsReferable() {
        if (this.isComplete()) {
            throw new TodoReferenceImpossibilityException();
        }
    }

    private boolean canComplete() {
        final boolean result = this.referenceChildTodos
                .stream()
                .allMatch(todo -> todo.getTodo().isComplete());
        return result;
    }

    private String getReferenceIdTags() {
        return referenceParentTodos.stream()
                .map(t -> "@" + t.getRefTodo().getId())
                .sorted(String::compareTo)
                .collect(Collectors.joining(" "));
    }

    private void addTodoReference(List<Todo> todoReferences) {
        todoReferences.stream()
                .forEach(todo -> {
                    todo.verifyTodoIsReferable();
                    this.referenceParentTodos.add(new TodoReference(this, todo));
                });
    }
}
