package biz.readmylegal.backend;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandler;
import java.net.http.HttpResponse.BodyHandlers;
import com.google.code.gson;

import io.reactivex.exceptions.Exceptions;
import retrofit2.http.Body;
import retrofit2.http.PATCH;

public class RequestHttp {

    public static void test2() throws Exception{

        Transcript transcript = new Transcript();
        transcript.setAudio_url("https://github.com/AssemblyAI-Examples/audio-examples/raw/main/20230607_me_canadian_wildfires.mp3");
        Gson gson = new Gson();


        HttpRequest postRequest = HttpRequest.newBuilder()
            .uri(new URI("https://api.assemblyai.com/v2/transcript"))
            .header("Authorization", "3dbe811b98354c81be4802a625657bc1")
            .POST(BodyPublishers.ofString("{\"audio_url\": \"https://github.com/AssemblyAI-Examples/audio-examples/raw/main/20230607_me_canadian_wildfires.mp3\"}"))
            .build();

        HttpClient httpClient = HttpClient.newHttpClient();
        
       HttpResponse<String> repostResponse =  httpClient.send(postRequest,BodyHandlers.ofString());

        System.out.println(repostResponse.body());


        
        

    }
    
}
