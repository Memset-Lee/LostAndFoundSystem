package org.example.aop;

import com.alibaba.fastjson.JSONObject;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.example.mapper.OperateLogMapper;
import org.example.pojo.OperateLog;
import org.example.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;

@Aspect
@Component
public class LogAspect {
    @Autowired
    private HttpServletRequest request;

    @Autowired
    private OperateLogMapper operateLogMapper;

    @Around("execution(* org.example.service.UserService.update(..)) || execution(* org.example.service.ItemService.post(..)) || execution(* org.example.service.ItemService.delete(..))")
    public Object recordLog(ProceedingJoinPoint joinPoint) throws Throwable {
        //获取操作人用户名
        String jwt = request.getHeader("token");
        Claims claims = JwtUtils.parseJwt(jwt);
        String operateUser = (String) claims.get("username");

        //获取操作时间
        LocalDateTime operateTime = LocalDateTime.now();

        //获取操作类名
        String className = joinPoint.getTarget().getClass().getName();

        //获取操作方法名
        String methodName = joinPoint.getSignature().getName();

        //获取操作方法参数
        Object[] args = joinPoint.getArgs();
        String methodParams = Arrays.toString(args);

        //记录原始目标方法开始时间
        long begin = System.currentTimeMillis();

        //调用原始目标方法
        Object result = joinPoint.proceed();

        //记录原始目标方法结束时间
        long end = System.currentTimeMillis();

        //获取原始目标方法返回值
        String returnValue = JSONObject.toJSONString(result);

        //获取操作耗时
        Long costTime = end - begin;

        //记录操作日志
        OperateLog operateLog = new OperateLog(operateUser, operateTime, className, methodName, methodParams, returnValue, costTime);
        operateLogMapper.insertLog(operateLog);

        //返回原始目标方法结果
        return result;
    }
}
