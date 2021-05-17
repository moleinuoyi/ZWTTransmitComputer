package com.zwt.transmit;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class Response {

    private OutputStream output;

    public Response(OutputStream outputStream){
        this.output = outputStream;
    }

    // 输出文本信息
    public void writeText(String text){
        FileInputStream fis = null;
        try {
            output.write("HTTP/1.1 200 OK\n".getBytes());
            output.write("Content-Type: text/html; charset=UTF-8\n\n".getBytes());
            output.write(text.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
