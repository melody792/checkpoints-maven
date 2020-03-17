package com.yaolong.webflux.demo.servlet;

import lombok.SneakyThrows;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @program: webflux-demo
 * @description:
 * @author: yaolong
 * @create: 2020-03-16 10:33
 **/
@WebServlet("/synServlet")
public class TestServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //当前开始时间
        long l = System.currentTimeMillis();
        //业务逻辑
        doSomeThing(request,response);

        System.out.println("耗时："+(System.currentTimeMillis()-l));

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }

    @SneakyThrows
    private void doSomeThing(HttpServletRequest request, HttpServletResponse response)  {
        try { TimeUnit.SECONDS.sleep(5); } catch (InterruptedException e) { e.printStackTrace(); }

        response.getWriter().append("done");
    }
}
