package com.sxkl.project.cloudnote.tool;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.sxkl.project.cloudnote.etl.utils.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/tool")
public class ToolController {

    @GetMapping("/jsonPage")
    public String jsonPage() {
        return "tool/json";
    }

    @GetMapping("/translatePage")
    public String translatePage() {
        return "tool/translate";
    }

    @GetMapping("/translate")
    @ResponseBody
    public String translate(@RequestParam("words") String words) throws UnirestException {
//        https://www.cnblogs.com/fanyang1/p/9414088.html
        String url = "http://translate.google.cn/translate_a/single?client=gtx&dt=t&dj=1&ie=UTF-8&sl=auto&tl=en&q="+words;
        HttpResponse<JsonNode> response = Unirest.get(url).asJson();
        JsonNode body = response.getBody();
        return body.toString();
    }
}
