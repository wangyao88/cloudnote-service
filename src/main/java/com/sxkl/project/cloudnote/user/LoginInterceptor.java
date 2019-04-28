package com.sxkl.project.cloudnote.user;

import com.google.common.collect.Sets;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Set;

public class LoginInterceptor implements HandlerInterceptor {

    public final static String SESSION_KEY = "user";

    private static final Set<String> RESOURCE_PATH =
            Sets.newHashSet("/css/", "/fonts/", "/images/",
                            "/js/", "/pages/", "/plugins/", "/scss/",
                            "/error/", "/login", "/es/", "/user/getPublicKey");

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String servletPath = request.getServletPath();
        boolean match = RESOURCE_PATH.stream().anyMatch(path -> servletPath.startsWith(path));
        if(match) {
            return true;
        }
        HttpSession session = request.getSession();
        if (session.getAttribute(SESSION_KEY) != null){
            return true;
        }
        String url = "login";
        response.sendRedirect(url);
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
