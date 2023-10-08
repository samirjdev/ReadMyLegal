package biz.readmylegal.backend;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RMLRequest {
    @JsonProperty("body")
    private String body;

    @JsonProperty("password")
    private String password;

    // Can have the value "transcript" or "document"
    @JsonProperty("type")
    private String type;

    public String getBody() {
        return this.body;
    }

    public String getPassword() {
        return this.password;
    }

    public String getType() {
        return this.type;
    }
}
