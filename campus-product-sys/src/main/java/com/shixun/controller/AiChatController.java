package com.shixun.controller;

import com.shixun.pojo.api.ApiResult;
import com.shixun.pojo.api.Result;
import com.shixun.service.AiChatService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

@RestController
@RequestMapping("/ai")
public class AiChatController {

    @Resource
    private AiChatService aiChatService;

    @PostMapping("/chat")
    public Result<Map<String, Object>> chat(@RequestBody Map<String, Object> body) {

        List<String> messages = (List<String>) body.get("messages");

        String reply = aiChatService.chat(messages);

        Map<String, Object> data = new HashMap<>();
        data.put("reply", reply);

        return ApiResult.success(data);
    }
}
