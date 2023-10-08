package biz.readmylegal.backend;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Charsets;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;


public class HttpBackend {
    private HttpServer server;

    public HttpBackend(int port) throws IOException {
        System.out.println("checkpoint");
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
                System.out.println(request.getPassword());
                if (!request.getPassword().equals("legaleaglez")) {
                    System.out.println("OOPS");
                    exchange.sendResponseHeaders(405, -1);
                    return;
                }
                
                System.out.println("checkpoint2");
                //String responseText = "{\"data\": \"" + pigLatin(request.getBody()) + "\"}";
                String responseText = "{\"data\": \"" + "HELLO" + "\"}";
                exchange.sendResponseHeaders(200, responseText.getBytes().length);
                OutputStream output = exchange.getResponseBody();
                output.write(responseText.getBytes());
                output.flush();
                System.out.println("checkpoint3");
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

    public void stop() {
        server.stop(5);
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
