package solvolabs.ai.me_ai;

import java.util.ArrayList;
import java.util.List;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.messages.AssistantMessage;

public class Conversation {
    private String id;
    private List<Message> messages = new ArrayList<>();

    public Conversation(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void addUserMessage(String message) {
        messages.add(new UserMessage(message));
    }

    public void addAssistantMessage(String message) {
        messages.add(new AssistantMessage(message));
    }
}
