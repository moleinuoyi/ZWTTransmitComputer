package com.zwt.transmit.webservice;

import com.zwt.transmit.utils.Utils;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class WebService {
    // 服务器配置文件
    private static Map<String, String> configMap;
    // 服务端Socket
    private ServerSocket serverSocket;
    // 回调函数
    ConnectServiceCallback connectServiceCallback;

    public WebService(ConnectServiceCallback connectServiceCallback){
        this.connectServiceCallback = connectServiceCallback;
        init();
    }

    /**
     * 初始化tcp服务器
     */
    private void init(){
        // 读取配置文件
        try {
            System.out.println("正在启动服务器...");
            readConfigurationFile();
            String port = configMap.getOrDefault("server_poin", "8888");
            if (Utils.isNumeric(port))
                serverSocket = new ServerSocket(Integer.parseInt(port));
            else
                serverSocket = new ServerSocket(8888);
            System.out.println("服务器启动成功，等待客户端连接...");
            connectServiceCallback.connectSuccess();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 启动服务器监听处理客户请求
     * @throws IOException
     */
    public void start() throws IOException{
        while(true){
            // 侦听并接受客户请求
            Socket socket = serverSocket.accept();
            // 启动新线程，为客户提供服务
            new Thread(() -> {
                serviceClient(socket);
            }).start();
        }
    }

    /**
     * 为连接的客户端提供服务
     * @param socket
     */
    private void serviceClient(Socket socket){
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();
            // 读取请求信息内容
            Request request = new RequestParser().parse(inputStream);
            Response response = new Response(outputStream);
            serviceClient(request, response);

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (socket != null){
                try {
                    // 关闭与客户端的连接
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("接收到客户端连接, " + socket.getInetAddress() + ":" + socket.getPort());
    }

    /**
     * 处理客户端请求，将请求交给框架派遣服务
     * @param request 包含了socket InputStream 客户的请求
     * @param response 包含了socket OutputStream 返回客户的数据
     */
    private void serviceClient(Request request, Response response){
        ServiceDispatcher serviceDispatcher = new ServiceDispatcher();
        serviceDispatcher.dispatcher(request, response);
    }

//    public static void main(String[] args) {
//        WebService webService = new WebService(new ConnectServiceCallback() {
//            @Override
//            public void connectSuccess() {
//                System.out.println("回调启动成功");
//            }
//        });
//        try {
//            webService.start();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }

    private static void readConfigurationFile() throws IOException {
        String path = System.getProperty("user.dir");
//        File file = new File(path+"src/html/time/clock.html");
        File file = new File("web_server.conf");
        configMap = new HashMap<>();
        if (!file.exists()){
            System.out.println("配置文件不存在");
            return;
        }
        FileInputStream inputStream = new FileInputStream(file);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        String str = null;
        while((str = bufferedReader.readLine()) != null){
            System.out.println(str);
            String[] strs = str.split("=");
            if (strs.length == 2)
                configMap.put(strs[0].trim(), strs[1].trim());
        }
        //close
        inputStream.close();
        bufferedReader.close();
    }
    public interface ConnectServiceCallback{
        public void connectSuccess();
    }
}
