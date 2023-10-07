package biz.readmylegal.backend;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import com.theokanning.openai.completion.chat.ChatCompletionChunk;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.completion.chat.ChatMessageRole;
import com.theokanning.openai.service.OpenAiService;

public class GPTBackend {
    private OpenAiService service;
    private List<String> currentResponse;
    private String lastChunkString;

    public GPTBackend(String apiKey) {
        this.service = new OpenAiService(apiKey, Duration.ofSeconds(30));
        this.currentResponse = new LinkedList<>();
        this.lastChunkString = "";
    }

    // Awaits response from gpt-3.5-turbo based on given prompt
    // Blocks until the response is given
    public String promptAwaitResponse(String prompt) {
        final List<ChatMessage> messages = new ArrayList<>();
        final ChatMessage systemMessage = new ChatMessage(ChatMessageRole.USER.value(), prompt);
        messages.add(systemMessage);
        ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest
                .builder()
                .model("gpt-3.5-turbo")
                .messages(messages)
                .n(1)
                .maxTokens(50)
                .logitBias(new HashMap<>())
                .build();

        service.streamChatCompletion(chatCompletionRequest)
                .doOnError(Throwable::printStackTrace)
                .blockingForEach(this::addChunk);
        
        String response = currentResponseAsString();
        currentResponse.clear();
        lastChunkString = "";
        return response;
    }

    public void stop() {
        service.shutdownExecutor();
    }

    private String currentResponseAsString() {
        StringBuilder response = new StringBuilder();
        for(String s : currentResponse) {
            response.append(s);
        }
        return response.toString();
    }

    private void addChunk(ChatCompletionChunk chunk) {
        if (chunk.getChoices().isEmpty() || chunk.getChoices().get(0).getMessage().getContent() == null)
            return;
        String chunkString = chunk.getChoices().get(0).getMessage().getContent();
        currentResponse.add(chunkString);
        lastChunkString = chunkString;
    }
}
