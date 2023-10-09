package biz.readmylegal.backend;

import java.io.File;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.file.Files;

import com.fasterxml.jackson.databind.ObjectMapper;

public class RequestHttp {

    public String myString;
    

    public static String test2() throws Exception{

        String myString;


        Transcript transcript = new Transcript();
        transcript.setAudio_url("https://drive.google.com/uc?export=download&id=13VKm5sF3kC5_99uyDPrY9VFSwuIwpBXV");

    
        //convert transcript inot Json
        ObjectMapper objectmapper = new ObjectMapper();
        //System.out.println("I am Here");   
        String json = objectmapper.writeValueAsString(transcript);
         //System.out.println("I am here Right NOW ");
        //pring the Set audio Url
        System.out.println();
        //System.out.println(json);
        System.out.println();


    //Poste Request
        HttpRequest postRequest = HttpRequest.newBuilder()
            .uri(new URI("https://api.assemblyai.com/v2/transcript"))
            .header("Authorization", Files.readString(new File("AssemblyAIToken.txt").toPath()).trim())
            .POST(BodyPublishers.ofString("{\"audio_url\": \"https://drive.google.com/uc?export=download&id=13VKm5sF3kC5_99uyDPrY9VFSwuIwpBXV\"}"))
            .build(); 

        HttpClient httpClient = HttpClient.newHttpClient();
        
        HttpResponse<String> repostResponse = httpClient.send(postRequest,BodyHandlers.ofString());

        System.out.println("here is Jason Start "+ repostResponse.body());
        
       //geting Id
        transcript = objectmapper.readValue(repostResponse.body(), Transcript.class);

        //priting test
        //System.out.println("Printing ID = "+ transcript.getId());


    //covert jsyn into transcript object and get the id using transcrtip id. 

    //get request
    while(true){
        HttpRequest getRequest = HttpRequest.newBuilder()
            .uri(new URI("https://api.assemblyai.com/v2/transcript/" + transcript.getId() ))
            .header("Authorization", "3dbe811b98354c81be4802a625657bc1")
            .GET()
            .build();

        HttpResponse<String> getResponse = httpClient.send(getRequest,BodyHandlers.ofString());

        transcript = objectmapper.readValue(getResponse.body(), Transcript.class);

        

        System.out.println(transcript.getStatus());

        if("completed".equals(transcript.getStatus() )|| "error".equals(transcript.getStatus())){
            break;
        }

        Thread.sleep(1000);
     }

     System.out.println("Transcription Completed ");
     //System.out.println(transcript.getText());

     myString = transcript.getText();

     return myString;
    }

    

    void ClassA (String myString) {
        this.myString = myString;
    }
    
}
