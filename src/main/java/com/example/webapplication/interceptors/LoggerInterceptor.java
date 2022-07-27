package com.example.webapplication.interceptors;

import com.google.common.base.Stopwatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class LoggerInterceptor implements HandlerInterceptor {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoggerInterceptor.class);
    private final Stopwatch timer = Stopwatch.createUnstarted();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        timer.reset();
        timer.start();
        LOGGER.info("Request path = " + request.toString());
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        timer.stop();
        LOGGER.info("Response = " + response.toString());
        LOGGER.info("Work time = " + timer);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }
}
