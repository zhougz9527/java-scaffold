package com.base.admin.config;

import com.alibaba.fastjson.JSON;
import com.base.common.result.Result;
import com.base.common.result.ResultEnum;
import com.base.service.entity.User;
import com.base.service.services.RedisService;
import com.base.service.services.UserService;
import com.google.common.base.Splitter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.context.support.XmlWebApplicationContext;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.List;

import static org.apache.commons.lang3.CharEncoding.UTF_8;

public class LoginFilter implements Filter {

    @Resource
    private UserService userService;

    private List<String> excludedUrlList;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        String excludedUrls = filterConfig.getInitParameter("excludeUrls");
        excludedUrlList = Splitter.on(",").omitEmptyStrings().splitToList(excludedUrls);
        ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(filterConfig.getServletContext());
        if (null != context && null != context.getBean("userService") && null == userService) {
            userService = (UserService) context.getBean("userService");
        }
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

        // 设置允许跨域的配置
        httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
        // 允许的访问方法
        httpServletResponse.setHeader("Access-Control-Allow-Methods","POST, GET, PUT, OPTIONS, DELETE, PATCH");
        // Access-Control-Max-Age 用于 CORS 相关配置的缓存
        httpServletResponse.setHeader("Access-Control-Max-Age", "3600");
        httpServletResponse.setHeader("Access-Control-Allow-Headers","X-XXX-ADMIN-Token");

        httpServletResponse.setCharacterEncoding(UTF_8);
        httpServletResponse.setContentType("application/json; charset=utf-8");
        if (httpServletRequest.getMethod().equals("OPTIONS")) {
            httpServletResponse.setStatus(HttpServletResponse.SC_OK);
        } else {
            if (excludedUrlList.contains(httpServletRequest.getRequestURI())) {
                filterChain.doFilter(httpServletRequest, httpServletResponse);
                return;
            }
            String token = httpServletRequest.getHeader("xxx");
            User user = null;
//            if (StringUtils.isNotEmpty(token)) {
//                user = userService.getUserInfoByRedis(token);
//            }
            user = userService.singleTableQuery("金木");
            if (null != user) {
                httpServletRequest.setAttribute("user", user);
            } else {
                PrintWriter writer = null;
                OutputStreamWriter osw = null;
                try {
                    osw = new OutputStreamWriter(httpServletResponse.getOutputStream(), UTF_8);
                    writer = new PrintWriter(osw, true);
                    String jsonStr = JSON.toJSONString(new Result(ResultEnum.USER_NOT_LOGIN));
                    writer.write(jsonStr);
                    writer.flush();
                    writer.close();
                    osw.close();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (null != writer) {
                        writer.close();
                    }
                    if (null != osw) {
                        osw.close();
                    }
                }
                return;
            }
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        }

    }


    @Override
    public void destroy() {

    }

}
