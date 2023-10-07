package biz.readmylegal.backend;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GPTRequest {
    @JsonProperty("body")
    private String body;

    @JsonProperty("password")
    private String password;

    public String getBody() {
        return this.body;
    }

    public String getPassword() {
        return this.password;
    }
}
