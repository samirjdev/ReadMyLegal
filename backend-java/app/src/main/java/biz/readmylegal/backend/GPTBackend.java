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

    public GPTBackend(String apiKey) {
        this.service = new OpenAiService(apiKey, Duration.ofSeconds(30));
        this.currentResponse = new LinkedList<>();
    }

    final static String legalInstructions = 
            "Give a small, succinct, and basic analysis of the following legal document " +
            "in a such a way that someone inexperienced with legal terms can understand it. " + 
            "Do so in the following format: a section which shows the legal rights and " +
            "responsibilities of the person/party submitting the document in bulleted form, " + 
            "a section which shows the legal rights and responsibilities of the person/party " + 
            "who wrote, created, or provided the document to the aforementioned person/party " +
            "in bulleted form, and finally a small paragraph summary of other aspects of the " + 
            " document which are important or should otherwise be noted if the prior two " +
            "categories aren't sufficient. If the provided document or text has nothing to do" +
            "with any kind of legal agreement or notice, simply express that it's not a legal " +
            " document and don't attempt any further analysis.";

    // Awaits response from gpt-3.5-turbo based on given prompt
    // Blocks until the response is given
    public synchronized String promptAwaitResponse(String prompt) {
        System.out.println("Processing prompt.");

        final List<ChatMessage> messages = new ArrayList<>();
        final ChatMessage systemMessage = new ChatMessage(ChatMessageRole.SYSTEM.value(), legalInstructions);
        final ChatMessage userMessage = new ChatMessage(ChatMessageRole.USER.value(), prompt);
        messages.add(systemMessage);
        messages.add(userMessage);
        ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest
                .builder()
                .model("gpt-3.5-turbo")
                .messages(messages)
                .n(1)
                .maxTokens(512)
                .logitBias(new HashMap<>())
                .build();

        service.streamChatCompletion(chatCompletionRequest)
                .doOnError(Throwable::printStackTrace)
                .blockingForEach(this::addChunk);
        
        System.out.println("Finished processing prompt.");

        String response = currentResponseAsString();
        currentResponse.clear();
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
    }
}
