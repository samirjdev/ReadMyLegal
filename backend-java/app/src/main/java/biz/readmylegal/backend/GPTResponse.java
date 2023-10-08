package biz.readmylegal.backend;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GPTResponse {
    @JsonProperty("response")
    private String response;

    public GPTResponse(String response) {
        this.response = response;
    }

    public String getResponse() {
        return response;
    }
}
