package com.zwt.transmit;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class SingleFileHTTPServer {

    //服务端启动端口
    private int port = 8888;

    // 服务端Socket
    private ServerSocket serverSocket;

    public SingleFileHTTPServer(){
        init();
    }

    //初始化Socket
    private void init(){
        try{
            serverSocket = new ServerSocket(port);
            System.out.println("服务器已启动，等待客户端连接...");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 启动服务器监听处理客户请求
    public void start() throws IOException{
        while(true){
            // 侦听并接受客户请求
            Socket socket = serverSocket.accept();
            // 启动新线程，为客户提供服务
            new Thread(() -> {
               service(socket);
            }).start();
        }
    }

    private void service(Socket socket){
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();
            // 读取请求信息内容
            Request request = new RequestParser().parse(inputStream);
            Response response = new Response(outputStream);
            service(request, response);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (socket != null){
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("接收到客户端连接, " + socket.getInetAddress() + ":" + socket.getPort());
    }

    // 处理客户端请求，将请求交给框架派遣服务
    private void service(Request request, Response response){
        ServiceDispatcher serviceDispatcher = new ServiceDispatcher();
        serviceDispatcher.dispatcher(request, response);
    }

    public static void main(String[] args) {
        try {
            new SingleFileHTTPServer().start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
