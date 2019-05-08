package com.sxkl.project.cloudnote.todo.service;

import com.google.common.collect.Lists;
import com.sxkl.project.cloudnote.base.mapper.BaseMapper;
import com.sxkl.project.cloudnote.base.service.BaseService;
import com.sxkl.project.cloudnote.todo.entity.Event;
import com.sxkl.project.cloudnote.todo.entity.Status;
import com.sxkl.project.cloudnote.todo.entity.Todo;
import com.sxkl.project.cloudnote.todo.mapper.TodoMapper;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TodoService extends BaseService<Todo> {

    @Autowired
    private TodoMapper todoMapper;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
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

    public List<Status> findAllStatusList() {
        return Collections.unmodifiableList(statuses);
    }

    public Map<String, String> findAllStatusMap() {
        Map<String, String> statusMap = statuses.stream().collect(Collectors.toMap(Status::getId, Status::getName));
        return Collections.unmodifiableMap(statusMap);
    }

    public List<Event> findAllEvent(String userId, Event event) {
        Todo condition = new Todo();
        condition.setUserId(userId);
        condition.setStartDate(event.getStartDateTime());
        condition.setEndDate(event.getEndDateTime());
        List<Todo> todos = todoMapper.findByCondition(condition);
        return convert(todos);
    }

    private List<Event> convert(List<Todo> todos) {
        return todos.stream().map(this::doConvert).collect(Collectors.toList());
    }

    private Event doConvert(Todo todo) {
        Event event = new Event();
        event.setId(todo.getId());
        event.setTitle(getTextFromHtml(todo.getTitle()));
        event.setStart(todo.getExpectedStartDate().format(FORMATTER));
        event.setEnd(todo.getExpectedEndDate().format(FORMATTER));
        event.setAllDay(todo.getExpectedEndDate().minusDays(1).isAfter(todo.getExpectedStartDate()));
        event.setTextColor("white");
        event.setBackgroundColor(getBackgroundColorByStatus(todo.getStatus()));
        return event;
    }

    private String getTextFromHtml(String title) {
        return Jsoup.parse(title).text();
    }

    private String getBackgroundColorByStatus(String status) {
        switch (status) {
            case "0":
                return "green";
            case "1":
                return "blue";
            case "2":
                return "purple";
            case "3":
                return "red";
            default:
                return "green";
        }
    }
}
