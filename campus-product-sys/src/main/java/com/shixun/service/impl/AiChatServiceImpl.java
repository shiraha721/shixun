package com.shixun.service.impl;

import com.shixun.service.AiChatService;
import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.ChatLanguageModel;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class AiChatServiceImpl implements AiChatService {

    @Resource
    private ChatLanguageModel chatLanguageModel;

    @Override
    public String chat(List<String> messages) {

        List<ChatMessage> history = new ArrayList<>();

        for (String msg : messages) {
            history.add(UserMessage.from(msg));
        }

        AiMessage aiMessage = chatLanguageModel.generate(history).content();

        return aiMessage.text();
    }
}
