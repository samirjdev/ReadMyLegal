package biz.readmylegal.backend;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.file.Files;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AssemblyAIBackend {
    String token;

    public AssemblyAIBackend() throws IOException {
        token = Files.readString(new File("assemblyai-token.txt").toPath());
    }

    public String getTranscript(String url) {
        System.out.print("");
        Transcript transcript;
        ObjectMapper objectmapper = new ObjectMapper();

        // Poste Request
        HttpRequest postRequest;
        try {
            postRequest = HttpRequest.newBuilder()
                    .uri(new URI("https://api.assemblyai.com/v2/transcript"))
                    .header("Authorization", Files.readString(new File("assemblyai-token.txt").toPath()).trim())
                    .POST(BodyPublishers.ofString("{\"audio_url\": \"" + url + "\"}"))
                    .build();
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
            return "";
        }

        HttpClient httpClient = HttpClient.newHttpClient();
        HttpResponse<String> postResponse;
        try {
            postResponse = httpClient.send(postRequest,BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return "";
        }
        
        // geting Id
        try {
            transcript = objectmapper.readValue(postResponse.body(), Transcript.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "";
        }

        //get request
        while (true) {
            HttpRequest getRequest;
            try {
                getRequest = HttpRequest.newBuilder()
                    .uri(new URI("https://api.assemblyai.com/v2/transcript/" + transcript.getId() ))
                    .header("Authorization", Files.readString(new File("assemblyai-token.txt").toPath()).trim())
                    .GET()
                    .build();
            } catch (URISyntaxException | IOException e) {
                e.printStackTrace();
                return "";
            }

            HttpResponse<String> getResponse;
            try {
                getResponse = httpClient.send(getRequest,BodyHandlers.ofString());
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
                return "";
            }

            try {
                transcript = objectmapper.readValue(getResponse.body(), Transcript.class);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                return "";
            }

            System.out.println(transcript.getStatus());

            if("completed".equals(transcript.getStatus() )|| "error".equals(transcript.getStatus())){
                break;
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
        }

        return transcript.getText();
    }
}
