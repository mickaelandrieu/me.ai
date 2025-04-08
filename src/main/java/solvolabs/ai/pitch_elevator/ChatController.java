package solvolabs.ai.pitch_elevator;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:8080")
public class ChatController {

    private final OpenAiChatModel chatModel;
    private static final Map<String, Conversation> conversationMap = new ConcurrentHashMap<>();

    @Autowired
    public ChatController(OpenAiChatModel chatModel) {
        this.chatModel = chatModel;
    }

    @PostMapping("/chat")
    public Map<String, String> generate(@RequestBody Map<String, String> body) {
        String conversationId = body.get("conversationId");
        String message = body.get("message");
        
        Conversation conversation;
        if (conversationId == null || !conversationMap.containsKey(conversationId)) {
            conversationId = UUID.randomUUID().toString();
            conversation = new Conversation(conversationId);
            conversationMap.put(conversationId, conversation);
        } else {
            conversation = conversationMap.get(conversationId);
        }
        
        conversation.addUserMessage(message);
        Prompt prompt = new Prompt(conversation.getMessages());
        
        ChatResponse response = this.chatModel.call(prompt);
        String generation = response.getResult().getOutput().getText();

        System.out.println(generation);

        conversation.addAssistantMessage(generation);

        return Map.of(
            "conversationId", conversationId,
            "generation", generation
        );
    }
}
