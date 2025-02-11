package org.example.filter;

import com.alibaba.fastjson.JSONObject;
import org.example.pojo.Result;
import org.example.utils.JwtUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@Component
@WebFilter(urlPatterns = "/*")
public class LoginCheckFilter implements Filter {
    @Override
    //在创建Filter时运行
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    //拦截到请求时运行
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        //获取请求url
        String url = req.getRequestURL().toString();

        //判断请求url中是否包含login或register，如果包含，说明是登录或注册操作，放行
        if (url.contains("login") || url.contains("register")) {
            chain.doFilter(request, response);
            return;
        }

        //获取请求头中的令牌token
        String jwt = req.getHeader("token");

        //判断令牌是否存在，如果不存在，返回错误结果
        if (!StringUtils.hasLength(jwt)) {
            Result error = Result.error("NOT_LOGIN");
            String notLogin = JSONObject.toJSONString(error);
            resp.getWriter().write(notLogin);
            return;
        }

        //解析token，如果解析失败，返回错误结果
        try {
            JwtUtils.parseJwt(jwt);
        } catch (Exception e) {
            e.printStackTrace();
            Result error = Result.error("NOT_LOGIN");
            String notLogin = JSONObject.toJSONString(error);
            resp.getWriter().write(notLogin);
            return;
        }

        //6.放行。
        chain.doFilter(request, response);
    }

    @Override
    //在销毁Filter时运行
    public void destroy() {
        Filter.super.destroy();
    }
}
