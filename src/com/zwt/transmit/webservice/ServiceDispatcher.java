package com.zwt.transmit.webservice;

import com.zwt.transmit.TestController;
import com.zwt.transmit.utils.Utils;
import com.zwt.transmit.webservice.Request;
import com.zwt.transmit.webservice.Response;

public class ServiceDispatcher {

    /**
     * 转发处理请求
     * @param request
     * @param response
     */
    public void dispatcher(Request request, Response response){
        execController(request, response);
    }

    private void execController(Request request, Response response){
        String text = getControllerRequest(request, response);

        response.writehtml(text);
    }

    // 模拟查找和执行控制器方法并返回结果
    private String getControllerRequest(Request request, Response response){
        String text = "";
        String uri = request.getUri();
        String[] uriArray = uri.split("\\/");
        System.out.println(uri);
        return uri;
    }
}
