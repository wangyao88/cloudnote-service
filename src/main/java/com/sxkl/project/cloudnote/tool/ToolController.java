package com.sxkl.project.cloudnote.tool;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.sxkl.project.cloudnote.etl.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/tool")
public class ToolController {

    @Autowired
    private ToolService toolService;

    @GetMapping("/jsonPage")
    public String jsonPage() {
        return "tool/json";
    }

    @GetMapping("/translatePage")
    public String translatePage() {
        return "tool/translate";
    }

    @GetMapping("/translateZH")
    @ResponseBody
    public String translateZH(@RequestParam("words") String words) throws UnirestException {
        return toolService.translateZH(words);
    }

    @GetMapping("/translateEN")
    @ResponseBody
    public String translateEN(@RequestParam("words") String words) throws UnirestException {
        return toolService.translateEN(words);
    }

    @GetMapping("/summaryPage")
    public String summaryPage() {
        return "tool/summary";
    }

    @GetMapping("/base64Page")
    public String base64Page() {
        return "tool/base64";
    }

    @GetMapping("/encryptPage")
    public String encryptPage() {
        return "tool/encrypt";
    }
}
