package biz.readmylegal.backend;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.file.Files;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Charsets;
import com.sun.net.httpserver.HttpServer;


public class HttpBackend {
    private HttpServer server;
    private GPTBackend gptBackend;
    private String password;
    private File tokenFile;

    public HttpBackend(int port, String password, File tokenFile) throws IOException {
        this.password = password;
        this.tokenFile = tokenFile;
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
                if (!request.getPassword().equals(this.password)) {
                    exchange.sendResponseHeaders(405, -1);
                    return;
                }
                System.out.println("Received prompt.");

                String responseString = gptBackend.promptAwaitResponse(request.getBody(), request.getType());
                GPTResponse response = new GPTResponse(responseString);
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

    public void start() throws IOException {
        server.start();
        gptBackend = new GPTBackend(Files.readString(this.tokenFile.toPath()).trim());
    }

    public void stop() {
        server.stop(5);
        gptBackend.stop();
    }
}
