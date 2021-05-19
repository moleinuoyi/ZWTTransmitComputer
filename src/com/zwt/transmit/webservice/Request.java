package com.zwt.transmit.webservice;

public class Request {

    // 请求方式
    private String type;
    // 请求 Uri
    private String uri;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
