package biz.readmylegal.backend;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Transcript {

    @JsonProperty("audio_url")
    private String audio_url = "";

    @JsonProperty("id")
    private String id = ""; 

    @JsonProperty("status")
    private String status = "";

    @JsonProperty("text")
    private String text = "";
    

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    //setter and getter for audio
    public String getAudio_url() {
        return audio_url;
    }

    public void setAudio_url(String audio_url) {
        this.audio_url = audio_url;
    }

    //setter for ID
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
