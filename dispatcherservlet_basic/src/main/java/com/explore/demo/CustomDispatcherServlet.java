package com.explore.demo;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.DispatcherServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
@Primary
public class CustomDispatcherServlet extends DispatcherServlet {

    @Override
    protected void doDispatch(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 在这里添加你自定义的逻辑
        System.out.println("自定义处理逻辑开始");

        // 调用父类的doDispatch方法，保持原有的处理方式
        super.doDispatch(request, response);
        // 在这里添加你自定义的逻辑
        System.out.println("自定义处理逻辑结束");
    }
}