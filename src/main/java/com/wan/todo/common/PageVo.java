package com.wan.todo.common;

import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class PageVo {
    private static final int DEFAULT_SIZE = 10;
    private static final int DEFAULT_MAX_SIZE = 50;
    private static final Integer ALL = Integer.MAX_VALUE;

    @ApiModelProperty(name = "page", value = "Results page you want to retrieve (1.N)")
    private int page;

    @ApiModelProperty(name = "size", value = "Number of records per page. (10 ~ 50)")
    private int size;

    public PageVo() {
        this.page = 1; // start page set 1 because Pageable lib is start page is 0
        this.size = DEFAULT_SIZE;
    }

    public Pageable makePageable(int direction, String... props) {
        Sort.Direction dir = direction == 0 ? Sort.Direction.DESC : Sort.Direction.ASC;
        return new PageRequest(this.page - 1, this.size, dir, props);
    }
}
