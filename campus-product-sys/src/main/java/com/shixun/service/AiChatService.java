package com.shixun.service;

import java.util.List;

public interface AiChatService {

    /**
     * 前端提供完整会话历史
     */
    String chat(List<String> messages);

}
