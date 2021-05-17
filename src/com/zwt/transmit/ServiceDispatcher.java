package com.zwt.transmit;

public class ServiceDispatcher {

    // 转发处理请求
    public void dispatcher(Request request, Response response){
        execController(request, response);
    }

    private void execController(Request request, Response response){
        String text = getControllerRequest(request, response);
        StringBuilder sb = new StringBuilder();
        sb.append("请求类型：" + request.getType());
        sb.append("<br/>请求URI：" + request.getUri());
        sb.append("<br/>返回结果" + text);
        response.writeText(sb.toString());
    }

    // 模拟查找和执行控制器方法并返回结果
    private String getControllerRequest(Request request, Response response){
        String text = "";
        String uri = request.getUri();
        String[] uriArray = uri.split("\\/");
        if(uriArray.length != 3){
            text = "请求路径没有找到相关匹配服务";
        }else if ("test".equalsIgnoreCase(uriArray[1])){
            TestController testController = new TestController();
            if ("test1".equalsIgnoreCase(uriArray[2])){
                text = testController.test1();
            }else if("test2".equalsIgnoreCase(uriArray[2])) {
                text = testController.test2();
            }else {
                text = "请求路径没有找到相关匹配服务1       ";
            }
        }else if("zwt".equalsIgnoreCase(uriArray[1])){
            if ("lyt".equalsIgnoreCase(uriArray[2])){

            }else {
                text = "请求路径没有找到相关匹配服务1       ";
            }
        }else {
            text = "请求路径没有找到相关匹配服务";
        }
        return text;
    }
}
