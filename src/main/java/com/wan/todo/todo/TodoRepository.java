package com.wan.todo.todo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Long> {

    List<Todo> findByIdIn(List<Long> refIds);

    @Query(
            value = "select t from Todo t left join fetch t.referenceParentTodos",
            countQuery = "select count(t) from Todo t"
    )
    Page<Todo> findAllFechJoinParents(Pageable pageable);

    @Query(
            value = "select t from Todo t left join fetch t.referenceParentTodos left  join fetch t.referenceChildTodos where t.id = :id",
            countQuery = "select count(t) from Todo t"
    )
    Todo findbyOneFetchJoin(@Param("id") Long id);

}

