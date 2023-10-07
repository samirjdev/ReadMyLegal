package biz.readmylegal.backend;

import com.theokanning.openai.completion.chat.*;
import com.theokanning.openai.service.OpenAiService;
import com.theokanning.openai.completion.CompletionRequest;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GPTTest {
    public static void test(String apiKey) {
        OpenAiService service = new OpenAiService(apiKey, Duration.ofSeconds(30));
        
        final List<ChatMessage> messages = new ArrayList<>();
        final ChatMessage systemMessage = new ChatMessage(ChatMessageRole.USER.value(), "What's 2+2?");
        messages.add(systemMessage);
        ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest
                .builder()
                .model("gpt-3.5-turbo")
                .messages(messages)
                .n(1)
                .maxTokens(10)
                .logitBias(new HashMap<>())
                .build();

        service.streamChatCompletion(chatCompletionRequest)
                .doOnError(Throwable::printStackTrace)
                .blockingForEach(GPTTest::printStuff);
        
        service.shutdownExecutor();
        
        System.out.println();
    }

    private static void printStuff(ChatCompletionChunk chunk) {
        if (chunk.getChoices().isEmpty() || chunk.getChoices().get(0).getMessage().getContent() == null)
            return;
        System.out.print(chunk.getChoices().get(0).getMessage().getContent());
    }
}
