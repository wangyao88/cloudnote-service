package com.sxkl.project.cloudnote.todo.service;

import com.google.common.collect.Lists;
import com.sxkl.project.cloudnote.base.mapper.BaseMapper;
import com.sxkl.project.cloudnote.base.service.BaseService;
import com.sxkl.project.cloudnote.todo.entity.Status;
import com.sxkl.project.cloudnote.todo.entity.Todo;
import com.sxkl.project.cloudnote.todo.mapper.TodoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.List;

@Service
public class TodoService extends BaseService<Todo> {

    @Autowired
    private TodoMapper todoMapper;

    private static List<Status> statuses = Lists.newArrayList();

    @PostConstruct
    private void init() {
        statuses.add(new Status("0", "新增"));
        statuses.add(new Status("1", "开始"));
        statuses.add(new Status("2", "结束"));
        statuses.add(new Status("3", "超时"));
    }

    @Override
    protected BaseMapper<Todo> getMapper() {
        return todoMapper;
    }

    public List<Status> findAllStatus() {
        return Collections.unmodifiableList(statuses);
    }
}
