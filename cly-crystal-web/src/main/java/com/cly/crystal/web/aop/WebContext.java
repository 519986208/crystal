package com.cly.crystal.web.aop;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSON;

/**
 * web上下文环境
 */
public class WebContext {

    /**
     * 获取request
     */
    public static HttpServletRequest getRequest() {
        ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return attrs.getRequest();
    }

    /**
     * 获取session
     */
    public static HttpSession getSession() {
        HttpServletRequest request = getRequest();
        return request.getSession();
    }

    /**
     * 获取请求uri
     */
    public static String getRequestUri() {
        HttpServletRequest httpServletRequest = getRequest();
        String requestURI = httpServletRequest.getRequestURI();
        return requestURI;
    }

    /**
     * 获取请求路径
     */
    public static String getRequestPath() {
        HttpServletRequest httpServletRequest = getRequest();
        StringBuilder builder = new StringBuilder();
        String scheme = httpServletRequest.getScheme();
        String serverName = httpServletRequest.getServerName();
        int serverPort = httpServletRequest.getServerPort();
        String requestURI = httpServletRequest.getRequestURI();
        builder.append(scheme);
        builder.append("://");
        builder.append(serverName);
        builder.append(":");
        builder.append(serverPort);
        builder.append(requestURI);

        Enumeration<String> parameterNames = httpServletRequest.getParameterNames();
        boolean hasMoreElements = parameterNames.hasMoreElements();
        if (hasMoreElements) {
            builder.append("?");
        }
        while (parameterNames.hasMoreElements()) {
            String element = parameterNames.nextElement();
            builder.append(element);
            builder.append("=");
            builder.append(httpServletRequest.getParameter(element));
            builder.append("&");
        }
        String str = builder.toString();
        if (hasMoreElements) {
            str = str.substring(0, str.length() - 1);
        }
        return str;
    }

    /**
     * 往前端写Json数据
     */
    public static void WriteToPage(HttpServletResponse response, Object obj) {
        if (response.isCommitted()) {
            return;
        }
        PrintWriter writer = null;
        try {
            response.setCharacterEncoding("utf-8");
            response.setHeader("Access-Control-Allow-Origin", "*");
            writer = response.getWriter();
            writer.write(JSON.toJSONString(obj));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }
}
