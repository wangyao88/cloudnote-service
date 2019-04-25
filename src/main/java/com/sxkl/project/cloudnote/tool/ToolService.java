package com.sxkl.project.cloudnote.tool;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.sxkl.project.cloudnote.etl.utils.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class ToolService {

    private static final String TRANSLATE_COUNTRY_CHINA = "zh";
    private static final String TRANSLATE_COUNTRY_ENGLISH = "en";

    public String translateEN(String words) throws UnirestException {
       return translate(TRANSLATE_COUNTRY_ENGLISH, words);
    }

    public String translateZH(String words) throws UnirestException {
        return translate(TRANSLATE_COUNTRY_CHINA, words);
    }

    private String translate(String country, String words) throws UnirestException {
        String url =  StringUtils.appendJoinEmpty("http://translate.google.cn/translate_a/single?client=gtx&dt=t&dj=1&ie=UTF-8&sl=auto&tl=",
                                                    country, "&q=",  words);
        HttpResponse<JsonNode> response = Unirest.get(url).asJson();
        JsonNode body = response.getBody();
        return body.toString();
    }
}
