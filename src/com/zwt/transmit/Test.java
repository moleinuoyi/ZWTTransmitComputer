package com.zwt.transmit;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.ba;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;
import com.zwt.transmit.webservice.WebService;


import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.math.BigInteger;


public class Test {
    private static WebService.ConnectServiceCallback connectServiceCallback = new WebService.ConnectServiceCallback() {
        @Override
        public void connectSuccess() {
            aaa();
        }
    };

    static {

        try {
            Field e = ba.class.getDeclaredField("e");
            e.setAccessible(true);
            Field f = ba.class.getDeclaredField("f");
            f.setAccessible(true);
            Field modifersField = Field.class.getDeclaredField("modifiers");
            modifersField.setAccessible(true);
            modifersField.setInt(e, e.getModifiers() & ~Modifier.FINAL);
            modifersField.setInt(f, f.getModifiers() & ~Modifier.FINAL);
            e.set(null, new BigInteger("1"));
            f.set(null, new BigInteger("1"));
            modifersField.setAccessible(false);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }
    public static void main(String[] args) throws IOException {
//        Browser browser = new Browser();
//        BrowserView view = new BrowserView(browser);
//
//        JFrame frame = new JFrame("JxBrowser - Hello World");
//        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
//        frame.add(view, BorderLayout.CENTER);
//        frame.setSize(500, 400);
//        frame.setLocationRelativeTo(null);
//        frame.setVisible(true);
//
//        browser.loadHTML("<html><body><h1>Hello World!</h1></body></html>");

//        final String url = "http://m.baidu.com/";

            WebService webService = new WebService(connectServiceCallback);
            try {
                webService.start();
            } catch (IOException e) {
                e.printStackTrace();
            }

//        aaa();
    }
    public static void aaa(){
        final String url = "http://127.0.0.1:8888/text";
//        final String url = "https://www.baidu.com";

        final String title = "百度";
        Browser browser = new Browser();
        BrowserView view = new BrowserView(browser);

        JFrame frame = new JFrame();
        //禁用close功能
//        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //不显示标题栏,最大化,最小化,退出按钮
//        frame.setUndecorated(true);
//        frame.setSize(400, 600);
        frame.setSize(600, 500);
        frame.add(view, BorderLayout.CENTER);
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        int x = (int)(toolkit.getScreenSize().getWidth()-frame.getWidth())/2;

        int y = (int)(toolkit.getScreenSize().getHeight()-frame.getHeight())/2;

        frame.setLocation(x, y);
//        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
//        frame.setLocationByPlatform(true);
        frame.setVisible(true);
        browser.loadURL(url);
    }

}
