package com.pavmaxdav.mymess.dto;

import java.util.ArrayList;
import java.util.List;

public class BigChatDTO {
    private Integer chatId;
    private List<MessageDTO> messages;

    public BigChatDTO(Integer chatId, List<MessageDTO> messages) {
        this.chatId = chatId;
        this.messages = messages;
    }


    public Integer getChatId() {
        return chatId;
    }
    public List<MessageDTO> getMessages() {
        return messages;
    }


    public void setChatId(Integer chatId) {
        this.chatId = chatId;
    }
    public void setMessages(List<MessageDTO> messages) {
        this.messages = messages;
    }
}
