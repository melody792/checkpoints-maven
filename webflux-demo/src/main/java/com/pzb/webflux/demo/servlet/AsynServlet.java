package com.pzb.webflux.demo.servlet;

import lombok.SneakyThrows;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * @program: webflux-demo
 * @description:
 * @author: pzb
 * @create: 2020-03-16 10:33
 **/
@WebServlet(urlPatterns ="/asynServlet",asyncSupported = true)
public class AsynServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //开启异步
        AsyncContext asyncContext = request.startAsync();
        //当前开始时间
        long l = System.currentTimeMillis();
        //业务逻辑
        CompletableFuture.runAsync(()->doSomeThing(asyncContext,asyncContext.getRequest(),asyncContext.getResponse()));

        System.out.println("耗时："+(System.currentTimeMillis()-l));

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }

    @SneakyThrows
    private void doSomeThing(AsyncContext asyncContext, ServletRequest request, ServletResponse response)  {
        try { TimeUnit.SECONDS.sleep(5); } catch (InterruptedException e) { e.printStackTrace(); }

        response.getWriter().append("done");

        //告诉上下文已经结束
        asyncContext.complete();
    }
}
