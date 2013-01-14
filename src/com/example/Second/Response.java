package com.example.Second;

public class Response {
    private final String http_status_code;
    private final String content;

    public Response(String http_status_code, String content) {
        this.http_status_code = http_status_code;
        this.content = content;
    }

    public String getHttp_status_code() {
        return http_status_code;
    }

    public String getContent() {
        return content;
    }
}
