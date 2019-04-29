package com.sxkl.project.cloudnote.utils;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Charsets;
import com.google.common.collect.Maps;
import com.sxkl.project.cloudnote.etl.utils.ObjectUtils;
import com.sxkl.project.cloudnote.etl.utils.StringUtils;
import com.sxkl.project.cloudnote.user.LoginInterceptor;
import com.sxkl.project.cloudnote.user.entity.User;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.util.Base64;
import java.util.Enumeration;
import java.util.Map;

/**
 * @author wangyao
 */
public class RequestUtils {

    private static final String DATATABLE_SEARCH_ENCODE= "_base64";

    public static <T> T requestToBean(HttpServletRequest request, Class<T> clazz){
        JSONObject json = new JSONObject();
        Base64.Decoder decoder = Base64.getDecoder();
        Enumeration<String> parameterNames = request.getParameterNames();
        while(parameterNames.hasMoreElements()){
            String element = parameterNames.nextElement();
            String value = request.getParameter(element);
            if(element.contains(DATATABLE_SEARCH_ENCODE)){
                value = new String(decoder.decode(value),Charsets.UTF_8);
                element = element.replaceAll(DATATABLE_SEARCH_ENCODE, StringUtils.EMPTY);
                json.put(element,value);
            }
            json.put(element,value);
        }
        return json.toJavaObject(clazz);
    }

    public static <E> E requestToBean(HttpServletRequest request, E entity){
        Map<String, String> fieldMap = Maps.newHashMap();
        Base64.Decoder decoder = Base64.getDecoder();
        Enumeration<String> parameterNames = request.getParameterNames();
        while(parameterNames.hasMoreElements()){
            String element = parameterNames.nextElement();
            String value = request.getParameter(element);
            if(element.contains(DATATABLE_SEARCH_ENCODE)){
                value = new String(decoder.decode(value),Charsets.UTF_8);
                element = element.replaceAll(DATATABLE_SEARCH_ENCODE, StringUtils.EMPTY);
                fieldMap.put(element,value);
            }
        }
        if(fieldMap.size() == 0) {
            return entity;
        }
        Field[] field = entity.getClass().getDeclaredFields();
        for (int j = 0; j < field.length; j++) {
            if(fieldMap.get(field[j].getName()) != null) {
                try {
                    field[j].setAccessible(true);
                    field[j].set(entity, fieldMap.get(field[j].getName()));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
       return entity;
    }

    public static String getUserId(HttpServletRequest request) {
        Object attribute = request.getSession().getAttribute(LoginInterceptor.SESSION_KEY);
        return ObjectUtils.isNull(attribute) ? StringUtils.EMPTY : ((User)attribute).getId();
    }
}
