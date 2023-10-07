package biz.readmylegal.backend;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Charsets;
import com.sun.net.httpserver.HttpServer;

public class HttpBackend {
    private HttpServer server;

    public HttpBackend(int port) throws IOException {
        server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/prompt/json", (exchange -> {
            if ("POST".equals(exchange.getRequestMethod())) {
                // Get JSON data and extract given text {body:text,password:text}
                String requestBody = new String(exchange.getRequestBody().readAllBytes(), Charsets.UTF_8);
                ObjectMapper objMapper = new ObjectMapper();
                GPTRequest request = objMapper.readValue(requestBody, GPTRequest.class);
                
                if (!request.getPassword().equals("legaleaglez")) {
                    exchange.sendResponseHeaders(405, -1);
                    return;
                }

                String responseText = "{\"data\": \"" + pigLatin(request.getBody()) + "\"}";
                exchange.sendResponseHeaders(200, responseText.getBytes().length);
                OutputStream output = exchange.getResponseBody();
                output.write(responseText.getBytes());
                output.flush();
            }
            else {
                exchange.sendResponseHeaders(405, -1);// 405 Method Not Allowed
            }
        }));
        server.setExecutor(null);
    }

    public void start() {
        server.start();
    }

    /*
     * {body:"",password:""}
     */

    
    public static String pigLatin(String input) {
        if (input == null || input.isEmpty()) {
            return "";
        }

        String[] words = input.split("\\s+");
        StringBuilder pigLatinSentence = new StringBuilder();

        for (String word : words) {
            char firstChar = word.charAt(0);
            if (isVowel(firstChar)) {
                pigLatinSentence.append(word).append("way ");
            } else {
                pigLatinSentence.append(word.substring(1)).append(firstChar).append("ay ");
            }
        }

        // Remove the trailing space and return the Pig Latin sentence
        return pigLatinSentence.toString().trim();
    }

    private static boolean isVowel(char c) {
        c = Character.toLowerCase(c);
        return c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u';
    }
}