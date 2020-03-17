package com.yaolong.webflux.demo.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @program: webflux-demo
 * @description: SSE
 * @author: yaolong
 * @create: 2020-03-16 17:00
 **/
@WebServlet("/SSEServlet")
public class SSEServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/event-stream");
        response.setCharacterEncoding("utf-8");

        for (int i = 0; i <10 ; i++) {
            response.getWriter().write("event:me\n");
            response.getWriter().write("data:"+i+"\n\n");
            response.getWriter().flush();
            try { TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) { e.printStackTrace(); }

        }

    }
}
