package com.sxkl.project.cloudnote.todo.controller;

import com.sxkl.project.cloudnote.base.controller.BaseController;
import com.sxkl.project.cloudnote.base.service.BaseService;
import com.sxkl.project.cloudnote.project.service.ProjectService;
import com.sxkl.project.cloudnote.todo.entity.Status;
import com.sxkl.project.cloudnote.todo.entity.Todo;
import com.sxkl.project.cloudnote.todo.service.TodoService;
import com.sxkl.project.cloudnote.utils.RequestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/todo")
public class TodoController extends BaseController<Todo> {

    @Autowired
    private TodoService todoService;
    @Autowired
    private ProjectService projectService;

    @Override
    protected BaseService<Todo> getBaseService() {
        return todoService;
    }

    @Override
    public ModelAndView tablePage() {
        ModelAndView mv = new ModelAndView(getViewName("table"));
        mv.addObject("statusMap", todoService.findAllStatusMap());
        mv.addObject("projects", projectService.findAll());
        return mv;
    }

    @GetMapping("findAllStatus")
    @ResponseBody
    public List<Status> findAllStatus() {
        return todoService.findAllStatusList();
    }

    @GetMapping("calendarPage")
    public ModelAndView calendarPage(HttpServletRequest request) {
        String userId = RequestUtils.getUserId(request);
        ModelAndView mv = new ModelAndView(getViewName("calendar"));
        mv.addObject("events", todoService.findAllEvent(userId));
        return mv;
    }
}
