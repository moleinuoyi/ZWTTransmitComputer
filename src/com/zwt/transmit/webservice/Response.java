package com.zwt.transmit.webservice;

import java.io.*;
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
    public void writehtml(String path){
        BufferedInputStream bis = null;
        try {
            if (path.endsWith(".css")){
                output.write("HTTP/1.1 200 OK\n".getBytes());
                output.write("Content-Type: text/css; charset=UTF-8\n\n".getBytes());
                bis = new BufferedInputStream(new FileInputStream("src/html/time/clock.css"));
            }else if (path.endsWith(".png")){
                output.write("HTTP/1.1 200 OK\n".getBytes());
                output.write("Content-Type: image/apng; charset=UTF-8\n\n".getBytes());
                bis = new BufferedInputStream(new FileInputStream("src/html/time/clock.png"));
            }
            else{
                output.write("HTTP/1.1 200 OK\n".getBytes());
                output.write("Content-Type: text/html; charset=UTF-8\n\n".getBytes());
                bis = new BufferedInputStream(new FileInputStream("src/html/time/clock.html"));
            }
            byte[] b = new byte[20];
            int len;

            while ((len = bis.read(b)) != -1) {
                output.write(b, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }

}
