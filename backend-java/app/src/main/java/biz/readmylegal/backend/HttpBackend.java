package biz.readmylegal.backend;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Charsets;
import com.sun.net.httpserver.HttpServer;


public class HttpBackend {
    private HttpServer server;
    private GPTBackend gptBackend;

    public HttpBackend(int port) throws IOException {
        server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/prompt/json", (exchange -> {
            exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
            exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
            exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type");

            if ("OPTIONS".equals(exchange.getRequestMethod())) {
                exchange.sendResponseHeaders(204, -1); // Respond with a 204 No Content status
                return;
            }
            if ("POST".equals(exchange.getRequestMethod())) {
                // Get JSON data and extract given text {body:text,password:text}
                String requestBody = new String(exchange.getRequestBody().readAllBytes(), Charsets.UTF_8);
                ObjectMapper objMapper = new ObjectMapper();
                GPTRequest request = objMapper.readValue(requestBody, GPTRequest.class);
                if (!request.getPassword().equals("legaleaglez")) {
                    exchange.sendResponseHeaders(405, -1);
                    return;
                }
                System.out.println("Received prompt.");
                GPTResponse response = new GPTResponse(gptBackend.promptAwaitResponse(request.getBody()));
                String responseJson = objMapper.writeValueAsString(response);

                System.out.println("Sending GPT-3.5 response to " + 
                        exchange.getRemoteAddress().getHostString() + ":" + 
                        exchange.getRemoteAddress().getPort());
                exchange.sendResponseHeaders(200, responseJson.getBytes().length);
                OutputStream output = exchange.getResponseBody();
                output.write(responseJson.getBytes());
                output.flush();
                System.out.println("GPT-3.5 response successfully sent.");
            }

            else {
                exchange.sendResponseHeaders(405, -1);// 405 Method Not Allowed
            }
        }));
        server.setExecutor(null);
    }

    public void start() {
        server.start();
        gptBackend = new GPTBackend(tokenContents());
    }

    public void stop() {
        server.stop(5);
        gptBackend.stop();
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

    // Returns the contents of a token file in the home directory
    // Do not use this in production
    private static String tokenContents() {
        String path = System.getProperty("user.home") + "/openai-token.txt";
        File file = new File(path);
        if (!file.isFile())
            return "";
        
        FileInputStream inFile;

        try {
            inFile = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            return "";
        }

        String token;

        try {
            token = new String(inFile.readAllBytes(), Charsets.UTF_8).strip();
        } catch (IOException e) {
            try {
                inFile.close();
            } catch (IOException e1) {
                return "";
            }
            return "";
        }

        try {
            inFile.close();
        } catch (IOException e) {
            return "";
        }

        return token;
    }
}
