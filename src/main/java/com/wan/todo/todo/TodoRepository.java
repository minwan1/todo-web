package com.wan.todo.todo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Long> {
    List<Todo> findByIdIn(List<Long> refIds);

    @Query(
            value = "select t from Todo t left join fetch t.referenceParentTodos r ",
            countQuery = "select count(t) from Todo t"
    )
    Page<Todo> findAllWithParents(Pageable pageable);
}

