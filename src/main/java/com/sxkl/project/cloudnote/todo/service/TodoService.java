package com.sxkl.project.cloudnote.todo.service;

import com.google.common.collect.Lists;
import com.sxkl.project.cloudnote.base.mapper.BaseMapper;
import com.sxkl.project.cloudnote.base.service.BaseService;
import com.sxkl.project.cloudnote.todo.entity.Event;
import com.sxkl.project.cloudnote.todo.entity.Status;
import com.sxkl.project.cloudnote.todo.entity.Todo;
import com.sxkl.project.cloudnote.todo.mapper.TodoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.TemporalUnit;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    public List<Status> findAllStatusList() {
        return Collections.unmodifiableList(statuses);
    }

    public Map<String, String> findAllStatusMap() {
        Map<String, String> statusMap = statuses.stream().collect(Collectors.toMap(Status::getId, Status::getName));
        return Collections.unmodifiableMap(statusMap);
    }

    public List<Event> findAllEvent(String userId) {
        ZoneId zone = ZoneId.systemDefault();
        LocalDateTime start = LocalDateTime.now()
                                         .with(TemporalAdjusters.firstDayOfMonth())
                                         .withHour(0)
                                         .withMinute(0)
                                         .withSecond(0)
                                         .withNano(0);

        Todo condition = new Todo();
        condition.setUserId(userId);
        condition.setStartDate(start);
        condition.setEndDate(start.plusMonths(1));

        List<Todo> todos = todoMapper.findByCondition(condition);

        Event event1 = new Event();
        event1.setStart(LocalDateTime.of(2019, 5, 6, 12, 23));
        event1.setAllDay(true);
        event1.setTitle("qweqwe");

        Event event2 = new Event();
        event2.setStart(LocalDateTime.of(2019, 5, 8, 2, 23));
        event2.setEnd(LocalDateTime.of(2019, 5, 12, 4, 23));
        event2.setAllDay(true);
        event2.setTitle("dddddddd");
        return Lists.newArrayList(event1, event2);
    }

    public static void main(String[] args) {
        ZoneId zone = ZoneId.systemDefault();
        LocalDateTime start = LocalDateTime.now(zone)
                .with(TemporalAdjusters.firstDayOfMonth())
                .withHour(0)
                .withMinute(0)
                .withSecond(0)
                .withNano(0);

        Instant startInstant = start.atZone(zone).toInstant();
        Instant endInstant = start.plusMonths(1).atZone(zone).toInstant();
        System.out.println();
    }
}
