package com.zwt.transmit;

import java.io.IOException;
import java.io.InputStream;

public class RequestParser {
    private final static int BUFFER_SIZE = 1024;

    // 解析请求
    public Request parse(InputStream inputStream){
        Request request = new Request();
        //读取请求信息
        String requestMessage = readRequestMessage(inputStream);
        //解析请求方式
        String type = parseType(requestMessage);
        request.setType(type);
        // 解析请求类型
        String uri = parseUri(requestMessage);
        request.setUri(uri);
        return request;
    }

    // 读取请求信息
    private String readRequestMessage(InputStream inputStream){
        StringBuffer requestMessage = new StringBuffer();
        int readLength = 0;
        byte[] buffer = new byte[BUFFER_SIZE];
        try{
            readLength = inputStream.read(buffer);
        } catch (IOException e) {
            e.printStackTrace();
            readLength = -1;
        }
        for (int i=0; i<readLength; i++){
            requestMessage.append((char) buffer[i]);
        }
        return requestMessage.toString();
    }

    // 解析请求方式
    private String parseType(String requestString){
        int index = 0;
        index = requestString.indexOf(' ');
        if(index != -1){
            return requestString.substring(0, index);
        }
        return null;
    }

    // 解析请求类型
    private String parseUri(String requestString){
        int index1, index2;
        index1 = requestString.indexOf(' ');
        if (index1 != -1){
            index2 = requestString.indexOf(' ', index1 + 1);
            if (index2 > index1){
                return requestString.substring(index1+1, index2);
            }
        }
        return null;
    }
}
