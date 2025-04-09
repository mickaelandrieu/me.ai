package solvolabs.ai.me_ai;

import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ChatService {

    @Autowired
    private SystemPromptService systemPromptService;

    private final OpenAiChatModel chatModel;

    @Value("${spring.ai.openai.api-key}")
    private String apiKey;

    private static final Map<String, Conversation> conversationMap = new ConcurrentHashMap<>();

    public ChatService(OpenAiChatModel chatModel) {
        this.chatModel = chatModel;
    }

    public Map<String, String> handleMessage(String conversationId, String message) {
        Conversation conversation;

        if (conversationId == null || !conversationMap.containsKey(conversationId)) {
            conversationId = UUID.randomUUID().toString();
            conversation = new Conversation(conversationId);
            conversationMap.put(conversationId, conversation);
        } else {
            conversation = conversationMap.get(conversationId);
        }

        conversation.addUserMessage(message);

        Prompt prompt = new Prompt(List.of(
                new SystemMessage(this.systemPromptService.getSystemPrompt()),
                new UserMessage(message)
        ));

        ChatResponse response = this.chatModel.call(prompt);
        String generation = response.getResult().getOutput().getText();

        conversation.addAssistantMessage(generation);

        return Map.of(
                "conversationId", conversationId,
                "generation", generation
        );
    }
}
