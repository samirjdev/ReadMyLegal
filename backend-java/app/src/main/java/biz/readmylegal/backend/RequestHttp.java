package biz.readmylegal.backend;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

import com.fasterxml.jackson.databind.ObjectMapper;

public class RequestHttp {

    public static void test2() throws Exception{

        Transcript transcript = new Transcript();
        transcript.setAudio_url("https://github.com/AssemblyAI-Examples/audio-examples/raw/main/20230607_me_canadian_wildfires.mp3");

    
        //convert transcript inot Json
        ObjectMapper objectmapper = new ObjectMapper();

        String json = objectmapper.writeValueAsString(transcript);
         System.out.println("I am here Right NOW ");
        //pring the Set audio Url
        System.out.println(json);


    //Poste Request
        HttpRequest postRequest = HttpRequest.newBuilder()
            .uri(new URI("https://api.assemblyai.com/v2/transcript"))
            .header("Authorization", "3dbe811b98354c81be4802a625657bc1")
            .POST(BodyPublishers.ofString("{\"audio_url\": \"https://github.com/AssemblyAI-Examples/audio-examples/raw/main/20230607_me_canadian_wildfires.mp3\"}"))
            .build(); 

        HttpClient httpClient = HttpClient.newHttpClient();
        
        HttpResponse<String> repostResponse = httpClient.send(postRequest,BodyHandlers.ofString());

        System.out.println("here is Jason Start "+ repostResponse.body());
       //geting Id
        transcript = objectmapper.readValue(repostResponse.body(), Transcript.class);

        System.out.println("Printing ID = "+transcript.getId());


    //get call.
    //covert jsyn into transcript object and get the id using transcrtip id. 
/* 
    //get request
    while(true){
        HttpRequest getRequest = HttpRequest.newBuilder()
            .uri(new URI("https://api.assemblyai.com/v2/transcript"))
            .header("Authorization", "3dbe811b98354c81be4802a625657bc1")
            .GET()
            .build();

        HttpResponse<String> getResponse = httpClient.send(getRequest,BodyHandlers.ofString());



     }
 
*/
        
        

    }
    
}
